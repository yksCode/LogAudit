package com.dataAuditLog;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.List;

public class logSignature {
    private hashChain hchain;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private Signature signature;
    public logSignature(){
        hchain = new hashChain();
    }

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
        byte[] digitalSignature = signature.sign();
        addUserAccessRecord(id,digitalSignature);
    }

    public void signBySM2(String log,String id,int select){
        try {
            Sm2CryptTools sm2CryptTools= new Sm2CryptTools();
            KeyPair keyPair = sm2CryptTools.mKeyPair;

//            System.out.println("原始明文：" + log);
            byte[] digitalSignature = sm2CryptTools.encrypt(keyPair.getPublic(),log).getBytes();
//            System.out.println("SM2加密后密文：" + digitalSignature);

            addUserAccessRecord(id,digitalSignature);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean verifySgn(byte[] sgn) throws Exception {
        // 使用公钥进行验证
        signature.initVerify(publicKey);
        boolean isValid = signature.verify(sgn);
        return isValid;
    }

    private String getPreviousSignature(String userId) {
        String previousSgn = "";
        //StringBuilder sb = new StringBuilder();
        for (byte[] signature : hchain.getUHC(userId).getHashChain()) {
            previousSgn += signature.toString();
        }
        return previousSgn;
    }

    public void addUserAccessRecord(String id, byte[] sgn) {
        //如果签名链中已经存在这个用户，则直接更新此用户的数字签名
        if(hchain.isContainUser(id)){
            hchain.updateChain(id,sgn);
        }
        //如果没有，则创建一个用户数字签名链对象
        else{
            userHashChain uhc = new userHashChain(id);
            uhc.setHashChain(sgn);
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


