# 同步数据api模块

主要用于第三方的数据同步，目前是科传的会员数据

## 科传同步

### 同步会员数据

科传的会员信息变更会调用我们的接口，我方再根据提供的会员去调科传的借口，获取更详细的用户数据并保存到本地。