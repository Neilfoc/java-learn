import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.DateUtils;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import tests.copyTest.Account;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static static1.StaticMethodClass.xixi;

/**
 * @author 11105157
 * @Description
 * @Date 2020/11/28
 */
@Slf4j
public class Test {

    @NotNull
    @NotBlank
    private String xixi;

    //@SneakyThrows
    public static void main(String[] args) throws IOException {
        /*long time = System.nanoTime();
        Thread.sleep(1000);
        // 该方法所基于的时间是随机的，但在同一个JVM中，不同的地方使用的原点时间是一样的。只能用来比较，不能用来当作时间
        System.out.println(System.nanoTime());
        //系统时间距离1970年1月1日的毫秒数
        System.out.println(System.currentTimeMillis());
        Date date = new Date(System.currentTimeMillis());
        System.out.println(date);*/
        /*String s = Base64.encodeBase64String("fzqusongjian".getBytes());
        System.out.println(s);
        byte[] bytes = Base64.decodeBase64("ZmZtcGVnIC1yZSAtaSBodHRwczovL3N0dmlkZW8tbGl2ZS1tb3ZpZS5vc3MtY24tYmVpamluZy1pbnRlcm5hbC5hbGl5dW5jcy5jb20vbW92aWUtcHVzaC9wcmUvJUU2JTkwJTlFJUU3JUFDJTkxJUU2JUFFJUI1JUU1JUFEJTkwL1MwMUEwMDA0NTM1MDI3MDEzMjYxMzMwLm1wNCAtdmNvZGVjIGxpYngyNjQgLWJmIDAgLWFjb2RlYyBhYWMgLWI6diAyMDAwayAtciAzMCAtZyA2MCAtcHJlc2V0IHVsdHJhZmFzdCAtYWYgdm9sdW1lPTkuMGRCIC1mIGZsdiBydG1wOi8vbGl2ZS1wdXNoLnZpdm8uY29tLmNuL2xpdmUvMTAwMTI4NF9wcmQ/dHhTZWNyZXQ9ZTkzMTZhZGMyZjljMDNkM2RiMTljZDAwMmY3ZWNiN2ImdHhUaW1lPTYwM0Q0ODY4");
        System.out.println(new String(bytes));
        String cmd = "ffmpeg -i '" + "input" + "' -vf \"drawtext = text = '" + "hello" + "'" + 1 + 2
                + ":fontfile =" + "msyh" + ":fontsize =" + "12" + ":fontcolor ="
                + "yellow" + "\" -y " + "ok";
        System.out.println(cmd);
        System.out.println(System.getProperty("app.env"));*/
        /*System.out.println("好的");
        URL url = new URL("https://cdcn02videostatic.kaixinkan.com.cn/api/tobfile/source/9037cf8ab7b4144c1193006af3c6ec18/B0S0646G7_d1s-OZy5rutloeCZOD57blTv3HOlGEMgwQ0XXr5zZuiEx1jpFGKmSCDWseZZFUMA1daZKrPQFkm7P-wMNb9Imf7N50QaCAg8zU5HXQ6LbRrMcWg3jkps752CoRxUN7NWMBial4EktK86NZfVGA?requestFrom=UGC-VIDEO&sign=c2b92ea8a543727a227b37019a3d2746&t=603e3d70");
        String file = url.getFile();
        if (file.indexOf('?') > -1) {
            file = file.substring(0, file.indexOf('?'));
        }
        if (file.indexOf('/') > -1) {
            file = file.substring(file.lastIndexOf('/') + 1);
        }
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
        file = "D:/video学习/aqq/b/" + uuid + "-" + file;
        System.out.println(file);
        System.out.println(File.separator);
        File f = new File(file);
        File dir = new File(f.getParent());
        System.out.println(System.getProperty("file.encoding"));
        try {
            f.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(f.getParent());*/

       /* SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        String date = dateFormat.format(new Date());
        System.out.println(date);*/

        //测试对象属性赋值
        /*Account account = new Account();
        try {
            account.setAge(Integer.valueOf("2k"));
        } catch (Exception e) {
            log.error("parse integer error",e);
        }
        System.out.println(account.getAge());*/

        //测试StringBuilder
        /*StringBuilder sb = new StringBuilder();
        sb.append("'xixihaha'");
        sb.insert(sb.indexOf("h"), "ok");
        sb.insert(sb.lastIndexOf("'"), "好");
        System.out.println(sb.toString());*/

        //测试对象的初始赋值
        /*Account account = new Account();
        System.out.println(account.getH());
        System.out.println(account.getAge());
        System.out.println(account.getMoney());*/


        //测试list
       /* Account account = new Account();
        account.setAge(100);
        List<Account> list = new ArrayList<>();
        list.add(account);
        account.setName("xx");
        list.add(account);
        account.setMoney(1.1);
        account.setAge(101);
        list.add(account);
        System.out.println(list.size());
        System.out.println(JSON.toJSONString(list));
        Account account1 = new Account();*/

        //测试增强for
        /*List<Account> list = new ArrayList<>();
        for (Account account : list) {
            System.out.println("xx");
        }*/

        //测试dateformat
        /*String s = DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss").toString();
        System.out.println(s);
        System.out.println(new Date().toString());*/

        //测试正则表达式
       /* Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        Matcher matcher = pattern.matcher("12");
        System.out.println(matcher.matches());*/

        //测试String和int转换成byte
        /*System.out.println(Bytes.toBytes("16"));
        System.out.println(Bytes.toBytes(16));*/

        //测试JSON
        /*Account account = new Account();
        account.setName("name");
        account.setAge(26);
        account.setMoney(1);
        account.setH(180);
        System.out.println(JSON.toJSON(account));
        System.out.println(JSON.toJSONString(account));

        JSONObject jsonObject = JSON.parseObject("{\"money\":1.0,\"h\":180,\"name\":\"name\",\"age\":26}");
        Account account1 = JSON.toJavaObject(jsonObject, Account.class);
        Account account2 = JSON.parseObject("{\"money\":1.0,\"h\":180,\"name\":\"name\",\"age\":26}", Account.class);
        System.out.println(JSON.toJSONString(account1));
        System.out.println(JSON.toJSONString(account2));*/

        /*SimpleDateFormat simpleDateFormat_YYMMDD = new SimpleDateFormat("yyyyMMdd");
        String indexName = String.format("%s_%s", "video_content_index",simpleDateFormat_YYMMDD.format(new Date()));
        System.out.println(indexName);*/

        xixi();
    }



}
