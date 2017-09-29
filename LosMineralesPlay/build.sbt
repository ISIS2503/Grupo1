name := """play-scala-starter-example"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

resolvers += Resolver.sonatypeRepo("snapshots")
routesGenerator := InjectedRoutesGenerator

scalaVersion := "2.12.2"
libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.0.0" % Test
libraryDependencies += "com.h2database" % "h2" % "1.4.194"
libraryDependencies += "com.impetus.kundera.client" % "kundera-cassandra" % "3.6"
libraryDependencies ++= Seq(
  "org.reactivemongo" %% "play2-reactivemongo" % "0.12.6-play26"
)
resolvers ++= Seq(
  "Kundera" at "https://oss.sonatype.org/content/repositories/releases",
  "Riptano" at "http://datastax.artifactoryonline.com/datastax/simple/datastax-public-releases-local/",
  "Kundera missing" at "http://kundera.googlecode.com/svn/maven2/maven-missing-resources",
  "Scale 7" at "https://github.com/s7/mvnrepo/raw/master"
)