package at.dinauer.reporting

class MavenDependencyGatherer implements DependencyGatherer {
    File rootPomFile
    String dependencyRegex
    String projectRegex

    Map<String, Set<String>> gatherDependencies() {
        new MavenMultiModulePomParser().parseMatchingDependencies(rootPomFile, dependencyRegex, projectRegex)
    }
}