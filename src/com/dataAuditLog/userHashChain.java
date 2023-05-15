package com.dataAuditLog;

import java.util.ArrayList;
import java.util.List;

/*
*用户哈希链
 */
public class userHashChain {

    private String Id;
    private String userName;
    private List<byte[]> hashChain;//存储用户
    public userHashChain(String id) {
        this.Id = id;
//        this.userName = userName;
        this.hashChain = new ArrayList<>();
    }

    public void setHashChain(byte[] digitalSignature) {
        hashChain.add(digitalSignature);
    }

    public List<byte[]> getHashChain() {
        return hashChain;
    }

    public void printInfo(){
        System.out.println("ID:"+this.Id);
        System.out.println("Signature Chain:");
        for(byte[] str:this.hashChain){
            System.out.println(str);
        }
    }
}
