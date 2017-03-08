package com.darwinsw.jee.sample

import org.springframework.stereotype.Component

@Component
class InMemoryCyclistRepository extends CyclistRepository {
  override def getAll: List[Cyclist] = InMemoryCyclistRepository.cyclists
}

object InMemoryCyclistRepository {
  val cyclists = List(Cyclist("Fausto", "Coppi"), Cyclist("Eddy", "Merckx"),
      Cyclist("Miguel", "Indurain"), Cyclist("Alberto", "Contador"))
}