class DependencyToDotLanguageTranslator {

	List<String> translateDependenciesToDotLanguage(Map<String, Set<String>> dependenciesGroupedByModule, String regex) {
		List<String> graphEntries = []

		dependenciesGroupedByModule.each { module, dependencies ->
			dependencies.each { dependency ->
				graphEntries << "\"$module\" -> \"$dependency\";"
			}
		}

		def matchingDependenciesGroupedByModule = dependenciesGroupedByModule.findAll{ it.key ==~ regex }

		matchingDependenciesGroupedByModule.each{ module, dependencies ->
			graphEntries << "\"$module\" [fontcolor=blue, color=blue, label=<<TABLE border=\"0\"><TR><TD title=\"$module\" href=\"javascript:redrawGraph('$module');\">$module</TD></TR></TABLE>>];"
		}


		def allDependencies = collectAllDependencies(dependenciesGroupedByModule)

		allDependencies.removeAll matchingDependenciesGroupedByModule.keySet()

		allDependencies.each { dependency ->
			graphEntries << "\"$dependency\" [label=<<TABLE border=\"0\"><TR><TD title=\"$dependency\" href=\"javascript:redrawGraph('$dependency');\">$dependency</TD></TR></TABLE>>];"
		}

		return graphEntries
	}

	Set<String> collectAllDependencies(Map<String, Set<String>> dependenciesGroupedByModule) {
		def allDependencies = [] as Set
		
		dependenciesGroupedByModule.each { module, dependencies ->
			allDependencies.addAll dependencies
		}

		return allDependencies
	}
}