package zookeeper.config;


import lombok.Data;

//这个class是你未来最关心的地方？
@Data
public class MyConf {

    // 保存的是的/testLock/AppConf数据
    private String conf;

}
