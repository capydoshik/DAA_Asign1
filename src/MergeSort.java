import java.util.*;

public class MergeSort {
    public static void sort(int[] arr, int cutoff, Metrics m) {
        int[] aux = new int[arr.length];
        sort(arr, 0, arr.length, cutoff, 1, m, aux);
    }

    private static void sort(int[] a, int lo, int hi, int cutoff, int depth, Metrics m, int[] aux) {
        m.countCall(depth);
        if (hi - lo <= cutoff) {
            insertionSort(a, lo, hi, m);
            return;
        }
        int mid = (lo + hi) / 2;
        sort(a, lo, mid, cutoff, depth + 1, m, aux);
        sort(a, mid, hi, cutoff, depth + 1, m, aux);
        merge(a, lo, mid, hi, aux, m);
    }

    private static void merge(int[] a, int lo, int mid, int hi, int[] aux, Metrics m) {
        int i = lo, j = mid, k = lo;
        while (i < mid && j < hi) {
            m.comparisons++;
            if (a[i] <= a[j]) aux[k++] = a[i++];
            else aux[k++] = a[j++];
            m.allocations++;
        }
        while (i < mid) { aux[k++] = a[i++]; m.allocations++; }
        while (j < hi) { aux[k++] = a[j++]; m.allocations++; }
        for (int t = lo; t < hi; t++) { a[t] = aux[t]; m.allocations++; }
    }

    private static void insertionSort(int[] a, int lo, int hi, Metrics m) {
        for (int i = lo + 1; i < hi; i++) {
            int key = a[i]; int j = i - 1;
            while (j >= lo && a[j] > key) {
                m.comparisons++;
                a[j+1] = a[j]; m.allocations++;
                j--;
            }
            if (j >= lo) m.comparisons++;
            a[j+1] = key; m.allocations++;
        }
    }
}
