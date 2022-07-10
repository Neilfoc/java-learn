package zookeeper.lock;

import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 每一个线程创建临时序列节点，并且watch前一个序列节点。这样只有最小的节点可以获取锁。并且释放锁时只会给后面一个节点发事件。
 */
public class TestLock {


    ZooKeeper zk;

    @Before
    public void conn() {
        zk = ZKUtils.getZK();
    }

    @After
    public void close() {
        try {
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void lock() {

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                LockWatchAndCallback lockWatchAndCallback = new LockWatchAndCallback();
                lockWatchAndCallback.setZk(zk);
                String threadName = Thread.currentThread().getName();
                lockWatchAndCallback.setThreadName(threadName);
                //1.抢锁
                lockWatchAndCallback.tryLock();
                //2.干活
                System.out.println(threadName + " 工作...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //3.释放锁
                lockWatchAndCallback.unLock();
            }).start();
        }
        while (true) {

        }


    }


}
