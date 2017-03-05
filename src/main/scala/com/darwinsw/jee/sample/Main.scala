package com.darwinsw.jee.sample

import org.apache.catalina.startup.Tomcat
import java.io.File
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

object Main extends App {
  val tomcat = new Tomcat()
  tomcat.setPort(2205)
  val ctx = tomcat.addContext("/embedded", new File(".").getAbsolutePath())
  tomcat.addServlet("/embedded", "embedded", new HttpServlet() {
    override def doGet(request : HttpServletRequest, response: HttpServletResponse) : Unit = {
      val writer = response.getWriter()
      writer.write("Embedded Tomcat Servlet\n")
      writer.flush()
      writer.close()
    }
  })
  ctx.addServletMapping("/sample", "embedded")
  tomcat.start()
  tomcat.getServer().await()
}