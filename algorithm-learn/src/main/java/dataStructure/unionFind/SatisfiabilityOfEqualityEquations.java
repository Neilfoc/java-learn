package dataStructure.unionFind;

import org.junit.Test;

/**
 * @author neilfoc
 * @Description
 * @Date 2021/7/25
 */
// 990 判定合法等式
public class SatisfiabilityOfEqualityEquations {
    public boolean equationsPossible(String[] equations) {
        if (equations.length == 0) {
            return true;
        }

        // 构造一个26长度（小写字母）的并查集
        UF uf = new UF(26);

        // 先遍历数组中的==，做下联通
        for (String equation : equations) {
            if (equation.charAt(1) == '=') {
                uf.union(equation.charAt(0) - 'a', equation.charAt(3) - 'a');
            }
        }

        // 遍历数组中的!=,
        for (String equation : equations) {
            if (equation.charAt(1) == '!') {
                if (uf.connected(equation.charAt(0) - 'a', equation.charAt(3) - 'a')) {
                    return false;
                }
            }
        }

        return true;
    }


    @Test
    public void test(){
        //String[] equations = {"a==b", "b!=c", "c==a"}; //false
        String[] equations = {"c==c","b==d","x!=z"}; // true
        boolean result = equationsPossible(equations);
        System.out.println(result);
    }
}
