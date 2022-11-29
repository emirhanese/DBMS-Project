// This is a generic class which is used in LinkedList and Stack.
public class Node<T> {
    
    T data;
    Node<T> next;

    public Node(T data) {

        this.data = data;
        this.next = null;
    }
}
