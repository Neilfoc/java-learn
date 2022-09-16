package zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author neilfoc
 * @Description 测试zk Curator客户端
 * @Date 2022/9/17
 */
public class CuratorTest {
    public static void main(String[] args) throws Exception {
        // 1.启动ZK
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(
                "192.168.1.4:2181,192.168.1.5:2181,192.168.1.6:2181,192.168.1.7:2181",
                retryPolicy);
        client.start();

        // 2.创建一个/myznode节点,
        client.create().forPath("/myZnode", "123".getBytes());

        // 查看节点
        byte[] bytes = client.getData().forPath("/myZnode");
        System.out.println(new String(bytes));

        // 3.关闭客户端
        client.close();
    }
}
