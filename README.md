# SWRLAPI Drools engine

A SWRL rule engine, SQWRL query engine and OWL 2 RL reasoner implementation for
SWRLAPI. The `protege-6` branch uses **SWRLAPI 2.1.5-SNAPSHOT**, **OWLAPI 5.5.1** and
**Drools 7.74.1.Final**. A Drools major-version upgrade is separate roadmap work.

## Use and build

Include this engine with the matching SWRLAPI library and create engines through
`SWRLAPIFactory`; see the [library example](https://github.com/protegeproject/swrlapi/blob/protege-6/README.md#run-a-query).
Build the current sibling sources together with JDK 17 / Maven 3.9 from the workspace root:

```sh
mvn -B -f swrlapi-project/pom.xml -Pcore,static-analysis clean verify
```

The core build needs no Protégé installation. It runs isolated rule tests, public
reasoner/query integration tests and SpotBugs, compiling to Java 11 bytecode.

## Reasoning boundary

The cached `getOWLReasoner()` accessor materializes an isolated ontology/imports
snapshot, refreshes after ontology changes and does not export inferred axioms.
Dispose it when finished. The interactive engine's rule selection is independent
of the accessor's default rule set.

Data-property rules use value semantics, retain lexical forms in results, and
leave unknown comparisons undecided. Qualified maximum-cardinality restrictions
retain their datatype filler; XML literals use canonical comparison. Consult the
[datatype support matrix](https://github.com/protegeproject/swrlapi-project/blob/protege-6/ops/runbook.md#datatype-support-matrix)
before relying on a construct: custom definitions and facet constraints are not
implemented. Passing consistency checks do not establish complete OWL 2 RL or DL
conformance, and known empty-class detection is not general satisfiability.

[Build guide](https://github.com/protegeproject/swrlapi-project/blob/protege-6/README.md) · [Runbook](https://github.com/protegeproject/swrlapi-project/blob/protege-6/ops/runbook.md) · [Roadmap](https://github.com/protegeproject/swrlapi-project/blob/protege-6/ops/roadmap.md)

## License

[BSD 2-Clause](license.txt), copyright Stanford University.
