disablePlugins(HaxeJavaPlugin)

enablePlugins(HaxeJsPlugin)

enablePlugins(HaxeJsNpmPlugin)

organization := "com.thoughtworks.microbuilder"

name := "microbuilder-js"

resolvers += "Sonatype Public" at "https://oss.sonatype.org/content/groups/public"

haxelibDependencies += "microbuilder-core" -> DependencyVersion.SpecificVersion("3.0.2")

libraryDependencies += "com.thoughtworks.microbuilder" % "microbuilder-core" % "3.0.2" % HaxeJs classifier HaxeJs.name

libraryDependencies += "com.thoughtworks.microbuilder" % "json-stream-core" % "3.0.2" % HaxeJs classifier HaxeJs.name

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

haxelibReleaseNote := "Adjust method signatures"

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

releaseUseGlobalVersion := false

scmInfo := Some(ScmInfo(
  url(s"https://github.com/ThoughtWorksInc/${name.value}"),
  s"scm:git:git://github.com/ThoughtWorksInc/${name.value}.git",
  Some(s"scm:git:git@github.com:ThoughtWorksInc/${name.value}.git")))

licenses += "Apache" -> url("http://www.apache.org/licenses/LICENSE-2.0")

haxeOptions in Js += "haxe.CallStack"

releaseProcess := {
  releaseProcess.value.patch(releaseProcess.value.indexOf(publishArtifacts), Seq[ReleaseStep](releaseStepTask(publish in Haxe)), 0)
}

releaseProcess := {
  releaseProcess.value.patch(releaseProcess.value.indexOf(pushChanges), Seq[ReleaseStep](releaseStepCommand("sonatypeRelease")), 0)
}

releaseProcess -= runClean

releaseProcess -= runTest
