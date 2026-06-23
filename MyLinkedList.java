public class MyLinkedList<T> {
    private static class Node<T> {
        T data;
        Node<T> next;
        Node<T> prev;
        
        Node(T data) {
            this.data = data;
        }
    }
    
    private Node<T> head;
    private Node<T> tail;
    private int size;
    
    public void addFirst(T element) {
        Node<T> newNode = new Node<>(element);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }
    
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }
    
    public int size() {
        return size;
    }
}