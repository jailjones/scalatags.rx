resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"

// ScalaJS
addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.25")

// Code Quality
addSbtPlugin("com.geirsson" % "sbt-scalafmt" % "1.5.1")
addSbtPlugin("org.wartremover" % "sbt-wartremover" % "2.3.5")

// Misc
addSbtPlugin("com.artima.supersafe" % "sbtplugin" % "1.1.7")
addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.0.3")
addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.1.1")
addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "1.0.0")

// Deployment
addSbtPlugin("org.foundweekends" % "sbt-bintray" % "0.5.4")