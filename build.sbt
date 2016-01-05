disablePlugins(HaxeJavaPlugin)

enablePlugins(HaxeJsPlugin)

libraryDependencies += "com.thoughtworks.microbuilder" %% "microbuilder-core" % "0.1.1" % HaxeJs classifier HaxeJs.name

libraryDependencies += "com.thoughtworks.microbuilder" %% "json-stream" % "2.0.3" % HaxeJs classifier HaxeJs.name


val haxelibs = Map(
  "continuation" -> DependencyVersion.SpecificVersion("1.3.2"),
  "microbuilder-HUGS" -> DependencyVersion.SpecificVersion("2.0.1")
)

haxelibDependencies ++= haxelibs

for (c <- Seq(Js, TestJs)) yield {
  haxeOptions in c ++= haxelibOptions(haxelibs)
}

