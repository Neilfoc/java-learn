package tests;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author 11105157
 * @Description
 * @Date 2021/3/2
 */

/**
 * 请求步骤：
 * 1、使用HttpClients创建ClosableHttpClient [关闭]
 * 2、创建HttpGet或HttpPost、HttpPut
 * 3、给2添加headers
 * 4、使用setParams(HetpParams params)或者setEntity(HttpEntity entity)给2添加请求参数
 * 5、执行2获得ClosableHttpResponse实例 [关闭]
 * 6、获取5的状态码、错误信息、响应页面等
 * 7、释放连接
 */
public class HttpClientTest {

    public static void main(String[] args) throws IOException {
        postTest();
    }

    // httpPost
    public static void postTest() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        HttpPost httpPost = new HttpPost("https://www.baidu.com");
        httpPost.setHeader("User-Agent", "Mozilla/5.0");
        httpPost.setEntity(null);

        BufferedReader reader = null;
        StringBuffer buffer =new StringBuffer();
        try {
            httpResponse = httpClient.execute(httpPost);
            /*reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }*/
            HttpEntity entity = httpResponse.getEntity();
            System.out.println(EntityUtils.toString(entity));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        }
        //System.out.println(buffer);
    }

    //httpGet
    public static void getTest() {

    }

    //httpPut
    public static void putTest() {

    }
}
