package socket.zhouzhilei;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class SocketMultiplexingThreadsV1 {

    private ServerSocketChannel server = null;
    private Selector selector1 = null;
    private Selector selector2 = null;
    private Selector selector3 = null;
    int port = 9090;

    public void initServer() {
        try {
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));
            selector1 = Selector.open();
            selector2 = Selector.open();
            selector3 = Selector.open();
            server.register(selector1, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        /**
         * 主线程要做的事：（不做IO和业务的事）
         * 1.创建IO threads（也可以只搞一个线程）
         * 2.把监听9090的server注册到某一个selector上
         */
        SocketMultiplexingThreadsV1 service = new SocketMultiplexingThreadsV1();
        service.initServer();
        NioThread T1 = new NioThread(service.selector1, 2);
        NioThread T2 = new NioThread(service.selector2);
        NioThread T3 = new NioThread(service.selector3);

        T1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        T2.start();
        T3.start();

        System.out.println("服务器启动了。。。。。");

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

/**
 * 每一个线程都有一个多路复用器
 */
class NioThread extends Thread {
    Selector selector;
    static int workerCnt = 0;

    int id = 0;

    //加个队列，解决在多个线程中：selector.select()阻塞，selector.wakeup()可以是左边立即返回，但这里会出现要么右边执行不了，要么执行了之后注册accept事件之前左边又阻塞了
    volatile static BlockingQueue<SocketChannel>[] workerQueues;

    static AtomicInteger idx = new AtomicInteger();

    NioThread(Selector selector, int n) {
        this.selector = selector;
        this.workerCnt = n;

        workerQueues = new LinkedBlockingQueue[workerCnt];
        for (int i = 0; i < n; i++) {
            workerQueues[i] = new LinkedBlockingQueue<>();
        }
        System.out.println("Boss 启动");
    }

    NioThread(Selector selector) {
        this.selector = selector;
        id = idx.getAndIncrement() % workerCnt;
        System.out.println("worker: " + id + " 启动");

    }

    @Override
    public void run() {
        try {
            while (true) {

                // 1.执行select()看是否有就绪的key
                // 2.处理这些key
                while (selector.select(10) > 0) {
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iter = keys.iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove();
                        if (key.isAcceptable()) { //只有boss会走到这个if
                            acceptHandler(key);
                        } else if (key.isReadable()) {
                            readHandler(key);
                        }
                    }
                }
                //3.处理一些task，这步很重要（注册accept，read到对应的selector都可以在这执行）
                if (!workerQueues[id].isEmpty()) {
                    ByteBuffer buffer = ByteBuffer.allocate(8192);
                    SocketChannel client = workerQueues[id].take();
                    client.register(selector, SelectionKey.OP_READ, buffer);
                    System.out.println("-------------------------------------------");
                    System.out.println("新客户端：" + client.socket().getPort() + "分配到：" + (id));
                    System.out.println("-------------------------------------------");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void acceptHandler(SelectionKey key) {
        try {
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            SocketChannel client = ssc.accept();
            client.configureBlocking(false);
            int num = idx.getAndIncrement() % workerCnt;

            workerQueues[num].add(client);

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
                } else {// read<0 客户端断开了
                    //client.close();
                    key.cancel();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}



