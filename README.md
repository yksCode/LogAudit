## Usage

```java
//创建日志审计对象实例
logSignature logSgn = new logSignature();

/*param1：用户访问记录，格式为json字符串；param2：用户id
- 合并数字签名并计算哈希值，生成新的数字签名
- 将签名添加到用户访问记录签名链的末尾
*/
logSgn.sign("{action:insert}\n" +
                            "{data:{hello world}\n" +
                            "{time:2023-5-13 17:31}",
                    "yks");

/*param1:用户id；param2：数字签名，类型为byte数组
*手动添加用户访问记录 
*/
addUserAccessRecord("yks",sgn);

//查看用户id为“yks”的数字签名链
logSgn.getHchain().getUHC("yks").printInfo();

//验证签名
logSgn.veritySgn(sgn);
```

