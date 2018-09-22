import sbt.util
import wartremover.Wart

enablePlugins(
  ScalaJSPlugin,
  GitBranchPrompt
)

organization := "io.github"
name := "scalatags.rx"
description := "Small scalatags to scala.rx binding library"
version := "0.1"

scalaVersion := "2.12.6"

scalacOptions ++= Seq(
  "-encoding", "UTF-8",
  //"-language:_",
  "-deprecation",
  "-feature",
  "-unchecked",
  "-explaintypes",
  "-P:scalajs:sjsDefinedByDefault"
)

logLevel := util.Level.Warn

Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-oVDF")

resolvers ++= Seq(
  "Artima Maven Repository" at "http://repo.artima.com/releases",
  Resolver.sonatypeRepo("releases")
)

// Libs
libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.6",
  "com.lihaoyi" %%% "scalatags" % "0.6.7",
  "com.lihaoyi" %%% "scalarx" % "0.4.0",
  "io.monix" %%% "monix" % "3.0.0-RC1"
)

// Test Libs
libraryDependencies ++= Seq(
  "org.scalatest" %%% "scalatest" % "3.0.5" % Test,
  "org.querki" %%% "jquery-facade" % "1.2" % Test
)

jsDependencies += "org.webjars" % "jquery" % "2.2.1" / "jquery.js" minified "jquery.min.js"
Test / jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv

// Code Formatter
Compile / scalafmtOnCompile := true
Test / scalafmtOnCompile := true

// Linter
Compile / compile / wartremoverErrors ++= unsafeBut(Wart.NonUnitStatements, Wart.DefaultArguments)
Test / wartremoverErrors ++= unsafeBut(Wart.NonUnitStatements)

def unsafeBut(ws: Wart*): Seq[Wart] = Warts.unsafe filterNot (w => ws exists (_.clazz == w.clazz))

val scmBaseUrl = settingKey[String]("Git base URL")
scmBaseUrl := s"https://github.com/jailjones/${name.value}"

homepage := Some(url(s"${scmBaseUrl.value}/#readme"))
licenses += ("MIT", url(s"${scmBaseUrl.value}/blob/${git.gitCurrentBranch.value}/LICENSE"))
scmInfo := Some(ScmInfo(url(s"${scmBaseUrl.value}"), s"git@github.com:jailjones/${name.value}.git"))

publishMavenStyle := true
publishArtifact := true
Test / publishArtifact := false

bintrayRepository := "maven"
bintrayReleaseOnPublish := false
bintrayPackage := name.value
bintrayOrganization := None