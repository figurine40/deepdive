package org.deepdive.inference

case class Factor(id: Long, factorFunction: String, weight: Weight, 
  variables: List[FactorVariable]) extends CSVFormattable {
  def toCSVRow = Array(id.toString, weight.id.toString, factorFunction.toString)
}

case class FactorVariable(factorId: Long, position: Integer, positive: Boolean, 
  variableId: Long) extends  CSVFormattable {
  def toCSVRow =  Array(factorId.toString, variableId.toString, position.toString, positive.toString)
}
