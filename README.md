Dependency Graph Creator
========================

A gradle project that allows you to visually analyse the dependencies of a multi-module Maven project.

Running
-------

You can generate a graph in graphviz dot language and an html report to render the graph by using the following command:

    gradle createDependencyReport -ProotDirOfProjectToSearch=/home/adinauer/repos/topsecret -PdependencyRegex='topsecret-web' -PprojectRegex='.*topsecret.*'


After creating the report you can either open it manually or have gradle open it for you using your default application for opening .html files.

    gradle openDependencyReport

The report is located in:

    build/report/dependencies/dependencies.html

