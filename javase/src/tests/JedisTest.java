package tests;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author 11105157
 * @Description
 * @Date 2021/1/19
 */
public class JedisTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        Map<String, String> data = new HashMap<>();
        jedis.select(8);
        jedis.flushDB();

        /**
         * hmset
         */
        long start = System.currentTimeMillis();
        for (int i = 0; i < 50000; i++) {
            data.clear();
            data.put("k_" + i, "v_" + i);
            jedis.hmset("key_" + i, data);
        }
        long end = System.currentTimeMillis();
        System.out.println("dbsize:[" + jedis.dbSize() + "] .. ");
        System.out.println("hmset without pipeline used [" + (end - start) + "] seconds ..");

        jedis.select(8);
        jedis.flushDB();
        // 使用pipeline hmset
        Pipeline pipeline = jedis.pipelined();
        start = System.currentTimeMillis();
        for (int i = 0; i < 50000; i++) {
            data.clear();
            data.put("k_" + i, "v_" + i);
            pipeline.hmset("key_" + i, data);
        }
        pipeline.sync();
        end = System.currentTimeMillis();
        System.out.println("dbsize:[" + jedis.dbSize() + "] .. ");
        System.out.println("hmset with pipeline used [" + (end - start) + "] seconds ..");


        /**
         * hmget
         */
        // 直接使用Jedis hgetall
        Set<String> keys = jedis.keys("*");
        start = System.currentTimeMillis();
        Map<String, Map<String, String>> result = new HashMap<>();
        for (String key : keys) {
            result.put(key, jedis.hgetAll(key));
        }
        end = System.currentTimeMillis();
        System.out.println("result size:[" + result.size() + "] ..");
        System.out.println("hgetAll without pipeline used [" + (end - start) + "] seconds ..");

        // 使用pipeline hgetall
        Map<String, Response<Map<String, String>>> responses =
                new HashMap<>(keys.size());
        result.clear();
        start = System.currentTimeMillis();
        for (String key : keys) {
            responses.put(key, pipeline.hgetAll(key));
        }
        pipeline.sync();
        for (String k : responses.keySet()) {
            result.put(k, responses.get(k).get());
        }
        end = System.currentTimeMillis();
        System.out.println("result size:[" + result.size() + "] ..");
        System.out.println("hgetAll with pipeline used [" + (end - start) + "] seconds ..");

        jedis.disconnect();

    }

}
