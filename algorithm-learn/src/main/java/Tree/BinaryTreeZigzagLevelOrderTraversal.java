package Tree;

import java.util.*;


/**
 * @author neilfoc
 * @Description 103.二叉树的锯齿形层序遍历
 * @date 2021/5/10 - 22:58
 */
public class BinaryTreeZigzagLevelOrderTraversal {


    List<List<Integer>> lists = new ArrayList<>();
    Queue<TreeNode> queue = new LinkedList<>();

    // 先做完层序遍历，再把偶数行reverse一下
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null) {
            return lists;
        }
        queue.add(root);
        processV1(queue);
        int s = lists.size();
        for (int i = 1; i < s; i=i+2) {
            Collections.reverse(lists.get(i));
        }
        return lists;
    }

    // 使用BFS进行层序遍历,再把偶数行reverse一下
    private void processV1(Queue<TreeNode> queue) {
        if (queue.isEmpty()) {
            return;
        }
        List<Integer> list = new ArrayList<>();
        int s = queue.size();
        for (int i = 0; i < s; i++) {
            TreeNode node = queue.poll();
            list.add(node.val);
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
        lists.add(list);
        processV1(queue);
    }

    Deque<TreeNode> deque = new LinkedList<>();
    // 使用双端队列，通过BFS进行层序遍历，加入一个一个布尔值reverse
    private void processV2(Deque<TreeNode> deque, boolean reverse) {
        if (deque.isEmpty()) {
            return;
        }
        int s = deque.size();
        List<Integer> list = new ArrayList<>();
        if (!reverse) {
            for (int i = 0; i < s; i++) {
                TreeNode node = deque.pollFirst();
                list.add(node.val);
                if (node.left != null) {
                    deque.addLast(node.left);
                }
                if (node.right != null) {
                    deque.addLast(node.right);
                }
            }
        } else {
            for (int i = 0; i < s; i++) {
                TreeNode node = deque.pollLast();
                list.add(node.val);
                if (node.right != null) {
                    deque.addFirst(node.right);
                }
                if (node.left != null) {
                    deque.addFirst(node.left);
                }
            }
        }
        lists.add(list);
        processV2(deque,!reverse);
    }
}
