import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

/**
 * @author 11105157
 * @Description
 * @Date 2021/4/14
 */
public class TestIndexAndTypeMapping {

    TransportClient transportClient;

    @Before
    //创建客户端
    public void before() throws UnknownHostException {

        //创建连接
        Settings settings = Settings.builder().put("cluster.name", "neilfoc").build();
        transportClient = new PreBuiltTransportClient(settings);

        // Kibanan连接ES的端口为9200,Java 连接ES的方式为TCP,所以使用的端口为9300
        //一定要修改elasticsearch.yml中的network.host 127.0.0.1 否则报错：NoNodeAvailableException[None of the configured nodes are available: [{#transport#-1}{DDSywLD7SESraZF9cC007w}{127.0.0.1}{127.0.0.1:9300}]
        transportClient.addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
    }

    @After
    //关闭客户端
    public void after() {
        transportClient.close();
    }




    //创建索引、类型mapping
    @Test
    public void testCreateIndex() throws ExecutionException, InterruptedException {

        //创建索引：需要一个CreateIndexRequest 请求
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("te");
        String mappingJson = "{\"properties\":{\"name\":{\"type\":\"text\"},\"age\":{\"type\":\"integer\"},\"sex\":{\"type\":\"keyword\"},\"content\":{\"type\":\"text\"}}}";
        createIndexRequest.mapping("tt", mappingJson, XContentType.JSON);

        CreateIndexResponse response = transportClient.admin().indices().create(createIndexRequest).get();
        System.out.println(response.isAcknowledged());//查看创建结果
    }

    //删除索引
    @Test
    public void testDeleteIndex() throws ExecutionException, InterruptedException {

        //删除索引:需要一个DeleteIndexRequest请求
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("test1");
        AcknowledgedResponse response = transportClient.admin().indices().delete(deleteIndexRequest).get();
        System.out.println(response.isAcknowledged());
    }

    //创建document
    @Test
    public void testCreateDocument() {
        Tt tt = new Tt();
        tt.setName("neilfoc");
        tt.setAge(16);
        tt.setContent("hhhh");
        tt.setSex("male");
        String s = JSON.toJSONString(tt);
        //IndexResponse indexResponse = transportClient.prepareIndex("te", "tt").setSource(s, XContentType.JSON).get();
        Tt tt1 = new Tt("xx", 11, "male", "我是xx");
        IndexResponse indexResponse = transportClient.prepareIndex("te", "tt", "tt02").setSource(JSON.toJSONString(tt1), XContentType.JSON).get();
        System.out.println(indexResponse.status());
    }


    //更新文档
    @Test
    public void testUpdateDocument() {
        Tt tt = new Tt();
        tt.setName("小王八");
        UpdateResponse updateResponse = transportClient.prepareUpdate("te","tt","tt01").setDoc(JSON.toJSONString(tt),XContentType.JSON).get();
        System.out.println(updateResponse.status());
    }

    //删除文档
    @Test
    public void testDeleteDocument(){
        DeleteResponse deleteResponse = transportClient.prepareDelete("te", "tt", "EzeVz3gBH2Hh3mL332p8").get();
        System.out.println(deleteResponse.status());
    }

    //批量操作文档
    @Test
    public void testBulkDocument(){
        //1.添加文档
        IndexRequest indexRequest = new IndexRequest("te","tt","tt03");
        Tt tt1 = new Tt("寒王", 11, "male", "你滴寒王");
        indexRequest.source(JSON.toJSONString(tt1), XContentType.JSON);

        //2.更新文档
        UpdateRequest updateRequest = new UpdateRequest("te","tt","tt01");
        Tt tt2 = new Tt();
        tt2.setName("中国军人");
        updateRequest.doc(JSON.toJSONString(tt2), XContentType.JSON);

        //3.删除文档
        DeleteRequest deleteRequest = new DeleteRequest("te", "tt", "tt02");

        //将需要执行的操作加入构建的bulk中:add可以添加多个
        BulkResponse itemResponses = transportClient.prepareBulk()
                .add(indexRequest)
                .add(updateRequest)
                .add(deleteRequest)
                .get();

        //打印结果，ES批量操作不支持原子性：可以成功,可以失败
        BulkItemResponse[] items = itemResponses.getItems();
        for (BulkItemResponse b : items) {
            System.out.println(b.status());
        }
    }

}
