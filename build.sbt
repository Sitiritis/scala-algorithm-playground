import Dependencies._

ThisBuild / scalaVersion := "2.13.8"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "io.sitiritis"
ThisBuild / organizationName := "sitiritis"

ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision
ThisBuild / scalafixScalaBinaryVersion := "2.13"

resolvers += "Artima Maven Repository" at "https://repo.artima.com/releases"

lazy val root = (project in file("."))
  .settings(
    name := "scala-algorithm-playground",
    libraryDependencies ++= playground
  )
