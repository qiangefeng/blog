package com.company;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) throws InterruptedException {
	// write your code here
        System.out.println("add shutdownhook");
        Runtime.getRuntime().addShutdownHook(new Main().new ShutDownClass());

        System.out.println("begin sleep");
        sleep(Integer.valueOf(args[0]));
        System.out.println("stop sleep");

    }
    protected class ShutDownClass extends Thread {
        @Override
        public void run(){
            System.out.println("I am ShutDownClass");
        }

    }
}

