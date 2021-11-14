package game;

import java.util.*;

/**
 * @author 11105157
 * @Description
 * @Date 2021/8/28
 */
class Union {
    int count;//树的个数
    int[] root;//每个点的根节点
    int[] size;//一棵树的节点数

    Union(int m) {
        root = new int[m];
        size = new int[m];
        for (int i = 0; i < m; i++) {
            root[i] = i;//初始点，每个点的根节点都是自己
            size[i] = 1;//每棵树只有1个节点
        }
        count = m;//总共有m棵树
    }

    public void unionF(int i, int j) {
        int x = find(i);//i的根节点
        int y = find(j);//j的根节点
        if (x != y) {
            if (size[x] > size[y]) {//x树更大，把y接上去
                root[y] = x;
                size[y] += size[x];
            } else {//y树更大，把x接上去
                root[x] = y;
                size[x] += size[y];
            }
            count--;
        }
    }

    public int find(int j) {
        while (root[j] != j) {
            //这句是为了压缩路径，不要的话可以跑的通，但效率变低
            root[j] = root[root[j]];
            j = root[j];
        }
        return j;
    }

    public int count() {
        return count;
    }

    public boolean connected(int i, int j) {
        int x = find(i);
        int y = find(j);
        return x == y;
    }


}
class Main{

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s1 = sc.nextLine();
        String[] s1s = s1.split(" ");
        int N = Integer.parseInt(s1s[0]);
        int size = Integer.parseInt(s1s[1]);
        int[][] connections = new int[size][3];
        for (int i = 0; i < size; i++) {
            String l1 = sc.nextLine();
            String[] l1s = l1.split(" ");
            for (int j = 0; j < 3; j++) {
                connections[i][j] = Integer.parseInt(l1s[j]);
            }
        }

        List<Integer> result = new ArrayList<>();

        /*if (N <= 1 || connections.length < N - 1){
            System.out.print(-1);
            return;
        }//边数量小于点-1，不可能构成树

        HashMap<Integer, ArrayList<int[]>> map = new HashMap<>();//顶和边
        for (int[] connect : connections) {
            if (map.containsKey(connect[0])) {
                ArrayList<int[]> array = map.get(connect[0]);
                int[] c = new int[]{connect[1], connect[2]};
                array.add(c);
                map.put(connect[0], array);
            } else {
                ArrayList<int[]> array = new ArrayList<>();
                int[] c = new int[]{connect[1], connect[2]};
                array.add(c);
                map.put(connect[0], array);
            }
            if (map.containsKey(connect[1])) {
                ArrayList<int[]> array = map.get(connect[1]);
                int[] c = new int[]{connect[0], connect[2]};
                array.add(c);
                map.put(connect[1], array);
            } else {
                ArrayList<int[]> array = new ArrayList<>();
                int[] c = new int[]{connect[0], connect[2]};
                array.add(c);
                map.put(connect[1], array);
            }
        }
        boolean[] flag = new boolean[N];
        Arrays.fill(flag, false);//判断是否读取过
        int start = connections[0][0];//起始点，可以随意取
        flag[start - 1] = true;
        int count = 1;
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(t -> t[1]));//设计堆
        pq.addAll(map.get(start));
        int res = 0;
        while (!pq.isEmpty()) {//若堆为空，还没把所有点读入，说明是无法连接的
            int[] c = pq.poll();
            if (flag[c[0] - 1])//该边另一个顶点已经读取过
                continue;
            else {
                pq.addAll(map.get(c[0]));
                flag[c[0] - 1] = true;
                count++;
                res += c[1];
            }
            if (count == N){
                result.add(res);
            }//所有点都被读取过了
        }
        if (result.size() != 1) {
            System.out.print(-1);
        }else {
            System.out.print(result.get(0));
        }*/

        if (N <= 1)
            System.out.print(-1);
        if (connections.length < N - 1)//边数量小于点-1，不可能构成树
            System.out.print(-1);
        Arrays.sort(connections, Comparator.comparingInt(t -> t[2]));//按权重排序
        Union u = new Union(N);
        int count = 1;
        int res = 0;
        for (int[] connect : connections) {
            if (u.connected(connect[0] - 1, connect[1] - 1))//两点曾经连接过，没必要再连
                continue;
            u.unionF(connect[0] - 1, connect[1] - 1);
            count++;
            res += connect[2];
            if (count == N)//所有点都连上了
                result.add(res);
        }
        if (result.size() != 1) {
            System.out.print(-1);
        }else {
            System.out.print(result.get(0));
        }

    }
}
