package printing

import types.{Row, Matrix, Payment, Entry}

class CsvPrinting:

  val string: String => String = identity

  val listOfStrings: List[String] => String = _.foldLeft("")(_ + _)

  private val payment: Payment => String = payment =>
    if (payment.isDefined) {
      s"${payment.get.setScale(2, BigDecimal.RoundingMode.HALF_UP)}"
    } else {
      ""
    }

  private val entry: Entry => String = entry =>
    entry match {
      case (s: String)   => string(s)
      case (p: Payment) => payment(p)
    }

  private val row: Row[Entry] => String = entryRow =>
    val length: Int = entryRow.length
    listOfStrings(entryRow.zipWithIndex.map { (z, j) =>
      s"${entry(z)}${
          if (j == length - 1) { "\n" }
          else { "," }
        }"
    })

  val matrix: Matrix[Entry] => String = entryMatrix => listOfStrings(entryMatrix.map(row))

val csvPrinting = new CsvPrinting