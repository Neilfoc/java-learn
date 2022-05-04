package socket.zhouzhilei;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 多路复用服务端单线程V2，用到写事件
 */
public class SocketMultiplexingSingleThreadV2 {

    private ServerSocketChannel server = null;
    private Selector selector = null;  //  select  poll  *epoll， selector就是多路复用器
    int port = 9090;

    public void initServer() {
        try {
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));

            // 【1】在epoll模式下，这里会进行epoll_create开辟空间 -->fd3
            selector = Selector.open();  // 优先选择epoll 但是可以通过-Djava.nio.channels.spi.SelectorProvider调整
            // server 约等于 listen状态的FD -->fd4
            /*
             * 【2】
             * select,poll： jvm里面开辟了数组 将listen的fd放在【Java进程】里面
             * epoll： epoll_ctl(fd3,add,fd4,EPOLLIN 将listen的fd传递到刚刚开辟的【内核空间fd3】中去
             */
            server.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        initServer();
        System.out.println("服务器启动了。。。。。");
        try {
            while (true) {
                Set<SelectionKey> keys = selector.keys();
                System.out.println("keys size = " + keys.size());

                //调用多路复用器,不传时间就会阻塞
                /*
                 * 【3】
                 * select,poll :相当于执行内核的select(fd4) poll(fd4)
                 * epoll: 执行epoll_wait()
                 */
                while (selector.select(500) > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();  //返回有状态的fd集合
                    Iterator<SelectionKey> iter = selectionKeys.iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove(); //需要移除，不然会重复处理
                        if (key.isAcceptable()) {
                            acceptHandler(key);// 【4】这里accept接收了新的连接，会返回新连接对应的FD --> fd5
                        } else if (key.isReadable()) {
                            readHandler(key); // 只处理read 并注册关心这个key的写事件
                        } else if (key.isWritable()) {// 只要send-Q是空的，就一定会满足写事件（因为此时send-Q肯定还有空间），所以这个不能一开始就注册，要等到有数据了再注册
                            writeHandler(key);
                        }
                    }
                    System.out.println("一次selector循环结束...");
                    System.out.println("---------------------------");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void acceptHandler(SelectionKey key) {
        System.out.println("...accept handler...");
        try {
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            SocketChannel client = ssc.accept();
            client.configureBlocking(false);
            ByteBuffer buffer = ByteBuffer.allocate(8192);

            /*
             * 【4】
             * select,epoll: 这两个没有开辟内核空间，会保存在jvm中开辟的数组，和前面的fd5在一起
             * epoll： epoll_ctl(fd3,add,fd5,EPOLLIN
             */
            client.register(selector, SelectionKey.OP_READ, buffer);
            System.out.println("新客户端：" + client.getRemoteAddress());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readHandler(SelectionKey key) {
        System.out.println("...read handler...");
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.clear();
        int read = 0;
        try {
            while (true) {
                read = client.read(buffer);
                System.out.println(Thread.currentThread().getName() + " " + read);
                if (read > 0) {
                    client.register(key.selector(), SelectionKey.OP_WRITE, buffer);// 关心写事件：其实是关心send-Queue是不是有空间
                } else if (read == 0) {
                    break;
                } else {
                    client.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeHandler(SelectionKey key) {
        System.out.println("...write handler...");
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.flip();
        while (buffer.hasRemaining()) {
            try {
                client.write(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        buffer.clear();
        key.cancel();
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SocketMultiplexingSingleThreadV2 service = new SocketMultiplexingSingleThreadV2();
        service.start();
    }
}
