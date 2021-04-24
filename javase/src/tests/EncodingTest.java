package tests;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @author 11105157
 * @Description
 * @Date 2021/2/22
 */

public class EncodingTest {

    public static void main(String[] args) throws UnsupportedEncodingException {
        //String a = new String("一个".getBytes(StandardCharsets.UTF_8), "gbk");
        //System.out.println(a);
        RestTemplate restTemplate =new RestTemplate();
        //String url = "http://cy.5156edu.com/html1/2248.html";
        String url = "https://www.baidu.com";
        ResponseEntity<byte[]> entity = restTemplate.getForEntity(url, byte[].class);
        System.out.println(entity.getStatusCode());
        System.out.println("------------------------------------------------------");
        System.out.println("------------------------------------------------------");
        System.out.println(entity.getHeaders());
        System.out.println("------------------------------------------------------");
        System.out.println("------------------------------------------------------");
        String s = new String(entity.getBody(), StandardCharsets.UTF_8);
        System.out.println(s);
    }
}
