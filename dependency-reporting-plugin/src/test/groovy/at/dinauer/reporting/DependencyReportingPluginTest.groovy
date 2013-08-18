package at.dinauer.reporting

import static org.junit.Assert.*

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

class DependencyReportingPluginTest {
    @Test void applyingPluginWorks() {
        Project project = new ProjectBuilder().builder().build()
        project.plugins.apply(DependencyReportingPlugin)


        assertTrue(project.getPlugins().hasPlugin(DependencyReportingPlugin));
    }
}