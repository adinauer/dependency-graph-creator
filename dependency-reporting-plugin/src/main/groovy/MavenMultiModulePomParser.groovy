import groovy.io.FileType

class MavenMultiModulePomParser {
	Map<String, Set<String>> parseMatchingDependencies(File rootPomFile, String dependencyRegex, String projectRegex) {
		return new MavenPomParser().parseMatchingDependencies(collectModulePomFiles(rootPomFile), dependencyRegex, projectRegex)
	}

	private List<File> collectModulePomFiles(File rootPomFile) {
		List<File> pomFiles = []

		rootPomFile.parentFile.eachFile(FileType.DIRECTORIES) { module ->
      File pomFile = new File(module.absolutePath + '/pom.xml')
      if (pomFile.exists()) {
  		  pomFiles << pomFile
  		  pomFiles.addAll(collectModulePomFiles(pomFile))
      }
		}

		return pomFiles
	}
}