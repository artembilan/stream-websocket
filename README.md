# stream-websocket

When trying to inject a Cloud Stream `MessageChannel` into a STOMP over WebSocket `ChannelInterceptor` we are getting cycle dependency 

The dependencies of some of the beans in the application context form a cycle:
```
┌─────┐
|  myChannelInterceptor defined in file [/Users/shahbour/IdeaProjects/stream-websocket/target/classes/com/shahbour/MyChannelInterceptor.class]
↑     ↓
|  org.springframework.cloud.stream.messaging.Source (field private java.util.Map org.springframework.cloud.stream.binding.BindableProxyFactory.bindingTargetFactories)
↑     ↓
|  org.springframework.cloud.stream.config.BindingServiceConfiguration (field private java.util.List org.springframework.cloud.stream.config.BindingServiceConfiguration.customMessageConverters)
↑     ↓
|  org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration
↑     ↓
|  webSocketConfig defined in file [/Users/shahbour/IdeaProjects/stream-websocket/target/classes/com/shahbour/WebSocketConfig.class]
└─────┘
```

To reproduce it just run

`./mvnw spring-boot:run`
