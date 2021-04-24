package com.neilfoc.es_springboot.elasticsearch.dao;

import com.neilfoc.es_springboot.model.Tt;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author 11105157
 * @Description ElasticsearchRepository
 * 第一个参数：指定对象类型
 * 第二个参数：ID类型
 * @Date 2021/4/17
 */
public interface TtRepository extends ElasticsearchRepository<Tt,String> {
    //通过 ElasticsearchRepository 接口除了ES提供的api还可以在自定义接口中【自定义】一些扩展查询的方法
    //ElasticsearchRepository只提供了基础查询方法

    //只需定义 无需实现
    List<Tt> findByName(String name);
}
