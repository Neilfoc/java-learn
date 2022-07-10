package zookeeper.config;

import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

public class ZKUtils {

    private static ZooKeeper zk;

    private static String address = "192.168.1.4:2181,192.168.1.5:2181,192.168.1.6:2181,192.168.1.7:2181/testLock";

    private static DefaultWatch defaultWatch = new DefaultWatch();

    private static CountDownLatch countDownLatch = new CountDownLatch(1);//connected:连接成功后减掉

    public static ZooKeeper getZK() {

        try {
            zk = new ZooKeeper(address, 1000, defaultWatch);
            defaultWatch.setCountDownLatch(countDownLatch);
            countDownLatch.await();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return zk;
    }


}
