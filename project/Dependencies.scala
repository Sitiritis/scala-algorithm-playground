import sbt._

object Dependencies {

  lazy val playground = Seq(
    "org.scalactic" %% "scalactic" % "3.2.11" % "test",
    "org.scalatest" %% "scalatest" % "3.2.11" % "test",
    "org.scalacheck" %% "scalacheck" % "1.15.4" % "test",
    "org.scalatestplus" %% "scalacheck-1-15" % "3.2.11.0" % "test"
  )

}
