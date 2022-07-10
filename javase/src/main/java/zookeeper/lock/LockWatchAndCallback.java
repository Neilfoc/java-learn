package zookeeper.lock;

import lombok.Data;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Data
public class LockWatchAndCallback implements Watcher, AsyncCallback.StringCallback, AsyncCallback.Children2Callback, AsyncCallback.StatCallback {

    ZooKeeper zk;
    String threadName;
    CountDownLatch countDownLatch = new CountDownLatch(1);
    String pathName;


    public void tryLock() {
        try {
            //System.out.println(threadName + "  线程创建");
            zk.create("/lock", threadName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, this, "abc");//回调StringCallback

            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void unLock() {
        try {
            zk.delete(pathName, -1);
            System.out.println(threadName + " 结束工作....");
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void process(WatchedEvent event) {//watcher
        //如果第一个哥们，那个锁释放了，其实只有第二个收到了回调事件！！
        //如果，不是第一个哥们，某一个，挂了，也能造成他后边的收到这个通知，从而让他后边那个跟去watch挂掉这个哥们前边的。。。
        switch (event.getType()) {
            case None:
            case NodeDataChanged:
            case NodeChildrenChanged:
            case NodeCreated:
                break;
            case NodeDeleted:
                System.out.println(event.getPath() + " 节点被删除...." + " 我是 " + threadName);
                zk.getChildren("/", false, this, "sdf");//回调Children2Callback
                break;
        }
    }

    @Override
    public void processResult(int rc, String path, Object ctx, String name) {//StringCallback
        if (name != null) {
            System.out.println(threadName + "  create node : " + name);
            pathName = name;
            zk.getChildren("/", false, this, "sdf");//回调Children2Callback
        }

    }

    @Override
    public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {//Children2Callback
        //一定能看到自己前边的。。
        /*System.out.println(threadName+" look locks.....");
        for (String child : children) {
            System.out.println(child);
        }*/

        Collections.sort(children);//拿到的children是乱序的，所以需要排序
        int i = children.indexOf(pathName.substring(1));//去掉pathName开头的斜线/

        //是不是第一个
        if (i == 0) {
            System.out.println(threadName + " first....");
            try {
                zk.setData("/", threadName.getBytes(), -1);
                countDownLatch.countDown();
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(threadName + " not first....");
            zk.exists("/" + children.get(i - 1), this, this, "sdf");//【watch监控自己前面的节点！！！】。并且回调StatCallback
        }

    }

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {//StatCallback
        //偷懒
    }
}
