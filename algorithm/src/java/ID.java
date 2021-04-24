import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by 11105157 on 2020/10/23.
 */
public class ID {
    public static void main(String[] args) {
        Scanner sc =new Scanner(System.in);
        //HashMap<Integer[],Integer> map =new HashMap<>();
        Map<String,Integer> map =new HashMap<>();
        map.put("00",1);
        map.put("10",2);
        map.put("20",11);
        map.put("-10",6);
        map.put("-20",19);
        map.put("01",4);
        map.put("02",15);
        map.put("0-1",8);
        map.put("-1-1",7);
        map.put("-2-1",20);
        map.put("1-1",9);
        map.put("2-1",10);
        map.put("-11",5);
        map.put("-21",18);
        map.put("11",3);
        map.put("21",12);
        map.put("12",14);
        map.put("22",13);
        map.put("-12",16);
        map.put("-22",17);
        int i=sc.nextInt();
        int j = sc.nextInt();
        String res =""+i+j;
        System.out.println(map.get(res));
    }
}
