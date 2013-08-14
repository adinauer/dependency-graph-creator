class DependencyToDotLanguageTranslator {

	List<String> translateDependenciesToDotLanguage(Map<String, Set<String>> dependenciesGroupedByModule) {
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