import groovy.io.FileType

class MavenPomParser {
	Map<String, Set<String>> parseMatchingDependencies(List<File> pomFiles, String regex) {
		Map<String, Set<String>> dependenciesGroupedByModule = [:]


		pomFiles.each { pomFile ->
			if (pomFile.exists()) {
				def pom = new XmlSlurper().parse(pomFile)

				String module = pom.artifactId.toString()
				def dependencies = pom.dependencies.dependency

				dependenciesGroupedByModule[module] = [] as Set

				dependencies.findAll{ it.artifactId ==~ regex }.each { dependency ->
					dependenciesGroupedByModule[module] << dependency.artifactId.toString()
				}
			}
		}

		return dependenciesGroupedByModule
	}
}

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