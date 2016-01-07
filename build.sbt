disablePlugins(HaxeJavaPlugin)

enablePlugins(HaxeJsPlugin)

organization := "com.thoughtworks.microbuilder"

name := "microbuilder-js"

haxelibDependencies += "microbuilder-core" -> DependencyVersion.SpecificVersion("0.3.0")

libraryDependencies += "com.thoughtworks.microbuilder" % "microbuilder-core" % "0.3.0" % HaxeJs classifier HaxeJs.name

libraryDependencies += "com.thoughtworks.microbuilder" % "json-stream-core" % "3.0.0" % HaxeJs classifier HaxeJs.name


val haxelibs = Map(
  "continuation" -> DependencyVersion.SpecificVersion("1.3.2")
)

haxelibDependencies ++= haxelibs

for {
  c <- Seq(Js, TestJs)
} yield {
  haxeOptions in c ++= haxelibOptions(haxelibs)
}


for {
  c <- Seq(Js, TestJs)
} yield {
  haxeOptions in c += (baseDirectory.value / "build.hxml").getAbsolutePath
}

developers := List(
  Developer(
    "Atry",
    "杨博 (Yang Bo)",
    "pop.atry@gmail.com",
    url("https://github.com/Atry")
  )
)

haxelibContributors := Seq("Atry")

haxelibReleaseNote := "Upgrade microbuilder-core"

haxelibTags ++= Seq(
  "javascript", "js",
  "utility", "rpc", "rest", "micro-service"
)

startYear := Some(2015)

autoScalaLibrary := false

crossPaths := false

homepage := Some(url(s"https://github.com/ThoughtWorksInc/${name.value}"))

releasePublishArtifactsAction := PgpKeys.publishSigned.value

import ReleaseTransformations._

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  publishArtifacts,
  releaseStepTask(publish in Haxe),
  setNextVersion,
  commitNextVersion,
  releaseStepCommand("sonatypeRelease"),
  pushChanges
)

releaseUseGlobalVersion := false

scmInfo := Some(ScmInfo(
  url(s"https://github.com/ThoughtWorksInc/${name.value}"),
  s"scm:git:git://github.com/ThoughtWorksInc/${name.value}.git",
  Some(s"scm:git:git@github.com:ThoughtWorksInc/${name.value}.git")))

licenses += "Apache" -> url("http://www.apache.org/licenses/LICENSE-2.0")
