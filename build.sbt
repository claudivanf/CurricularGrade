name := "lab3"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  javaCore,
  javaJpa,
  cache
)     

play.Project.playJavaSettings

libraryDependencies ++= Seq(
	"postgresql" % "postgresql" % "9.1-901-1.jdbc4"
)


