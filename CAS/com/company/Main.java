package com.company;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.System.*;

public class Main {
    private volatile long value=0;
    private static  Unsafe unsafe;
    static {
        try {
            Field getUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            getUnsafe.setAccessible(true);
            unsafe = (Unsafe) getUnsafe.get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    public synchronized void add_value(){
        value++;
        out.println(Thread.currentThread().getName()+" "+value);
    }
    class A implements Runnable{
        Main main_a;

        public A(Main main_a) {
            this.main_a = main_a;
        }

        @Override
        public void run(){
                value++;

        }
    }
    class C implements Runnable{
        Main main_c;

        public C(Main main_c) {
            this.main_c = main_c;
        }

        @Override
        public void run(){
            long old_value=0;
            long count=0;
            try {
                do{
                    old_value=main_c.value;
                    count++;
                }while (!unsafe.compareAndSwapLong(main_c, unsafe.objectFieldOffset(Main.class.getDeclaredField("value")), old_value, old_value + 1));
                System.out.println(Thread.currentThread().getName()+": "+count+" value: "+ old_value+1);
            }catch (Exception e){
                //todo nothing
            }
        }
//        @Override
//        public void run(){
//            this.main_c.add_value();
//        }
    }


    public static void main(String[] args) {

        Main main_main=new Main();

        ExecutorService executorService = Executors.newFixedThreadPool(100);
        int i=0;
        while(i++<50){
            executorService.execute(new Main().new C(main_main));
        }
        executorService.shutdown();
    }
}

