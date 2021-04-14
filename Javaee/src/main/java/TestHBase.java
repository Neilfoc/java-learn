import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * @author 11105157
 * @Description
 * @Date 2021/4/9
 */
public class TestHBase {
    public static void main(String[] args) throws IOException {
        Configuration config;
        HTablePool pool =new HTablePool( );
        pool.getTable("user");
        put();
    }

    //增
    public static void put() throws IOException {
        // 获取默认的配置
        Configuration conf = HBaseConfiguration.create();
        // 获取Table实例
        HTable table = new HTable(conf, "tab1");
        // 创建Put实例，并且指定rowKey
        Put put = new Put(Bytes.toBytes("row-1"));
        // 添加一个 column，值为 "Hello"，在 "cf1:greet" 列中
        put.add(Bytes.toBytes("cf1"), Bytes.toBytes("greet"), Bytes.toBytes("Hello"));
        // 添加一个 column，值为 "John"，在 "cf1:person" 列中
        put.add(Bytes.toBytes("cf1"), Bytes.toBytes("person"), Bytes.toBytes("John"));
        put.add(Bytes.toBytes("cf1"), Bytes.toBytes("age"), Bytes.toBytes(16));
        table.put(put);
        table.close();

    }

    public static void get() throws IOException {
        // 获取默认的配置
        Configuration conf = HBaseConfiguration.create();
        // 获取Table实例
        HTable table = new HTable(conf, "tab1");
        // 创建Put实例，并且指定rowKey
        Get get = new Get(Bytes.toBytes("row-1"));
        //
        get.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("greet"));
        // 添加一个 column，值为 "John"，在 "cf1:person" 列中
        Result result = table.get(get);
        byte[] value = result.getValue(Bytes.toBytes("cf1"), Bytes.toBytes("greet"));
        System.out.println("获取到的值" + new String(value));
        table.close();
    }

    public static void update() throws IOException {
        Configuration conf = HBaseConfiguration.create();
        // 获取Table实例
        HTable table = new HTable(conf, "tab1");
        // 创建Put实例，并且指定rowKey
        Put put = new Put(Bytes.toBytes("row-1"));
        // 添加一个 column，值为 "Hello"，在 "cf1:greet" 列中
        put.add(Bytes.toBytes("cf1"), Bytes.toBytes("greet"), Bytes.toBytes("Good Morning"));
        // 添加一个 column，值为 "John"，在 "cf1:person" 列中
//        put.add(Bytes.toBytes("cf1"), Bytes.toBytes("person"), Bytes.toBytes("John"));
        table.put(put);
        table.close();
    }

    public void delete() throws IOException {
        Configuration conf = HBaseConfiguration.create();
        // 获取Table实例
        HTable table = new HTable(conf, "tab1");
        // 创建Delete实例，并且指定rowKey
        Delete delete = new Delete(Bytes.toBytes("row-1"));
        // 删除 column "cf1:greet"
        delete.deleteColumn(Bytes.toBytes("cf1"), Bytes.toBytes("greet"));

        table.delete(delete);
        table.close();
    }

}
