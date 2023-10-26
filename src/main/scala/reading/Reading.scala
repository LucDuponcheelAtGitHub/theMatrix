package reading

import java.nio.file.{Files, Paths}

import types.{Matrix}

import fromCsv.{csvToMatrix}

val reading: String => (Unit => Matrix[String]) = filePath =>
  case () =>
    val path = Paths.get(filePath)
    csvToMatrix(new String(Files.readAllBytes(path)))

// OK
