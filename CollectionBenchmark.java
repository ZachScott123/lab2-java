import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CollectionBenchmark {
    
    // Test sizes: from small to large to see the growth curves
    private static final int[] SIZES = {1000, 5000, 10000, 50000, 100000, 500000, 1000000};
    
    // Number of warmup iterations to let JIT optimize
    private static final int WARMUP_ITERATIONS = 10000;
    
    // Number of measurement iterations for each operation
    private static final int MEASUREMENT_ITERATIONS = 10000;
    
    public static void main(String[] args) throws IOException {
        System.out.println("Starting Collection Time Complexity Benchmark...");
        
        // Prepare CSV file
        try (PrintWriter writer = new PrintWriter(new FileWriter("timeA.csv"))) {
            // Write CSV header
            writer.println("Collection,Operation,n,Time_ns");
            
            // Run all benchmarks
            benchmarkArrayList(writer);
            benchmarkLinkedList(writer);
            benchmarkHashSet(writer);
            benchmarkTreeSet(writer);
            benchmarkHashMap(writer);
            benchmarkTreeMap(writer);
            benchmarkPriorityQueue(writer);
        }
        
        System.out.println("Benchmark complete! Results written to timeA.csv");
    }
    
    // ============ ArrayList Benchmarks ============
    private static void benchmarkArrayList(PrintWriter writer) {
        System.out.println("Benchmarking ArrayList...");
        
        for (int n : SIZES) {
            // ArrayList.get(i) - O(1)
            long getTime = measureArrayListGet(n);
            writer.printf("ArrayList,get,%d,%d%n", n, getTime);
            
            // ArrayList.add(end) - O(1) amortized
            long addTime = measureArrayListAdd(n);
            writer.printf("ArrayList,add_end,%d,%d%n", n, addTime);
        }
    }
    
    private static long measureArrayListGet(int n) {
        // Create and populate list
        ArrayList<Integer> list = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            list.add(i);
        }
        
        // Warmup
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            list.get(i % n);
        }
        
        // Measure
        long totalTime = 0;
        for (int i = 0; i < MEASUREMENT_ITERATIONS; i++) {
            long start = System.nanoTime();
            list.get(i % n);
            long end = System.nanoTime();
            totalTime += (end - start);
        }
        
        return totalTime / MEASUREMENT_ITERATIONS; // Average time
    }
    
    private static long measureArrayListAdd(int n) {
        long totalTime = 0;
        
        for (int i = 0; i < MEASUREMENT_ITERATIONS; i++) {
            ArrayList<Integer> list = new ArrayList<>();
            
            long start = System.nanoTime();
            for (int j = 0; j < n; j++) {
                list.add(j);
            }
            long end = System.nanoTime();
            totalTime += (end - start);
        }
        
        return totalTime / MEASUREMENT_ITERATIONS / n; // Average per add
    }
    
    // ============ LinkedList Benchmarks ============
    private static void benchmarkLinkedList(PrintWriter writer) {
        System.out.println("Benchmarking LinkedList...");
        
        for (int n : SIZES) {
            // LinkedList.get(i) - O(n)
            long getTime = measureLinkedListGet(n);
            writer.printf("LinkedList,get,%d,%d%n", n, getTime);
            
            // LinkedList.addFirst - O(1)
            long addFirstTime = measureLinkedListAddFirst(n);
            writer.printf("LinkedList,addFirst,%d,%d%n", n, addFirstTime);
        }
    }
    
    private static long measureLinkedListGet(int n) {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            list.add(i);
        }
        
        // Warmup
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            list.get(i % n);
        }
        
        // Measure - average of middle element access (worst case)
        long totalTime = 0;
        int targetIndex = n / 2;
        for (int i = 0; i < MEASUREMENT_ITERATIONS; i++) {
            long start = System.nanoTime();
            list.get(targetIndex);
            long end = System.nanoTime();
            totalTime += (end - start);
        }
        
        return totalTime / MEASUREMENT_ITERATIONS;
    }
    
    private static long measureLinkedListAddFirst(int n) {
        long totalTime = 0;
        
        for (int i = 0; i < MEASUREMENT_ITERATIONS; i++) {
            LinkedList<Integer> list = new LinkedList<>();
            
            long start = System.nanoTime();
            for (int j = 0; j < n; j++) {
                list.addFirst(j);
            }
            long end = System.nanoTime();
            totalTime += (end - start);
        }
        
        return totalTime / MEASUREMENT_ITERATIONS / n;
    }
    
    // ============ HashSet Benchmarks ============
    private static void benchmarkHashSet(PrintWriter writer) {
        System.out.println("Benchmarking HashSet...");
        
        for (int n : SIZES) {
            // HashSet.contains - O(1) average
            long containsTime = measureHashSetContains(n);
            writer.printf("HashSet,contains,%d,%d%n", n, containsTime);
        }
    }
    
    private static long measureHashSetContains(int n) {
        HashSet<Integer> set = new HashSet<>(n * 2);
        for (int i = 0; i < n; i++) {
            set.add(i);
        }
        
        // Warmup
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            set.contains(i % n);
        }
        
        // Measure - test for elements that exist and don't exist
        long totalTime = 0;
        for (int i = 0; i < MEASUREMENT_ITERATIONS; i++) {
            int testValue = (i % 2 == 0) ? i % n : n + i; // Half exist, half don't
            long start = System.nanoTime();
            set.contains(testValue);
            long end = System.nanoTime();
            totalTime += (end - start);
        }
        
        return totalTime / MEASUREMENT_ITERATIONS;
    }
    
    // ============ TreeSet Benchmarks ============
    private static void benchmarkTreeSet(PrintWriter writer) {
        System.out.println("Benchmarking TreeSet...");
        
        for (int n : SIZES) {
            // TreeSet.contains - O(log n)
            long containsTime = measureTreeSetContains(n);
            writer.printf("TreeSet,contains,%d,%d%n", n, containsTime);
        }
    }
    
    private static long measureTreeSetContains(int n) {
        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < n; i++) {
            set.add(i);
        }
        
        // Warmup
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            set.contains(i % n);
        }
        
        // Measure
        long totalTime = 0;
        for (int i = 0; i < MEASUREMENT_ITERATIONS; i++) {
            long start = System.nanoTime();
            set.contains(i % n);
            long end = System.nanoTime();
            totalTime += (end - start);
        }
        
        return totalTime / MEASUREMENT_ITERATIONS;
    }
    
    // ============ HashMap Benchmarks ============
    private static void benchmarkHashMap(PrintWriter writer) {
        System.out.println("Benchmarking HashMap...");
        
        for (int n : SIZES) {
            // HashMap.get - O(1) average
            long getTime = measureHashMapGet(n);
            writer.printf("HashMap,get,%d,%d%n", n, getTime);
        }
    }
    
    private static long measureHashMapGet(int n) {
        HashMap<Integer, String> map = new HashMap<>(n * 2);
        for (int i = 0; i < n; i++) {
            map.put(i, "value" + i);
        }
        
        // Warmup
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            map.get(i % n);
        }
        
        // Measure
        long totalTime = 0;
        for (int i = 0; i < MEASUREMENT_ITERATIONS; i++) {
            long start = System.nanoTime();
            map.get(i % n);
            long end = System.nanoTime();
            totalTime += (end - start);
        }
        
        return totalTime / MEASUREMENT_ITERATIONS;
    }
    
    // ============ TreeMap Benchmarks ============
    private static void benchmarkTreeMap(PrintWriter writer) {
        System.out.println("Benchmarking TreeMap...");
        
        for (int n : SIZES) {
            // TreeMap.get - O(log n)
            long getTime = measureTreeMapGet(n);
            writer.printf("TreeMap,get,%d,%d%n", n, getTime);
        }
    }
    
    private static long measureTreeMapGet(int n) {
        TreeMap<Integer, String> map = new TreeMap<>();
        for (int i = 0; i < n; i++) {
            map.put(i, "value" + i);
        }
        
        // Warmup
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            map.get(i % n);
        }
        
        // Measure
        long totalTime = 0;
        for (int i = 0; i < MEASUREMENT_ITERATIONS; i++) {
            long start = System.nanoTime();
            map.get(i % n);
            long end = System.nanoTime();
            totalTime += (end - start);
        }
        
        return totalTime / MEASUREMENT_ITERATIONS;
    }
    
    // ============ PriorityQueue Benchmarks ============
    private static void benchmarkPriorityQueue(PrintWriter writer) {
        System.out.println("Benchmarking PriorityQueue...");
        
        for (int n : SIZES) {
            // PriorityQueue.offer - O(log n)
            long offerTime = measurePriorityQueueOffer(n);
            writer.printf("PriorityQueue,offer,%d,%d%n", n, offerTime);
            
            // PriorityQueue.peek - O(1)
            long peekTime = measurePriorityQueuePeek(n);
            writer.printf("PriorityQueue,peek,%d,%d%n", n, peekTime);
        }
    }
    
    private static long measurePriorityQueueOffer(int n) {
        long totalTime = 0;
        
        for (int i = 0; i < MEASUREMENT_ITERATIONS; i++) {
            PriorityQueue<Integer> pq = new PriorityQueue<>();
            
            long start = System.nanoTime();
            for (int j = 0; j < n; j++) {
                pq.offer(j);
            }
            long end = System.nanoTime();
            totalTime += (end - start);
        }
        
        return totalTime / MEASUREMENT_ITERATIONS / n;
    }
    
    private static long measurePriorityQueuePeek(int n) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            pq.offer(i);
        }
        
        // Warmup
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            pq.peek();
        }
        
        // Measure
        long totalTime = 0;
        for (int i = 0; i < MEASUREMENT_ITERATIONS; i++) {
            long start = System.nanoTime();
            pq.peek();
            long end = System.nanoTime();
            totalTime += (end - start);
        }
        
        return totalTime / MEASUREMENT_ITERATIONS;
    }
}