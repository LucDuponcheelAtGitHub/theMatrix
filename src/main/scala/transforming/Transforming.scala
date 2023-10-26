package transforming

import types.{Row, Column, Matrix, Payment}

//
// body ignores the first row and the last column of a matrix
//
// .-------------------------------------------------------------------------------.
// |   N   |   N   |   N   |   N   |   N   |   N   |   N   |   N   |   N   |       |
// .-------------------------------------------------------------------------------.
// |   P   |   P   |   P   |   P   |   P   |   P   |   P   |   P   |   P   |   I   |
// .-------------------------------------------------------------------------------.
// |   P   |   P   |   P   |   P   |   P   |   P   |   P   |   P   |   P   |   I   |
// .-------------------------------------------------------------------------------.
// |   P   |   P   |   P   |   P   |   P   |   P   |   P   |   P   |   P   |   I   |
// .-------------------------------------------------------------------------------.
// |   P   |   P   |   P   |   P   |   P   |   P   |   P   |   P   |   P   |   I   |
// .-------------------------------------------------------------------------------.
// |   P   |   P   |   P   |   P   |   P   |   P   |   P   |   P   |   P   |   I   |
// .-------------------------------------------------------------------------------.
// |   P   |   P   |   P   |   P   |   P   |   P   |   P   |   P   |   P   |   I   |
// .-------------------------------------------------------------------------------.
// |   P   |   P   |   P   |   P   |   P   |   P   |   P   |   P   |   P   |   I   |
// .-------------------------------------------------------------------------------.
//
// for 'theInputMatrix'
//
// the first row conists of names
//
// the last column consists of payment information
//
// the remaining matrix consists of payments
//

def body[Z]: Matrix[Z] => Matrix[Z] = _.tail.map(_.reverse).map(_.tail).map(_.reverse)

val paymentsMatrix: Matrix[String] => Matrix[Payment] = stringMatrix =>
  body(stringMatrix).map { paymentRow =>
    val somePayment: String => Payment = z => Some(BigDecimal(z))
    val noPayment: Payment = None
    paymentRow.map { string =>
      if (string == "") {
        noPayment
      } else {
        somePayment(string)
      }
    }
  }

val totalAmount: Row[Payment] => Payment = paymentRow =>
  Some(
    paymentRow
      .map { payment =>
        if (payment.isDefined) { payment.get }
        else { BigDecimal(0.0) }
      }
      .foldLeft(BigDecimal(0.0))(_ + _)
  )

val numberOfPayers: Row[Payment] => Int = paymentRow =>
  val sum: List[Int] => Int = _.foldLeft(0)(_ + _)
  paymentRow
    .map { (payment: Payment) =>
      if (payment.isDefined) { 1 }
      else { 0 }
    }
    .foldLeft(0)(_ + _)

val balancedPaymentsMatrix: Matrix[Payment] => Matrix[Payment] = paymentRow =>
  paymentRow.map { payment =>
    val total = totalAmount(payment).get
    val payers = numberOfPayers(payment)
    payment.map { payment =>
      if (payment.isDefined) {
        val amount = payment.get
        Some(amount - total / payers)
      } else { None }
    }
  }

val balancedPersonsMatrix: Matrix[Payment] => Matrix[Payment] = balancedPaymentsMatrix =>
  balancedPaymentsMatrix.transpose

val balancedToBePaidRow: Matrix[Payment] => Row[Payment] = balancedPersonsMatrix =>
  balancedPersonsMatrix.map(totalAmount)

val namesAndInfoRow: Matrix[String] => Row[String] = stringMatrix => stringMatrix.head

val infosColumn: Matrix[String] => Column[String] = stringMatrix => stringMatrix.map(_.last).tail

// OK
