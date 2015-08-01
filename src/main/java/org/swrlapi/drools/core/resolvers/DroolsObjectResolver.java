package org.swrlapi.drools.core.resolvers;

import checkers.nullness.quals.NonNull;
import org.swrlapi.core.OWLObjectResolver;
import org.swrlapi.drools.owl.classes.CE;
import org.swrlapi.drools.owl.classes.C;
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
public class DroolsObjectResolver
{
  @NonNull private final DroolsCResolver droolsCResolver;
  @NonNull private final DroolsCEResolver droolsCEResolver;
  @NonNull private final DroolsOPEResolver droolsOPEResolver;
  @NonNull private final DroolsDPEResolver droolsDPEResolver;

  public DroolsObjectResolver()
  {
    this.droolsCResolver = new DroolsCResolver();
    this.droolsCEResolver = new DroolsCEResolver();
    this.droolsOPEResolver = new DroolsOPEResolver();
    this.droolsDPEResolver = new DroolsDPEResolver();
  }

  public void reset()
  {
    this.droolsCResolver.reset();
    this.droolsCEResolver.reset();
    this.droolsOPEResolver.reset();
    this.droolsDPEResolver.reset();
  }

  public boolean recordsCID(@NonNull String cid)
  {
    return this.droolsCResolver.recordsCID(cid);
  }

  public void recordC(@NonNull C c)
  {
    this.droolsCResolver.recordC(c);
  }

  @NonNull public C resolveC(@NonNull String cid) { return this.droolsCResolver.resolveC(cid); }

  @NonNull public Set<C> getCs()
  {
    return this.droolsCResolver.getCs();
  }

  public boolean recordsCEID(@NonNull String ceid)
  {
    return this.droolsCEResolver.recordsCEID(ceid);
  }

  public void recordCE(@NonNull CE ce)
  {
    this.droolsCEResolver.recordCE(ce);
  }

  @NonNull public CE resolveCE(@NonNull String ceid) { return this.droolsCEResolver.resolveCE(ceid); }

  @NonNull public Set<CE> getCEs()
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

  @NonNull public DPE resolveDPE(@NonNull String pid) { return this.droolsDPEResolver.resolveDPE(pid); }

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
