public class MyArrayList<T> {
    private Object[] elements;
    private int size;
    
    public MyArrayList() {
        elements = new Object[10];
        size = 0;
    }
    
    public void add(T element) {
        if (size == elements.length) {
            // This part doubles the array size.
            Object[] newArray = new Object[elements.length * 2];
            System.arraycopy(elements, 0, newArray, 0, size);
            elements = newArray;
        }
        elements[size++] = element;
    }
    
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return (T) elements[index];
    }
    
    public int size() {
        return size;
    }
}