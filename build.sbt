lazy val commonSettings = Seq(
  scalaVersion := "2.12.6",
)

lazy val benchmark = (project in file("benchmark"))
  .enablePlugins(GatlingPlugin)
  .settings(commonSettings)
  .settings(
    name := "benchmark",
    libraryDependencies ++= Seq(
      "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.3.0" % "test",
      "io.gatling" % "gatling-test-framework" % "2.3.0" % "test",
      "org.scalatest" %% "scalatest" % "3.0.5" % "test",
    ),
  )
