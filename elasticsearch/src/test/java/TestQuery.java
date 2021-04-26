import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author 11105157
 * @Description
 * @Date 2021/4/14
 */
public class TestQuery {

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


    //查询模板
    public void queryTemplate(QueryBuilder query) {

        //查询所有文档
        SearchResponse searchResponse = transportClient.prepareSearch("te")//索引
                .setTypes("tt")//类型
                .setQuery(query) //传入查询的类型
                .get();//开始查询

        System.out.println("符合条件的总条数:" + searchResponse.getHits().getTotalHits());
        System.out.println("最高得分:" + searchResponse.getHits().getMaxScore());
        System.out.println("详细记录如下：");
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit h : hits) {
            System.out.println("当前索引分数:" + h.getScore());
            System.out.println("[字符串形式打印]====>" + h.getSourceAsString());
            System.out.println("[map集合形式打印]====>" + h.getSourceAsMap());
        }
    }

    //1. 查询所有并排序
    @Test
    public void testQueryMathAll() {

        //查询所有文档
        SearchResponse searchResponse = transportClient.prepareSearch("te")//索引
                .setTypes("tt")//类型
                .setQuery(QueryBuilders.matchAllQuery()) //查询所有
                .addSort("age", SortOrder.DESC) //====> 如果按照指定字段进行排序展示，就打乱了分数排序的规则，其分数就会失效,getScore结果为NaN
                .get();//开始查询

        System.out.println("符合条件的总条数:" + searchResponse.getHits().getTotalHits());
        System.out.println("最高得分:" + searchResponse.getHits().getMaxScore());
        System.out.println("详细记录如下：------------------------------");
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit h : hits) {
            System.out.println("当前索引分数:" + h.getScore());
            System.out.println("[字符串形式打印]====>" + h.getSourceAsString());
            System.out.println("[map集合形式打印]====>" + h.getSourceAsMap());
        }

    }


    //2. From Size分页查询
    @Test
    public void testQueryFromAndSize() {
        //查询所有文档
        SearchResponse searchResponse = transportClient.prepareSearch("te")//索引
                .setTypes("tt")//类型
                .setQuery(QueryBuilders.matchAllQuery()) //传入查询的类型
                .setFrom(0) //====>
                .setSize(1)
                .get();//开始查询

        System.out.println("符合条件的总条数:" + searchResponse.getHits().getTotalHits());
        System.out.println("最高得分:" + searchResponse.getHits().getMaxScore());
        System.out.println("详细记录如下：");
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit h : hits) {
            System.out.println("当前索引分数:" + h.getScore());
            System.out.println("[字符串形式打印]====>" + h.getSourceAsString());
            System.out.println("[map集合形式打印]====>" + h.getSourceAsMap());
        }
    }


    //3. source 查询返回字段
    @Test
    public void testQuerySource() {
        //查询所有文档
        SearchResponse searchResponse = transportClient.prepareSearch("te")//索引
                .setTypes("tt")//类型
                .setQuery(QueryBuilders.matchAllQuery()) //传入查询的类型
                .setFetchSource("*", "age") //====>
                .get();//开始查询

        System.out.println("符合条件的总条数:" + searchResponse.getHits().getTotalHits());
        System.out.println("最高得分:" + searchResponse.getHits().getMaxScore());
        System.out.println("详细记录如下：");
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit h : hits) {
            System.out.println("当前索引分数:" + h.getScore());
            System.out.println("[字符串形式打印]====>" + h.getSourceAsString());
            System.out.println("[map集合形式打印]====>" + h.getSourceAsMap());
        }
    }


    /**
     * size from _source highlight 是在query外面
     * match_all term range prefix wildcard ids fuzzy bool multi_match query_string 是在query内部，可以使用QueryBuilder
     */
    //4. term 指定查询
    @Test
    public void testTermQuery() {
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "王");
        queryTemplate(termQueryBuilder);
    }


    //5. range 范围查询
    @Test
    public void testRange() {
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("age").gte(10).lte(20);
        queryTemplate(rangeQueryBuilder);
    }

    //6.prefix 前缀查询
    @Test
    public void testPrefix() {
        PrefixQueryBuilder prefixQueryBuilder = QueryBuilders.prefixQuery("name", "王");
        queryTemplate(prefixQueryBuilder);
    }

    //7.wildcard 通配符查询
    @Test
    public void testWildcardQuery() {
        WildcardQueryBuilder wildcardQuery = QueryBuilders.wildcardQuery("content", "我?*");
        queryTemplate(wildcardQuery);
    }

    //8.Ids 指定id查询
    @Test
    public void testIds() {
        IdsQueryBuilder queryBuilder = QueryBuilders.idsQuery().addIds("tt01", "tt03");
        queryTemplate(queryBuilder);
    }

    //9.fuzzy 模糊查询

    /**
     * 查询的个数0-2：必须和ES的词条完全匹配
     * 3-5：value可以和词条差一个
     * 大于5：value可以和词条差两个
     */
    @Test
    public void testFuzzy() {
        FuzzyQueryBuilder fuzzyQueryBuilder = QueryBuilders.fuzzyQuery("content", "xxx");
        queryTemplate(fuzzyQueryBuilder);
    }

    //10.bool 复杂查询
    @Test
    public void testBool() {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termQuery("age", "11"));
        boolQueryBuilder.should(QueryBuilders.prefixQuery("content", "王"));
        queryTemplate(boolQueryBuilder);
    }

    //11.高亮查询

    /**
     * 高亮查询
     * .highlighter(highlightBuilder) 用来指定高亮设置
     * requireFieldMatch(false) 开启多个字段高亮 [默认只高亮匹配部分]
     * field 用来定义高亮字段
     * preTags("<span style='color:red'>")  用来指定高亮前缀
     * postTags("</span>") 用来指定高亮后缀
     */
    @Test
    public void testHighlight() {

        //===>高亮配置
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.requireFieldMatch(false);
        highlightBuilder.field("name");
        highlightBuilder.field("content");
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");

        //查询所有文档
        SearchResponse searchResponse = transportClient.prepareSearch("te")//索引
                .setTypes("tt")//类型
                .setQuery(QueryBuilders.termQuery("content", "王")) //====>
                .highlighter(highlightBuilder)//====>
                .get();//开始查询

        System.out.println("符合条件的总条数:" + searchResponse.getHits().getTotalHits());
        System.out.println("最高得分:" + searchResponse.getHits().getMaxScore());
        System.out.println("详细记录如下：");
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit h : hits) {
            System.out.println("当前索引分数:" + h.getScore());
            System.out.println("[字符串形式打印]====>" + h.getSourceAsString());
            System.out.println("[map集合形式打印]====>" + h.getSourceAsMap());
            System.out.println("[高亮字段打印]====>" + h.getHighlightFields());
        }
    }

    //【回显高亮数据】，解决高亮数据和原数据分开的问题
    //先将原数据存入对象，再把高亮部分遍历出来替换掉需要高亮显示的部分
    @Test
    public void testHighlight1() {


        //用于储存数据:===>
        List<Tt> list = new ArrayList<>();

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.requireFieldMatch(false);
        highlightBuilder.field("name");
        highlightBuilder.field("content");
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");

        //查询所有文档
        SearchResponse searchResponse = transportClient.prepareSearch("te")//索引
                .setTypes("tt")//类型
                .setQuery(QueryBuilders.termQuery("content", "王")) //====>
                .highlighter(highlightBuilder)//====>
                .get();//开始查询

        System.out.println("符合条件的总条数:" + searchResponse.getHits().getTotalHits());
        System.out.println("最高得分:" + searchResponse.getHits().getMaxScore());
        System.out.println("详细记录如下：");
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit h : hits) {
            System.out.println("当前索引分数:" + h.getScore());
            System.out.println("[字符串形式打印]====>" + h.getSourceAsString());
            System.out.println("[map集合形式打印]====>" + h.getSourceAsMap());
            System.out.println("[高亮字段打印]====>" + h.getHighlightFields());

            //原数据
            Tt tt = new Tt();
            tt.setName((String) h.getSourceAsMap().get("name"));
            tt.setAge((Integer) h.getSourceAsMap().get("age"));
            tt.setSex((String) h.getSourceAsMap().get("sex"));
            tt.setContent((String) h.getSourceAsMap().get("content"));

            //高亮数据
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
        //遍历集合,【回显】
        System.out.println("对象方式显示高亮部分===>:");
        list.forEach(tt -> System.out.println(tt));
    }


    //FilterQuery 过滤查询
    @Test
    public void testFilterQuery() {

        //查询所有文档
        TermQueryBuilder termQueryBuilder = new TermQueryBuilder("name","王" );
        PrefixQueryBuilder prefixQueryBuilder = new PrefixQueryBuilder("content","x" );
        SearchResponse searchResponse = transportClient.prepareSearch("te")//索引
                .setTypes("tt")//类型
                .setQuery(termQueryBuilder) //===>传入查询的类型
                .setQuery(prefixQueryBuilder)
                .highlighter(new HighlightBuilder().field("*").requireFieldMatch(false).preTags("<span style='color:red>").postTags("</span>")) //高亮部分[高亮搜寻字段]
                .setQuery(termQueryBuilder) //queryBuilder以最后一个为准
                .setPostFilter(QueryBuilders.rangeQuery("age").gte(10).lte(20))//===>过滤
                .get();//开始查询

        System.out.println("符合条件的总条数:" + searchResponse.getHits().getTotalHits());
        System.out.println("最高得分:" + searchResponse.getHits().getMaxScore());
        System.out.println("详细记录如下：");
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit h : hits) {
            System.out.println("当前索引分数:" + h.getScore());
            System.out.println("[字符串形式打印]====>" + h.getSourceAsString());
            System.out.println("[map集合形式打印]====>" + h.getSourceAsMap());
        }
    }


}
