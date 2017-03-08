package com.darwinsw.jee.sample

import org.apache.catalina.startup.Tomcat
import java.io.File
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.web.servlet.DispatcherServlet
import org.apache.tomcat.websocket.server.WsSci

object Main extends App {
  val tomcat = new Tomcat()
  tomcat.setPort(2205)
  
  val docRoot = new File("docroot")
  println("DOCUMENT ROOT: " + docRoot.getAbsolutePath())
  val ctx = tomcat.addContext("", docRoot.getAbsolutePath())
  Tomcat.addServlet(ctx, "hello", new HelloServlet())
  ctx.addServletMapping("/hello", "hello")
  
  ctx.addParameter("contextConfigLocation", "classpath*:application-context.xml")
  ctx.addApplicationListener("org.springframework.web.context.ContextLoaderListener")
  
  ctx.addServletContainerInitializer(new WsSci(), null)

  val apiServlet = Tomcat.addServlet(ctx, "api", new DispatcherServlet())
  apiServlet.addInitParameter("contextConfigLocation", "classpath*:api-servlet-context.xml")
  apiServlet.setAsyncSupported(true)
  ctx.addServletMapping("/api/*", "api")
  
  val staticContentServlet = Tomcat.addServlet(ctx, "staticContentServlet", "org.apache.catalina.servlets.DefaultServlet")
  staticContentServlet.addInitParameter("debug", "1")
  staticContentServlet.addInitParameter("listings", "true")
  staticContentServlet.setLoadOnStartup(1)
  ctx.addServletMapping("/", "staticContentServlet")
  
  tomcat.start()
  tomcat.getServer().await()
}