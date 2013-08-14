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