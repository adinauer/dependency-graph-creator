class DependencyToDotLanguageTranslator {

	List<String> translateDependenciesToDotLanguage(Map<String, Set<String>> dependenciesGroupedByModule, String regex) {
		List<String> graphEntries = []

		def matchingDependenciesGroupedByModule = dependenciesGroupedByModule.findAll{ it.key ==~ regex }

		graphEntries.addAll graphEntriesForDependencies(dependenciesGroupedByModule)
		graphEntries.addAll graphEntriesForMatchedNodes(matchingDependenciesGroupedByModule)
		graphEntries.addAll graphEntriesForNonMatchedNodes(dependenciesGroupedByModule, matchingDependenciesGroupedByModule)

		return graphEntries
	}

	Set<String> collectAllDependencies(Map<String, Set<String>> dependenciesGroupedByModule) {
		def allDependencies = [] as Set

		dependenciesGroupedByModule.each { module, dependencies ->
			allDependencies.addAll dependencies
		}

		return allDependencies
	}

	List<String> graphEntriesForDependencies(Map<String, Set<String>> dependenciesGroupedByModule) {
		List<String> graphEntries = []

		dependenciesGroupedByModule.each { module, dependencies ->
			dependencies.each { dependency ->
				graphEntries << "\"$module\" -> \"$dependency\";"
			}
		}

		return graphEntries
	}

	List<String> graphEntriesForMatchedNodes(Map<String, Set<String>> matchingDependenciesGroupedByModule) {
		List<String> graphEntries = []

		matchingDependenciesGroupedByModule.each{ module, dependencies ->
			graphEntries << "\"$module\" [fontcolor=blue, color=blue, label=<<TABLE border=\"0\"><TR><TD title=\"$module\" href=\"javascript:redrawGraph('$module');\">$module</TD></TR></TABLE>>];"
		}

		return graphEntries
	}

	List<String> graphEntriesForNonMatchedNodes(Map<String, Set<String>> dependenciesGroupedByModule, Map<String, Set<String>> matchingDependenciesGroupedByModule) {
		List<String> graphEntries = []

		def allDependencies = collectAllDependencies(dependenciesGroupedByModule)

		allDependencies.removeAll matchingDependenciesGroupedByModule.keySet()

		allDependencies.each { dependency ->
			graphEntries << "\"$dependency\" [label=<<TABLE border=\"0\"><TR><TD title=\"$dependency\" href=\"javascript:redrawGraph('$dependency');\">$dependency</TD></TR></TABLE>>];"
		}

		return graphEntries
	}
}