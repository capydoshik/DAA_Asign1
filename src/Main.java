import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        int n = 10000;
        int[] arr = ThreadLocalRandom.current().ints(n, 0, n*10).toArray();

        Metrics m1 = new Metrics();
        int[] a1 = Arrays.copyOf(arr, arr.length);
        long t1 = System.nanoTime();
        MergeSort.sort(a1, 32, m1);
        long t2 = System.nanoTime();
        System.out.println("MergeSort: " + m1 + " time(ms)=" + (t2-t1)/1e6);

        Metrics m2 = new Metrics();
        int[] a2 = Arrays.copyOf(arr, arr.length);
        long t3 = System.nanoTime();
        QuickSort.sort(a2, 16, m2);
        long t4 = System.nanoTime();
        System.out.println("QuickSort: " + m2 + " time(ms)=" + (t4-t3)/1e6);

        Metrics m3 = new Metrics();
        int[] a3 = Arrays.copyOf(arr, arr.length);
        long t5 = System.nanoTime();
        int median = DeterministicSelect.select(a3, a3.length/2, m3);
        long t6 = System.nanoTime();
        System.out.println("Deterministic Select median=" + median + " " + m3 + " time(ms)=" + (t6-t5)/1e6);

        Metrics m4 = new Metrics();
        ClosestPair.Point[] pts = new ClosestPair.Point[n];
        for (int i = 0; i < n; i++) pts[i] = new ClosestPair.Point(Math.random(), Math.random());
        long t7 = System.nanoTime();
        double best = ClosestPair.closestPair(pts, m4);
        long t8 = System.nanoTime();
        System.out.println("Closest Pair distance=" + best + " " + m4 + " time(ms)=" + (t8-t7)/1e6);
    }
}

