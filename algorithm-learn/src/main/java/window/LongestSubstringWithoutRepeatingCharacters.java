package window;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author neilfoc
 * @Description
 * @Date 2021/7/22
 */
// 3 最长无重复子串
public class LongestSubstringWithoutRepeatingCharacters {
    public int lengthOfLongestSubstring(String s) {
        char[] ss = s.toCharArray();
        Map<Character, Integer> window = new HashMap<>();
        int left = 0, right = 0;
        int len = 0;
        while (right < s.length()) {
            char c = ss[right];
            right++;
            // 扩张后操作
            window.put(c, window.getOrDefault(c, 0) + 1);
            while (window.get(c) > 1) {//收缩时机
                char d = ss[left];
                left++;
                //收缩后操作
                window.put(d, window.get(d) - 1);
            }
            //返回结果
            len = Math.max(len, right - left);
        }
        return len;
    }

    @Test
    public void test(){
        String s = "abcabcbb";//3
        int length = lengthOfLongestSubstring(s);
        System.out.println(length);
    }
}
