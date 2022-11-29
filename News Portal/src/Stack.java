// Generic Stack class.
public class Stack<T> {
    
    Node<T> headNode;

    public Stack() {

        this.headNode = null;
    }

    // This function pushes an element type T to the Stack.
    public void push(T title) {

        Node<T> node = new Node<>(title);

        if(headNode == null) {

            this.headNode = node;
        }

        else {

            node.next = this.headNode;
            headNode = node;
        }
    }

    // This function removes and returns the last element(Type T) pushed to the Stack.
    public T pop() {

        T poppedElement = null;

        if(!isEmpty()) {

            poppedElement = headNode.data;
            this.headNode = headNode.next;
        }

        return poppedElement;
    }

    // This function checks if the Stack is empty.
    public boolean isEmpty() {

        return this.headNode == null;
    }

    // This function prints out the values inside the Stack.
    public void printStack() {

        Node<T> temp = headNode;

        while(temp != null) {

            System.out.println("Data: " + temp.data);
            temp = temp.next;
        }
    }
}
