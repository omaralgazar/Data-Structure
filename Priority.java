public class Priority {
    Product item;
    String priority;

    public Priority(Product item, String priority) {
        this.item = item;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return item.toString() + " | Priority: " + priority;
    }
}
