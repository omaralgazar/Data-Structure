import java.util.ArrayList;
import java.util.List;

public class Linkedlist {
    private static Node head = null;
    private static Node tail = null;

    private static Node createNode(Product p) {
        return new Node(p);
    }

    public static void add(Product p) {
        Node n = createNode(p);
        if (head == null) {
            head = n;
            tail = n;
        } else {
            tail.next = n;
            n.prev = tail;
            tail = n;
        }
    }

    public Product delete(int id) {
        if (head == null) return null;

        if (head.data.id == id) {
            Product deleted = head.data;
            head = head.next;
            if (head == null) {
                tail = null; 
            } else {
                head.prev = null;
            }
            return deleted;
        }

        Node current = head;
        while (current.next != null) {
            if (current.next.data.id == id) {
                Product deleted = current.next.data;
                current.next = current.next.next;
                if (current.next != null) {
                    current.next.prev = current;
                } else {
                    tail = current; 
                }
                return deleted;
            }
            current = current.next;
        }
        return null;
    }


    public List<Product> toList() {
        List<Product> list = new ArrayList<>();
        Node current = head;
        while (current != null) {
            list.add(current.data);
            current = current.next;
        }
        return list;
    }
    public Product find(int id) {
        Node current = head;
        while (current != null) {
            if (current.data.id == id) return current.data;
            current = current.next;
        }
        return null;
    }
}
