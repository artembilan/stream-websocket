package com.shahbour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@SpringBootApplication
@EnableBinding(Source.class)
@EnableWebSocketMessageBroker
public class StreamWebsocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamWebsocketApplication.class, args);
	}
}

@Configuration
class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

   private final ChannelInterceptor channelInterceptor;

	public WebSocketConfig(ChannelInterceptor myChannelInterceptor) {
		this.channelInterceptor = myChannelInterceptor;
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
		stompEndpointRegistry.addEndpoint("/v1/stomp");
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.setInterceptors(channelInterceptor);
	}
}

@Component
class MyChannelInterceptor extends ChannelInterceptorAdapter {


	private final MessageChannel output;

	public MyChannelInterceptor(MessageChannel output) {
		this.output = output;
	}

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		return message;
	}
}
