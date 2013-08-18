package at.dinauer.reporting

import org.gradle.api.*

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
    }
}