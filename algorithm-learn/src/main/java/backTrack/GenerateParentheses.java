package backTrack;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author neilfoc
 * @Description
 * @Date 2021/7/28
 */
// 22.括号生成（中等）
public class GenerateParentheses {
    List<String> list = new ArrayList<>();
    public List<String> generateParenthesis(int n) {
        StringBuilder sb = new StringBuilder();
        backtrack(sb, n, n);
        return list;
    }

    //穷举：括号字符串长2n，每一位上都有可能是'('或者')'
    private void backtrack(StringBuilder sb, int left, int right) {
        if (left == 0 && right == 0) {
            list.add(sb.toString());
        }
        if (left > right) {
            return;
        }
        if (left > 0) {
            sb.append("(");
            backtrack(sb, left - 1, right);
            sb.deleteCharAt(sb.length() - 1);
        }
        if (right > 0) {
            sb.append(")");
            backtrack(sb, left, right - 1);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    @Test
    public void test() {
        // ["((()))","(()())","(())()","()(())","()()()"]
        List<String> result = generateParenthesis(3);
        System.out.println(result);
    }
}
