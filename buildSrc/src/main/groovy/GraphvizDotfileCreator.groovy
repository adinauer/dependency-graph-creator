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
