package at.dinauer.reporting

import org.gradle.api.*
import org.gradle.api.tasks.*

import java.awt.Desktop

class DependencyReportingPlugin implements Plugin<Project> {
    void apply(Project project) {
        def dependencyReportDir = "${project.buildDir}/report/dependencies"

        project.task('helloFromPlugin') << {
            println 'hello from the plugin!'
        }

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
    }
}