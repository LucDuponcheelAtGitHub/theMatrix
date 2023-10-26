package types

type Row[Z] = List[Z]

type Column[Z] = List[Z]

type Matrix[Z] = List[Row[Z]]

type Amount = BigDecimal

type Payment = Option[Amount]

type Entry = String | Payment

// OK

