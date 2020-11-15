package com.hgy.common;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Stream;

/**
 * 重写目的：
 *      1. 由于Iterator的next方法执行后就不能回退了，就好像流水线上的产品流走之后不可回退
 *      但在代码具体的实现过程中需要获取当前元素，并且在下一次next的时候任然能够获取当前元素
 *      列如流： A -> B -> C
 *      next: 获取到A，下次next只能获取B
 *
 *      需要实现方法peek
 *      peek：获取到A，下次执行next任然获取到A
 *
 *      总结：peek可以查看当前元素但是不影响next
 *
 * @param <T>
 */
public class PeekIterator<T> implements Iterator<T> {

    private Iterator<T> iterator;

    private static int SLIDING_WINDOW_SIZE = 10;
    private LinkedList<T> slideWindowQueue = new LinkedList<>();
    private LinkedList<T> stackPutBacks = new LinkedList<>();

    // 程序代码的结束符号
    private T endToken = null;


    /**
     * 输入一个流， 获取流的迭代器
     * @param iterator
     */
    public PeekIterator(Stream<T> iterator) {
        this.iterator = iterator.iterator();
    }

    public PeekIterator(Iterator<T> it, T endToke) {
        this.iterator = it;
        this.endToken = endToke;
    }

    public PeekIterator(Stream<T> stream, T endToken){
        this.iterator = stream.iterator();
        this.endToken = endToken;
    }

    /**
     * 获取元素但不影响next
     * 所以peek查看的元素需要暂存在stackPutBacks
     * @return 当前所指的元素
     */
    public T peek() {
        if(this.stackPutBacks.size() > 0) {
            return this.stackPutBacks.getFirst();
        }
        if (!iterator.hasNext()) {
            return endToken;
        }
        // 获取元素并且当前元素已缓存在slideWindowQueue队首
        T val = next();
        // 为了不影响next 需要另外一个结构存储被查看的元素
        putBack();
        return val;
    }


    /**
     * 调用后在使用peek即可查看是上一个被next过的元素
     * 如：当前流： A -> B -> C
     * next: A
     * slideWindowQueue: A
     * next: B
     * slideWindowQueue: B A  (A是队首)
     * putBack => stackPutBacks: B
     * peek: B
     * putBack => stackPutBacks: A B (A是栈顶)
     * peek: A
     * next: A  stackPutBacks: B
     * next: B  stackPutBacks:
     */
    public void putBack(){
        // 存储刚刚next的元素，下一次next先从stackPutBacks处获取
        if(this.slideWindowQueue.size() > 0) {
            this.stackPutBacks.push(this.slideWindowQueue.pollLast());
        }
    }

    @Override
    public boolean hasNext() {
        return endToken != null || this.stackPutBacks.size() > 0 || iterator.hasNext();
    }

    @Override
    public T next() {
        T val = null;
        // 首先确认是否又暂存元素
        if (this.stackPutBacks.size() > 0) {
            val = stackPutBacks.pop();
        } else {
            if(!this.iterator.hasNext()) {
                T tmp = endToken;
                // 用于确认是否又下一个元素
                endToken = null;
                return tmp;
            }
            val = iterator.next();
        }
        // 判断滑动窗口是否满了，满了先从队首删除元素
        if (slideWindowQueue.size() >= SLIDING_WINDOW_SIZE) {
            slideWindowQueue.poll();
        }
        // 缓存元素到队尾
        slideWindowQueue.add(val);
        return val;
    }
}
