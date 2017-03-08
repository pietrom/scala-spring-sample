package com.darwinsw.jee.sample

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Controller
@RequestMapping(Array("/hello"))
class HelloController(private val repo : CyclistRepository) {
  @RequestMapping(method = Array(RequestMethod.GET))
  @ResponseBody()
  def sayHello() : Hello = {
    new Hello("Hello, Spring World!")
  }
  
  @RequestMapping(value = Array("events"), method = Array(RequestMethod.GET))
  @ResponseBody()
  def getEvents() : SseEmitter = {
    val emitter = new SseEmitter()
    emitter.send("Message 0 - Synchronous")
    
    new Thread(new Runnable {
      override def run(): Unit = {
        try {
          for (p <- repo.getAll) {
            Thread.sleep(250)
            val msg = "Hello, " + p.firstName + " " + p.lastName + "!"
            emitter.send(msg)
          }
        } catch {
          case e: Exception => e.printStackTrace()
        } finally {
          try {
            println("Closing output")
            emitter.complete()
          } catch {

            case e: Exception => println("Error closing output")
          }
        }
      }
    }).start()
    
    emitter
  }
}