package game;

import java.util.Scanner;

/**
 * @author 11105157
 * @Description
 * @Date 2021/8/28
 */
public class XianShou {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        String[] s1 = s.split(" ");
        int[] boxArr = new int[s1.length];
        for (int i = 0; i < s1.length; i++) {
            boxArr[i] = Integer.parseInt(s1[i]);
        }
        int n = boxArr.length;
        // 初始化 dp 数组
        C[][] dp = new C[n][n];
        for (int i = 0; i < n; i++)
            for (int j = i; j < n; j++)
                dp[i][j] = new C(0, 0);
        // 填入 base case
        for (int i = 0; i < n; i++) {
            dp[i][i].fir = boxArr[i];
            dp[i][i].sec = 0;
        }
        // 斜着遍历数组
        for (int l = 2; l <= n; l++) {
            for (int i = 0; i <= n - l; i++) {
                int j = l + i - 1;
                // 先手选择最左边或最右边的分数
                int left = boxArr[i] + dp[i+1][j].sec;
                int right = boxArr[j] + dp[i][j-1].sec;
                // 套用状态转移方程
                if (left > right) {
                    dp[i][j].fir = left;
                    dp[i][j].sec = dp[i+1][j].fir;
                } else {
                    dp[i][j].fir = right;
                    dp[i][j].sec = dp[i][j-1].fir;
                }
            }
        }
        C res = dp[0][n-1];
        System.out.println(res.fir + " " + res.sec);
    }
}

class C {
    int fir, sec;
    C(int fir, int sec) {
        this.fir = fir;
        this.sec = sec;
    }
}
