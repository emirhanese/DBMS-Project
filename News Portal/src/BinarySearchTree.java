import java.util.ArrayList;

public class BinarySearchTree {
    
    private class BSTNode {

        News data;
        BSTNode right;
        BSTNode left;

        BSTNode(News data) {

            this.data = data;
            this.right = null;
            this.left = null;
        }
    }

    BSTNode root;
    ArrayList<News> treeValues = new ArrayList<>(); // Stores the values inserted to the tree.

    public BinarySearchTree () {

        this.root = null;
    }

    // This function inserts a data to the tree.
    public void insert(News data) {

        this.root = insertRec(root, data);
    }

    // This function returns the root of the tree.
    public BSTNode getRoot() {

        return this.root;
    }

    // Recursive function to insert the data to the tree.
    private BSTNode insertRec(BSTNode root, News data) {

        if(root == null) {

            root = new BSTNode(data);
            this.treeValues.add(data);
            return root;
        }

        if(data.getViewsCounter() > root.data.getViewsCounter()) {

            root.right = insertRec(root.right, data);
        }

        else if(data.getViewsCounter() <= root.data.getViewsCounter()) {

            root.left = insertRec(root.left, data);
        }

        return root;
    }

    // Inorder traversal method.
    public void printInorder() {

        inorderRec(this.root);
    }

    // This function checks if user hasn't read any news yet.
    private boolean notViewedYet() {

        for(News item : this.treeValues) {

            if(item.getViewsCounter() != 0)
                return false;
        }

        return true;
    }

    // Recursive function to print the values inside the tree with inorder traversal.
    private void inorderRec(BSTNode root) {

        if(root != null) {

            inorderRec(root.left);
            System.out.println("Category: " + root.data.getCategory() + "\nNumber of views: " + root.data.getViewsCounter());
            inorderRec(root.right);
        }
    }

    // This function returns an arraylist which includes tree's values.
    private ArrayList<News> getTreeValues() {

        ArrayList<News> temp = new ArrayList<>();

        for(News el : this.treeValues) {

            temp.add(el);
        }

        return temp;
    }

    // This function deletes the whole tree.
    private void clearTree() {

        this.root = null;
        this.treeValues.clear();
    }

    // This function recreates the tree from an arraylist.
    public void updateTree() {

        ArrayList<News> temp = getTreeValues();
        clearTree();

        for(News item : temp) {

            insert(item);
        }
    }

    // This function finds the node which has the most read number by the user and then returns that node's category.
    public String getMostViewedCategory(BSTNode node) {

        if(notViewedYet())
            return "You haven't read any news yet.";

        while(node.right != null) {

            node = node.right;
        }

        return node.data.getCategory();
    }
}
