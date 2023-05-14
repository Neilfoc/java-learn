package thread;

import java.util.concurrent.locks.LockSupport;

/**
 * park unpark 参考 https://blog.csdn.net/anlian523/article/details/106752414
 * 底层涉及两个变量：整型permit 和 布尔interrupt状态
 */
public class LockSupportDemo {

    public static Object u = new Object();
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (u) {
                System.out.println(getName() + " start");
                LockSupport.park();
                System.out.println(getName() + " continue");
                /*if (Thread.currentThread().isInterrupted()) {
                    System.out.println(getName() + " 被中断了");
                }*/
                System.out.println(getName() + " 继续执行");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        t2.start();
        Thread.sleep(1000L);
        Thread.sleep(3000L);
        t1.interrupt();
        // LockSupport.unpark(t2);
        t1.join();
        t2.join();
    }
}