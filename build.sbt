lazy val sparkStreaming = (project in file("./"))
  .enablePlugins(JavaAppPackaging)
  .settings(
    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-core" % "2.4.4",
      "org.apache.spark" %% "spark-sql" % "2.4.4"
    ),
    name := "spark-streaming",
    mainClass in Compile := Some("ru.neoflex.triad.SparkTellDifference"),
    mappings in Universal += {
      val conf = (resourceDirectory in Compile).value / "application.conf"
      conf -> "conf/application.conf"
    },
    mainClass in assembly := Some("ru.neoflex.vtbtriad.CustomerProfileSender"),
    test in assembly := {},
    version := "0.1",
    scalaVersion := "2.12.1"
  )