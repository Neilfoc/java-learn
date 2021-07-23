package window;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 11105157
 * @Description
 * @Date 2021/7/22
 */
// 438 找出所有字母异位词
public class FindAllAnagramsInAString {

    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        char[] ss = s.toCharArray();
        char[] ps = p.toCharArray();
        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();
        for (char i : ps) {
            need.put(i, need.getOrDefault(i, 0) + 1);
        }

        int left = 0, right = 0;
        int valid = 0;
        while (right < s.length()) {
            char c = ss[right];
            right++;
            if (need.containsKey(c)) { // 1.移入字符后该做什么
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c))) {
                    valid++;
                }
            }

            while (right - left > p.length()) {// 2.什么条件下开始移出字符
                char d = ss[left];
                left++;
                if (need.containsKey(d)) {// 3.移出字符要做什么
                    window.put(d, window.get(d) - 1);
                    if (window.get(d).equals(need.get(d) - 1)) {
                        valid--;
                    }
                }
            }

            if (valid == need.size()) { // 4.结果在缩小窗口时更新
                result.add(left);
            }
        }
        return result;
    }

    @Test
    public void test() {
        String s = "cbaebabacd", p = "abc";//[0,6]
        List<Integer> list = findAnagrams(s, p);
        System.out.println(list);
    }
}
