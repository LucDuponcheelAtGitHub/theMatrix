package main

import java.io.{FileWriter}

import types.{Row, Column, Matrix, Payment, Entry}

import reading.{reading}

import transforming.{
  paymentsMatrix,
  balancedPaymentsMatrix,
  balancedPersonsMatrix,
  balancedToBePaidRow,
  namesAndInfoRow,
  infosColumn
}

import writing.{writing}

@main def main(args: String*): Unit =
  val inputFilePath = args(0)

  val theInputMatrix: Matrix[String] = reading(inputFilePath)(())

  val theLength: Int = theInputMatrix.head.length

  val thePaymentsMatrix: Matrix[Payment] = paymentsMatrix(theInputMatrix)
  val theBalancedPaymentsMatrix: Matrix[Payment] = balancedPaymentsMatrix(thePaymentsMatrix)
  val theBalancedPersonsMatrix: Matrix[Payment] = balancedPersonsMatrix(theBalancedPaymentsMatrix)
  val theBalancedToBePaidRow: Row[Payment] = balancedToBePaidRow(theBalancedPersonsMatrix)

  val theInfosColumn: Column[String] = infosColumn(theInputMatrix)

  val theNamesAndInfoRow: Row[Entry] = namesAndInfoRow(theInputMatrix)

  val theNamesAndInfoMatrix: Matrix[Entry] = List(theNamesAndInfoRow)
  val thePaymentsAndInfoMatrix: Matrix[Entry] =
    theInfosColumn.zip(thePaymentsMatrix).map { (info, paymentRow) => paymentRow ::: List(info) }
  val theBalancedPaymentsAndInfoMatrix: Matrix[Entry] =
    theInfosColumn.zip(theBalancedPaymentsMatrix).map { (info, paymentRow) => paymentRow ::: List(info) }
  val theBalancedToBePaidAndInfoMatrix: Matrix[Entry] = List(theBalancedToBePaidRow ::: List("to be paid"))

  val separatorMatrix: Matrix[Entry] = List(theNamesAndInfoRow.map { entry => "" })

  val theOutputMatrix: Matrix[Entry] =
    theNamesAndInfoMatrix :::
      separatorMatrix :::
      thePaymentsAndInfoMatrix :::
      separatorMatrix :::
      theBalancedPaymentsAndInfoMatrix :::
      separatorMatrix :::
      theBalancedToBePaidAndInfoMatrix

  val outputFileWriterPath = args(1)    

  writing(outputFileWriterPath)(theOutputMatrix)

// OK  