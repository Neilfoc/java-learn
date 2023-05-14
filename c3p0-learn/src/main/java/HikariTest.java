import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariTest {
    public static void main(String[] args) throws InterruptedException {
        HikariConfig config = new HikariConfig("/hikari.properties");
        HikariDataSource dataSource = new HikariDataSource(config);
        Thread.sleep(20 * 60 * 1000);
        dataSource.close();
    }
}
