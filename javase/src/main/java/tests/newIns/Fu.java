package tests.newIns;

/**
 * @author neilfoc
 * @Description
 * @Date 2022/1/23
 */
public class Fu {

    private String name = "Fu";

    static {
        System.out.println("fu static code");
    }

    {
        System.out.println("fu constructor code..." + name);
    }

    Fu() {
        System.out.println("Fu constructor Run");
        show();
    }

    Fu(String name){
        this.name = name;
    }

     void show() {
        System.out.println("fu show");
    }
}
