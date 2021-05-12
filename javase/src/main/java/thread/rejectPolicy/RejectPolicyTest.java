package thread.rejectPolicy;

import org.junit.Test;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 11105157
 * @Description
 * @Date 2021/5/10
 */
public class RejectPolicyTest {


    // AbortPolicy策略 报完异常后丢弃
    @Test
    public void testAbortPolicy() {
        // 核心线程：1    等待队列：3   最大线程：2
        // 核心线程满了，先放等待队列，队列满了再用最大线程
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 0,
                TimeUnit.MICROSECONDS,
                new LinkedBlockingDeque<>(3),
                new ThreadPoolExecutor.AbortPolicy());

        for (int i = 1; i <= 6; i++) {
            System.out.println("添加第" + i + "个任务");
            try {

                executor.execute(new MyThread("线程" + i));
            } catch (Exception e) {
                System.out.println("抛拒绝异常了："+e.getMessage());
            }
            Iterator iterator = executor.getQueue().iterator();
            while (iterator.hasNext()) {
                MyThread thread = (MyThread) iterator.next();
                System.out.println("等待===列表：" + thread.name);
            }
            System.out.println("==========================================");
        }
    }


    // CallerRunsPolicy 调用当前线程池的所在的线程去执行被拒绝的任务
    @Test
    public void testCallerRunsPolicy(){
        // 核心线程：1    等待队列：2   最大线程：2
        // 核心线程满了，先放等待队列，队列满了再用最大线程
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 0,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(2),
                new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 1; i <= 6; i++) {
            System.out.println("添加第" + i + "个任务");
            executor.execute(new MyThread("线程" + i));
            Iterator iterator = executor.getQueue().iterator();
            while (iterator.hasNext()) {
                MyThread thread = (MyThread) iterator.next();
                System.out.println("等待===列表：" + thread.name);
            }
            System.out.println("==========================================");
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // DiscardPolicy 直接丢弃
    @Test
    public void testDiscardPolicy(){
        // 核心线程：1    等待队列：3   最大线程：2
        // 核心线程满了，先放等待队列，队列满了再用最大线程
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 0,
                TimeUnit.MICROSECONDS,
                new LinkedBlockingDeque<>(3),
                new ThreadPoolExecutor.DiscardPolicy());

        for (int i = 1; i <= 6; i++) {
            System.out.println("添加第" + i + "个任务");
            executor.execute(new MyThread("线程" + i));
            Iterator iterator = executor.getQueue().iterator();
            while (iterator.hasNext()) {
                MyThread thread = (MyThread) iterator.next();
                System.out.println("等待===列表：" + thread.name);
            }
            System.out.println("==========================================");
        }
    }

    // DiscardOldestPolicy 丢弃队列中最老的，把新的加进去（这是什么垃圾规则，人家已经排了那么久）
    @Test
    public void testDiscardOldestPolicy(){
        // 核心线程：1    等待队列：3   最大线程：2
        // 核心线程满了，先放等待队列，队列满了再用最大线程
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 0,
                TimeUnit.MICROSECONDS,
                new LinkedBlockingDeque<>(3),
                new ThreadPoolExecutor.DiscardOldestPolicy());

        for (int i = 1; i <= 6; i++) {
            System.out.println("添加第" + i + "个任务");
            executor.execute(new MyThread("线程" + i));
            Iterator iterator = executor.getQueue().iterator();
            while (iterator.hasNext()) {
                MyThread thread = (MyThread) iterator.next();
                System.out.println("等待===列表：" + thread.name);
            }
            System.out.println("==========================================");
        }
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    // 自定义拒绝策略
    @Test
    public void testMyRejectPolicy(){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 0,
                TimeUnit.MICROSECONDS,
                new LinkedBlockingDeque<>(3),
                new MyRejectedExecutionHandler());
        for (int i = 1; i <= 6; i++) {
            System.out.println("添加第" + i + "个任务");
            executor.execute(new MyThread("线程" + i));
            Iterator iterator = executor.getQueue().iterator();
            while (iterator.hasNext()) {
                MyThread thread = (MyThread) iterator.next();
                System.out.println("等待===列表：" + thread.name);
            }
            System.out.println("==========================================");
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

