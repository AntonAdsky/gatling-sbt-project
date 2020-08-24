import Dependencies._

lazy val root = (project in file("."))
  .settings(
    inThisBuild(List(
      organization := "example",
      scalaVersion := "2.12.10",
      version := "0.1.0-SNAPSHOT"
    )),
    name := "realtimeotus",
    libraryDependencies ++= gatling
  )

scalacOptions ++= Seq(
  "-language:existentials",
  "-language:implicitConversions",
)

enablePlugins(GatlingPlugin, AssemblyPlugin)

Project.inConfig(Test)(baseAssemblySettings)

assemblyJarName in (Test, assembly) := "Example.jar"

mainClass in (Test, assembly) := Some("io.gatling.app.Gatling")

assemblyMergeStrategy in (Test, assembly) := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

scalaVersion := "2.12.10"

scalacOptions := Seq(
  "-encoding", "UTF-8", "-target:jvm-1.8", "-deprecation",
  "-feature", "-unchecked", "-language:implicitConversions", "-language:postfixOps")

libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % "3.1.3" % "test,it"
libraryDependencies += "io.gatling"            % "gatling-test-framework"    % "3.1.3" % "test,it"

PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)

libraryDependencies += "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf"

libraryDependencies ++= Seq(
  "io.grpc" % "grpc-netty" % scalapb.compiler.Version.grpcJavaVersion,
  "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion
)
libraryDependencies += "com.github.phisgr" %% "gatling-grpc" % "0.4.0" % "test,it"