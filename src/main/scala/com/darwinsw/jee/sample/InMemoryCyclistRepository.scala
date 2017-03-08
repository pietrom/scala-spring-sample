package com.darwinsw.jee.sample

import org.springframework.stereotype.Component

@Component
class InMemoryCyclistRepository extends CyclistRepository {
  override def getAll: List[Cyclist] = InMemoryCyclistRepository.cyclists
}

object InMemoryCyclistRepository {
  val cyclists = List(new Cyclist("Fausto", "Coppi"), new Cyclist("Eddy", "Merckx"),
      new Cyclist("Miguel", "Indurain"), new Cyclist("Alberto", "Contador"))
}