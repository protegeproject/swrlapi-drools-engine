## SWRLAPI Drools Engine

[Drools](http://www.drools.org/) implementation of a [SWRLAPI](https://github.com/protegeproject/swrlapi/wiki)-based [OWL 2 RL](http://www.w3.org/TR/owl2-profiles/#OWL_2_RL) reasoner and SWRL rule engine. 

Documentation can be found at the [SWRLAPI Drools Engine Wiki](https://github.com/protegeproject/swrlapi-drools-engine/wiki/SWRLAPI-Drools-Engine).

#### Building Prerequisites

To build this project you must have the following items installed:

+ A tool for checking out a [Git](http://git-scm.com/) repository.
+ Apache's [Maven](http://maven.apache.org/index.html).
+ [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

#### Building

Get a copy of the latest code:

    git clone https://github.com/protegeproject/swrlapi-drools-engine.git 

Change into the swrlapi-drools-engine directory:

    cd swrlapi-drools-engine

Then build it with Maven:

    mvn clean install

On build completion your local Maven repository will contain the generated swrlapi-drools-engine-${version}.jar file.

This JAR is used by the [Protégé](http://protege.stanford.edu/) [SWRLTab Plugin](https://github.com/protegeproject/swrltab-plugin)
and by the standalone [SWRLTab](https://github.com/protegeproject/swrltab) tool.

A [Build Project](https://github.com/protegeproject/swrltab-project) is provided to build core SWRLAPI-related components.
