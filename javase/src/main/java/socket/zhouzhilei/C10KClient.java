package socket.zhouzhilei;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

/**
 * @author neilfoc
 * @Description 用来做客户端连接的压测 BIO(SocketBIO) NIO(SocketNIO)
 * @Date 2022/5/2
 */
public class C10KClient {

    public static void main(String[] args) {
        LinkedList<SocketChannel> clients = new LinkedList<>();
        InetSocketAddress serverAddr = new InetSocketAddress("192.168.150.11", 9090);// 虚拟机上启动服务器 150.11

        for (int i = 10000; i < 65000; i++) {
            try {

                SocketChannel client1 = SocketChannel.open();
                SocketChannel client2 = SocketChannel.open();
                client1.bind(new InetSocketAddress("192.168.150.1", i)); //使用VMvare后会有VMnet8地址 150.1
                client1.connect(serverAddr);
                boolean c1 = client1.isOpen();
                clients.add(client1);
                client2.bind(new InetSocketAddress("192.168.110.100", i));// win自己的地址 110.100
                //注意：程序跑起来的时候应该每次都是两个相同的端口，比如10000 10000，但是实际只有1个，原因就是win自己地址路由有问题，三次握手丢弃了。
                //在Linux机子上看路由宝 route -n 可以看到没有指向110.100的，所以就会走默认路由，我们需要加上一条路由 route add -host 110.100 gw 150.1
                client2.connect(serverAddr);
                boolean c2 = client2.isOpen();
                clients.add(client2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
