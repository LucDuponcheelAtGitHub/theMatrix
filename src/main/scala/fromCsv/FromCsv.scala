package fromCsv

import types.{Matrix}

import parsing.{theMatrixToCsvParser}

import theMatrixToCsvParser.{parse, matrix, handleParseResult}

val csvToMatrix: String => Matrix[String] = csv => handleParseResult(parse(matrix, csv))