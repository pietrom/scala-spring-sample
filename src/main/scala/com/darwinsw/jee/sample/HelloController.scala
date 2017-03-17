package com.darwinsw.jee.sample

import akka.actor.Actor
import akka.actor.Actor._

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.ActorRef

@Controller
@RequestMapping(Array("/hello"))
class HelloController(private val repo: CyclistRepository) {
    @RequestMapping(method = Array(RequestMethod.GET))
    @ResponseBody()
    def sayHello(): Hello = {
        new Hello("Hello, Spring World!")
    }

    @RequestMapping(value = Array("events"), method = Array(RequestMethod.GET))
    @ResponseBody()
    def getEvents(): SseEmitter = {
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
                    //          try {
                    //            println("Closing output")
                    //            emitter.complete()
                    //          } catch {
                    //
                    //            case e: Exception => println("Error closing output")
                    //          }
                }
            }
        }).start()

        val system = ActorSystem("HelloSystem")
        val coordinator = system.actorOf(Props(new Coordinator(emitter)), name = "coordinator")
        val producer = system.actorOf(Props[Producer], name = "producer")
        producer ! ""
        
        
        emitter
    }
}

class Coordinator(private val emitter: SseEmitter) extends Actor {
    def receive() = {
        case p: Cyclist => {
            val message = "Hello, " + p.firstName + " " + p.lastName + "!"
            emitter.send(message) }
    }
}

class Producer extends Actor {
    def receive() = {
        case msg: Any => {
            Thread.sleep(200)
            println("===== Sending PM")
            context.actorSelection("/user/coordinator") ! new Cyclist("Pietro", "Martinelli")
        }
    }
}
