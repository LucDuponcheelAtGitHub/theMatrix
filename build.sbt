val scala3Version = "3.3.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "theMatrix",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.2.0"    
  )

