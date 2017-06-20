scalaVersion := "2.12.2"

fork in run := true

val lv = "0.24-SNAPSHOT"

libraryDependencies += "org.longevityframework" %% "longevity" % lv

libraryDependencies += "org.longevityframework" %% "longevity-cassandra-deps" % lv

libraryDependencies += "org.longevityframework" %% "longevity-mongodb-deps" % lv

libraryDependencies += "org.longevityframework" %% "longevity-sqlite-deps" % lv

libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.16.0"

libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.21"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
