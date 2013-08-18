package at.dinauer.reporting

import org.gradle.api.*
import org.gradle.api.tasks.*

import java.awt.Desktop

class DependencyReportingPlugin implements Plugin<Project> {
    void apply(Project project) {
        def dependencyReportDir = "${project.buildDir}/report/dependencies"

        project.extensions.create('dependencyReporting', DependencyReportingPluginExtension)

        project.task('openDependencyReport') << {
            Desktop.getDesktop().open(new File("$dependencyReportDir/dependencies.html"))
        }

        project.task('createDependencyReport', dependsOn: 'copyDependencyReportTemplate')

        project.task('copyDependencyReportTemplate', dependsOn: ['createDependencyGraph', 'copyDependencyReportLibraries']) << {
            project.copy {
                from "src/main/resources"
                into dependencyReportDir

                String graphAsDot = project.createDependencyGraph.outputs.files.singleFile.text

                expand(dependencies: graphAsDot)
            }
        }

        project.task('copyDependencyReportLibraries', type: Copy) {
            from "src/main/lib"
            into dependencyReportDir
        }

        project.task('createDependencyGraph', type: CreateDependencyGraph) { task ->
            task.conventionMapping.dependencyGatherer = { project.dependencyReporting.dependencyGatherer }
            translator = new DependencyToDotLanguageTranslator()
            fileCreator = new GraphvizDotfileCreator()
            task.conventionMapping.regex = { project.dependencyReporting.dependencyRegex }

            graphDotFile = project.file("$dependencyReportDir/dependencies.gv")

            outputs.upToDateWhen { false }
        }
    }
}

class DependencyReportingPluginExtension {
    def rootDirOfProjectToSearch
    def dependencyRegex
    def projectRegex
    DependencyGatherer dependencyGatherer
}

class CreateDependencyGraph extends DefaultTask {
    DependencyGatherer dependencyGatherer
    DependencyToDotLanguageTranslator translator
    GraphvizDotfileCreator fileCreator
    String regex

    @OutputFile
    File graphDotFile

    @TaskAction
    void createDotfile() {
        def dependenciesGroupedByModule = getDependencyGatherer().gatherDependencies()

        def graphEntries = translator.translateDependenciesToDotLanguage(dependenciesGroupedByModule, getRegex())

        fileCreator.createDirectedGraphFile(graphDotFile, graphEntries)
    }
}

