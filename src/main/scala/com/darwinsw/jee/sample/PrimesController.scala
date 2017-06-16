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
import org.springframework.web.bind.annotation.PathVariable
import scala.annotation.tailrec

@Controller
@RequestMapping(Array("/primes"))
class PrimesController {
    @RequestMapping(value = Array("{number}"), method = Array(RequestMethod.GET))
    @ResponseBody()
    def getEvents(@PathVariable number: Long): SseEmitter = {
        val emitter = new SseEmitter()
        //        emitter.complete()

        new Thread(new Runnable {
            override def run(): Unit = {
                try {
                    @tailrec
                    def nextStep(n: Long, p: Long) : Unit = {
                        val nn = if(n % p == 0) n / p else n
                        val pp = if(n % p == 0) p else p + 1
                        
                        if (n % p == 0) {
                            emitter.send(p)                            
                        }
                        
                        if(n % p == 0 || n > 1) {
                            nextStep(nn, pp)
                        } else {
                            emitter.complete()
                        }
                    }
                    nextStep(number, 2)
                } catch {
                    case e: Exception => e.printStackTrace()
                }
            }
        }).start()
        emitter
    }
}
