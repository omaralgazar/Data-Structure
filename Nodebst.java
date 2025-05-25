public class Nodebst {
    Product item;
    Nodebst next;
    Nodebst left, right;

    public Nodebst(Product item) {
        this.item = item;
        this.next = null;
        this.left = this.right = null;
    }
}
