import java.util.*;

public class DeterministicSelect {
    public static int select(int[] arr, int k, Metrics m) {
        int lo = 0, hi = arr.length;
        int depth = 1;
        while (true) {
            m.countCall(depth);
            int n = hi - lo;
            if (n <= 10) {
                Arrays.sort(arr, lo, hi);
                m.comparisons += n;
                return arr[lo + k];
            }
            List<Integer> medians = new ArrayList<>();
            for (int i = lo; i < hi; i += 5) {
                int end = Math.min(i + 5, hi);
                int[] g = Arrays.copyOfRange(arr, i, end);
                Arrays.sort(g);
                medians.add(g[g.length / 2]);
                m.allocations += g.length;
            }
            int pivot = medianOfMedians(medians);
            // Partition 3-way
            int lt = lo, gt = hi - 1, i = lo;
            while (i <= gt) {
                m.comparisons++;
                if (arr[i] < pivot) { swap(arr, i++, lt++, m); }
                else if (arr[i] > pivot) { swap(arr, i, gt--, m); }
                else i++;
            }
            if (k < lt - lo) { hi = lt; continue; }
            else if (k <= gt - lo) return pivot;
            else { k = k - (gt - lo + 1); lo = gt + 1; }
        }
    }

    private static int medianOfMedians(List<Integer> list) {
        if (list.size() <= 10) {
            Collections.sort(list);
            return list.get(list.size() / 2);
        }
        List<Integer> medians = new ArrayList<>();
        for (int i = 0; i < list.size(); i += 5) {
            int end = Math.min(i + 5, list.size());
            List<Integer> g = list.subList(i, end);
            List<Integer> gCopy = new ArrayList<>(g);
            Collections.sort(gCopy);
            medians.add(gCopy.get(gCopy.size() / 2));
        }
        return medianOfMedians(medians);
    }

    private static void swap(int[] a, int i, int j, Metrics m) {
        int tmp = a[i]; a[i] = a[j]; a[j] = tmp;
        m.allocations += 3;
    }
}
