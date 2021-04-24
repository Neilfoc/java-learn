package tests.copyTest;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author 11105157
 * @Description https://segmentfault.com/a/1190000019356477
 * @Date 2021/2/3
 */
@RunWith(Parameterized.class)
//1、当类被@RunWith注解修饰，或者类继承了一个被该注解修饰的类，JUnit将会使用这个注解所指明的运行器（runner）来运行测试，而不使用JUnit默认的运行器。
/**
 * 参数化测试
 * 1、用 @RunWith(Parameterized.class) 来注释 test 类。
 * 2、创建一个由 @Parameters 注释的公共的静态方法，它返回一个对象的集合(数组)来作为测试数据集合。
 * 3、创建一个公共的构造函数，它接受和上面一行测试数据相等同的字段。
 * 4、为每一列测试数据创建一个实例变量。
 * 5、用实例变量作为测试数据的来源来创建你的测试用例。
 */
public class PropertiesCopierTest {

    @Parameterized.Parameter
    // 3、使用此注解，不需要加测试类构造函数  4、实例变量
    public PropertiesCopier propertiesCopier;

    @Parameterized.Parameter(1)
    public int anInt;

    // 测试次数
    private static List<Integer> testTimes = Arrays.asList(100, 1000, 10_000, 100_000, 1_000_000);

    // 测试结果以 markdown 表格的形式输出
    private static StringBuilder resultBuilder = new StringBuilder("|  实现  |  100  |  1000  |  10000  | 100000 | 1000000 |\n");


    @Parameterized.Parameters
    // 2、返回一个二维数组
    public static Collection<Object[]> data() {
        Collection<Object[]> params = new ArrayList<>();
        params.add(new Object[]{new GetSetPropertiesCopier(),1});
        params.add(new Object[]{new StaticCglibBeanCopierPropertiesCopier(),1});
        params.add(new Object[]{new CglibBeanCopierPropertiesCopier(),1});
        params.add(new Object[]{new SpringBeanUtilsPropertiesCopier(),1});
        params.add(new Object[]{new CommonsPropertyUtilsPropertiesCopier(),1});
        params.add(new Object[]{new CommonsBeanUtilsPropertiesCopier(),1});
        return params;
    }

    @Before
    public void setUp() {
        String name = propertiesCopier.getClass().getSimpleName().replace("PropertiesCopier", "");
        resultBuilder.append("|").append(name).append("|");
    }

    @Test
    // 5、测试
    public void copyProperties() throws Exception {
        Account source = new Account(1, "test1", 30D);
        Account target = new Account();

        for (Integer time : testTimes) {
            long start = System.nanoTime();
            for (int i = 0; i < time; i++) {
                propertiesCopier.copyProperties(source, target);
            }
            // 将ns转成ms
            resultBuilder.append((System.nanoTime() - start) / 1_000_000D).append("|");

            /* // 该方法所基于的时间是随机的，但在同一个JVM中，不同的地方使用的原点时间是一样的。只能用来比较，不能用来当作时间
            System.out.println(System.nanoTime());
            //系统时间距离1970年1月1日的毫秒数
            System.out.println(System.currentTimeMillis());*/
        }
        resultBuilder.append("\n");
    }

    @AfterClass
    public static void tearDown() {
        System.out.println("测试结果：");
        System.out.println(resultBuilder);
    }
}
