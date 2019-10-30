package com.company;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Privilege {
    public static final int ECS_READ = 1;
    public static final int ECS_WRITE = 1 << 1;
    public static final int SLB_READ = 1 << 2;
    public static final int SLB_WRITE = 1 << 3;
    public static final int OSS_READ = 1 << 4;
    public static final int OSS_WRITE = 1 << 5;
    private static Map<String, Integer> privilegeString = new TreeMap<String, Integer>();

    static {
        Privilege m = new Privilege();
        for (Field field : Privilege.class.getFields()) {
            try {
                privilegeString.put(field.getName(), (Integer) field.get(m));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    //授权一组权限
    private static int getPrisIntValue(String priStrs) {
        int tmpPri = 0;
        for (String priStr : priStrs.split(",")) {
            tmpPri |= ((Integer) privilegeString.get(priStr)).intValue();
        }
        return tmpPri;
    }

    //增加一组权限
    public static int addPrivilege(int pri, String priStrs) {
        return pri |= getPrisIntValue(priStrs);
    }

    //取消一组权限
    public static int delPrivilege(int pri, String priStrs) {
        return pri &= getPrisIntValue(priStrs);
    }

    //查询是否包含某些权限
    public static boolean hasPrivilege(int pri, String priStrs) {
        int prisIntValue = getPrisIntValue(priStrs);
        return (pri & prisIntValue) == prisIntValue;
    }

    //列出所有权限
    public static String listPrivilege(int pri) {
        String priStrs = "";
        for(Map.Entry entry: privilegeString.entrySet()){
            int priIntValue = ((Integer) entry.getValue()).intValue();
            if((pri & priIntValue) == priIntValue){
                priStrs = priStrs + entry.getKey().toString() + ",";
            }
        }
        if (! "".equals(priStrs)){
            priStrs = priStrs.substring(0,priStrs.length()-1);
        }
        return priStrs;
    }
}

