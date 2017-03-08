package com.darwinsw.jee.sample

import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class MySocketHandler(private val repo: CyclistRepository) extends TextWebSocketHandler {
  override def handleTextMessage(session: WebSocketSession, message: TextMessage): Unit = {
    val cyclist = repo.getAll(scala.util.Random.nextInt(repo.getAll.size))
    
    session.sendMessage(new TextMessage(message.getPayload.toUpperCase() + "[from " + cyclist.firstName + "]"))
  }
}