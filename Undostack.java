public class Undostack  {
    Stackar<Product> stack = new Stackar<>();

    public void push(Product item) {
        stack.push(item);
    }

    public Product pop() {
        return stack.isEmpty() ? null : stack.pop();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
