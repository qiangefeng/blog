package com.company;

import static java.lang.Thread.sleep;

public class Main_4_things {

    class WaitA implements Runnable {
        @Override
         public synchronized void run(){
            try {
                int timeOut=10000;
                System.out.println(Thread.currentThread().getName()+" to wait "+timeOut);
                wait(timeOut);
                System.out.println(Thread.currentThread().getName()+" end wait");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    class WaitB implements Runnable {
        WaitA a;

        public WaitB( WaitA a){
            this.a = a;
        }
        @Override
        public synchronized void run(){
            try {
                int timeOut=5000;
                System.out.println(Thread.currentThread().getName()+" to sleep "+timeOut);
                sleep(timeOut);
                System.out.println(Thread.currentThread().getName()+" to invoke a.notify");
//                synchronized (a){
                    a.notify();
//                }
                System.out.println(Thread.currentThread().getName()+" end invoke a.notify");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class WaitC implements Runnable {
        WaitA a ;
        public WaitC(WaitA a){
            this.a = a;
        }
        @Override
        public synchronized void run(){
            try {
                int timeOut=5000;
                System.out.println(Thread.currentThread().getName()+" to sleep "+timeOut);
                sleep(timeOut);
                System.out.println(Thread.currentThread().getName()+" to invoke a.notifyAll");
                synchronized (a){
                    a.notifyAll();
                }
                System.out.println(Thread.currentThread().getName()+" end invoke a.notifyAll");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class WaitD implements Runnable {
        Thread a;
        public WaitD( Thread a){
            this.a = a;
        }
        @Override
        public void run(){
            try {
                int timeOut=5000;
                System.out.println(Thread.currentThread().getName()+" to sleep "+timeOut);
                sleep(timeOut);
                System.out.println(Thread.currentThread().getName()+" to invoke a.interrupt");
                a.interrupt();
                System.out.println(Thread.currentThread().getName()+" end invoke a.interrupt");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        System.out.println("begin------1------notify");
	    // 1
        Main_4_things main_ = new Main_4_things();
	    WaitA waitA = main_.new WaitA();
        Thread a = new Thread(waitA);
        Thread b = new Thread(main_.new WaitB(waitA));
        a.setName("waitA");
        b.setName("WaitB");
        a.start();
        b.start();

        sleep(20000);
        System.out.println("begin------2------wait");
        // 2
        waitA = (new Main_4_things()).new WaitA();
        a = new Thread(waitA);
        a.setName("waitA");
        a.start();


        sleep(20000);
        System.out.println("begin------3------notifyAll");
        // 3
        waitA = (new Main_4_things()).new WaitA();
        a = new Thread(waitA);
        Thread c = new Thread((new Main_4_things()).new WaitC(waitA));
        a.setName("waitA");
        c.setName("WaitC");
        a.start();
        c.start();

        sleep(20000);
        System.out.println("begin------4------interrupt");
        // 4
        waitA = (new Main_4_things()).new WaitA();
        a = new Thread(waitA);
        Thread d = new Thread((new Main_4_things()).new WaitD(a));
        a.setName("waitA");
        d.setName("WaitD");
        a.start();
        d.start();
    }
}
