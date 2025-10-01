# Algorithm Benchmarking and Analysis

This project benchmarks and analyzes several fundamental algorithms:

- **MergeSort** (with insertion sort cutoff)
- **QuickSort** (randomized pivot, insertion sort cutoff)
- **Deterministic Selection** (median-of-medians, worst-case linear time)
- **Closest Pair of Points** (divide and conquer in 2D)

We record **metrics** (`comparisons`, `allocations`, `calls`, and `maxDepth`) to study recursion depth, cost of auxiliary storage, and constant-factor effects, alongside running time.

---

## Architecture Notes

- **Metrics class** tracks:
    - `comparisons`: number of key comparisons
    - `allocations`: number of data moves/temporary assignments
    - `calls`: recursive (or iterative “logical”) calls made
    - `maxDepth`: maximum recursion depth reached

- **MergeSort**: Recurses until subproblems are below a cutoff (e.g. 32), then switches to insertion sort. This reduces depth and improves cache behavior. Auxiliary array (`aux`) is reused across recursive calls to avoid repeated allocations.

- **QuickSort**: Uses randomized partitioning with tail-recursion elimination (always recurse into the smaller half, iterate on the larger). This guarantees **O(log n)** stack depth, preventing worst-case blowups. A cutoff triggers insertion sort for small partitions.

- **Deterministic Selection**: Implements the median-of-medians strategy. Groups of size 5 are used to ensure a good pivot, giving worst-case **O(n)** time. Tracks allocations during partitioning and comparisons during scanning.

- **Closest Pair**: Standard divide-and-conquer in the plane. After splitting on the median x-coordinate, recursively solve left/right halves, then scan a vertical strip around the median line. Sorting by y-coordinate in the strip ensures only 7 neighbors need checking. Depth corresponds to binary splitting of the point set.

---

## Recurrence Analysis

### MergeSort
- Recurrence: `T(n) = 2T(n/2) + Θ(n)` until cutoff, then insertion sort cost.
- By the **Master Theorem**, case 2: `T(n) = Θ(n log n)`.
- With cutoff, small subproblems avoid recursive overhead and run in `Θ(n²)` locally, but this only affects constant factors.

### QuickSort
- Recurrence: `T(n) = T(k) + T(n-k-1) + Θ(n)` with randomized pivot.
- In expectation, `T(n) = Θ(n log n)`.
- Worst-case (adversarial pivots) is `Θ(n²)`, but randomization plus tail-recursion elimination keeps recursion depth at `O(log n)` expected.

### Deterministic Selection
- Recurrence: `T(n) = T(n/5) + T(7n/10) + Θ(n)` (since pivot discards at least 30%).
- By **Akra–Bazzi theorem**, `T(n) = Θ(n)`.
- Constant factors are larger than randomized QuickSelect due to repeated grouping and sorting.

### Closest Pair
- Recurrence: `T(n) = 2T(n/2) + Θ(n)` (sorting by x at the top, strip merge step).
- Same as MergeSort → `T(n) = Θ(n log n)`.
- Strip check only compares constant neighbors, so linear work per level.

---

## Experimental Plots

### Time vs n
Insert a plot of running time against input size `n` for each algorithm.
- Expect MergeSort and QuickSort to scale close to `n log n`.
- Deterministic Select should grow roughly linearly.
- Closest Pair should follow `n log n`.

### Depth vs n
Insert a plot of recursion `maxDepth` against `n`.
- MergeSort → about `log₂(n)` until cutoff.
- QuickSort → `O(log n)` expected, with small variance due to pivot randomness.
- Deterministic Select → `O(log n)` but shallower because each level reduces size aggressively.
- Closest Pair → balanced binary splitting, depth `≈ log₂(n)`.

---

## Constant-Factor Discussion

- **Cutoffs to insertion sort** reduce runtime significantly, especially for small `n`, due to better cache locality and lower recursive overhead.
- **Java GC (garbage collection)**: frequent array copies or list allocations (as in Deterministic Select) introduce noise.
- **Random pivot in QuickSort**: small fluctuations in runtime due to variance in partition balance.
- **Closest Pair**: strip construction involves `ArrayList` allocations and sorting by y, which impacts constants but not asymptotic behavior.

---

## Summary

- **Theory vs Measurements**:
    - MergeSort and Closest Pair both follow `Θ(n log n)` growth, as expected.
    - QuickSort shows average `Θ(n log n)` but constant factors are smaller than MergeSort (due to in-place partitioning).
    - Deterministic Select confirms `Θ(n)` scaling but runs slower than QuickSort median-finding for practical `n`, due to higher constants.

- **Alignment**: Asymptotic theory matches measurements.
- **Mismatch**: Practical performance is dominated by constants (insertion cutoffs, memory allocations, cache effects). For selection, the theoretically optimal deterministic method is slower in practice than randomized approaches.

---
