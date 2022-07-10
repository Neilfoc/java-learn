package zookeeper.lock;

import lombok.Data;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.CountDownLatch;

/**
 * 这个是client的session级别的watch
 *
 */
@Data
public class DefaultWatch implements Watcher {

    CountDownLatch countDownLatch;


    @Override
    public void process(WatchedEvent event) {

        System.out.println(event.toString());

        // session，客户端连接的状态
        switch (event.getState()) {
            case Unknown:
            case Disconnected:
            case NoSyncConnected:
            case AuthFailed:
            case ConnectedReadOnly:
            case SaslAuthenticated:
            case Expired:
                break;
            case SyncConnected:
                System.out.println("SyncConnected:Lock 客户端连接成功...");
                countDownLatch.countDown();
                break;
        }
    }
}
