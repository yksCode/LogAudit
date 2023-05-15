package com.dataAuditLog;

import java.util.HashMap;

public class hashChain {

    private HashMap<String,userHashChain> TotalChain;

    public hashChain() {
        this.TotalChain = new HashMap<>();
    }

    public void addChain(String userId, userHashChain uhc) {
        TotalChain.put(userId,uhc);
    }

    public void updateChain(String userId,byte[] sgn){
        userHashChain tmp = TotalChain.get(userId);
        tmp.setHashChain(sgn);
        TotalChain.put(userId,tmp);
    }

    public boolean isContainUser(String userId){
        return TotalChain.containsKey(userId);
    }

    public userHashChain getUHC(String userId){
        return TotalChain.get(userId);
    }
}
