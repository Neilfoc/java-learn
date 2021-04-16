package com.neilfoc.es_springboot;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @author 11105157
 * @Description
 * @Date 2021/4/15
 */
@SpringBootTest
public class TestRestClient {
    @Autowired
    private RestHighLevelClient restHighLevelClient;//用于复杂查询

    //操作document
    //【】必须使用org.junit.jupiter.api.Test，使用org.junit.Test会报nullpointer
    @Test
    public void test() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("te", "tt", "tt01");
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(deleteResponse.status());
    }
}
