package backTrack;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * @author neilfoc
 * @Description
 * @Date 2021/7/26
 */
// 698 划分为k个相等的子集（中等） 【hard】
public class PartitionToKEqualSumSubsets {
    LinkedList<Integer> result = new LinkedList<>();

    public boolean canPartitionKSubsets(int[] nums, int k) {
        // 基本情况先做判断
        if (k > nums.length) {
            return false;
        }
        int total = 0;
        for (int num : nums) {
            total += num;
        }
        if (total % k != 0) {
            return false;
        }

        total /= k;
        LinkedList<Integer> list = new LinkedList<>();
        boolean[] used = new boolean[nums.length];
        return backtrack(nums, total, 0, k, used, 0);
        //return result.size() == nums.length;
    }

    //start是数组 k是桶
    private boolean backtrack(int[] nums, int target, int start, int k, boolean[] used, int sum) {
        if (k == 0) {
            return true;
        }

        if (sum == target) {
            return backtrack(nums, target, 0, k - 1, used, 0);
        }

        for (int i = start; i < nums.length; i++) {
            if (used[i]) {
                continue;
            }

            // 做选择
            used[i] = true;
            sum += nums[i];

            // 递归
            if (sum <= target) {
                //return backtrack(nums, target, i + 1, k, used, sum);
                if (backtrack(nums, target, i + 1, k, used, sum)) {
                    return true;
                }
            }

            // 撤销选择
            used[i] = false;
            sum -= nums[i];
        }
        return false;
    }

    // 自己写的，缺少桶的遍历处理
    private void backtrack(int[] nums, int total, LinkedList<Integer> list) {
        if (list.size() == nums.length) {
            result = new LinkedList<>(list);
            return;
        }
        int sum = 0, count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (list.contains(i)) {
                continue;
            }
            if (nums[i] + sum <= total) {
                list.add(i);
                sum += nums[i];
                count++;
            }
            if (sum == total) {
                backtrack(nums, total, list);
            }
            //撤销选择
            for (int j = 0; j < count; j++) {
                list.removeLast();
            }
        }
    }

    @Test
    public void test() {
        int[] nums = {1, 1, 1, 1, 2, 2, 2, 2};
        //int[] nums = {4, 3, 2, 3, 5, 2, 1};
        //int k = 4;
        int k = 4;
        boolean result = canPartitionKSubsets(nums, k);
        System.out.println(result);
    }
}
