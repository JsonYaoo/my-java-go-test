package juc.collections;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * q.take()每次取出元素时，就相当于消费掉一个元素，而且是优先级最大的元素。剩下的元素会进行一次堆调整。
 * @author end 2020/12/25 21:14
 **/
public class PriorityBlockingQueueTest {


    public static void main(String[] args) throws Exception{


        PriorityBlockingQueue<Task> q = new PriorityBlockingQueue<Task>();

        Task t1 = new Task();
        t1.setId(3);
        t1.setName("id为3");
        Task t2 = new Task();
        t2.setId(4);
        t2.setName("id为4");
        Task t3 = new Task();
        t3.setId(1);
        t3.setName("id为1");

        q.add(t1);	//3
        q.add(t2);	//4
        q.add(t3);  //1

        System.out.println("容器：" + q);
        System.out.println(q.take().getId());
        System.out.println("容器：" + q);

    }
}
class Task implements Comparable<Task>{

    private int id ;
    private String name;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Task task) {
        return this.id > task.id ? 1 : (this.id < task.id ? -1 : 0);
    }

    @Override
    public String toString(){
        return this.id + "," + this.name;
    }

}
