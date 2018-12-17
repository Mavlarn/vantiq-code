# EDA 实例

本实例用于演示使用vantiq作为EDA(事件驱动架构)平台的示例代码。

## 准备
需要有一个dev.vantiq.cn的开发账号，创建一个用于测试的name space或者使用现有的。实例中用到的topic：
 * /mt/capitalheat/sap/domain1    # 演示SAP中发生数据修改，发送事件
 * /mt/capitalheat/service1/project/new    # 服务1中的project领域发生一个新建项目的事件
 * /mt/capitalheat/service1/project/update    # 服务1中的project领域发生一个更新的事件

## 说明
### TestRestApi
使用Rest API发送事件到vantiq的topic队列

### EventPublisher
使用vantiq java sdk发送事件

### ProjectEventSubscriber
使用vantiq java sdk订阅事件
