## `theMatrix` is a simple, csv text based, "share a bill app"

- Not every payment has to be done with all persons involved.
- Every participant of a payment can decide to 
  - not contribute to it with a zero amount,
  - contribute to it with a positive amount,
  - contribute to it with a negative amount.
- The total amount of a payments can be zero, positive or negative.

Running `theMatrix` with first command line argument `input.csv` 

https://github.com/PSBP-Library/theMatrix/blob/main/input.csv

and second command line argument `output.csv`, creates `output.csv`

https://github.com/PSBP-Library/theMatrix/blob/main/output.csv

(assuming `output.csv` does not exist).

The only library dependency is the parser combinator library one

 - `"org.scala-lang.modules" %% "scala-parser-combinators" % "2.2.0"`

**Limitation**

Payment information can consist of any sentence starting with a word, followed
by alphanumeric words or a date, but, *if* the sentence contains a date, *then*
that date *must* be of the form `dd-mm-yyyy`.
