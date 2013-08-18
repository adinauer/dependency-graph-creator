package at.dinauer.reporting

interface DependencyGatherer {
    Map<String, Set<String>> gatherDependencies()
}