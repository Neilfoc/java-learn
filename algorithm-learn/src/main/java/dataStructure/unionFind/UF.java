package dataStructure.unionFind;

import lombok.Data;

/**
 * @author 11105157
 * @Description
 * @Date 2021/7/23
 */
//用一个parent数组表示自己的父亲，初始值：自己
public class UF {
    // 记录连通分量个数
    private int count;
    // 存储若干棵树
    private int[] parent;
    // 记录树的“重量”
    private int[] size;

    public UF(int n) {
        count = n;
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    // 联通两个点
    public void union(int p, int q) {
        int pf = find(p);
        int qf = find(q);
        if (pf == qf) {
            return;
        }
        if (size[pf] > size[qf]) {
            parent[qf] = pf;
            size[pf] += size[qf];
        } else {
            parent[pf] = qf;
            size[qf] += size[pf];
        }
        count--;
    }

    // 连通分量
    public int count() {
        return count;
    }

    // 两点是否联通
    public boolean connected(int p, int q) {
        int pf = find(p);
        int qf = find(q);
        return pf == qf;
    }


    // 寻找点的父亲 【使用路径压缩】
    private int find(int p) {
        int pf = parent[p];
        while (pf != p) {
            parent[p] = parent[pf];
            p = parent[p];
            pf = parent[p];
        }
        return pf;
    }

    public int[] getParent(){
        return parent;
    }
}
