public class BST<Key extends Comparable<Key>, Value> {
    private Node root; // root of binary search tree.

    private class Node{
        private Node left, right; // left and right side of the tree.
        private Value val; // Value
        private Key key; // Key
        private int N;

        public Node (Key key, Value val, int N)
        { this.key = key;this.val = val; this.N = N; }
    }

    public int size()
    { return size(root); }

    private int size(Node x)
    {
        if (x == null) {return  0;}
        else {return x.N;}
    }
    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        // Return value associated with key in the subtree rooted at x;
        // return null if key not present in subtree rooted at x.
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x.val;
    }
    // See page 399.
    public void put(Key key, Value val) {
        this.put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) {return new Node(key, val, 1)}
        int cmp = key.compareTo(x.key);
        if (cmp > 0) {
            x.right = put(x.right, key, val);
        } else if (cmp < 0) {
            x.left = put(x.left, key, val);
        } else {
            x.val = val;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

}