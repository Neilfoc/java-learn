package tests.newIns;

/**
 * @author neilfoc
 * @Description
 * @Date 2022/1/23
 */
public class Test {
    public static void main(String[] args) {
        // 执行顺序：父类的static方法，子类的static方法，进入当前类的构造函数（父类成员变量初始化，父类构造代码块，父构造函数）：成员变量初始化、构造代码块、构造函数。
        /**
         * fu static code
         * zi static code...
         * fu constructor code...Fu
         * Fu constructor Run
         * fu show
         * constructor code...9
         * zi constructor run...99
         */
        Zi son = new Zi();//父类的构造函数里面执行的方法如果在子类重写了，执行子类重写的
        System.out.println(son.num);
    }
}
