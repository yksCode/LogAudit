java version：20.1
## Usage

```java
将bouncyCastle添加为库：ide为idea的情况下，右键“bcprov-jdk18on-1.73.jar”文件，选择Add as libarary
如果项目用的是maven，可以直接添加依赖：
<dependency>
    <groupId>org.bouncycastle</groupId>
    <artifactId>bcprov-jdk18on</artifactId>
    <version>1.73</version>
</dependency>

//创建日志审计对象实例
logSignature logSgn = new logSignature();

/*param1：用户访问记录，格式为json字符串；param2：用户id
- 合并数字签名并计算哈希值，生成新的数字签名
-签名算法为SM2
- 将签名添加到用户访问记录签名链的末尾
*/
logSgn.signBySM2("{action:insert}\n" +
                            "{data:{hello world}\n" +
                            "{time:2023-5-13 17:31}",
                    "yks",1);

/*param1：用户访问记录，格式为json字符串；param2：用户id
- 合并数字签名并计算哈希值，生成新的数字签名
-签名算法为SM2
- 将签名添加到用户访问记录签名链的末尾
*/
logSgn.signBySM2("{action:insert}\n" +
                            "{data:{hello world}\n" +
                            "{time:2023-5-13 17:31}",
                    "yks");

/*param1：用户访问记录，格式为json字符串；param2：用户id
- 合并数字签名并计算哈希值，生成新的数字签名
-签名算法为ECDSA
- 将签名添加到用户访问记录签名链的末尾
*/
logSgn.signByECDSA("{action:insert}\n" +
                            "{data:{hello world}\n" +
                            "{time:2023-5-13 17:31}",
                    "yks");

/*param1:用户id；param2：数字签名，类型为byte数组
*手动添加用户访问记录，一般不用
*/
addUserAccessRecord("yks",sgn);

//查看用户id为“yks”的数字签名链
logSgn.getHchain().getUHC("yks").printInfo();

/*param:String类型用户id
*return:boolean类型，true表示用户数字签名链的真实性和完整性没有被破坏
*/
logSgn.verifySM2Sgn("yks");
```

