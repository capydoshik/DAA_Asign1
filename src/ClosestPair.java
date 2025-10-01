import java.util.*;

public class ClosestPair {
    public static class Point {
        double x, y;
        public Point(double x, double y) { this.x = x; this.y = y; }
    }

    public static double closestPair(Point[] points, Metrics m) {
        Arrays.sort(points, Comparator.comparingDouble(p -> p.x));
        return closestPair(points, 0, points.length, 1, m);
    }

    private static double closestPair(Point[] pts, int lo, int hi, int depth, Metrics m) {
        m.countCall(depth);
        int n = hi - lo;
        if (n <= 8) return bruteForce(pts, lo, hi, m);
        int mid = (lo + hi) / 2;
        double midx = pts[mid].x;
        double dl = closestPair(pts, lo, mid, depth + 1, m);
        double dr = closestPair(pts, mid, hi, depth + 1, m);
        double d = Math.min(dl, dr);
        List<Point> strip = new ArrayList<>();
        for (int i = lo; i < hi; i++) if (Math.abs(pts[i].x - midx) < d) strip.add(pts[i]);
        strip.sort(Comparator.comparingDouble(p -> p.y));
        for (int i = 0; i < strip.size(); i++) {
            for (int j = i+1; j < strip.size() && j <= i+7; j++) {
                d = Math.min(d, dist(strip.get(i), strip.get(j), m));
            }
        }
        return d;
    }

    private static double bruteForce(Point[] pts, int lo, int hi, Metrics m) {
        double best = Double.POSITIVE_INFINITY;
        for (int i = lo; i < hi; i++) {
            for (int j = i+1; j < hi; j++) {
                best = Math.min(best, dist(pts[i], pts[j], m));
            }
        }
        return best;
    }

    private static double dist(Point a, Point b, Metrics m) {
        m.comparisons++;
        m.allocations++;
        double dx = a.x - b.x, dy = a.y - b.y;
        return Math.hypot(dx, dy);
    }
}
