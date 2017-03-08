package com.darwinsw.jee.sample

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class MyWebsocketConfig @Autowired() (private val handler: MySocketHandler) extends WebSocketConfigurer {
  override def registerWebSocketHandlers(registry: WebSocketHandlerRegistry): Unit = {
    registry.addHandler(handler, "/chat");
  }
}