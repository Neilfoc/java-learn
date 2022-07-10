package zookeeper.config;

import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestConfig {


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
    public void getConf() {

        ConfigWatchAndCallback configWatchAndCallback = new ConfigWatchAndCallback();
        configWatchAndCallback.setZk(zk);
        MyConf myConf = new MyConf();
        configWatchAndCallback.setConf(myConf);

        configWatchAndCallback.aWait();// 阻塞等待/testLock/AppConf有没有数据
        // 什么时候会走到这
        //1，节点不存在，只有等到创建了才会走到这
        //2，节点存在，直接就走到这
        System.out.println("循环外await成功...");

        while (true) {

            if (myConf.getConf().equals("")) {
                System.out.println("conf diu le ......");
                configWatchAndCallback.aWait();
            } else {
                System.out.println(myConf.getConf());
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}
