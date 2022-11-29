// Generic LinkedList class.
public class LinkedList<T> {

    Node<T> root;
    
    public LinkedList() {

        this.root = null;
    }

    // This functions adds a data which is type T(Generic) to Linked List.
    public void add(T data) {

        Node<T> nodeToBeAdded = new Node<>(data);

        if(this.root == null) {

            this.root = nodeToBeAdded;
        }

        else {

            nodeToBeAdded.next = this.root;
            this.root = nodeToBeAdded;
        }
    }


    // This is a searching algorithm which finds and returns the index of the specified data.
    public int indexOf(T data) {

        Node<T> temp = this.root;

        int index = 0;

        while(temp != null) {

            if(temp.data == data) {

                break;
            }

            index++;
            temp = temp.next;
        }

        return index;
    }

    // This function returns type T data by given index.
    public T get(int index) {

        Node<T> temp = this.root;

        int counter = 0;

        while(temp != null) {

            if(counter == index) {

                break;
            }

            counter++;
            temp = temp.next;
        }

        return temp.data;
    }

    // This function returns the length of the linked list.
    public int length() {

        Node<T> temp = this.root;

        int counter = 0;

        while(temp != null) {

            counter++;
            temp = temp.next;
        }

        return counter;
    }

    // This function prints out the linked list.
    public void printList() {

        Node<T> temp = this.root;

        int ctr = 0;

        while(temp != null) {

            System.out.println(ctr + 1 + " " + temp.data);
            ctr++;
            temp = temp.next;
        }
    }
}
