public class Metrics {
    public long comparisons = 0;
    public long allocations = 0;
    public long calls = 0;
    public long maxDepth = 0;

    public void countCall(int depth) {
        calls++;
        maxDepth = Math.max(maxDepth, depth);
    }

    @Override
    public String toString() {
        return String.format("comparisons=%d, allocations=%d, calls=%d, maxDepth=%d",
                comparisons, allocations, calls, maxDepth);
    }
}
