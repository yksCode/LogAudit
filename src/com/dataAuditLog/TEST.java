package com.dataAuditLog;

public class TEST {
    public static void main(String[] args) {

        logSignature logSgn = new logSignature();

        //插入一条用户访问记录
        try {
            logSgn.sign("{action:insert}\n" +
                            "{data:{hello world}" ,
                    "yks");
            logSgn.sign("{action:insert}\n" +
                            "{data:{hello world}" ,
                    "yks");
            logSgn.sign("{action:insert}\n" +
                            "{data:{hello world}" ,
                    "yks");
            logSgn.getHchain().getUHC("yks").printInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
