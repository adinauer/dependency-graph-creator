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


Using graphviz
--------------

Alternatively you can also use the dot tool which comes with graphviz to transform the resulting file into JPG, SVG, ...
You can find the .gv file at:

    build/report/dependencies/dependencies.gv

To transform the file you can use a command like the following:

    dot dependencies.gv -Tsvg > tmp.svg


Examples
--------

Matched modules will be colored in blue.

![Alt text](/examples/matched_dependency.png)

![Alt text](/examples/all_project_dependencies.png)


If you click on one of the nodes only interesting edges (dependencies and dependents of the node) will be highlighted.
![Alt text](/examples/only_interesting_edges_highlighted.png)


