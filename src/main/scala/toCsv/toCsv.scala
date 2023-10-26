package toCsv

import types.{Matrix, Entry}

import printing.{csvPrinting}

val stringToCsv: String => String = csvPrinting.string

val listOfStringsToCsv: List[String] => String = csvPrinting.listOfStrings

val matrixToCsv: Matrix[Entry] => String = csvPrinting.matrix
