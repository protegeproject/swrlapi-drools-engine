## SWRLAPI Drools Engine

[Drools](http://www.drools.org/) implementation of a [SWRLAPI](https://github.com/protegeproject/swrlapi/wiki)-based [OWL 2 RL](http://www.w3.org/TR/owl2-profiles/#OWL_2_RL) reasoner and SWRL rule engine. 

Documentation can be found at the [SWRLAPI Drools Engine Wiki](https://github.com/protegeproject/swrlapi-drools-engine/wiki/SWRLAPI-Drools-Engine).

#### Getting Started

This library provides SWRL rule and SQWRL engine implementations for the [SWRLAPI library](https://github.com/protegeproject/swrlapi). See the README for the SWRLAPI library for usage instructions.

If you want to include this library in a project, either download the latest JAR from the [project's GitHub Release area](https://github.com/protegeproject/swrlapi-drools-engine/releases) or use your favourite dependency management tool to download it from Maven Central. Here is the Maven dependency for this library:

```
  <dependency>
    <groupId>edu.stanford.swrl</groupId>
    <artifactId>swrlapi-drools-engine</artifactId>
    <version>1.0.0-beta-7</version>
  </dependency>
```

#### Building

To build this project you must have the following items installed:

+ A tool for checking out a [Git](http://git-scm.com/) repository.
+ Apache's [Maven](http://maven.apache.org/index.html).
+ [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

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
A project containing a [library of integration tests](https://github.com/protegeproject/swrlapi-integration-tests) is also proviced.

#### Questions

If you have questions about this library, please go to the main
Protégé website and subscribe to the [Protégé Developer Support
mailing list](http://protege.stanford.edu/support.php#mailingListSupport).
After subscribing, send messages to protege-dev at lists.stanford.edu.
