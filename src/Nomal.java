import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * author aa
 * Created by 11105157 on 2020/10/23.
 */
public class Nomal {
    /*public static  int NthPrime(int n){
        int i = 2, j = 1;
        while (true) {
            j = j + 1;
            if (j > i / j) {
                n--;
                if (n == 0) {
                    break;
                }
                j = 1;
            }
            if (i % j == 0) {
                i++;
                j = 1;
            }
        }
        return i;
    }
    *//**
     * @param args
     *//*
    public static void main(String[] args) {
        System.out.println("请输入N的值:");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int result = NthPrime(n);
        //System.out.println("第N个素数的值是:"+result);
        char[] hex = "0123456789abcdef".toCharArray();
        String s = new String();
        while(result != 0){
            int end = result &15;
            s = hex[end] + s;
            //无符号右移
            result >>>=4;
        }
        if(s.length() == 0){
            s = "0";
        }

        System.out.println("0x" + s);

    }*/
   /* public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        List<Integer> list = new ArrayList<>();
        for (int i = 2; i <= 80000; i++) {
            boolean flag = true;
            for (int j = 2; j < i; j++) {

                if (i % j == 0) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                list.add(i);
            }
        }

        System.out.println("请输入小于"+list.size()+"的正整数");
        int n = sc.nextInt();

        if (n > list.size()) {
            System.out.println("请输入小于" + list.size() + "的正整数");
            return;
        }

        Integer integer = list.get(n);
        String s = Integer.toHexString(integer);
        System.out.println(s);
    }*/
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {
            // 输出第n个素数
            int n = scan.nextInt();
            int current = 2;//最小的素数是2
            int sum = 0;
            List<Integer> list = new ArrayList<>();//list来保存前n个素数
            for (;;) {
                //除了2以外的所有偶数都不是素数，为了减少判断将这些偶数避开
                if (current > 2 && current % 2 == 0) {//如果current为大于2的偶数
                    current++;//current++得到奇数,利用continue进行下一次循环
                    continue;
                }
                int a = -1;
                //下面i的循环条件值得玩味,优化下得到更少的判断次数更好的性能更小的时间复杂度
                for (int i = 2; i <= Math.sqrt(current); i++) {
                    if (current % i == 0) {
                        a = 0;
                        break;
                    }
                }
                //a==-1,则当前current是素数,sum用于控制得到n个素数
                if (a == -1) {
                    sum++;
                    // System.out.println(current);
                    list.add(current);

                }
                if (sum == n) {
                    break;
                }
                current++;
            }
            //输出list的最后一个数(即第n个素数)
            System.out.println(list.get(list.size() - 1));
        }
    }
}
