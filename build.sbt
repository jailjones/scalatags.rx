
enablePlugins(ScalaJSPlugin)

name := "scalatags-rx"

version := "0.1"

scalaVersion := "2.12.6"

scalacOptions ++= Seq(
  "-P:scalajs:sjsDefinedByDefault",
  "-feature",
  "-language:implicitConversions",
  "-language:higherKinds",
  "-language:existentials",
  "-language:postfixOps"
)

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.6"
libraryDependencies += "com.lihaoyi" %%% "scalatags" % "0.6.7"
libraryDependencies += "com.lihaoyi" %%% "scalarx" % "0.4.0"

testFrameworks += new TestFramework("utest.runner.Framework")

libraryDependencies += "com.lihaoyi" %%% "utest" % "0.6.3" % Test

jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv()