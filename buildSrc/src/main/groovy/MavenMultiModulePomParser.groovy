import groovy.io.FileType

class MavenMultiModulePomParser {
	Map<String, Set<String>> parseMatchingDependencies(File rootPomFile, String regex) {
		return new MavenPomParser().parseMatchingDependencies(collectModulePomFiles(rootPomFile), regex)
	}

	private List<File> collectModulePomFiles(File rootPomFile) {
		List<File> pomFiles = []

		rootPomFile.parentFile.eachFile(FileType.DIRECTORIES) { module ->
		    pomFiles << new File(module.absolutePath + '/pom.xml')
		}

		return pomFiles
	}
}