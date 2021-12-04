name := "producer"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  dependencies.typesafeConfig,
  dependencies.scalaFaker,
  dependencies.scalaTest,
  dependencies.fabricator,
  dependencies.akkaActor,
  dependencies.akkaTestKit,
  dependencies.leftweb,
  dependencies.pubsub
)

resolvers ++= Seq(
  "Confluent" at "https://packages.confluent.io/maven",
  "Fabricator" at "https://dl.bintray.com/biercoff/Fabricator"
)

lazy val dependencies = {

  new {
    val akkaVersion = "2.5.13"

    val typesafeConfig       = "com.typesafe"               % "config"                        % "1.4.0"
    val scalaFaker           = "com.github.stevenchen3"     %% "scala-faker"                  % "0.1.1"
    val scalaTest            = "org.scalatest"              %% "scalatest"                    % "3.2.2" % "test"
    val fabricator           = "com.github.azakordonets"    % "fabricator_2.10"               % "1.0.4"
    val akkaActor            = "com.typesafe.akka"          %% "akka-actor"                   % akkaVersion
    val akkaTestKit          = "com.typesafe.akka"          %% "akka-testkit"                 % akkaVersion
    val leftweb              = "net.liftweb"                %% "lift-json"                    % "2.6-M4"
    val logging              = "com.typesafe.scala-logging" %% "scala-logging"                % "3.9.0"
    val pubsub               = "com.google.cloud"           % "google-cloud-pubsub"           % "1.114.7"
  }
}


