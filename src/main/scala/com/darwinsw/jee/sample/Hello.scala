package com.darwinsw.jee.sample

import scala.beans.BeanProperty

class Hello(@BeanProperty() val message: String) {
}