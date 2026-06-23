import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class ComparisonBenchmark {
    private static final int[] SIZES = {1000, 5000, 10000, 50000, 100000};
    private static final int WARMUP = 10000;
    private static final int MEASUREMENTS = 10000;
    
    public static void main(String[] args) throws IOException {
        System.out.println("Starting JDK vs Custom Comparison...");
        
        try (PrintWriter writer = new PrintWriter(new FileWriter("compareD.csv"))) {
            writer.println("Collection,Operation,n,Time_ns,MemoryBytes,IsCustom");
            
            // Compare ArrayList
            compareArrayList(writer);
            
            // Compare LinkedList  
            compareLinkedList(writer);
            
            // Compare HashMap
            compareHashMap(writer);
        }
        
        System.out.println("Comparison complete! compareD.csv generated.");
    }
    
    // ============ ARRAYLIST COMPARISON ============
    private static void compareArrayList(PrintWriter writer) {
        System.out.println("Comparing ArrayList...");
        
        for (int n : SIZES) {
            // JDK ArrayList - get operation
            long jdkGetTime = measureJdkArrayListGet(n);
            long jdkMemory = measureJdkArrayListMemory(n);
            writer.printf("ArrayList,get,%d,%d,%d,false%n", n, jdkGetTime, jdkMemory);
            
            // Custom MyArrayList - get operation
            long customGetTime = measureCustomArrayListGet(n);
            long customMemory = measureCustomArrayListMemory(n);
            writer.printf("MyArrayList,get,%d,%d,%d,true%n", n, customGetTime, customMemory);
            
            // JDK ArrayList - add operation
            long jdkAddTime = measureJdkArrayListAdd(n);
            writer.printf("ArrayList,add_end,%d,%d,%d,false%n", n, jdkAddTime, jdkMemory);
            
            // Custom MyArrayList - add operation
            long customAddTime = measureCustomArrayListAdd(n);
            writer.printf("MyArrayList,add_end,%d,%d,%d,true%n", n, customAddTime, customMemory);
        }
    }
    
    private static long measureJdkArrayListGet(int n) {
        ArrayList<Integer> list = new ArrayList<>(n);
        for (int i = 0; i < n; i++) list.add(i);
        
        // Warmup
        for (int i = 0; i < WARMUP && i < n; i++) {
            list.get(i % n);
        }
        
        // Measure
        long total = 0;
        for (int i = 0; i < MEASUREMENTS; i++) {
            long start = System.nanoTime();
            list.get(i % n);
            long end = System.nanoTime();
            total += (end - start);
        }
        return total / MEASUREMENTS;
    }
    
    private static long measureCustomArrayListGet(int n) {
        MyArrayList<Integer> list = new MyArrayList<>();
        for (int i = 0; i < n; i++) list.add(i);
        
        // Warmup
        for (int i = 0; i < WARMUP && i < n; i++) {
            list.get(i % n);
        }
        
        // Measure
        long total = 0;
        for (int i = 0; i < MEASUREMENTS; i++) {
            long start = System.nanoTime();
            list.get(i % n);
            long end = System.nanoTime();
            total += (end - start);
        }
        return total / MEASUREMENTS;
    }
    
    private static long measureJdkArrayListAdd(int n) {
        long total = 0;
        for (int i = 0; i < MEASUREMENTS; i++) {
            ArrayList<Integer> list = new ArrayList<>();
            long start = System.nanoTime();
            for (int j = 0; j < n; j++) {
                list.add(j);
            }
            long end = System.nanoTime();
            total += (end - start);
        }
        return total / MEASUREMENTS / n;
    }
    
    private static long measureCustomArrayListAdd(int n) {
        long total = 0;
        for (int i = 0; i < MEASUREMENTS; i++) {
            MyArrayList<Integer> list = new MyArrayList<>();
            long start = System.nanoTime();
            for (int j = 0; j < n; j++) {
                list.add(j);
            }
            long end = System.nanoTime();
            total += (end - start);
        }
        return total / MEASUREMENTS / n;
    }
    
    private static long measureJdkArrayListMemory(int n) {
        BenchmarkUtils.forceGC();
        long before = BenchmarkUtils.usedMemoryBytes();
        
        ArrayList<Integer> list = new ArrayList<>(n);
        for (int i = 0; i < n; i++) list.add(i);
        
        BenchmarkUtils.forceGC();
        long after = BenchmarkUtils.usedMemoryBytes();
        list = null;
        return (after - before) / n;
    }
    
    private static long measureCustomArrayListMemory(int n) {
        BenchmarkUtils.forceGC();
        long before = BenchmarkUtils.usedMemoryBytes();
        
        MyArrayList<Integer> list = new MyArrayList<>();
        for (int i = 0; i < n; i++) list.add(i);
        
        BenchmarkUtils.forceGC();
        long after = BenchmarkUtils.usedMemoryBytes();
        list = null;
        return (after - before) / n;
    }
    
    // ============ LINKEDLIST COMPARISON ============
    private static void compareLinkedList(PrintWriter writer) {
        System.out.println("Comparing LinkedList...");
        
        for (int n : SIZES) {
            // JDK LinkedList - get operation
            long jdkGetTime = measureJdkLinkedListGet(n);
            long jdkMemory = measureJdkLinkedListMemory(n);
            writer.printf("LinkedList,get,%d,%d,%d,false%n", n, jdkGetTime, jdkMemory);
            
            // Custom MyLinkedList - get operation
            long customGetTime = measureCustomLinkedListGet(n);
            long customMemory = measureCustomLinkedListMemory(n);
            writer.printf("MyLinkedList,get,%d,%d,%d,true%n", n, customGetTime, customMemory);
            
            // JDK LinkedList - addFirst operation
            long jdkAddFirstTime = measureJdkLinkedListAddFirst(n);
            writer.printf("LinkedList,addFirst,%d,%d,%d,false%n", n, jdkAddFirstTime, jdkMemory);
            
            // Custom MyLinkedList - addFirst operation
            long customAddFirstTime = measureCustomLinkedListAddFirst(n);
            writer.printf("MyLinkedList,addFirst,%d,%d,%d,true%n", n, customAddFirstTime, customMemory);
        }
    }
    
    private static long measureJdkLinkedListGet(int n) {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < n; i++) list.add(i);
        
        // Warmup
        for (int i = 0; i < WARMUP && i < n; i++) {
            list.get(i % n);
        }
        
        // Measure - access middle element (worst case)
        long total = 0;
        int targetIndex = n / 2;
        for (int i = 0; i < MEASUREMENTS; i++) {
            long start = System.nanoTime();
            list.get(targetIndex);
            long end = System.nanoTime();
            total += (end - start);
        }
        return total / MEASUREMENTS;
    }
    
    private static long measureCustomLinkedListGet(int n) {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        for (int i = 0; i < n; i++) list.addFirst(i);
        
        // Warmup
        for (int i = 0; i < WARMUP && i < n; i++) {
            list.get(i % n);
        }
        
        // Measure - access middle element (worst case)
        long total = 0;
        int targetIndex = n / 2;
        for (int i = 0; i < MEASUREMENTS; i++) {
            long start = System.nanoTime();
            list.get(targetIndex);
            long end = System.nanoTime();
            total += (end - start);
        }
        return total / MEASUREMENTS;
    }
    
    private static long measureJdkLinkedListAddFirst(int n) {
        long total = 0;
        for (int i = 0; i < MEASUREMENTS; i++) {
            LinkedList<Integer> list = new LinkedList<>();
            long start = System.nanoTime();
            for (int j = 0; j < n; j++) {
                list.addFirst(j);
            }
            long end = System.nanoTime();
            total += (end - start);
        }
        return total / MEASUREMENTS / n;
    }
    
    private static long measureCustomLinkedListAddFirst(int n) {
        long total = 0;
        for (int i = 0; i < MEASUREMENTS; i++) {
            MyLinkedList<Integer> list = new MyLinkedList<>();
            long start = System.nanoTime();
            for (int j = 0; j < n; j++) {
                list.addFirst(j);
            }
            long end = System.nanoTime();
            total += (end - start);
        }
        return total / MEASUREMENTS / n;
    }
    
    private static long measureJdkLinkedListMemory(int n) {
        BenchmarkUtils.forceGC();
        long before = BenchmarkUtils.usedMemoryBytes();
        
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < n; i++) list.add(i);
        
        BenchmarkUtils.forceGC();
        long after = BenchmarkUtils.usedMemoryBytes();
        list = null;
        return (after - before) / n;
    }
    
    private static long measureCustomLinkedListMemory(int n) {
        BenchmarkUtils.forceGC();
        long before = BenchmarkUtils.usedMemoryBytes();
        
        MyLinkedList<Integer> list = new MyLinkedList<>();
        for (int i = 0; i < n; i++) list.addFirst(i);
        
        BenchmarkUtils.forceGC();
        long after = BenchmarkUtils.usedMemoryBytes();
        list = null;
        return (after - before) / n;
    }
    
    // ============ HASHMAP COMPARISON ============
    private static void compareHashMap(PrintWriter writer) {
        System.out.println("Comparing HashMap...");
        
        for (int n : SIZES) {
            // JDK HashMap - get operation
            long jdkGetTime = measureJdkHashMapGet(n);
            long jdkMemory = measureJdkHashMapMemory(n);
            writer.printf("HashMap,get,%d,%d,%d,false%n", n, jdkGetTime, jdkMemory);
            
            // Custom MyHashMap - get operation
            long customGetTime = measureCustomHashMapGet(n);
            long customMemory = measureCustomHashMapMemory(n);
            writer.printf("MyHashMap,get,%d,%d,%d,true%n", n, customGetTime, customMemory);
            
            // JDK HashMap - put operation
            long jdkPutTime = measureJdkHashMapPut(n);
            writer.printf("HashMap,put,%d,%d,%d,false%n", n, jdkPutTime, jdkMemory);
            
            // Custom MyHashMap - put operation
            long customPutTime = measureCustomHashMapPut(n);
            writer.printf("MyHashMap,put,%d,%d,%d,true%n", n, customPutTime, customMemory);
        }
    }
    
    private static long measureJdkHashMapGet(int n) {
        HashMap<Integer, String> map = new HashMap<>(n * 2);
        for (int i = 0; i < n; i++) {
            map.put(i, "value" + i);
        }
        
        // Warmup
        for (int i = 0; i < WARMUP && i < n; i++) {
            map.get(i % n);
        }
        
        // Measure
        long total = 0;
        for (int i = 0; i < MEASUREMENTS; i++) {
            long start = System.nanoTime();
            map.get(i % n);
            long end = System.nanoTime();
            total += (end - start);
        }
        return total / MEASUREMENTS;
    }
    
    private static long measureCustomHashMapGet(int n) {
        MyHashMap<Integer, String> map = new MyHashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(i, "value" + i);
        }
        
        // Warmup
        for (int i = 0; i < WARMUP && i < n; i++) {
            map.get(i % n);
        }
        
        // Measure
        long total = 0;
        for (int i = 0; i < MEASUREMENTS; i++) {
            long start = System.nanoTime();
            map.get(i % n);
            long end = System.nanoTime();
            total += (end - start);
        }
        return total / MEASUREMENTS;
    }
    
    private static long measureJdkHashMapPut(int n) {
        long total = 0;
        for (int i = 0; i < MEASUREMENTS; i++) {
            HashMap<Integer, String> map = new HashMap<>();
            long start = System.nanoTime();
            for (int j = 0; j < n; j++) {
                map.put(j, "value" + j);
            }
            long end = System.nanoTime();
            total += (end - start);
        }
        return total / MEASUREMENTS / n;
    }
    
    private static long measureCustomHashMapPut(int n) {
        long total = 0;
        for (int i = 0; i < MEASUREMENTS; i++) {
            MyHashMap<Integer, String> map = new MyHashMap<>();
            long start = System.nanoTime();
            for (int j = 0; j < n; j++) {
                map.put(j, "value" + j);
            }
            long end = System.nanoTime();
            total += (end - start);
        }
        return total / MEASUREMENTS / n;
    }
    
    private static long measureJdkHashMapMemory(int n) {
        BenchmarkUtils.forceGC();
        long before = BenchmarkUtils.usedMemoryBytes();
        
        HashMap<Integer, String> map = new HashMap<>(n * 2);
        for (int i = 0; i < n; i++) {
            map.put(i, "value" + i);
        }
        
        BenchmarkUtils.forceGC();
        long after = BenchmarkUtils.usedMemoryBytes();
        map = null;
        return (after - before) / n;
    }
    
    private static long measureCustomHashMapMemory(int n) {
        BenchmarkUtils.forceGC();
        long before = BenchmarkUtils.usedMemoryBytes();
        
        MyHashMap<Integer, String> map = new MyHashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(i, "value" + i);
        }
        
        BenchmarkUtils.forceGC();
        long after = BenchmarkUtils.usedMemoryBytes();
        map = null;
        return (after - before) / n;
    }
}