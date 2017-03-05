package com.darwinsw.jee.sample

import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest

class HelloServlet extends HttpServlet {
  override def doGet(request: HttpServletRequest, response: HttpServletResponse): Unit = {
    val writer = response.getWriter()
      writer.write("Hello, World!")
      writer.flush()
      writer.close()
  }
}