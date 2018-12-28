# EDA 实例

本实例用于演示使用vantiq作为EDA(事件驱动架构)平台的示例代码。

## 准备
需要有一个dev.vantiq.cn的开发账号，创建一个用于测试的name space或者使用现有的。实例中用到的topic：
具体的实例相关的vantiq平台的部分，请参考文档：
https://github.com/Mavlarn/vantiq-doc/blob/master/2_vantiq_pronto_tutorial.md


## 说明
### TestRestApi
使用Rest API发送事件到vantiq的topic队列

### EventPublisher
使用vantiq java sdk发送事件

### ProjectEventSubscriber
使用vantiq java sdk订阅事件
