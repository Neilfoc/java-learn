package socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

/**
 * @author neilfoc
 * @Description nio
 * @Date 2022/3/11
 */
public class SocketNIO {

    public static void main(String[] args) throws IOException {
        LinkedList<SocketChannel> clients = new LinkedList<>();

        ServerSocketChannel ss = ServerSocketChannel.open();
        ss.bind(new InetSocketAddress(9090), 2);
        ss.configureBlocking(false);  //【非阻塞】
        // socket(PF_INET6,SOCK_STREAM, IPPROT0_IP) = 4  4是server
        // bind(4,{sa_family=AF_INET,sin6_port=htons(9090), inet_pton(AF_INET6, "::", &sin6_addr), sin6_flowinfo=0,sin6_scope_id=0},28) = 0  绑定server端口
        // listen(4,50) 监听
        // fcntl(4,F_SETFL, O_RDWR|O_NONBLOCK) = 0  在4上设置了非阻塞

        while (true) {
            SocketChannel client = ss.accept();
            // accept(4,..) = -1   -1表示没有获取到，但不阻塞
            if (client == null) {
                // do nothing
            } else {
                client.configureBlocking(false);
                int port = client.socket().getPort();
                System.out.println("client...port is " + port);
                clients.add(client);
            }

            ByteBuffer buffer = ByteBuffer.allocateDirect(4096);

            for (SocketChannel c : clients) {
                int num = c.read(buffer);
                if (num > 0) {
                    buffer.flip();
                    byte[] aaa = new byte[buffer.limit()];
                    buffer.get(aaa);

                    String b = new String(aaa);
                    System.out.println(c.socket().getPort() + " : " + b);
                    buffer.clear();
                }
            }
        }
    }
}
