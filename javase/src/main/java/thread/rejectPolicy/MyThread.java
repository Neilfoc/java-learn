package thread.rejectPolicy;

/**
 * @author 11105157
 * @Description
 * @Date 2021/5/10
 */
public class MyThread implements Runnable {
    String name;
    public MyThread(String name) {
        this.name = name;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name+" 执行     使用:"+Thread.currentThread().getName());
    }
}
