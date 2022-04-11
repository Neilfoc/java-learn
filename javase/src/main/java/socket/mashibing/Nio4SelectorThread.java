package socket.mashibing;

import java.io.IOException;
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

/**
 * @author neilfoc
 * @Description
 * @Date 2022/4/12
 */

/**
 * step1 定义全局变量
 * Selector
 * bool isBoss //是否boss
 * AtomicInteger IncIndex 自增    static
 * int count： worker 个数     static
 * int workerId：第几个worker
 *
 * step2  构造 worker
 *
 * step3  构造 boss
 *
 * step4 thread
 * run 方法  01
 * while（true){while(selector.select()>0)  //当 selector 获取到了 accept  readable 事件  分发到  对应的handler 方法
 *
 * step5 thread run方法 02
 * 如果 queue[i]中有值 将 queue 中的值转换为 对应客户端 channel,将此channel 注册到对应的selector 上面
 *
 * step6 acceptHandler
 * 从 serverSocketChannel 中取 socketChannel ,放到对应  queue 中取  ，与 step5 相互呼应  ，step 5 中 完成 client 分配到 select
 *
 * 总结 step5  step6  , 在单线程中， acceptHandler 中 从 ServerSocketChannel 的取到 socketChannel 直接放到 selector 中去
 * 当前多线程中  boos  worker1  worker2 是 不同线程跑的  ，服务端的acceptHandler 取到 socketChannel 将其 放到 queue 中去
 * 客户端的线程 run方法 会 将 queue (static) 中的 socketChannel 取出 ， 放到对应的selector 中去
 * ------   注意此处的 queue 定义为 static 很重要，要充分理解，三个线程 boss worker1 worker2   主线程的 appcept 方法 将 queue 增加
 *          worker1/2 线程 run 方法 从 queue 中取出socketChannel 放到 其selector 中  ----------------------------------------------
 *
 * step7 readHandler
 * 同单线程的写法
 */
class Nio4SelectorThread extends Thread {

    private boolean isBoss = false;                                     // 是否是boss线程
    private Selector selector = null;                                     // 多路复用器 此值是在构造函数 时候传递过来的
    private static int workerCnt = 0;                                      // worker 线程的数量 boss 线程初始化的时候 传递过来的值  (是类层面的  static 如果不是static,在 worker线程初始化的时候娶不到）
    private static AtomicInteger indexClientConn = new AtomicInteger();  // 自增序列号 ，来一个连接增加1  这个需要是类层面的 所以要加static

    private static BlockingQueue<SocketChannel>[] queueWorker;          // LinkedBlockingQueue  用于存放 SocketChannel  在boss 线程实例化  需要是static 的  只在 boss线程实例化 其他线程使用

    private static AtomicInteger indexWorkerConn = new AtomicInteger();  // 自增序列号，用于实例化时候 分配 worker1  worker2 -- 类层面的 static
    private int workerIndex;                                           // worker 线程启动实例化的时候，根据  indexWorkerConn  workerCnt 数，得到当前线程的index


    // boss 构造函数
    public Nio4SelectorThread(Selector tmpSelector, int count) {
        this.isBoss = true;
        this.selector = tmpSelector;
        this.workerCnt = count;
        queueWorker = new LinkedBlockingQueue[this.workerCnt];
        for (int i = 0; i < count; i++) {
            queueWorker[i] = new LinkedBlockingQueue<>();
        }
        System.out.println("当前线程是 " + Thread.currentThread().getName() + " 初始化 bossSelect 线程 ");
    }

    // worker 构造函数
    public Nio4SelectorThread(Selector tmpSelector) {
        this.selector = tmpSelector;
        workerIndex = indexWorkerConn.getAndIncrement() % workerCnt;
        System.out.println("当前线程是 " + Thread.currentThread().getName() + " 初始化 workerSelect 线程 workerIndex is " + workerIndex);
    }

    @Override
    public void run() {
        //thread 线程 已经启动
        try {
            while (true) {
                //有注册事件发生    此处可能是  boss  worker1  worker2    表示 boss 有accept  或者  worker1/2 有readable 事件发送
                while (selector.select(6000) > 0) {
                    //System.out.println(" 当有客户端 连接到 服务器端  ，由于initServer中将 server 注册到了 selector , selector(server) 会得到client的accept 或者 read事件  ");
                    Set<SelectionKey> keySet = selector.selectedKeys();
                    Iterator<SelectionKey> iter = keySet.iterator();
                    if (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove();
                        System.out.println("");
                        if (key.isAcceptable()) {
                            acceptHandler(key);
                        } else if (key.isReadable()) {
                            readHandler(key);
                        } else {
                        }
                    }
                }

                System.out.println("当前线程是 " + Thread.currentThread().getName());
                try {
                    // 处理队列任务   boss 不参与
                    // 判断 当前线程队列 queue[0] / queue[1] (acceptHandler 中放入的内容 ) 有数据  &&  当前线程非 boss
                    // 将 queue 中的  SocketChannel 分配到 指定的 selector 上面

                    if (!isBoss && !queueWorker[workerIndex].isEmpty()) {
                        SocketChannel client = (SocketChannel) queueWorker[workerIndex].take();
                        ByteBuffer buffer = ByteBuffer.allocate(40000);
                        client.register(this.selector, SelectionKey.OP_READ, buffer);
                        System.out.println("-------------------------------------------");
                        System.out.println("将客户端读事件注册到 指定selector（实例化时候指定的1,2 ）上面 ；此客户端地址是" + client.getRemoteAddress());
                        System.out.println("-------------------------------------------");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //有客户端连接到serverSocketChannel   将连接放到queue上面
    private void acceptHandler(SelectionKey key) {
        try {
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            SocketChannel client = ssc.accept();
            client.configureBlocking(false);
            System.out.println(" 当有客户端连接到服务端（selector）");
            // 区别 单线程处 将 client 直接注册到 selector 上面 ，本示例将 client 放到 queueWorker[] 中去
            int num = indexClientConn.getAndIncrement() % workerCnt;
            queueWorker[num].add(client);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //接受客户端 读写事件
    private void readHandler(SelectionKey key) {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
        byteBuffer.clear();
        try {
            while (true) {
                int read = client.read(byteBuffer);
                if (read > 0) { //读到有数据写入 channel 将数据从 channel 读到 byteBuffer 中去
                    byteBuffer.flip();
                    client.write(byteBuffer);
                    byteBuffer.clear();
                    break;
                } else if (read == 0) {  //没有数据 退出循环
                    break;
                } else {     //客户端断开连接  -1
                    System.out.println("客户端断开连接");
                    client.close();
                    break;
                }
                //System.out.println(" 有客户端连接 ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
