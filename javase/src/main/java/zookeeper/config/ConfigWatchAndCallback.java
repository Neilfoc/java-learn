package zookeeper.config;

import lombok.Data;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

@Data
public class ConfigWatchAndCallback implements Watcher, AsyncCallback.StatCallback, AsyncCallback.DataCallback {

    ZooKeeper zk;
    MyConf conf;
    CountDownLatch countDownLatch = new CountDownLatch(1);


    public void aWait() {
        zk.exists("/AppConf", this, this, "ABC");//回调StatCallback
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {//DataCallback

        if (data != null) {
            String s = new String(data);
            conf.setConf(s);
            countDownLatch.countDown();
        }else{
            System.out.println("获取数据为空，不countDown");
        }
    }

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {//StatCallback
        if (stat != null) {//不为空表示节点存在
            zk.getData("/AppConf", this, this, "sdfs");//回调DataCallback
        }

    }

    @Override
    public void process(WatchedEvent event) {//Watcher

        //type:节点的类型
        switch (event.getType()) {
            case None:
            case NodeChildrenChanged:
                break;
            case NodeCreated:
            case NodeDataChanged:
                zk.getData("/AppConf", this, this, "sdfs");//回调DataCallback
                break;
            case NodeDeleted:
                //容忍性
                conf.setConf("");
                countDownLatch = new CountDownLatch(1);
                System.out.println(event.getPath() + " 节点被删除"); //【】只给/testLock/AppConf加了watcher，删除其他节点没有watch
                break;
        }

    }
}
