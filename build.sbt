
enablePlugins(ScalaJSPlugin)

name := "scalatags-rx"

version := "0.1"

scalaVersion := "2.12.6"

scalacOptions ++= Seq(
  "-P:scalajs:sjsDefinedByDefault",
  "-language:implicitConversions",
  "-language:higherKinds",
  "-language:existentials",
  "-language:postfixOps",
  "-deprecation", // warning and location for usages of deprecated APIs
  "-feature", // warning and location for usages of features that should be imported explicitly
  "-unchecked", // additional warnings where generated code depends on assumptions
  "-explaintypes", // explain type errors in detail
  "-Xlint", // recommended additional warnings
  "-Xcheckinit", // runtime error when a val is not initialized due to trait hierarchies (instead of NPE somewhere else)
  "-Ywarn-adapted-args", // Warn if an argument list is modified to match the receiver
  "-Ywarn-value-discard", // Warn when non-Unit expression results are unused
  "-Ywarn-inaccessible",
  "-Ywarn-dead-code"
)

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.6"
libraryDependencies += "com.lihaoyi" %%% "scalatags" % "0.6.7"
libraryDependencies += "com.lihaoyi" %%% "scalarx" % "0.4.0"

libraryDependencies += "org.scalatest" %%% "scalatest" % "3.0.5" % Test
libraryDependencies += "org.querki" %%% "jquery-facade" % "1.2" % Test

jsDependencies += "org.webjars" % "jquery" % "2.2.1" / "jquery.js" minified "jquery.min.js"

jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv()