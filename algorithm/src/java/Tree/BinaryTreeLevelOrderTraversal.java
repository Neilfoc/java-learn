package Tree;

import org.junit.Test;

import java.util.*;

/**
 * @author neilfoc
 * @date 2021/5/9 - 14:58
 */
public class BinaryTreeLevelOrderTraversal {
    List<List<Integer>> lists = new ArrayList();


    public List<List<Integer>> levelOrder(TreeNode root) {
        processV1(root, 1);
        if (map.size() == 0) {
            return lists;
        }
        int level = 1;
        while (map.get(level) != null) {
            lists.add(map.get(level));
            level++;
        }
        return lists;
    }

    // 1.使用一个map<integer，list>，然后使用前序遍历，记录每一层，这其实是深度优先遍历DFS
    Map<Integer,List<Integer>> map = new HashMap<>();
    private void processV1(TreeNode node, int level) {
        if (node == null) {
            return;
        }
        if (map.get(level)==null) {
            List<Integer> list = new ArrayList<>();
            list.add(node.val);
            map.put(level, list);
            processV1(node.left, level + 1);
            processV1(node.right, level + 1);
        }else {
            List<Integer> list = map.get(level);
            list.add(node.val);
            processV1(node.left, level + 1);
            processV1(node.right, level + 1);
        }
    }


    // 2.使用队列，利用广度优先遍历BFS
    Queue<TreeNode> queue = new LinkedList<>();

    private void processV2(Queue<TreeNode> queue) {
        if (queue.isEmpty()) {
            return;
        }
        List<Integer> list = new ArrayList<>();
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            TreeNode node = queue.poll();
            list.add(node.val);
            //注意：要判断孩子是否为空，直接插入也会把null插进去，我以为不会。
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
        lists.add(list);
        processV2(queue);
    }


    @Test
    public void test(){
        TreeNode root = new TreeNode(3);
        root.setLeft(new TreeNode(9));
        root.setRight(new TreeNode(20));
        root.getRight().setLeft(new TreeNode(15));
        root.getRight().setRight(new TreeNode(7));
        queue.add(root);
        processV2(queue);
    }
}
