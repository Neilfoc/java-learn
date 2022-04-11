package socket.mashibing;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * @author neilfoc
 * @Description https://www.bilibili.com/video/BV1JT4y1u71v?p=4
 * 多线程 版本的select 分为四个线程 处理 事务 1. 主线程 2. boss线程负责建立连接 worker1线程 worker2线程
 * 主线程 定义 ServerSocketChannel SelectorBoss SelectorWorker1 SelectorWorker2 将ServerSocketChannel 定义到 SelectorBoss 上面
 * @Date 2022/4/12
 */
public class MultiSelector {

    //step1  定义全局变量  serverSocketChannel 一个 ； selector 三个  ； 端口 一个
    private ServerSocketChannel server = null;
    private Selector selectorBoss = null;
    private Selector selectorWork1 = null;
    private Selector selectorWork2 = null;
    private int port = 8094;

    // step2  initServer 方法
    // 初始化全局变量 ServerSocketChannel (一个)
    // Selector（三个）
    // 将ServerSocketChannel 注册到第一个selector上面

    private void initServer() {
        try {
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));

            selectorBoss = Selector.open();
            selectorWork1 = Selector.open();
            selectorWork2 = Selector.open();

            server.register(selectorBoss, SelectionKey.OP_ACCEPT);
            System.out.println("---initServer()---- mainThread initServer is starting ... ");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //实例化 boss  worker1  worker2 线程 并启动 运行
    public static void main(String[] args) {
        MultiSelector mainThread = new MultiSelector();
        mainThread.initServer();

        new Nio4SelectorThread(mainThread.selectorBoss, 2).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Nio4SelectorThread(mainThread.selectorWork1).start();
        new Nio4SelectorThread(mainThread.selectorWork2).start();
        System.out.println("---main()----服务器启动了 " + "当前线程是 " + Thread.currentThread().getName());
    }

}






