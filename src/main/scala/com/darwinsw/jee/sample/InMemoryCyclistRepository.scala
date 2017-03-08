package com.darwinsw.jee.sample

class InMemoryCyclistRepository extends CyclistRepository {
  override def getAll: List[Cyclist] = InMemoryCyclistRepository.cyclists
}

object InMemoryCyclistRepository {
  val cyclists = List(Cyclist("Fausto", "Coppi"), Cyclist("Eddy", "Merckx"),
      Cyclist("Miguel", "Indurain"), Cyclist("Alberto", "Contador"))
}