package com.dataAuditLog;

import java.util.ArrayList;
import java.util.List;

/*
*用户哈希链
 */
public class userHashChain {

    private String Id;
    private String userName;
    private List<String> hashChain;//存储用户
    private List<String> logChain;//存储log条目
    public userHashChain(String id) {
        this.Id = id;
        this.hashChain = new ArrayList<>();
        this.logChain  = new ArrayList<>();
    }

    public void setHashChain(String digitalSignature) {
        hashChain.add(digitalSignature);
    }

    public void setLogChain(String log){
        logChain.add(log);
    }
    public List<String> getHashChain() {
        return hashChain;
    }
    public int hashChainLen(){
        return hashChain.size();
    }
    public String getLastSgn(){
        if(hashChainLen() != 0)
            return hashChain.get(hashChainLen()-1);
        else
            return null;
    }

    public void printInfo(){
        System.out.println("ID:"+this.Id);
        System.out.println("Signature Chain:");
        for(String str:this.hashChain){
            System.out.println(str);
        }
    }

    public List<String> getLogChain() {
        return logChain;
    }
    public int logChainLen(){
        return logChain.size();
    }
    public String getLastLog(){
        if(logChainLen() != 0)
        {
            return logChain.get(logChainLen()-1);
        }
        else
            return null;
    }

    public String sumOflog(){
        StringBuilder sb = new StringBuilder();
        for(String str:logChain){
            sb.append(str+"\n");
        }
//        System.out.println(sb.toString());
        return sb.toString();
    }
}
