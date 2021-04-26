package com.neilfoc.es_springboot.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * @author 11105157
 * @Description RestHighLevelClient 客户端配置
 * @Date 2021/4/15
 */
@Configuration
public class RestClientConfig extends AbstractElasticsearchConfiguration {

    //RestHighLevelClient 用来替换TransportClient
    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("127.0.0.1:9200")  //===>与kibana客户端类型都是restful分格,都是连接9200端口
                .build();
        return RestClients.create(clientConfiguration).rest();
    }

}
