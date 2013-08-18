package at.dinauer.reporting

import org.gradle.api.*

class DependencyReportingPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.task('helloFromPlugin') << {
            println 'hello from the plugin!'
        }
    }
}