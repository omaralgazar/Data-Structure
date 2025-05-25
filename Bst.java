import java.util.ArrayList;
import java.util.List;

public class Bst {
    Nodebst root;

    public void insert(Product item) {
        root = insertRec(root, item);
    }

    private Nodebst insertRec(Nodebst root, Product item) {
        if (root == null) return new Nodebst(item);
        if (item.id < root.item.id) root.left = insertRec(root.left, item);
        else if (item.id > root.item.id) root.right = insertRec(root.right, item);
        return root;
    }

    public Product search(int id) {
        return searchRec(root, id);
    }

    public List<Product> search(String name) {
        List<Product> result = new ArrayList<>();
        searchn(root, name, result);
        return result;
    }

    public List<Product> searchc(String category) {
        List<Product> result = new ArrayList<>();
        Searchca(root, category, result);
        return result;
    }


    private Product searchRec(Nodebst root, int id ) {
        if (root == null) return null;
        if (id == root.item.id) return root.item;
        return id < root.item.id ? searchRec(root.left, id) : searchRec(root.right, id);
    }
    private void searchn(Nodebst node, String name, List<Product> result) {
        if (node == null) return;

        searchn(node.left, name, result);

        if (name.equalsIgnoreCase(node.item.name)) {
            result.add(node.item);
        }

        searchn(node.right, name, result);
    }

    private void Searchca(Nodebst node, String category, List<Product> result) {
        if (node == null) return;

        Searchca(node.left, category, result);

        if (category.equalsIgnoreCase(node.item.category)) {
            result.add(node.item);
        }

        Searchca(node.right, category, result);
    }
}
