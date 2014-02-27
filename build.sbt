name := "helloworld"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  postgresql,
  9.1-901-1.jdbc4
)     

play.Project.playJavaSettings
