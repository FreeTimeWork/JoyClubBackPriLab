*TODO*

- spring test mapper
- mybatis generator
- security
- `service`层的更新时，把createTime等信息置为null应该可以抽象为通用方法。
- getList里的getSum应该用缓存
- mybatis generator comment generator
- 创建和更新删除时不必要的数据项
- 退出登陆 某些state不清空
- act 和 product 抽象
- 把每一个功能单独模块化，比如会员卡相关，可以新建一个会员卡模块。
- 删除会员 17600690737，17600690738，17600690739
- 压力测试，京东借口
- 将某些常量变为枚举类型
- 项目和平台版关于猫酷的问题，目前平台版也可以进行猫酷相关操作，其实应该更改。
*NOTE*
- 使用相同名字的properties在不同module时使用`@PropertySource`可能会出错
- formData乱码，使用`CommonsMultipartResolver`
- 不同模块的spring bean也不能重名
- 系统管理员最好是单点登录，比如制卡，如果同时操作很可能制出重复的卡号
- `com.mysql.cj.jdbc.exceptions.PacketTooBigException: Packet for query is too large (5,089,070 > 4,194,304)`
- `FastJson` parse 不同结构时不会抛出异常，只会返回空对象
- mapper.xml不要重名，否则绑定异常
- 记得给jre包增加加密解密长度无限制`java.security.InvalidKeyException: Illegal key size or default parameters`[see](http://stackoverflow.com/questions/6481627/java-security-illegal-key-size-or-default-parameters/14237459)
- 号段：商通的号段暂时不用。等将商通的功能全部实现并停止后，导入商通的生成的卡号并且将号段拿过来使用
- `List<String>` 并不继承 `List<Object>`,[see](http://stackoverflow.com/questions/22144671/why-liststring-is-not-acceptable-as-listobject),
  所以，一个`void test(List<Object> list)`的方法不能用`List<String>`去调用
- 积分记录排序
+
- decimalFormat '.00'对于0.0123会变成'.00'，没有个位，应该用'0.00'
- mysql `1!=null`的结果是 `null`
- update 也可以join
- 对于住宅项目，登录时记录的是在平台公众号的openId,所以以后没法对住宅项目的应用发送模板消息