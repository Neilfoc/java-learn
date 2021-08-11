import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.SetUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import tests.copyTest.Account;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
        //ArrayList<String> l1 = Lists.newArrayList("123", "345");
        //ArrayList<String> l2 = Lists.newArrayList("123", "345");
        //boolean b = Collections.disjoint(l1, l2);
        //boolean b = l1.retainAll(l2);
        //System.out.println(b);
        //System.out.println(CollectionUtils.isNotEmpty(l1));
        //System.out.println(l2.contains(null));
        //
        //int[] arr = {1,2};
        //Long time = 1630339200000L;
        //int l = (int) ((time - System.currentTimeMillis()) /1000);
        //System.out.println(l);
        //
        //int[][] arr1 = new int[0][1];
        //System.out.println(arr1[0].length);

        //String[] s = "江苏_".split("_");
        //System.out.println(s.length);
        //System.out.println(JSON.toJSONString(s));

        //Pattern pattern = Pattern.compile("\\d+");
        //System.out.println(pattern.pattern());

        System.out.println("9ecc18b47f25f47f".hashCode());
    }

    private static HttpClientBuilder cb;

    public void hasTested() {
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

        /*Deque<Integer> deque = new LinkedList<>();
        deque.addFirst(1);
        deque.addFirst(2);
        System.out.println(JSON.toJSONString(deque));*/

        // 测试HttpGet(url)
        // 支持 -._~:/?#[]@!$&'()*+,;=
        // %可以充当url编码后面接16进制
        // 不支持 | 空格
        // https://qastack.cn/programming/1547899/which-characters-make-a-url-invalid
        /*String url = "http://p6-ad.bytecdn.cn/list/640x360/tos-cn-p-0015/d8c61e1d64484154a82f8e832473cb05 |%ab";
        String encode = URLEncoder.encode(url, "UTF-8");
        //String url = null;
        HttpGet httpGet = new HttpGet(encode);*/

        // Java8 findFirst和filter
        /*List<Boolean> list = new ArrayList<>();
        list.add(false);
        //list.add(true);
        //list.add(true);
        Optional<Boolean> first = list.stream().filter(a -> a).findFirst();
        System.out.println(first.isPresent());*/


        // findAny和findFirst
        /*OptionalInt first1 = IntStream.range(1, 10).findAny();
        OptionalInt first2 = IntStream.range(1, 10).parallel().findAny();
        for (int i = 0; i < 10; i++) {
            System.out.println(first1.getAsInt() +" " +first2.getAsInt() );
        }*/

        // leftpad
        /*int hashCode = "neilfoc".hashCode();
        System.out.println(hashCode);
        int abs = Math.abs(-1832990048);
        System.out.println(abs);
        String leftPad = StringUtils.leftPad("-1832990048", 4, "0");
        System.out.println(leftPad);*/
        //System.out.println("40df482ff412a26b".hashCode());

        // new String[0]
        /*String[] strings = new String[0];
        System.out.println(strings.length);
        List<String> list =Lists.newArrayList("com.neilfoc.boot");
        String[] strings1 = list.toArray(new String[0]);
        System.out.println(strings1.length);*/

        // 过滤集合的第一个元素
        /*ArrayList<String> list = Lists.newArrayList("1", "2", "1");
        if (list.get(0).equals("1")) {
            list.remove(0);
        }
        System.out.println(JSON.toJSONString(list));*/

        //System.out.println(DigestUtils.md5Hex("0522"));

        // json转换异常 inputStream什么的
        //JSON.toJSONString(object, SerializeConfig.globalInstance,new SerializeFilter[0],null,10000,new SerializerFeature[0])

        // 替换中文
        /*String str = "我[爱慕]浑身难受[老虎爱慕]";
        String s = str.replaceAll("\\[[\u4e00-\u9fa5]+\\]", "a");
        System.out.println(s);*/

        // 正则表达式
        /*String cookie = "onlyoffice_editor=9; PS_DEVICEFEATURES=maf:0 width:1920 height:1080 clientWidth:1920 clientHeight:937 pixelratio:1 touch:0 geolocation:1 websockets:1 webworkers:1 datepicker:1 dtpicker:1 timepicker:1 dnd:1 sessionstorage:1 localstorage:1 history:1 canvas:1 svg:1 postmessage:1 hc:0; bpmssotoken=Value=kwzb8Pv5iElC_sN8R9iwd4wUJeP6AyPWawnB5HLKkCL9RkMyVyCGTPucTRP2n_KtN5SEDJDMIgI*; uuc-uuid=E%252F98ojttrzWrnpf7eczIfIpog6od2V9SMl9%252FZ6fReWaKtq77OmCL0w%253D%253D; uuc-token=6HtUv6WPblQcX7RMNBF8R9%252F7c%252B5n8L2XLL%252FacaEFRyU4EDANsRa%252FCjGz%252FjrhYx5j; uuc-vlang=zh-CN; BDSP_TOKEN=a6b015c18f5262dd4e6edc5a77b4e7e6345023991b6025c022454cc52ce3e3d2; token=kwzb8Pv5iElC_sN8R9iwd4wUJeP6AyPWawnB5HLKkCL9RkMyVyCGTPucTRP2n_KtN5SEDJDMIgI*; CSRF_TOKEN=\"ZDE4MTViZDdhMmE3M2I3MTE2MTkyZmE3OTkyYWJkMTQ=\"; JSESSIONID=F11FF56D8D7409D2AA16D71C73FCCF57";
        String cookie1 = " CSRF_TOKEN=\"ZDE4MTViZDdhMmE3M2I3MTE2MTkyZmE3OTkyYWJkMTQ=\";";
        String reg = "(^ )" + "CSRF_TOKEN" + "=([^;]*)(;$)";
        String reg1 = " CSRF_TOKEN=[^;]*;";
        //String reg = "(^| )" + "CSRF_TOKEN" + "=([^;]*)(;|$)";
        Pattern pattern = Pattern.compile(reg1);
        Matcher matcher = pattern.matcher(cookie1);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }*/

        // subList
        /*List<String> list = new ArrayList<String>();
        list.add("JavaWeb编程词典");  //向列表中添加数据
        list.add("Java编程词典");  //向列表中添加数据
        list.add("C#编程词典");  //向列表中添加数据
        list.add("ASP.NET编程词典");  //向列表中添加数据
        list.add("VC编程词典");  //向列表中添加数据
        list.add("SQL编程词典");  //向列表中添加数据
        List<String> subList = list.subList(1, 14);  //获取子列表
        System.out.println(subList);*/

        /*String str = "{\"secondId\":0,\"musicIds\":\"2306\"}";
        Map map = JSON.parseObject(str, Map.class);
        if (StringUtils.isNotEmpty(map.get("secondId")+"")) {
            System.out.println("xx");
        }*/

        /*Map<String, String> map = new HashMap<>();
        map.put(null, "1");
        System.out.println(map.get(null));*/
        //JSON.toJSONString(null);

        /*Person p1 = new Person(111, "a", 111);
        Person p2 = new Person(122, "b", 122);
        Person p3 = new Person(133, "a", 133);
        List<Person> list = Lists.newArrayList(p1, p2, p3);
        Map<String, List<Person>> collect = list.stream().collect(Collectors.groupingBy(Person::getName));
        System.out.println(JSON.toJSONString(collect));*/

        /* ArrayList<String> list = Lists.newArrayList("abc1", "abc2", "abc3");
        System.out.println(JSON.toJSONString(list));
        System.out.println(list.toString());
        final String s = JSON.toJSONString(list);
        final List<String> strings = JSON.parseArray(list.toString(), String.class);
        System.out.println(strings);
        System.out.println(JSON.toJSONString(strings));*/

        /*Person p1 = new Person(1, "a", 0);
        Person p2 = new Person(2, "b", 122);
        Person p3 = new Person(3, "c", 0);
        Person p4 = new Person(4, "a", 133);
        ArrayList<Person> list = Lists.newArrayList(p1, p2, p3, p4);
        List<Person> c1 = new ArrayList<>();
        List<Person> c0 = new ArrayList<>();
        for (Person p : list) {
            if (p.getMoney() == 0) {
                c0.add(p);
            }else {
                c1.add(p);
            }
        }
        c1.addAll(c0);
        System.out.println(JSON.toJSONString(c1));*/

        /*ArrayList<String> words = Lists.newArrayList("Hello", "World");
        List<String> collect = words.stream()
                .flatMap(word -> Arrays.asList(word.split("")).stream())
                .distinct()
                .collect(Collectors.toList());
        System.out.println(collect);

        List<Integer> a=new ArrayList<>();
        a.add(1);
        a.add(2);
        List<Integer> b=new ArrayList<>();
        b.add(3);
        b.add(4);
        List<Integer> figures= Stream.of(a,b)
                .flatMap(u->u.stream())
                .collect(Collectors.toList());*/
    }

    //洗牌
    public static <T> T[] randomSelected(T[] array, int num) {
        T[] temp = Arrays.copyOf(array, array.length);// 获得一个该数组的复制
        int length = temp.length;
        int left = length;
        while (length - left < num) {// length - left 为还需要计算多少次
            int i = (int) Math.floor(Math.random() * left--);// 随机选取一个元素，left 自减，这样不会覆盖上次产生的结果，并将下次选取的范围缩小
            T tmp = temp[i];// 将被选中的数与数组的最后一位进行调换
            temp[i] = temp[left];
            temp[left] = tmp;
        }
        return Arrays.copyOfRange(temp, 0, num > length ? length : num);// 从临时数组中复制出指定长度的数组
    }
}
