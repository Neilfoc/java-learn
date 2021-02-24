import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.SneakyThrows;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.*;

/**
 * @author 11105157
 * @Description
 * @Date 2020/11/28
 */
public class Test {
    @SneakyThrows
    public static void main(String[] args) {
        long time = System.nanoTime();
        Thread.sleep(1000);
        // 该方法所基于的时间是随机的，但在同一个JVM中，不同的地方使用的原点时间是一样的。只能用来比较，不能用来当作时间
        System.out.println(System.nanoTime());
        //系统时间距离1970年1月1日的毫秒数
        System.out.println(System.currentTimeMillis());
        Date date = new Date(System.currentTimeMillis());
        System.out.println(date);
    }


}
