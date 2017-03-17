# reliable-message

各个模块的作用:

message-core:                   定义消息对象和消息接口
message-center-service:         核心消息服务

unconfirmed-message-checker:    未确认消息处理, 调用主动业务的业务确认接口
unconsumed-message-checker:     未消费消息处理, 发送给消息消费者

message-mgr-web:                消息管理的web程序, 主要用在错误人工处理

demo-initiative-business-core:  主动业务 的 业务接口
demo-initiative-business:       主动业务demo, 生产消息
demo-message-business-consumer: 消息消费者, 接收消息 调用demo-passive-business的业务接口, 获得接口返回值之后 确认消息
demo-passive-business-core:     定义被动业务 的 业务接口
demo-passive-business:          被动业务, 被demo-message-business-consumer调用
