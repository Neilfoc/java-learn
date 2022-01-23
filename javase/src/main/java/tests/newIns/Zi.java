package tests.newIns;

/**
 * @author neilfoc
 * @Description
 * @Date 2022/1/23
 */
public class Zi extends Fu {
    Integer num = 9;

    static {
        System.out.println("zi static code...");
    }

    {
        System.out.println("constructor code..." + num);
        num = 99;
    }

    Zi() {
        //super();
        System.out.println("zi constructor run..." + num);
    }


    void show() {
        System.out.println("zi show..." + num);
    }
}
