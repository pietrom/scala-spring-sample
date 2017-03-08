package com.darwinsw.jee.sample

import org.apache.catalina.startup.Tomcat

import java.io.File
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.web.servlet.DispatcherServlet
import org.apache.tomcat.websocket.server.WsSci
import org.apache.catalina.Context

class WebContainer(private val docRoot: String) {
  private var tomcat: Tomcat = null
  private var ctx: Context = null

  def this(docRoot: String, port: Int) {
    this(docRoot)
    tomcat = new Tomcat()
    tomcat.setPort(port)
    ctx = tomcat.addContext("", new File(docRoot).getAbsolutePath())
  }

  def enableStaticFiles() = {
    val staticContentServlet = Tomcat.addServlet(ctx, "staticContentServlet", "org.apache.catalina.servlets.DefaultServlet")
    staticContentServlet.addInitParameter("debug", "1")
    staticContentServlet.addInitParameter("listings", "true")
    staticContentServlet.setLoadOnStartup(1)
    ctx.addServletMapping("/", "staticContentServlet")
    this
  }

  def enableWebSocket() = {
    ctx.addServletContainerInitializer(new WsSci(), null)
    this
  }

  def enableSpringApplicationContext(context: String) = {
    ctx.addParameter("contextConfigLocation", "classpath*:application-context.xml")
    ctx.addApplicationListener("org.springframework.web.context.ContextLoaderListener")
    this
  }

  def enableSpringMvc(path: String, context: String) = {
    val apiServlet = Tomcat.addServlet(ctx, "api", new DispatcherServlet())
    apiServlet.addInitParameter("contextConfigLocation", context)
    apiServlet.setAsyncSupported(true)
    ctx.addServletMapping(path + "/*", "api")

    this
  }

  def startAndwait() = {
    tomcat.start()
    tomcat.getServer().await()
  }
}

object Main extends App {
  new WebContainer("docroot", 2205)
    .enableStaticFiles()
    .enableWebSocket()
    .enableSpringApplicationContext("classpath:application-context.xml")
    .enableSpringMvc("/api", "classpath:api-servlet-context.xml")
    .startAndwait()
}