package com.company;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

import static java.lang.System.out;
import static java.lang.Thread.sleep;

public class Main{
    class LimitLatch extends AbstractQueuedSynchronizer{
        @Override
        public int tryAcquireShared(int arg){
            int data = count.incrementAndGet();
            if(data > limit){
                count.decrementAndGet();
                return -1;
            }
            return 1;
        }

        @Override
        public boolean tryReleaseShared(int arg){
            count.decrementAndGet();
            return true;
        }
    }

    private int limit = 10;
    private AtomicInteger count = new AtomicInteger();
    public LimitLatch limitLatch = new LimitLatch();

    class A implements Runnable{
        LimitLatch limitLatch;

        public A(LimitLatch limitLatch) {
            this.limitLatch = limitLatch;
        }

        @Override
        public void run(){
            out.println(Thread.currentThread().getName()+ " start");
            try {
                sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            limitLatch.releaseShared(0);
            out.println(Thread.currentThread().getName()+ " end");
        }
    }
    public static void main(String[] argv){
        Main test_main=new Main();
        for(int i=0;i<100;i++){
            test_main.limitLatch.acquireShared(0);
            (new Thread(test_main.new A(test_main.limitLatch))).start();
        }

    }
}