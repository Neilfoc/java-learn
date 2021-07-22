package window;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author neilfoc
 * @Description
 * @Date 2021/7/22
 */
// 567
public class PermutationInString {
    public boolean checkInclusion(String s1, String s2) {
        char[] s1s = s1.toCharArray();
        char[] s2s = s2.toCharArray();
        Map<Character,Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();
        for (char i : s1s) {
            need.put(i, need.getOrDefault(i, 0) + 1);
        }
        int left = 0, right = s1.length();
        int valid = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (need.containsKey(s2s[i])) {
                window.put(s2s[i], window.getOrDefault(s2s[i], 0) + 1);
                if (window.get(s2s[i]).equals(need.get(s2s[i]))) {
                    valid++;
                }
            }
        }
        if (need.size() == valid) {
            return true;
        }
        while (right < s2.length()) {
            char c = s2s[right];
            right++;
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c))) {
                    valid++;
                }
            }
            left++;
            char d = s2s[left];
            if (need.containsKey(d)) {
                window.put(d, window.getOrDefault(d, 0) - 1);
                if (window.get(d).equals(need.get(d) - 1)) {
                    valid--;
                }
            }
            if (valid == need.size()) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void test(){
        String s1 = "ab";
        String s2 = "eidbaooo";
        boolean b = checkInclusion(s1, s2);
        System.out.println(b);
    }
}
