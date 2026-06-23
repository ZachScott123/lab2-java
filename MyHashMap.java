public class MyHashMap<K, V> {
    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;
        
        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    
    private Entry<K, V>[] buckets;
    private int size;
    private static final int DEFAULT_CAPACITY = 16;
    
    public MyHashMap() {
        buckets = new Entry[DEFAULT_CAPACITY];
        size = 0;
    }
    
    public void put(K key, V value) {
        int index = Math.abs(key.hashCode() % buckets.length);
        Entry<K, V> entry = buckets[index];
        
        while (entry != null) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
            entry = entry.next;
        }
        
        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = buckets[index];
        buckets[index] = newEntry;
        size++;
    }
    
    public V get(K key) {
        int index = Math.abs(key.hashCode() % buckets.length);
        Entry<K, V> entry = buckets[index];
        
        while (entry != null) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
            entry = entry.next;
        }
        return null;
    }
    
    public int size() {
        return size;
    }
}