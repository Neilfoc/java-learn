package backTrack;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author neilfoc
 * @Description
 * @Date 2021/7/27
 */
// 78 子集（中等）
public class Subsets {
    List<List<Integer>> lists = new ArrayList<>();
    public List<List<Integer>> subsets(int[] nums) {
        LinkedList<Integer> list = new LinkedList<>();
        backtrack(nums, list, 0);
        return lists;
    }

    private void backtrack(int[] nums, LinkedList<Integer> list, int start) {
        lists.add(new ArrayList<>(list));
        for (int i = start; i < nums.length; i++) {
            list.add(nums[i]);
            backtrack(nums, list, i + 1);
            list.removeLast();
        }
    }

    @Test
    public void test(){
        int[] nums = {1, 2, 3};
        List<List<Integer>> result = subsets(nums);
        System.out.println(JSON.toJSONString(result));
    }
}
