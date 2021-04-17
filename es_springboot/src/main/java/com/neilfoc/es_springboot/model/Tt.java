package com.neilfoc.es_springboot.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author 11105157
 * @Description
 * @Date 2021/4/16
 */
@Data
@Document(indexName="te",type="tt")
public class Tt {
    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Integer)
    private Integer age;

    @Field(type = FieldType.Text)
    private String sex;

    @Field(type = FieldType.Text)
    private String content;
}
