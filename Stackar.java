import java.util.ArrayList;

public class Stackar<S> {
    private ArrayList<S> stack;

    public Stackar() {
        stack = new ArrayList<>();
    }
    public void push(S item) {
        stack.add(item);
    }


    public S pop() {
        if (isEmpty()) {
            System.out.println("Stack is empty. Cannot pop.");
            return null;
        }
        return stack.remove(stack.size() - 1);
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

}
