import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MemoryBenchmark {
    
    // Test sizes for memory measurement
    private static final int[] SIZES = {1000, 5000, 10000, 50000, 100000, 500000, 1000000};
    
    public static void main(String[] args) throws IOException {
        System.out.println("Starting Memory Footprint Benchmark...");
        System.out.println("This will take a few minutes...");
        
        // Force GC before starting
        BenchmarkUtils.forceGC();
        long baselineMemory = BenchmarkUtils.usedMemoryBytes();
        System.out.println("Baseline memory: " + baselineMemory / 1024 + " KB");
        
        try (PrintWriter writer = new PrintWriter(new FileWriter("memoryB.csv"))) {
            // CSV Header
            writer.println("Collection,n,TotalBytes,Elements,BytesPerElement");
            
            // Test each collection
            testArrayList(writer);
            testLinkedList(writer);
            testHashSet(writer);
            testTreeSet(writer);
            testHashMap(writer);
            testTreeMap(writer);
            testPriorityQueue(writer);
            testArrayDeque(writer);
        }
        
        System.out.println("Memory benchmark complete! Results written to memoryB.csv");
    }
    
    private static void testArrayList(PrintWriter writer) {
        System.out.println("Testing ArrayList memory...");
        for (int n : SIZES) {
            BenchmarkUtils.forceGC();
            long before = BenchmarkUtils.usedMemoryBytes();
            
            ArrayList<Integer> list = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                list.add(i);
            }
            
            BenchmarkUtils.forceGC();
            long after = BenchmarkUtils.usedMemoryBytes();
            long totalBytes = after - before;
            long bytesPerElement = totalBytes / n;
            
            writer.printf("ArrayList,%d,%d,%d,%d%n", n, totalBytes, n, bytesPerElement);
            writer.flush();
            System.out.printf("  n=%,d: %,d bytes (%,d bytes/element)%n", n, totalBytes, bytesPerElement);
            
            list = null;
        }
    }
    
    private static void testLinkedList(PrintWriter writer) {
        System.out.println("Testing LinkedList memory...");
        for (int n : SIZES) {
            BenchmarkUtils.forceGC();
            long before = BenchmarkUtils.usedMemoryBytes();
            
            LinkedList<Integer> list = new LinkedList<>();
            for (int i = 0; i < n; i++) {
                list.add(i);
            }
            
            BenchmarkUtils.forceGC();
            long after = BenchmarkUtils.usedMemoryBytes();
            long totalBytes = after - before;
            long bytesPerElement = totalBytes / n;
            
            writer.printf("LinkedList,%d,%d,%d,%d%n", n, totalBytes, n, bytesPerElement);
            writer.flush();
            System.out.printf("  n=%,d: %,d bytes (%,d bytes/element)%n", n, totalBytes, bytesPerElement);
            
            list = null;
        }
    }
    
    private static void testHashSet(PrintWriter writer) {
        System.out.println("Testing HashSet memory...");
        for (int n : SIZES) {
            BenchmarkUtils.forceGC();
            long before = BenchmarkUtils.usedMemoryBytes();
            
            HashSet<Integer> set = new HashSet<>(n * 2);
            for (int i = 0; i < n; i++) {
                set.add(i);
            }
            
            BenchmarkUtils.forceGC();
            long after = BenchmarkUtils.usedMemoryBytes();
            long totalBytes = after - before;
            long bytesPerElement = totalBytes / n;
            
            writer.printf("HashSet,%d,%d,%d,%d%n", n, totalBytes, n, bytesPerElement);
            writer.flush();
            System.out.printf("  n=%,d: %,d bytes (%,d bytes/element)%n", n, totalBytes, bytesPerElement);
            
            set = null;
        }
    }
    
    private static void testTreeSet(PrintWriter writer) {
        System.out.println("Testing TreeSet memory...");
        for (int n : SIZES) {
            BenchmarkUtils.forceGC();
            long before = BenchmarkUtils.usedMemoryBytes();
            
            TreeSet<Integer> set = new TreeSet<>();
            for (int i = 0; i < n; i++) {
                set.add(i);
            }
            
            BenchmarkUtils.forceGC();
            long after = BenchmarkUtils.usedMemoryBytes();
            long totalBytes = after - before;
            long bytesPerElement = totalBytes / n;
            
            writer.printf("TreeSet,%d,%d,%d,%d%n", n, totalBytes, n, bytesPerElement);
            writer.flush();
            System.out.printf("  n=%,d: %,d bytes (%,d bytes/element)%n", n, totalBytes, bytesPerElement);
            
            set = null;
        }
    }
    
    private static void testHashMap(PrintWriter writer) {
        System.out.println("Testing HashMap memory...");
        for (int n : SIZES) {
            BenchmarkUtils.forceGC();
            long before = BenchmarkUtils.usedMemoryBytes();
            
            HashMap<Integer, String> map = new HashMap<>(n * 2);
            for (int i = 0; i < n; i++) {
                map.put(i, "value" + i);
            }
            
            BenchmarkUtils.forceGC();
            long after = BenchmarkUtils.usedMemoryBytes();
            long totalBytes = after - before;
            long bytesPerElement = totalBytes / n;
            
            writer.printf("HashMap,%d,%d,%d,%d%n", n, totalBytes, n, bytesPerElement);
            writer.flush();
            System.out.printf("  n=%,d: %,d bytes (%,d bytes/element)%n", n, totalBytes, bytesPerElement);
            
            map = null;
        }
    }
    
    private static void testTreeMap(PrintWriter writer) {
        System.out.println("Testing TreeMap memory...");
        for (int n : SIZES) {
            BenchmarkUtils.forceGC();
            long before = BenchmarkUtils.usedMemoryBytes();
            
            TreeMap<Integer, String> map = new TreeMap<>();
            for (int i = 0; i < n; i++) {
                map.put(i, "value" + i);
            }
            
            BenchmarkUtils.forceGC();
            long after = BenchmarkUtils.usedMemoryBytes();
            long totalBytes = after - before;
            long bytesPerElement = totalBytes / n;
            
            writer.printf("TreeMap,%d,%d,%d,%d%n", n, totalBytes, n, bytesPerElement);
            writer.flush();
            System.out.printf("  n=%,d: %,d bytes (%,d bytes/element)%n", n, totalBytes, bytesPerElement);
            
            map = null;
        }
    }
    
    private static void testPriorityQueue(PrintWriter writer) {
        System.out.println("Testing PriorityQueue memory...");
        for (int n : SIZES) {
            BenchmarkUtils.forceGC();
            long before = BenchmarkUtils.usedMemoryBytes();
            
            PriorityQueue<Integer> pq = new PriorityQueue<>(n);
            for (int i = 0; i < n; i++) {
                pq.offer(i);
            }
            
            BenchmarkUtils.forceGC();
            long after = BenchmarkUtils.usedMemoryBytes();
            long totalBytes = after - before;
            long bytesPerElement = totalBytes / n;
            
            writer.printf("PriorityQueue,%d,%d,%d,%d%n", n, totalBytes, n, bytesPerElement);
            writer.flush();
            System.out.printf("  n=%,d: %,d bytes (%,d bytes/element)%n", n, totalBytes, bytesPerElement);
            
            pq = null;
        }
    }
    
    private static void testArrayDeque(PrintWriter writer) {
        System.out.println("Testing ArrayDeque memory...");
        for (int n : SIZES) {
            BenchmarkUtils.forceGC();
            long before = BenchmarkUtils.usedMemoryBytes();
            
            ArrayDeque<Integer> deque = new ArrayDeque<>(n);
            for (int i = 0; i < n; i++) {
                deque.add(i);
            }
            
            BenchmarkUtils.forceGC();
            long after = BenchmarkUtils.usedMemoryBytes();
            long totalBytes = after - before;
            long bytesPerElement = totalBytes / n;
            
            writer.printf("ArrayDeque,%d,%d,%d,%d%n", n, totalBytes, n, bytesPerElement);
            writer.flush();
            System.out.printf("  n=%,d: %,d bytes (%,d bytes/element)%n", n, totalBytes, bytesPerElement);
            
            deque = null;
        }
    }
}