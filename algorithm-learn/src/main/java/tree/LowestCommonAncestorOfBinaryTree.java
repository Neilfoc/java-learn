package tree;

/**
 * @author 11105157
 * @Description 236. 二叉树的最近公共祖先
 * @Date 2021/5/11
 */
public class LowestCommonAncestorOfBinaryTree {

    // 使用递归
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        if (root == p || root == q) {
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) {
            return root;
        }
        return left != null ? left : right;
    }
}
