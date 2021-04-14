package tests.copyTest;

import com.alibaba.fastjson.JSON;

/**
 * @author 11105157
 * @Description
 * @Date 2021/3/12
 */
public class Test {
    public static void main(String[] args) {
        Account account = new Account();
        account.setAge(12);
        account.setMoney(11);
        account.setName("xx");
        System.out.println(JSON.toJSONString(account));
    }
}
