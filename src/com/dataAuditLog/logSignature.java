package com.dataAuditLog;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.List;

public class logSignature {
    private hashChain hchain;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private Signature signature;
    private String DecLog;
    public logSignature() throws Exception {
        hchain = new hashChain();
    }
    private Sm2CryptTools sm2CryptTools= new Sm2CryptTools();
    private KeyPair  keyPair = sm2CryptTools.mKeyPair;
    public void signByECDSA(String log,String id) throws Exception {
        // 生成ECDSA密钥对
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        keyPairGenerator.initialize(new ECGenParameterSpec("secp256r1"));
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();

        //将log和之前的数字签名记录进行合并
        if(hchain.isContainUser(id)){
            log = log + getPreviousSignature(id);
        }

        // 使用私钥进行签名并添加到哈希链末尾
        this.signature = Signature.getInstance("SHA256withECDSA");
        this.signature.initSign(privateKey);
        this.signature.update(log.getBytes());
        String digitalSignature = signature.sign().toString();
        addUserAccessRecord(id,digitalSignature,log);
    }

    public void signBySM2(String log,String id){
        try {
            String digitalSignature = sm2CryptTools.encrypt(keyPair.getPublic(),log);
            //DecLog用于接下来的验证
            DecLog              = sm2CryptTools.decrypt(keyPair.getPrivate(),digitalSignature);
            //添加到用户数字签名链
            addUserAccessRecord(id,digitalSignature,log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean verifySM2Sgn(String id){
        return hchain.getUHC("yks").getLastLog().equals(DecLog);
    }


    private String getPreviousSignature(String userId) {
        String previousSgn = "";
        //StringBuilder sb = new StringBuilder();
        for (String signature : hchain.getUHC(userId).getHashChain()) {
            previousSgn += signature.toString();
        }
        return previousSgn;
    }

    public void addUserAccessRecord(String id, String sgn,String log) {
        //如果签名链中已经存在这个用户，则直接更新此用户的数字签名
        if(hchain.isContainUser(id)){
            hchain.updateChain(id,sgn,log);
        }
        //如果没有，则创建一个用户数字签名链对象
        else{
            userHashChain uhc = new userHashChain(id);
            uhc.setHashChain(sgn);
            uhc.setLogChain(log);
            hchain.addChain(id,uhc);
        }
    }

    public hashChain getHchain(){
        return hchain;
    }
//
//    // 获取所有用户的访问记录哈希链
//    public static String getAllUserAccessRecordHashChain(List<DataSignature> userAccessRecords) {
//        StringBuilder sb = new StringBuilder();
//        for (DataSignature signature : userAccessRecords) {
//            sb.append(signature.getPreviousSignature());
//        }
//        return sha256(sb.toString());
//    }
}


