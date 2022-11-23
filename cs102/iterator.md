# proj3 的一些心得

## iterator的语法

首先我的数据结构deque implement了iterable 这个接口。因此我需要一个可以帮助我iterate整个deque的类。也就是Itr类。

遇到问题：itr的开头是什么？最好是在deque之前用一个sentinel来缓冲，这样可以避免很多head和tail空的情况。
``` java
public Iterator<E> iterator() {
        return new Itr(); // wait for implement.
}

 private class Itr implements Iterator<E> {
        Node c;
        int index = 0;

        /** Iterator constructor that construct an iterator */
        public Itr() {
            c = new Node(null, null, head); // 这个是bug。没想到更好的解决办法。。
        }

        /** Check if the next object is null. */
        public boolean hasNext() {
            if (c.next != null) {
                return true;
            }
            return false;
        }

        /**
         * Return next item.
         * @return next node's item
         * @throws NullPointerException if next node is null.
         */
        public E next() throws NullPointerException {
            if (c.next != null) {
                c = c.next;
                if (c == head) {
                    c.pre = null; // 这个是bug。没想到更好的解决办法。。bug也就是这样潦草解决，前提是用了一次itr.next()这个方法，总归不是很简洁。
                }
                return c.item;
            }
            throw new NullPointerException("There is no item next.");
        }
    }

``` 
## 杂收获
1. 时刻写doc 这个很重要，方便查阅。
2. 有些时刻要cache的变量比如size和midindex要时刻监测他们是不是有变化。比如说在pop的时候，midindex会不时的变化因为middle Node的位置被不知不觉的调前了一位，因此要有大局观。
3. 尽量在草纸上画出来，代码可以搞稍微
4. switch的语句可以这样，同时具有穿透性，记得在每个case后面加上break。
``` java
public class Main {
    public static void main(String[] args) {
        int option = 2;
        switch (option) {
        case 1:
            System.out.println("Selected 1");
        case 2:
            System.out.println("Selected 2");
        case 3:
            System.out.println("Selected 3");
        default:
            System.out.println("Not selected");
        }
    }
}
```