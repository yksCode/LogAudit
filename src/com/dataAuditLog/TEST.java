package com.dataAuditLog;

public class TEST {
    public static void main(String[] args) {

        logSignature logSgn = new logSignature();

        //插入一条用户访问记录
        try {
            logSgn.signBySM2("{action:insert}\n" +
                            "{data:{hello world}\n" +
                            "{time:2023-5-13 17:31}",
                    "yks");
            logSgn.signBySM2("{action:insert}\n" +
                            "{data:{hello world}\n" +
                            "{time:2023-5-13 17:31}",
                    "yks");
            logSgn.signBySM2("{action:insert}\n" +
                            "{data:{hello world}\n" +
                            "{time:2023-5-13 17:31}",
                    "yks");
            logSgn.getHchain().getUHC("yks").printInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
