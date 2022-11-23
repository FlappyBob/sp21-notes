# 5. searching and sorting

## 引言
这是nyu的内容。主要就是讲search and sorting. 

## Searching
**Linear Search**
``` java
/** Linear Search */
int linearSearch(Point[] points, Point p) {
    if ( p == null )
        return -1;
    for (int i = 0; i < point.length; i ++) {
        if (p.equals(points[i])) return i;
    } 
    return -1;
}
```
**Binary Search**
```java
/** Iterative implementation of binary search algorithm. */
int binSearchIterative( Point[] points, Point p) {
    int left = 0;
    int right = points.length - 1;
    int mid;
    while ( left <= right ) { //array is not empty
        mid = (left+right) / 2;
        if (0 > points[mid].compareTo(p) )       //mid element is smaller than p
          left = mid+1;                          //discard left half
        else if ( 0 < points[mid].compareTo(p) ) //mid element is larger than p
          right = mid-1;                         //discard right half
        else
          return mid;                            //found it, return the index
        }
    return -1;          //did not find it, return -1
}

/** Recursive implementation of binary search algorithm. */
private int binSearchRecursive( Point[] points, Point p, int left, int right) {
    if (right < left) {
        return -1;
    }
    int mid = (right + left) / 2;
    int cmp = p.compareTo(points[mid]);\
    if (cmp == 0) {
        return mid;
    } else if  (cmp  > 0) {
        return binSearchRecursive(points, p, mid + 1, right);
    } else if  (cmp < 0) {
        return binSearchRecursive(points, p, left, mid - 1);
    }
}

public int binSearchRecursive( Point[] points, Point p) {
    if (points == null ) throw new NoSuchElementException("array is null");
    if (p == null) return -1;
    int left = 0;
    int right = points.length - 1;
    int mid;
    return binSearchRecursive( points, p, left, right);
}
```
![](../pic/pic5/Screenshot%202022-11-16%20115455.png)


## Sorting
**bubbleSort**： 从第一个开始，遍历到最后，如果比较的第一个和相邻（较大，则交换），因此第一次遍历
会把最大的放到最后。直到整个list从后到前由大到小排列。
``` java
void bubbleSort(double[] list) {
    double tmp;
    for (int i = 0; i < (list.length - 1); i++) {
      for (int j = 0; j < list.length - i - 1; j++) {~~
        //for each pair of elements, swap them, if they are out o~~f order
        if (list[j] > list[j + 1])
        {
          tmp = list[j];
          list[j] = list[j + 1];
          list[j + 1] = tmp;
        }
      }
    }
  }

  ```

  **selectionSort**: 遍历一遍，找到最小的跟第一个换。遍历第二遍，找到最小的和第二个换。。。
  ``` java
public static void selectionSort(double[] list) {
  for (int i = 0; i < list.length - 1; i++) {
    // find the minimum in the list[i...list.length-1]
    double currentMin = list[i];
    int currentMinIndex = i;
    for (int j = i + 1; j < list.length; j++) {
      if (currentMin > list[j]) {
        currentMin = list[j];
        currentMinIndex = j;
      }
    }
    // swap list[i] with list[currentMinIndex] if necessary;
    if (currentMinIndex != i) {
      list[currentMinIndex] = list[i];
      list[i] = currentMin;
    }
  }
}
```

```java
public static void insertionSort(double[] list) {
  for (int i = 1; i < list.length; i++) {
    // insert list[i] into a sorted sublist list[0..i-1] so that
    // list[0..i] is sorted
    double currentElement = list[i];
    int k;
    for (k = i - 1; k >= 0 && list[k] > currentElement; k--) {
      list[k + 1] = list[k];
    }
    // insert the current element into list[k+1]
    list[k + 1] = currentElement;
  }
}
```
![](../pic/pic5/Screenshot%202022-11-16%20121405.png)

## Merge Sort
因为只要把整个array一分为二，那么时间复杂度就可以成为$O(\frac{n^2}{2})$, 如果一直divide直到只剩下一个element，那么这个sorting的问题
就被解决了。这是merge sort的基础。
``` java
public static void mergeSort(int[] array) {
	if (array == null || array.length <= 1)
		return;
	sort(array, 0, array.length - 1);
}

private static void sort(int[] array, int left, int right) {
	if (left == right) 
		return;
	int mid = (left + right) / 2;
	sort(array, left, mid);
	sort(array, mid + 1, right);
	merge(array, left, mid, right);
}

private static void merge(int[] array, int left, int mid, int right) {
	int[] temp = new int[right - left + 1];
	int i = 0;
	int p1 = left;
	int p2 = mid + 1;
	// 比较左右两部分的元素，哪个小，把那个元素填入temp中
	while (p1 <= mid && p2 <= right) {
		temp[i++] = array[p1] < array[p2] ? array[p1++] : array[p2++];
	}
	// 上面的循环退出后，把剩余的元素依次填入到temp中
	// 以下两个while只有一个会执行
	while (p1 <= mid) {
		temp[i++] = array[p1++];
	}
	while (p2 <= right) {
		temp[i++] = array[p2++];
	}
	// 把最终的排序的结果复制给原数组
	for (i = 0; i < temp.length; i++) {
		array[left + i] = temp[i];
	}
}
```

```java
public class QuickSort {

    public static void quickSort(int[] a, int n) {
        quickSortRecursive(a, 0, n - 1);
    }

    public static void quickSortRecursive(int[] a, int p, int r) {
        if (p >= r) {
            return;
        }
        int q = partition(a, p, r);
        quickSortRecursive(a, p, q - 1);
        quickSortRecursive(a, q + 1, r);
    }

    public static int partition(int[] a, int p, int r) {
        int pivot = a[r];
        int i = p;
        for (int j = p; j < r; j++) {
            if (a[j] < pivot) {
                int t = a[i];
                a[i] = a[j];
                a[j] = t;
                i++;
            }
        }
        int t = a[i];
        a[i] = a[r];
        a[r] = t;
        return i;
    }
}
```