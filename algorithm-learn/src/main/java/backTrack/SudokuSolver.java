package backTrack;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

/**
 * @author neilfoc
 * @Description
 * @Date 2021/7/27
 */
// 37.解数独（困难）
public class SudokuSolver {
    public void solveSudoku(char[][] board) {
        backtrack(board, 0, 0);
    }

    private boolean backtrack(char[][] board, int i, int j) {
        if (i == board.length) {
            return true;
        }

        if (j == board.length) {
            //if (backtrack(board, i+1, 0)) {
            //    return true;
            //}
            return backtrack(board, i + 1, 0);
        }
        if (board[i][j] != '.') {
            //if (backtrack(board, i, j+1)) {
            //    return true;
            //}
            return backtrack(board, i, j + 1);
        }

        for (char c = '1'; c <= '9'; c++) {
            if (!isValid(board, i, j, c)) {
                continue;
            }
            board[i][j] = c;
            //backtrack(board, i, j + 1);
            if (backtrack(board, i, j + 1)) {
                return true;
            }
            board[i][j] = '.';
        }
        return false;
    }

    private boolean isValid(char[][] board, int i, int j,char c) {
        for (int k = 0; k < board.length; k++) {

            // 检测该行，ij值是'.'，所以不用单独再判断
            if (board[i][k] == c) {
                return false;
            }
            // 检测该列
            if (board[k][j] == c) {
                return false;
            }
            // 检测该3*3区域
            if (board[i / 3 * 3 + k / 3][j / 3 * 3 + k % 3] == c) {
                return false;
            }
        }
        return true;
    }


    @Test
    public void test() {
        char[][] board = {{'5', '3', '.', '.', '7', '.', '.', '.', '.'}, {'6', '.', '.', '1', '9', '5', '.', '.', '.'}, {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'}, {'4', '.', '.', '8', '.', '3', '.', '.', '1'}, {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'}, {'.', '.', '.', '4', '1', '9', '.', '.', '5'}, {'.', '.', '.', '.', '8', '.', '.', '7', '9'}};
        solveSudoku(board);
        System.out.println(JSON.toJSONString(board));
    }
}
