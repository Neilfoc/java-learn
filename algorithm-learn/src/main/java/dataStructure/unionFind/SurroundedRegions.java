package dataStructure.unionFind;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

/**
 * @author 11105157
 * @Description
 * @Date 2021/7/24
 */
// 130 被围绕的区域
public class SurroundedRegions {
    public void solve(char[][] board) {
        int m = board.length;
        int n = board[0].length;
        int dummy = m * n;

        // 初始化并查集
        UF uf = new UF(dummy + 1);

        // 将边缘两行融入dummy
        for (int i = 0; i < n; i++) {
            int p = 0 * n + i;
            if (board[0][i] == 'O') {
                uf.union(p, dummy);
            }
            int q = (m - 1) * n + i;
            if (board[m - 1][i] == 'O') {
                uf.union(q, dummy);
            }
        }

        // 将边缘两列融入dummy
        for (int i = 0; i < m; i++) {
            int p = i * n + 0;
            if (board[i][0] == 'O') {
                uf.union(p, dummy);
            }
            int q = i * n + n - 1;
            if (board[i][n - 1] == 'O') {
                uf.union(q, dummy);
            }
        }

        // 遍历board内部的值，并做联通
        int[][] d = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int i = 1; i < m - 1; i++) {
            for (int j = 1; j < n - 1; j++) {
                if (board[i][j] == 'O') {
                    for (int k = 0; k < 4; k++) {
                        int x = i + d[k][0];
                        int y = j + d[k][1];
                        if (board[x][y] == 'O') {
                            if (uf.connected(x * n + y, dummy)) {
                                uf.union(i * n + j, dummy);
                            } else {
                            }
                            uf.union(i * n + j, x * n + y);
                        }
                    }
                }
            }
        }
        //System.out.println(JSON.toJSONString(uf.getParent()));

        // 遍历board，将没联通的O转成X
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') {
                    if (!uf.connected(i * n + j, dummy)) {
                        board[i][j] = 'X';
                    }
                }
            }
        }
    }

    @Test
    public void test() {
        //char[][] board = {{'X', 'X', 'X', 'X'}, {'X', 'O', 'O', 'X'}, {'X', 'X', 'O', 'X'}, {'X', 'O', 'X', 'X'}};
        // [["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]
        // input [["O","X","X","O","X"],["X","O","O","X","O"],["X","O","X","O","X"],["O","X","O","O","O"],["X","X","O","X","O"]]
        char[][] board = {{'O', 'X', 'X', 'O', 'X'}, {'X', 'O', 'O', 'X', 'O'}, {'X', 'O', 'X', 'O', 'X'}, {'O', 'X', 'O', 'O', 'O'}, {'X', 'X', 'O', 'X', 'O'}};
        //output [["O","X","X","O","X"],["X","X","X","X","O"],["X","X","X","O","X"],["O","X","O","O","O"],["X","X","O","X","O"]]
        solve(board);
        System.out.println(JSON.toJSONString(board));
    }
}
