package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static java.lang.System.out;
import static java.lang.Thread.sleep;

public class Main {
    private int value;
    List<Thread> threads = new ArrayList<Thread>(100);


    private synchronized void inc_value(){
        out.println(Thread.currentThread().getName()+" begin inc_value");
        threads.add(Thread.currentThread());
        value++;
        //Thread.currentThread().interrupt();
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notifyAll();
        out.println(Thread.currentThread().getName()+" end inc_value");
    }

    private synchronized void print_value() throws InterruptedException {
        out.println(Thread.currentThread().getName()+" "+Thread.currentThread().getState()+ " begin print_value");
        threads.add(Thread.currentThread());
        while (value <100){
            wait();
        }
        out.println(value);
        out.println(Thread.currentThread().getName()+" "+Thread.currentThread().getState()+ " end print_value");

    }

    class A implements Runnable{
        Main a_main;
        public A(Main main) {
            a_main=main;
        }
        @Override
        public void run(){
            a_main.inc_value();
        }
    }
    class B implements Runnable{
        Main b_main;
        public B(Main main) {
            b_main=main;
        }
        @Override
        public void run(){
            try {
                b_main.print_value();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class PrintThreadState implements Runnable{

        @Override
        public void run(){
            while(true){
                for(Thread thread: threads){
                    out.println(thread.getName()+" "+thread.getState());
                }
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                out.println("-----------");

            }
        }
    }
    public static void main(String[] args){

        ExecutorService executorService = Executors.newCachedThreadPool();
        Main testMain=new Main();
        int i=0;
        while(i++<100){
            executorService.execute(testMain.new A(testMain));
            executorService.execute(new Thread(testMain.new B(testMain)));
        }
        executorService.execute(testMain.new PrintThreadState());
        for (Runnable thread: ((ThreadPoolExecutor)executorService).getQueue()){

            out.println(((Thread) thread).getName()+" "+((Thread) thread).getState());

        }
        executorService.shutdown();
    }
}




//public class Main {
//    public static void main(String[] args){
//
//    }
//}