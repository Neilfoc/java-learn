package backTrack;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author neilfoc
 * @Description
 * @Date 2021/7/26
 */
public class NQueens1 {
    List<List<String>> result = new ArrayList<>();

    // 事先没定义n*n的board
    public List<List<String>> solveNQueens(int n) {
        LinkedList<String> list = new LinkedList<>();
        backtrack(n, list);
        return result;
    }

    private void backtrack(int n, LinkedList<String> list) {
        if (list.size() == n) {
            result.add(new ArrayList<>(list));
            return;
        }
        int i = list.size();
        char[][] c = new char[i][n];
        for (int k = 0; k < list.size(); k++) {
            c[k] = list.get(k).toCharArray();
        }

        for (int j = 0; j < n; j++) {
            char[] row = new char[n];
            for (int l = 0; l < n; l++) {
                row[l]='.';
            }
            row[j] = 'Q';
            String s = new String(row);

            if (!isValid(c, i, j)) {
                continue;
            }

            // 做选择
            list.add(s);
            backtrack(n, list);
            // 撤销选择
            list.removeLast();
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
        for (int i = row - 1, j = col + 1; i >= 0 && j < c[0].length; i--, j++) {// c[0]需要c不能为空，否则指针溢出，但是为空在上面的判断已经返回了
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
