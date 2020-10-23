import java.util.LinkedList;
import java.util.Queue;

public class DataConsumer {
    //共享数据区
    private final Queue<String> data = new LinkedList();
    private int capacity;
    private int size = 0;

    public DataConsumer(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void push(String x) throws InterruptedException {
        while (size == capacity) { //当buffer满时，producer进入waiting 状态
            this.wait(); //使用this对象来加锁
        }
        data.add(x);
        size++;
        notifyAll(); //当buffer 有数据时，唤醒所有等待的consumer线程
    }

    public synchronized String pop() throws InterruptedException {
        while (size == 0) {//当buffer为空时，consumer 进入等待状态
            this.wait();
        }
        String result = data.poll();
        size--;
        notifyAll(); //当数据被消耗，空间被释放，通知所有等待的producer。
        return result;
    }

    public static void main(String[] args) throws InterruptedException {
        DataConsumer dc = new DataConsumer(10);
        dc.push("x");
        dc.pop();
        System.out.println("b");
        dc.pop();
        System.out.println("a");
    }
}
