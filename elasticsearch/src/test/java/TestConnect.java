import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author 11105157
 * @Description
 * @Date 2021/4/14
 */
public class TestConnect {


    //创建 ES 客户端操作对象
    @Test
    public void testJavaClient() throws UnknownHostException {
        //创建连接
        TransportClient transportClient = new PreBuiltTransportClient(Settings.EMPTY);

        // Kibanan连接ES的端口为9200,Java 连接ES的方式为TCP,所以使用的端口为9300
        transportClient.addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"),9300));

        //ES相关操作


        //关闭连接
        transportClient.close();
    }

}
