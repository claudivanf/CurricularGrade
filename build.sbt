name := "helloworld"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  postgresql
)     

play.Project.playJavaSettings
