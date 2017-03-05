package com.darwinsw.jee.sample

import org.apache.catalina.startup.Tomcat
import java.io.File
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

object Main extends App {
  val tomcat = new Tomcat()
  tomcat.setPort(2205)
  val ctx = tomcat.addContext("/", new File(".").getAbsolutePath())
  tomcat.addServlet("/", "embedded", new HelloServlet())
  ctx.addServletMapping("/hello", "embedded")
  
  tomcat.start()
  tomcat.getServer().await()
}