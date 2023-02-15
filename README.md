## SWRLAPI Drools Engine

[![Build Status](https://travis-ci.org/protegeproject/swrlapi-drools-engine.svg?branch=master)](https://travis-ci.org/protegeproject/swrlapi-drools-engine)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/edu.stanford.swrl/swrlapi-drools-engine/badge.svg)](https://maven-badges.herokuapp.com/maven-central/edu.stanford.swrl/swrlapi-drools-engine)

[Drools](http://www.drools.org/) implementation of a [SWRLAPI](https://github.com/protegeproject/swrlapi/wiki)-based [OWL 2 RL](http://www.w3.org/TR/owl2-profiles/#OWL_2_RL) reasoner and SWRL rule engine. 

Documentation can be found at the [SWRLAPI Drools Engine Wiki](https://github.com/protegeproject/swrlapi-drools-engine/wiki/SWRLAPI-Drools-Engine).

#### Getting Started

This library provides SWRL rule and SQWRL engine implementations for the [SWRLAPI library](https://github.com/protegeproject/swrlapi). See the [SWRLAPI README](https://github.com/protegeproject/swrlapi) for usage instructions.

The engine's dependency information can be found here:

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/edu.stanford.swrl/swrlapi-drools-engine/badge.svg)](https://maven-badges.herokuapp.com/maven-central/edu.stanford.swrl/swrlapi-drools-engine)

#### Building from Source

To build this project you must have the following items installed:

+ [Java 11](https://www.oracle.com/java/technologies/downloads/archive/)
+ A tool for checking out a [Git](https://git-scm.com/) repository
+ Apache's [Maven](https://maven.apache.org/index.html)
+ A Protégé (5.6.0 or higher) distribution. Download [here](https://protege.stanford.edu/products.php#desktop-protege).

Get a copy of the latest code:

    git clone https://github.com/protegeproject/swrlapi-drools-engine.git 

Change into the swrlapi-drools-engine directory:

    cd swrlapi-drools-engine

Then build it with Maven:

    mvn clean install

On build completion your local Maven repository will contain the generated swrlapi-drools-engine-${version}.jar file.

This JAR is used by the [Protégé](http://protege.stanford.edu/) [SWRLTab Plugin](https://github.com/protegeproject/swrltab-plugin)
and by the standalone [SWRLTab](https://github.com/protegeproject/swrltab) tool.

A [Build Project](https://github.com/protegeproject/swrlapi-project) is provided to build core SWRLAPI-related components.
A project containing a [library of integration tests](https://github.com/protegeproject/swrlapi-integration-tests) is also provided.
A sample project that used this rule engine can be found [here](https://github.com/protegeproject/swrlapi-example).

#### License

The software is licensed under the [BSD 2-clause License](https://github.com/protegeproject/swrlapi-drools-engine/blob/master/license.txt).

#### Questions

If you have questions about this library, please go to the main
Protégé website and subscribe to the [Protégé Developer Support
mailing list](http://protege.stanford.edu/support.php#mailingListSupport).
After subscribing, send messages to protege-dev at lists.stanford.edu.
