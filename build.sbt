scalaVersion := "2.12.1"

fork in run := true

libraryDependencies += "org.longevityframework" %% "longevity" % "0.19.0"

libraryDependencies += "org.longevityframework" %% "longevity-cassandra-deps" % "0.19.0"

libraryDependencies += "org.longevityframework" %% "longevity-mongo-deps" % "0.19.0"

libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.12.0"

libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.21"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
