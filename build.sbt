name := "StoreStaffBot"

lazy val commonSettings = Seq(
  version := "0.1",
  scalaVersion := "2.12.4"
)

lazy val chatIf = (project in file("chat-if"))
  .settings(
    commonSettings,
    resolvers += Resolver.bintrayRepo("hseeberger", "maven"),
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"       % "10.0.10",
      "com.typesafe.akka" %% "akka-slf4j"      % "2.4.19",
      "de.heikoseeberger" %% "akka-http-circe" % "1.18.1",
      "io.circe"          %% "circe-generic"   % "0.8.0",
      "com.google.inject" %  "guice"           % "4.1.0",
      "com.typesafe"      %  "config"          % "1.3.2",
      "ch.qos.logback"    % "logback-classic"  % "1.2.3",
      "org.scalatest"     %% "scalatest"       % "3.0.4" % "test",
    )
  )
