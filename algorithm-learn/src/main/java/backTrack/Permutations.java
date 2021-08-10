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
// 46.全排列（中等）
public class Permutations {
    List<List<Integer>> lists = new ArrayList<>();

    public List<List<Integer>> permute(int[] nums) {
        LinkedList<Integer> list = new LinkedList<>();
        boolean[] used = new boolean[nums.length];
        backtrack(nums, list, used);
        return lists;
    }

    private void backtrack(int[] nums, LinkedList<Integer> list, boolean[] used) {
        if (list.size() == nums.length) {
            lists.add(new ArrayList<>(list));
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) {
                continue;
            }
            list.add(nums[i]);
            used[i] = true;
            backtrack(nums, list, used);
            list.removeLast();
            used[i] = false;
        }
    }

    @Test
    public void test() {
        int[] nums = {1, 2, 3};
        List<List<Integer>> result = permute(nums);
        System.out.println(JSON.toJSONString(result));
    }
}
