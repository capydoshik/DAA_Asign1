import java.util.concurrent.ThreadLocalRandom;

public class QuickSort {
    public static void sort(int[] arr, int cutoff, Metrics m) {
        sort(arr, 0, arr.length, cutoff, 1, m);
    }

    private static void sort(int[] a, int lo, int hi, int cutoff, int depth, Metrics m) {
        m.countCall(depth);
        while (hi - lo > cutoff) {
            int p = partition(a, lo, hi, m);
            int leftSize = p - lo, rightSize = hi - (p + 1);
            if (leftSize < rightSize) {
                sort(a, lo, p, cutoff, depth + 1, m);
                lo = p + 1; // iterate on larger
            } else {
                sort(a, p + 1, hi, cutoff, depth + 1, m);
                hi = p;
            }
        }
        insertionSort(a, lo, hi, m); // ✅ fixed base case
    }

    private static int partition(int[] a, int lo, int hi, Metrics m) {
        int pivotIndex = ThreadLocalRandom.current().nextInt(lo, hi);
        int pivot = a[pivotIndex];
        swap(a, pivotIndex, hi - 1, m);
        int store = lo;
        for (int i = lo; i < hi - 1; i++) {
            m.comparisons++;
            if (a[i] < pivot) {
                swap(a, i, store, m);
                store++;
            }
        }
        swap(a, store, hi - 1, m);
        return store;
    }

    private static void swap(int[] a, int i, int j, Metrics m) {
        int tmp = a[i]; a[i] = a[j]; a[j] = tmp;
        m.allocations += 3;
    }

    private static void insertionSort(int[] a, int lo, int hi, Metrics m) {
        for (int i = lo + 1; i < hi; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= lo && a[j] > key) {
                m.comparisons++;
                a[j+1] = a[j];
                m.allocations++;
                j--;
            }
            if (j >= lo) m.comparisons++;
            a[j+1] = key;
            m.allocations++;
        }
    }
}
