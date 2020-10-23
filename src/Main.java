import java.util.Scanner;

/**
 * Created by 11105157 on 2020/10/23.
 */
import java.util.Scanner;

/**
 * @author 11124049
 * @date 2020/10/23
 */
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {// 注意，如果输入是多个测试用例，请通过while循环处理多个测试用例
            int a = in.nextInt();
            System.out.println(convertBalancedTernary(a).toString());
        }

    }

    public static StringBuffer convertBalancedTernary(int source){
        String temp = String.valueOf(source);
        char[] arr = temp.toCharArray();
        int num = 0;

        for (int i = 0; i <= arr.length-1; i++) {
            num = num * 3 + (arr[i]-48);
        }

        //System.out.println(num);

        StringBuffer result=new StringBuffer();
        byte[] ter=new byte[21];
        int lower=0, upper=0, index=0;

        do{
            switch(num%3){
                case 0:
                    lower=upper;
                    upper=0;
                    break;
                case 1:
                    lower=upper+1;
                    upper=0;
                    break;
                case 2:
                    lower=upper-1;
                    upper=1;
                    break;
                default:
                    return new StringBuffer("error:1");
            }
            if(lower==2){
                lower=-1;
                upper++;
            }
            ter[index++]=(byte)lower;
            num/=3;
        }while(num>0);

        if(upper==1) ter[index]=(byte)upper;
        else index--;

        for(int i=index; i>=0;i--){
            switch(ter[i]){
                case -1:
                    result.append('T');
                    break;
                case 0:
                    result.append('0');
                    break;
                case 1:
                    result.append('1');
                    break;
                default:
                    return new StringBuffer("error:2");
            }
        }
        return result;
    }
}
