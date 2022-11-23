# Disjoint sets API and implementation。

## 1. **Union find API**:
| Method  | Specification  |
|  ----  | ----  |
| UF(int N)  | initialize N sites with integer names (0 to N-1)  |
| void union(int p, int q)  | add connection between p and q |
| int find(int p)| component identifier for p (0 to N-1)| 
| boolean connected(int p, int q) | return true if p and q are in the same component |
| int count() | number of components |

一些词汇：
*component*：组成的set
*site*：set中的元素。


方法：
*find(int p)*：是在既定的p下面，找到它的component identifier。
*connected(int p, int q)*。是找这俩site是不是在一个component里面。
*count()*：是找多少component。
*union(int p, int q)*：是在没有site 在不同的component的情况下，把这两个component连接起来。count --；

## 2. 怎么设计它？
### 2.1 **QUICK Find**
这种方法是用一个 id array。每个element储存的内容是一个component中任意的一个代表性site。假设$\{3, 5, 8\}$连接起来，那么假设8是那个identifier，id[3] = 8, id[5] = 8.

所以我们的find很简单，因为是return一个代表性site.

``` java
int find(int p) {
    return id[p];
}
``` 
Connect方法因此也只要 ```return find[p] == find[q]```就行，所以这个也叫quickfind,因为它的find实在是太快了。时间复杂度是$\Theta (1)$.

union因此，如果对比的p和q中代表site一致，那就啥也不干，但是如果不同，那就要把其中一个component的site全部改成另外一个的形状。具体实现：

``` java
public void union(int p, int q) { 
    // Put p and q into the same component.
    int pID = find(p);
    int qID = find(q);
    // Nothing to do if p and q are already
    // in the same component.
    if (pID = qID) 
        return;
    // Change values from id[p] to id[q]. (反过来也一样)
    for (int i = 0; i < id.length; i++)
    if (id[i] == pID)
        id[i] = qID;
    count--;
}
```
[Anno]:时间复杂度用一次array access作为单位。
> When studying algorithms to implement the union-find API, we count array accesses (the number of times an array entry is accessed, for read or write).

但是因为每次union都话差不多n + 3 -- 2n + 1 的时间。但是从一个全是一个elemetn的set连成一个完整的set至少需要n - 1。这俩乘起来就是quadratic time。


### 2.2 **Quick-union**
第二种一样是array based data structure，只不过是一种tree的形式。一样id[] index本身是element的数字，但是每一个element装的是一个reference到它的母亲。最终的root就是每个element的identifier。（附：最终的root会指向自己）
``` java
public int find(int p)
{ // Find component name: find the name of the root 
    while (p != id[p]) // 在root 停下。
        p = id[p];
    return p;
}
```

而union也就很简单了，直接找到这俩的root看看相不相同，如果不相同那把一个接到另一个root上。这里是把5的root（1）接到9的root（8）上。
![](../../../pic/pic2/9.png)

``` java
public void union(int p, int q)
{ // Give p and q the same root.
    int i = find(p);
    int j = find(q);
    if (i == j) // identifier
        return;
    id[i] = j; // 左边是root，root里藏了一个reference到另外一个root，这样他们就是一家人了。
    count--;
}
```

为什么好：虽然find变慢了，但是union不需要$\Theta(N)$而稍微缓和了一下。（虽然worst case依然是$O(N)$）
> you can regard quick-union as an improvement over quick-find because it
removes quick-find’s main liability (that union() always takes linear time).

[anno] 树的size是多少个node，node的depth是从他到上面的root要多少跨度（root depth = 0）。height是对所有的node来说，最深的那个node的深度。

*find*: 1 + 2 * depth of p.
*union*: 2 * (1 + 2 * depth of p).

### 2.3 一种解法：weighted quick-union
![](../../../pic/pic2/10.png)
这就是在把一个root接上另一个root之前，比较一下他们的size，把小的接到大的上。假设每次都是worst case那么，每当$2^n$的tree接上$2^n$, 得到的也是一个$2^{n + 1}$的新tree。所以它的union可以想象，take $\log N$ time。

也就是加上一个判断的过程。
```java
public class WeightedQuickUnionUF
{
    private int[] id; // parent link (site indexed)
    private int[] sz; // size of component for roots (site indexed)
    private int count; // number of components
    public WeightedQuickUnionUF(int N)
    {
        count = N;
        id = new int[N];
        for (int i = 0; i < N; i++) 
            id[i] = i;
        sz = new int[N];
        for (int i = 0; i < N; i++) 
            sz[i] = 1;
    }
    public int count()
    { return count; }
    public boolean connected(int p, int q)
    { return find(p) == find(q); }
    public int find(int p)
    { // Follow links to find a root.
        while (p != id[p]) p = id[p];
        return p;
    }
    public void union(int p, int q)
    {
        int i = find(p);
        int j = find(q);
        if (i == j) return;
        // Make smaller root point to larger one.
        if (sz[i] < sz[j]) { id[i] = j; sz[j] += sz[i]; }
        else { id[j] = i; sz[i] += sz[j]; }
        count--;
    }
}
```

![](../../../pic/pic2/11.png)
其实变化就加了union中这一个比较。

### 最后的改进：compression
最后是weighted union with compression. 意思就是在每次union的时候不仅把最上面的root union上，而且把每个node都搞到目标root上面。这下height理想中就永远是1。

最终实现：来自红宝书。
```java
public class QuickUnionPathCompressionUF {
    private int[] id;    // id[i] = parent of i
    private int count;   // number of components

    /**
     * Initializes an empty union-find data structure with
     * {@code n} elements {@code 0} through {@code n-1}.
     * Initially, each element is in its own set.
     *
     * @param  n the number of elements
     * @throws IllegalArgumentException if {@code n < 0}
     */
    public QuickUnionPathCompressionUF(int n) {
        count = n;
        id = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
    }

    /**
     * Returns the number of sets.
     *
     * @return the number of sets (between {@code 1} and {@code n})
     */
    public int count() {
        return count;
    }

    /**
     * Returns the canonical element of the set containing element {@code p}.
     *
     * @param  p an element
     * @return the canonical element of the set containing {@code p}
     * @throws IllegalArgumentException unless {@code 0 <= p < n}
     */
    public int find(int p) {
        int root = p;
        while (root != id[root])
            root = id[root]; // 让指针最终指向root。
        while (p != root) {
            int newp = id[p]; // 相当于temp储存当前指针。
            id[p] = root;
            p = newp;
        }
        return root;
    }

    /**
     * Returns true if the two elements are in the same set.
     *
     * @param  p one element
     * @param  q the other element
     * @return {@code true} if {@code p} and {@code q} are in the same set;
     *         {@code false} otherwise
     * @throws IllegalArgumentException unless
     *         both {@code 0 <= p < n} and {@code 0 <= q < n}
     * @deprecated Replace with two calls to {@link #find(int)}.
     */
    @Deprecated
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    /**
     * Merges the set containing element {@code p} with the
     * the set containing element {@code q}.
     *
     * @param  p one element
     * @param  q the other element
     * @throws IllegalArgumentException unless
     *         both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;
        id[rootP] = rootQ;
        count--;
    }
}
```

