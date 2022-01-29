name := "producer"

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
  dependencies.typesafeConfig,
  dependencies.scalaFaker,
  dependencies.scalaTest,
  dependencies.fabricator,
  dependencies.akkaActor,
  dependencies.akkaTestKit,
  dependencies.leftweb,
  dependencies.slf4j,
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
    val scalaTest            = "org.scalatest"              %% "scalatest"                    % "3.2.2" % Test
    val fabricator           = "com.github.azakordonets"    % "fabricator_2.10"               % "1.0.4"
    val akkaActor            = "com.typesafe.akka"          %% "akka-actor"                   % akkaVersion
    val akkaTestKit          = "com.typesafe.akka"          %% "akka-testkit"                 % akkaVersion
    val leftweb              = "net.liftweb"                %% "lift-json"                    % "2.6-M4"
    val pubsub               = "com.google.cloud"           % "google-cloud-pubsub"           % "1.114.7"
    val slf4j                 = "org.slf4j"                  % "slf4j-api"                     % "1.7.32"
  }
}

mainClass := Some("org.socialmedia.Producer")
//assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = true, includeDependency = true)

//assemblyMergeStrategy in assembly := {
  //case PathList("joda-convert-2.2.0.jar", xs @ _*) => MergeStrategy.last
  //case PathList("gson-2.8.8.jar", xs @ _*) => MergeStrategy.last
  //case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
  //case _ => MergeStrategy.first
//}
