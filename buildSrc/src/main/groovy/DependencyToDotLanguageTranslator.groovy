class DependencyToDotLanguageTranslator {

	List<String> translateDependenciesToDotLanguage(Map<String, Set<String>> dependenciesGroupedByModule, String regex) {
		List<String> graphEntries = []

		dependenciesGroupedByModule.each { module, dependencies ->
			dependencies.each { dependency ->
				graphEntries << "\"$module\" -> \"$dependency\";"
			}
		}

		dependenciesGroupedByModule.findAll{ it.key ==~ regex }.each{ module, dependencies ->
			graphEntries << "\"$module\";"
		}

		return graphEntries
	}
}