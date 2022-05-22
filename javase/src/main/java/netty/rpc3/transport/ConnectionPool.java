package netty.rpc3.transport;

import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author neilfoc
 * @Description
 * @Date 2022/5/17
 */
public class ConnectionPool {
    NioSocketChannel[] clients;
    Object[] locks;

    ConnectionPool(int size) {
        clients = new NioSocketChannel[size];
        locks = new Object[size];
        for (int i = 0; i < locks.length; i++) {
            locks[i] = new Object();
        }
    }
}
