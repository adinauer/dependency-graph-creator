import groovy.io.FileType

// now run
// groovy dependencies.groovy '.*acquisition.*' && dot tmp.gv -Tsvg > tmp.svg

class MavenDependencyRetriever {
	Map<String, Set<String>> parseMatchingDependenciesFromPoms(List<File> pomFiles, String regex) {
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

class MavenDependencyGraphCreator {

	List<String> createGraphForMatchingDependenciesInPoms(Map<String, Set<String>> dependenciesGroupedByModule) {
		List<String> graphEntries = []
		dependenciesGroupedByModule.each { module, dependencies ->
			graphEntries << "\"$module\";"
			dependencies.each { dependency ->
				graphEntries << "\"$module\" -> \"$dependency\";"
			}
		}

		return graphEntries
	}
}

class GraphvizDotfileCreator {
	void createDirectedGraphFile(File targetFile, List<String> graphEntries) {
		targetFile.withWriter { out ->
			out.println 'digraph dependencies {'
			
			graphEntries.each {
				out.println it
			}

			out.println '}'
		}
	}
}

def dependencyRegex = args[0]

List<File> pomFiles = []
new File('/home/alex/repos/fhbay').eachFile(FileType.DIRECTORIES) { module ->
    pomFiles << new File(module.absolutePath + '/pom.xml')
}

def dependenciesGroupedByModule = new MavenDependencyRetriever().parseMatchingDependenciesFromPoms(pomFiles, dependencyRegex)
def graphEntries = new MavenDependencyGraphCreator().createGraphForMatchingDependenciesInPoms(dependenciesGroupedByModule)

graphEntries.each {	println it }

File graphDotFile = new File("tmp.gv")
new GraphvizDotfileCreator().createGraphvizDotfile(graphDotFile, graphEntries)