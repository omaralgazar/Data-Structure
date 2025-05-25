import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static Undostack undostack = new Undostack(); // Stack for undo function
    static Bst bst = new Bst(); // Binary Search Tree for searching
    static Linkedlist linkedlist = new Linkedlist(); // Linked List for main storage
    static Queue queue = new Queue(10); // Priority queue with capacity 10

    public static void main(String[] args) {
        int choice;
        do {
            // Display menu options
            System.out.println("""
                \n
                1. Add Product
                2. View All Products
                3. Update Product
                4. Delete Product
                5. Undo Last Delete
                6. Manage Priority Queue
                7. Search Product
                8. Save Products to File
                9. Load Products from File
                0. Exit
                Enter choice:\s""");
            choice = scanner.nextInt();
            scanner.nextLine();

            // Handle user choice
            switch (choice) {
                case 1 -> addItem();
                case 2 -> viewItems();
                case 3 -> updateItem();
                case 4 -> deleteItem();
                case 5 -> undoDelete();
                case 6 -> managePriority();
                case 7 -> searchItem();
                case 8 -> saveToFile();
                case 9 -> loadFromFile();
                case 0 -> System.out.println("Thank you");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    // Add a new product to the system
    public static void addItem() {
        // Get product details from user
        System.out.print("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Description: ");
        String desc = scanner.nextLine();
        System.out.print("Enter Category: ");
        String cat = scanner.nextLine();

        // Check if ID already exists
        if (linkedlist.find(id) != null) {
            System.out.println("This ID already exists.");
            System.out.println("Failed to add Item.");
        } else {
            // Create new product and add to data structures
            Product item = new Product(id, name, desc, cat);
            linkedlist.add(item);
            bst.insert(item);
            System.out.println("Item added.");
        }
    }

    // Display all products
    public static void viewItems(){
        List<Product> items = linkedlist.toList();
        if (items.isEmpty()) {
            System.out.println("No items to display.");
        } else {
            for (Product item : items)
                System.out.println(item.toString());
        }
    }

    // Update an existing product
    public static void updateItem(){
        System.out.print("Enter ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Product item = linkedlist.find(id);
        if (item == null) {//check if id is exist or not
            System.out.println("Item not found.");
            return;
        }
        // Update product details
        System.out.print("Enter new name: ");
        item.name = scanner.nextLine();
        System.out.print("Enter new description: ");
        item.description = scanner.nextLine();
        System.out.print("Enter new category: ");
        item.category = scanner.nextLine();
        System.out.println("Item updated.");
    }

    // Delete a product
    public static void deleteItem(){
        System.out.print("Enter ID to delete: ");
        int id = scanner.nextInt();
        Product deleted = linkedlist.delete(id);
        if (deleted != null) {
            undostack.push(deleted);
            System.out.println("Item deleted. You can undo this.");
        } else {
            System.out.println("Item not found.");
        }
    }


    // Undo the last delete operation
    public static void undoDelete(){
        if (!undostack.isEmpty()) {
            Product item = undostack.pop();
            linkedlist.add(item); // Restore from undo stack
            System.out.println("Undo complete. Item restored.");
        } else {
            System.out.println("Nothing to undo.");
        }
    }

    // Manage priority queue operations
    public static void managePriority() {
        System.out.println("1. Add to Priority Queue");
        System.out.println("2. View Priority Queue");
        System.out.println("3. Process Next Item");
        int ch = scanner.nextInt();
        scanner.nextLine();
        switch (ch) {
            case 1 -> {
                System.out.print("Enter ID of item to prioritize: ");
                int id = scanner.nextInt();
                scanner.nextLine();
                Product item = linkedlist.find(id);
                if (item == null) {
                    System.out.println("Item not found.");
                    return;
                }
                System.out.print("Enter Priority (Urgent/Normal): ");
                String prio = scanner.nextLine();
                if (!prio.equalsIgnoreCase("urgent") && !prio.equalsIgnoreCase("normal")) {
                    System.out.println("Invalid priority. Use 'Urgent' or 'Normal'.");
                    return;//check that user used urgent or normal only no other words and ignore case of letters
                }
                queue.enqueue(new Priority(item, prio));
                System.out.println("Item added to priority queue.");
            }
            case 2 -> queue.display();
            case 3 -> {
                Priority pItem = queue.dequeue();
                if (pItem != null) {
                    System.out.println("Next item in queue: " + pItem);
                    System.out.println("1. Process item");
                    System.out.println("2. Change priority");
                    System.out.println("3. Cancel");
                    int option = scanner.nextInt();
                    scanner.nextLine();

                    switch (option) {
                        case 1 -> {
                            // معالجة العنصر
                            System.out.println("Deleting: " + pItem);
                            System.out.println("Item removed from queue.");
                        }
                        case 2 -> {
                            // تغيير الأولوية
                            System.out.print("Enter new priority (Urgent/Normal): ");
                            String newPriority = scanner.nextLine();
                            if (!newPriority.equalsIgnoreCase("urgent") && !newPriority.equalsIgnoreCase("normal")) {
                                System.out.println("Invalid priority. Must be 'Urgent' or 'Normal'.");
                            } else {
                                queue.enqueue(new Priority(pItem.item, newPriority)); // إعادة إدخاله بالأولوية الجديدة
                                System.out.println("Priority updated and item re-added to queue.");
                            }
                        }
                        case 3 -> {
                            // إلغاء الإجراء
                            queue.enqueue(pItem); // نرجعه للطابور
                            System.out.println("Cancelled. Item returned to queue.");
                        }
                        default -> System.out.println("Invalid choice.");
                    }

                } else {
                    System.out.println("Queue is empty.");
                }
            }

        }
    }

    // Search for products using id or name or category
    public static void searchItem(){
        System.out.println("1. Search Item by ID:");
        System.out.println("2. Search Item by Name:");
        System.out.println("3. Search Item by Category:");
        int ch = scanner.nextInt();
        scanner.nextLine();
        switch (ch) {
            case 1 -> {
                System.out.print("Enter ID to search: ");
                int id = scanner.nextInt();
                Product item = bst.search(id);//search for product using binary search tree.
                System.out.println(item != null ? item : "Not found.");
            }
            case 2 -> {
                System.out.print("Enter Name to search: ");
                String name = scanner.nextLine();
                List<Product> items = bst.search(name);
                if (items.isEmpty()) {
                    System.out.println("No products found with that name.");
                } else {
                    for (Product item : items) {//print all products have the same name
                        System.out.println(item);
                    }
                }
            }
            case 3 -> { //search by category
                System.out.print("Enter Category to search: ");
                String category = scanner.nextLine();
                List<Product> items = bst.searchc(category);
                if (items.isEmpty()) {
                    System.out.println("No products found in this category.");
                } else {
                    for (Product item : items) {//print all products have the same category
                        System.out.println(item);
                    }
                }
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    // Save products to a file
    public static void saveToFile(){
        try (PrintWriter writer = new PrintWriter("items.txt")) {
            for (Product item : linkedlist.toList()) {
                writer.println(item.id + "," + item.name + "," + item.description + "," + item.category);
            }
            System.out.println("Items saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving to file.");
        }
    }

    // Load products from a file
    static void loadFromFile() {
        try (Scanner fileScanner = new Scanner(new File("items.txt"))) {
            while (fileScanner.hasNextLine()) {
                String[] data = fileScanner.nextLine().split(",");
                Product item = new Product(Integer.parseInt((data[0])), data[1], data[2], data[3]);
                if (linkedlist.find(item.id) == null) {// check if the id is already exist don't use it again.
                    linkedlist.add(item);
                    bst.insert(item);
                } else {
                    System.out.println("Id already exists:"+item.id);
                }
            }
            System.out.println("Items loaded from file.");
        } catch (IOException e) {
            System.out.println("Error loading from file.");
        }
    }
}