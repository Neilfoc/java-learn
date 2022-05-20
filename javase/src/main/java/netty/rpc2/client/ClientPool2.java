package netty.rpc2.client;

import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author neilfoc
 * @Description
 * @Date 2022/5/17
 */
public class ClientPool2 {
    NioSocketChannel[] clients;
    Object[] locks;

    ClientPool2(int size) {
        clients = new NioSocketChannel[size];
        locks = new Object[size];
        for (int i = 0; i < locks.length; i++) {
            locks[i] = new Object();
        }
    }
}
