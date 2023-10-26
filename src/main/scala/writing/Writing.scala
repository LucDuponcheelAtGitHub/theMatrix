package writing

import java.io.{FileWriter}

import types.{Matrix, Entry}

import toCsv.{matrixToCsv}

val writing: String => (Matrix[Entry] => Unit) = fileWriterPath =>
  matrix =>
    val outputFileWriter = new FileWriter(fileWriterPath, true)
    outputFileWriter.write(matrixToCsv(matrix))
    outputFileWriter.close()
    ()

// OK
