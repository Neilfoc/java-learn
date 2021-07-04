package backTrack;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author neilfoc
 * @Description
 * @Date 2021/7/4
 */
// 51. N-Queens
public class NQueens {
    List<List<String>> result = new ArrayList<>();

    public List<List<String>> solveNQueens(int n) {
        LinkedList<String> list = new LinkedList<>();
        char[][] c = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                c[i][j] = '.';
            }
        }
        backTrack(c, list);
        return result;
    }

    private void backTrack(char[][] c, LinkedList<String> list) {
        int n = c.length;
        if (list.size() == n) {
            result.add(new ArrayList<>(list));
            return;
        }
        for (int i = 0; i < n; i++) {
            if (!isValid(c, list.size(), i)) {
                continue;
            }
            c[list.size()][i] = 'Q';
            String s = new String(c[list.size()]);
            list.add(s);
            backTrack(c, list);
            list.removeLast();
            c[list.size()][i] = '.';
        }
    }

    // 校验该位置能不能放Q
    private boolean isValid(char[][] c, int row, int col) {
        // 判断上面是否有Q
        for (int i = 0; i < row; i++) {
            if (c[i][col] == 'Q') {
                return false;
            }
        }

        // 判断左上角是否有Q
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (c[i][j] == 'Q') {
                return false;
            }
        }

        // 判断右上角是否有Q
        for (int i = row - 1, j = col + 1; i >= 0 && j < c.length; i--, j++) {
            if (c[i][j] == 'Q') {
                return false;
            }
        }
        return true;
    }

    @Test
    public void test(){
        List<List<String>> lists = solveNQueens(4);
        System.out.println(lists);
    }
}
