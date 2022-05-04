package socket.zhouzhilei;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 多路复用服务端单线程V1，没用到写事件
 */
public class SocketMultiplexingSingleThreadV1 {

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
                System.out.println(keys.size() + "   size");

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
                            readHandler(key); // 【5】在当前线程，这个方法可能会阻塞耗时，所以需要这个方法足够快 从而提出IO threads
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void acceptHandler(SelectionKey key) {
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
            System.out.println("-------------------------------------------");
            System.out.println("新客户端：" + client.getRemoteAddress());
            System.out.println("-------------------------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readHandler(SelectionKey key) {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.clear();
        int read = 0;
        try {
            while (true) {
                read = client.read(buffer);
                if (read > 0) {
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        client.write(buffer);
                    }
                    buffer.clear();
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

    public static void main(String[] args) {
        SocketMultiplexingSingleThreadV1 service = new SocketMultiplexingSingleThreadV1();
        service.start();
    }
}
