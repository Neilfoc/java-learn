package backTrack;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author neilfoc
 * @Description
 * @Date 2021/7/29
 */
// 77.组合（中等）
public class Combinations {
    List<List<Integer>> lists = new ArrayList<>();
    public List<List<Integer>> combine(int n, int k) {
        LinkedList<Integer> list = new LinkedList<>();
        backtrack(1, n, k, list);
        return lists;
    }

    private void backtrack(int start, int n, int k, LinkedList<Integer> list) {
        if (list.size() == k) {
            lists.add(new ArrayList<>(list));
        }
        for (int i = start; i <= n; i++) {
            list.add(i);
            backtrack(i + 1, n, k, list);
            list.removeLast();
        }
    }

    @Test
    public void test(){
        List<List<Integer>> result = combine(4, 2);
        System.out.println(JSON.toJSONString(result));
    }
}
