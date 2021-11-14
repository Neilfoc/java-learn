package game;

import java.util.Scanner;


public class Shu {

    static int INF = 0x7fffffff;
    public static int[][] map = new int[105][105];
    public static int[] dis = new int[105];
    static int m, n;
    static boolean[] visit = new boolean[105];


    static int Prim() {
        int i, j, k = 0;
        int temp, ans = 0;
        for (i = 1; i <= n; i++)
            dis[i] = map[1][i];
        visit[1] = true;
        for (i = 1; i < n; i++) {
            for (temp = INF, j = 1; j <= n; j++)
                if (dis[j] < temp && visit[j] == false)
                    temp = dis[k = j];
            ans += temp;
            visit[k] = true;
            for (j = 1; j <= n; j++)
                if (visit[j] == false && dis[j] == temp)
                    return -1;
            for (j = 1; j <= n; j++)
                if (visit[j] == false)
                    if (dis[j] > map[k][j])
                        dis[j] = map[k][j];
                    else if (dis[j] == map[k][j] && dis[j] != INF)
                        return -1;
        }
        return ans;

    }

    static void init() {
        int i;
        for (i = 1; i <= n; i++)
            for (int j = 1; j <= n; j++)
                map[i][j] = INF;
        for (i = 1; i <= n; i++)
            map[i][i] = 0;
    }

    public static void main(String[] args) {
        int T = 0;
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String[] s1 = s.split(" ");
        int x, y;
        init();
        n = Integer.parseInt(s1[0]);
        m = Integer.parseInt(s1[1]);
        for (int i = 0; i < m; i++) {
            String l1 = sc.nextLine();
            String[] l1s = l1.split(" ");
            x = Integer.parseInt(l1s[0]);
            y = Integer.parseInt(l1s[1]);
            map[x][y] = Integer.parseInt(l1s[2]);
            map[y][x] = Integer.parseInt(l1s[2]);
        }
        int ans = Prim();
        if (ans == -1) {
            System.out.print(-1);
        } else {
            System.out.print(ans);
        }

    }
}
