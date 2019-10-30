package com.company;
public class Main {

    public static void main(String[] args) {
        int i = 0;
        System.out.println(System.currentTimeMillis());
        while(i<1000000){
            int a = Privilege.addPrivilege(0,"ECS_READ,ECS_WRITE,SLB_READ,SLB_WRITE,OSS_READ,OSS_WRITE");
            //Privilege.listPrivilege(a);
        }
        System.out.println(System.currentTimeMillis());

    }
}
