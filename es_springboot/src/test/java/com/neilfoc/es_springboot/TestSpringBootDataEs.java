package com.neilfoc.es_springboot;

import com.neilfoc.es_springboot.elasticsearch.dao.TtRepository;
import com.neilfoc.es_springboot.model.Tt;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * @author 11105157
 * @Description
 * @Date 2021/4/17
 */
@SpringBootTest
//@RunWith(SpringRunner.class)
public class TestSpringBootDataEs {
    @Autowired
    private TtRepository ttRepository;

    @Autowired
    private RestHighLevelClient restHighLevelClient;


    /**
     * 添加索引和更新索引 id 存在更新 不存在添加
     */
    @Test
    public void testSaveOrUpdate(){
        Tt tt = new Tt();
        tt.setId("tt01");
        tt.setName("寒王");
        tt.setAge(25);
        tt.setContent("你滴寒王");
        tt.setSex("male");
        ttRepository.save(tt);
    }


    @Test
    public void testDelete(){
        Tt tt = new Tt();
        tt.setId("tt04");
        ttRepository.delete(tt);
    }

    /**
     *  查询一个
     */
    @Test
    public void testFindOne(){
        Optional<Tt> byId = ttRepository.findById("tt04");
        System.out.println(byId.get());
    }

    /**
     *  查询所有
     */
    @Test
    public void testFindAll(){
        Iterable<Tt> tts = ttRepository.findAll();
        for (Tt tt : tts) {
            System.out.println(tt);
        }
    }

    //查询并排序
    @Test
    public void testFindAllOrder(){
        Iterable<Tt> tts = ttRepository.findAll(Sort.by(Sort.Order.asc("age")));
        tts.forEach(tt -> System.out.println(tt));
    }

    //自定义ttRepository的方法
    @Test
    public void testFindByName(){
        List<Tt> tts = ttRepository.findByName("寒王");
        tts.forEach(tt -> System.out.println(tt));
    }

    //分页查询
    @Test
    public void testSearchPage() throws IOException {
        //查询请求
        SearchRequest searchRequest = new SearchRequest();

        //查询条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(0).size(2).sort("age", SortOrder.ASC).query(QueryBuilders.matchAllQuery());

        //去哪个索引/类型查询
        searchRequest.indices("te").types("tt").source(sourceBuilder);

        //====>查询方法
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        SearchHit[] hits = search.getHits().getHits();
        for (SearchHit hit : hits) {
            //字符串格式展示
            System.out.println(hit.getSourceAsString());
        }
    }

    //高亮查询
    @Test
    public void testLight() throws IOException {

        //集合存放查找到的数据
        List<Tt> list = new ArrayList<>();

        //查询请求
        SearchRequest searchRequest = new SearchRequest();

        //查询条件[对象]
        SearchSourceBuilder builder = new SearchSourceBuilder();

        //高亮配置
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("*").requireFieldMatch(false).preTags("<span style='color:red'>").postTags("</span>");

        //具体按...查询
        builder.from(0).size(2)
                .sort("age", SortOrder.DESC)
                .highlighter(highlightBuilder)
                .query(QueryBuilders.multiMatchQuery("王", "name", "content"));

        //从哪个索引/类型查找
        searchRequest.indices("te").types("tt").source(builder);

        //===>查询方法
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        System.out.println("符合条件总数:" + searchResponse.getHits().getTotalHits());
        System.out.println("最大得分:" + searchResponse.getHits().getMaxScore());

        System.out.println("每条文档详细信息===>");
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit h :hits) {
            //===>原文档部分
            System.out.println(h.getSourceAsMap());
            //返回对象
            Tt tt = new Tt();
            tt.setName((String) h.getSourceAsMap().get("name"));
            tt.setAge((Integer) h.getSourceAsMap().get("age"));
            tt.setSex((String) h.getSourceAsMap().get("sex"));
            tt.setContent((String) h.getSourceAsMap().get("content"));

            //==>高亮部分
            Map<String, HighlightField> highlightFields = h.getHighlightFields();
            if (highlightFields.containsKey("name")) {
                String nameHigh = highlightFields.get("name").fragments()[0].toString();
                //替换
                tt.setName(nameHigh);
            }
            if (highlightFields.containsKey("content")) {
                String contentHigh = highlightFields.get("content").fragments()[0].toString();
                tt.setContent(contentHigh);
            }
            list.add(tt);
        }
        //===>存入对象的文档[包括高亮部分]
        System.out.println("===>存入对象的文档[包括高亮部分]");
        list.forEach(tt -> System.out.println(tt));

    }


}
