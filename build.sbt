scalaVersion := "2.12.1"

fork in run := true

mainClass in (Compile, run) := Some("futureApplication")

val lv = "0.26.0"

libraryDependencies += "org.longevityframework" %% "longevity" % lv
libraryDependencies += "org.longevityframework" %% "longevity-cassandra-deps" % lv
libraryDependencies += "org.longevityframework" %% "longevity-mongodb-deps" % lv
libraryDependencies += "org.longevityframework" %% "longevity-sqlite-deps" % lv
libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.16.0"
libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.21"
libraryDependencies += "org.typelevel" %% "cats-effect" % "0.3"

enablePlugins(longevity.migrations.Plugin)
modelPackage := "domainModel"
migrationsPackage := "migrations"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
