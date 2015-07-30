package org.swrlapi.drools.owl.resolvers;

import checkers.nullness.quals.NonNull;
import org.swrlapi.core.OWLObjectResolver;
import org.swrlapi.drools.owl.classexpressions.CE;
import org.swrlapi.drools.owl.properties.DPE;
import org.swrlapi.drools.owl.properties.OPE;

import java.util.Set;

/**
 * Drools does not create class or property expressions but does generate OWL axioms that use them. This class
 * allows Drools to record and resolve class and property expressions using a unique identifier, obviating the need
 * to recreate new expressions.
 *
 * @see OWLObjectResolver
 */
public class DroolsExpressionResolver
{
  @NonNull private final DroolsCEResolver droolsCEResolver;
  @NonNull private final DroolsOPEResolver droolsOPEResolver;
  @NonNull private final DroolsDPEResolver droolsDPEResolver;

  public DroolsExpressionResolver()
  {
    this.droolsCEResolver = new DroolsCEResolver();
    this.droolsOPEResolver = new DroolsOPEResolver();
    this.droolsDPEResolver = new DroolsDPEResolver();
  }

  public void reset()
  {
    this.droolsCEResolver.reset();
    this.droolsOPEResolver.reset();
    this.droolsDPEResolver.reset();
  }

  public boolean recordsCEID(@NonNull String ceid)
  {
    return this.droolsCEResolver.recordsCEID(ceid);
  }

  public void recordCE(@NonNull CE ce)
  {
    this.droolsCEResolver.recordCE(ce);
  }

  public @NonNull Set<CE> getCEs()
  {
    return this.droolsCEResolver.getCEs();
  }

  @NonNull public String generateCEID()
  {
    return this.droolsCEResolver.generateCEID();
  }

  public boolean recordsOPEID(@NonNull String peid)
  {
    return this.droolsOPEResolver.recordsOPEID(peid);
  }

  public void recordOPE(@NonNull OPE pe)
  {
    this.droolsOPEResolver.recordOPE(pe);
  }

  @NonNull public Set<OPE> getOPEs()
  {
    return this.droolsOPEResolver.getOPEs();
  }

  @NonNull public String generateOPEID()
  {
    return this.droolsOPEResolver.generateOPEID();
  }

  public boolean recordsDPEID(@NonNull String peid)
  {
    return this.droolsDPEResolver.recordsDPEID(peid);
  }

  public void recordDPE(@NonNull DPE pe)
  {
    this.droolsDPEResolver.recordDPE(pe);
  }

  @NonNull public Set<DPE> getDPEs()
  {
    return this.droolsDPEResolver.getDPEs();
  }

  @NonNull public String generateDPEID()
  {
    return this.droolsDPEResolver.generateDPEID();
  }
}
