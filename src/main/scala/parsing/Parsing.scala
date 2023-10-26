package parsing

import scala.util.parsing.combinator.*

import types.{Matrix}

import toCsv.{stringToCsv, listOfStringsToCsv}

private val makeSentence: List[String] => String = addAll =>
  val length: Int = addAll.length
  listOfStringsToCsv(addAll.zipWithIndex.map { (z, j) =>
    z + (if (j == length - 1) { "" }
         else { " " })
  })

val nl = System.getProperty("line.separator")

class TheMatrixToCsvParser extends RegexParsers:

  def string = (_: Parser[String]) ^^ stringToCsv

  def addAll = (_: Parser[List[String]]) ^^ listOfStringsToCsv

  def makeRestOfSentence = (_: Parser[List[String]]) ^^ makeSentence

  def add2 = (_: Parser[String ~ String]) ^^ { case z ~ y => z + y }

  def add3 = (_: Parser[String ~ String ~ String]) ^^ { case z ~ y ~ x => z + y + x }

  def add5 = (_: Parser[String ~ String ~ String ~ String ~ String]) ^^ { case z ~ y ~ x ~ w ~ v => z + y + x + w + v }

  override val whiteSpace = """""".r

  private val empty = literal("")

  private val blank = literal(" ")

  private val newLine = literal(nl)

  private val dot = literal(".")

  private val dash = literal("-")

  private val comma = literal(",")

  private val letter = string("""[a-zA-Z]""".r)

  private val letters1 = addAll(rep1(letter))

  private val digit = string("""[0-9]""".r)

  private val digits = addAll(rep(digit))

  private val twoDigits = add2(digit ~ digit)

  private val fourDigits = add2(twoDigits ~ twoDigits)

  private val date = add5(twoDigits ~ dash ~ twoDigits ~ dash ~ fourDigits)

  private val letterOrDigit = letter | digit

  private val lettersOrDigits = addAll(rep(letterOrDigit))

  private val word = add2(letters1 ~ lettersOrDigits)

  private val sentence = add2(word ~ makeRestOfSentence(repsep(date | lettersOrDigits, blank)))

  private val positiveDecimal = add3(digits ~ dot ~ digits) | digits

  private val negativeDecimal = add2(literal("-") ~ positiveDecimal)

  private val decimal = negativeDecimal | positiveDecimal

  private val entry = sentence | decimal | empty

  private val row = repsep(entry, comma)

  val matrix = repsep(row, newLine)

  def handleParseResult[Z]: ParseResult[List[List[Z]]] => List[List[Z]] = parseResult =>
    parseResult match {
      case Success(result, _) => result
      case Failure(msg, _)    => println(s"Failure: $msg"); List(List())
      case Error(msg, _)      => println(s"Error: $msg"); List(List())
    }

val theMatrixToCsvParser = new TheMatrixToCsvParser

// OK
