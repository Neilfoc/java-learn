package window;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author neilfoc
 * @Description
 * @Date 2021/7/22
 */
// 76 最小覆盖子串
// 滑动窗口是使用左右指针，通过遍历的方法，不需要使用递归
// 涉及到字符串和字符char的，感觉使用C++比较方便

/**
 * 1、当移动 right 扩大窗口，【即加入字符时，应该更新哪些数据】？valid，window
 * 2、什么条件下，窗口应该暂停扩大，开始移动 left 缩小窗口？
 * 3、当移动 left 缩小窗口，【即移出字符时，应该更新哪些数据】？valid，window
 * 4、我们要的结果应该在扩大窗口时还是缩小窗口时进行更新？【结果的返回受窗口缩小条件影响】
  */
public class MinimumWindowSubstring {

    public String minWindow(String s, String t) {

        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();
        char[] ss = s.toCharArray();
        char[] ts = t.toCharArray();
        for (char c : ts) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }
        int left = 0, right = 0;//遍历需要走完right
        int valid = 0;//判断need和window的关系
        int start = 0, len = Integer.MAX_VALUE; //最终的结果
        while (right < s.length()) {
            char c = ss[right];
            right++;
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c))) {// 这里必须要用equals，用==会有用例不通过
                    valid++;
                }
            }

            while (valid == need.size()) {
                // 更新最终结果，比较难
                if (right - left < len) {
                    start = left;
                    len = right - left;
                }

                char d = ss[left];
                left++;
                if (need.containsKey(d)) {
                    window.put(d, window.get(d) - 1);
                    if (window.get(d) < need.get(d)) {
                        valid--;
                    }
                }
            }

        }

        return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
    }

    @Test
    public void test() {
        String s = "cabwefgewcwaefgcf";
        String t = "cae";//cwae
        System.out.println(minWindow(s, t));
    }
}
