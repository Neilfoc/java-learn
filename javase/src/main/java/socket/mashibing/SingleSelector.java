package socket.mashibing;

/**
 * @author neilfoc
 * @Description 多路复用-单线程版：单selector
 * @Date 2022/4/12
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SingleSelector {

    public static void main(String[] args) throws IOException {
        SingleSelector select01single = new SingleSelector();
        select01single.start();
    }

    //step 01  初始化全局变量  select  server  port
    private ServerSocketChannel server = null;
    private Selector selector = null;
    private int port = 8092;

    //step 02  initServer  实例化 server  selector  并将server注册到select的op_accept上面
    private void initServer() throws IOException {
        server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(port));
        server.configureBlocking(false);

        selector = Selector.open(); // 1.这里如果使用的时epoll的情况下，其实就是相当于执行epoll_create -> fd3，在内核中开辟了空间

        /**
         * 相当于服务端socket.accept -> fd4
         * select,poll jvm里面开辟了数组 将listen的fd4放在【Java进程】里面
         * epoll epoll_ctl(fd3,add,fd4,epolling) 将listen的fd4传递到刚刚开辟的【内核空间fd3】中去
         * 2.此时server相当于listen的状态
         */
        server.register(selector, SelectionKey.OP_ACCEPT);//服务端的accept注册到selector
    }

    // step 03  start
    // while (true)  {  while(select.select(0）>0) {  //当有02步骤的服务端注册到select  ,
    // 取出  所有的 selectionKeys
    private void start() throws IOException {
        initServer();
        System.out.println("端口号为：" + port + " ，服务正在启动中... ");
        while (true) {
            /**
             * select  poll :相当于执行内核的select(fd4) poll(fd4)
             * epoll 执行epoll_wait()
             */
            while (selector.select(500) > 0) {
                Set<SelectionKey> keySets = selector.selectedKeys(); //返回就绪的fd集合
                Iterator<SelectionKey> iter = keySets.iterator();

                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    iter.remove();
                    if (key.isAcceptable()) {  //如果select 是服务端触发的accept
                        acceptHandler(key);
                    } else if (key.isReadable()) { //如果 select 是客户端触发的 readable
                        readHandler(key);
                    } else if (key.isWritable()) {

                    }
                }
            }
        }
    }


    // 04 acceptable
    // 当服务端有客户端连接 即是有 accetable 事件 ， 注册 客户端的事件 到 select (顺带注册一个byteBuffer空间)
    // 通过   SelectionKey 获取 serverSocketChannel
    // 配置此serverSocketChannel的客户端socketChannel为非阻塞状态
    // 将socketChannel注册为 select 的 readable 并附赠 byteBuffer
    private void acceptHandler(SelectionKey key) throws IOException {
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        SocketChannel client = ssc.accept(); // accept系统调用得到客户端描述符fd7
        client.configureBlocking(false);
        ByteBuffer byteBuffer = ByteBuffer.allocate(20000);//注册此处在堆分配内存，不是栈
        /**
         * select,poll jvm里面开辟了数组 将fd7放在【Java进程】里面
         * epoll epoll_ctl(fd3,add,fd7,epollin 将fd7传递到刚刚开辟的【内核空间fd3】中去
         */
        client.register(selector, SelectionKey.OP_READ, byteBuffer);
        System.out.println("----------------------------------");
        System.out.println("新客户端" + client.getRemoteAddress());
        System.out.println("----------------------------------");
    }


    // 05 处理客户端readable事件
    private void readHandler(SelectionKey key) {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
        byteBuffer.clear();
        int count = 0; //socketChannel中获取到的个数

        try {
            while (true) {
                count = client.read(byteBuffer);
                if (count > 0) {  // 如果能够读到 客户端 的值  ，将此值 会写给客户端
                    byteBuffer.flip();
                    while (byteBuffer.hasRemaining()) {
                        client.write(byteBuffer);
                    }
                    byteBuffer.clear();      //打扫卫生
                } else if (count == 0) {         //如果读不到值 ，停止当前循环
                    break;
                } else {                       //如果是-1 也就是客户端不连接了 , 关闭当前客户端
                    System.out.println(" 客户端断开连接 " + client.getRemoteAddress());
                    client.close();
                    break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}


