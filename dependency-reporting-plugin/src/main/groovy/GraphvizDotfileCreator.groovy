class GraphvizDotfileCreator {
	void createDirectedGraphFile(File targetFile, List<String> graphEntries) {
		targetFile.withWriter { out ->
			out.println 'digraph dependencies {'
			out.println 'rankdir=LR;'
			out.println 'edge [penwidth=2];'
			
			graphEntries.each {
				out.println it
			}

			out.println '}'
		}
	}
}
