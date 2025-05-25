public class Queue {
    private Priority[] array;
    private int front;
    private int rear;
    private int size;
    private int capacity;

    public Queue(int capacity) {
        this.capacity = capacity;
        array = new Priority[capacity];
        front = 0;
        rear = -1;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public void enqueue(Priority p) {
        if (isFull()) {
            System.out.println("Queue is full. Cannot enqueue.");
            return;
        }

        if (isEmpty()) {
            rear = (rear + 1) % capacity;
            array[rear] = p;
            size++;
            return;
        }


        if (p.priority.equalsIgnoreCase("urgent")) {
            int i = size - 1;
            int idx;

            while (i >= 0) {
                idx = (front + i) % capacity;
                Priority current = array[idx];

                if (current.priority.equalsIgnoreCase("urgent")) {
                    break;
                }


                int nextIdx = (idx + 1) % capacity;
                array[nextIdx] = array[idx];
                i--;
            }


            int insertIdx = (front + i + 1) % capacity;
            array[insertIdx] = p;


            rear = (rear + 1) % capacity;
            size++;
        } else {

            rear = (rear + 1) % capacity;
            array[rear] = p;
            size++;
        }
    }


    public Priority dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is empty. Cannot dequeue.");
            return null;
        }
        Priority value = array[front];
        front = (front + 1) % capacity;
        size--;
        return value;
    }

    public void display() {
        if (isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }
        System.out.println("Queue: ");
        for (int i = 0; i < size; i++) {
            System.out.println(array[(front + i) % capacity] + " ");
        }
        System.out.println();
    }
}
