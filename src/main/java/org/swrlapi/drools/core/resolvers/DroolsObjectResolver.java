package org.swrlapi.drools.core.resolvers;

import checkers.nullness.quals.NonNull;
import org.swrlapi.core.OWLObjectResolver;
import org.swrlapi.drools.owl.classes.C;
import org.swrlapi.drools.owl.classes.CE;
import org.swrlapi.drools.owl.dataranges.D;
import org.swrlapi.drools.owl.individuals.I;
import org.swrlapi.drools.owl.properties.DP;
import org.swrlapi.drools.owl.properties.DPE;
import org.swrlapi.drools.owl.properties.OP;
import org.swrlapi.drools.owl.properties.OPE;

import java.util.Set;

/**
 * This class allows Drools to record and its representations of OWLAPI objects using a unique identifier, obviating
 * the need to recreate new objects.
 *
 * @see OWLObjectResolver
 */
public class DroolsObjectResolver
{
  @NonNull private final DroolsCResolver droolsCResolver;
  @NonNull private final DroolsCEResolver droolsCEResolver;
  @NonNull private final DroolsIResolver droolsIResolver;
  @NonNull private final DroolsOPEResolver droolsOPEResolver;
  @NonNull private final DroolsOPResolver droolsOPResolver;
  @NonNull private final DroolsDPEResolver droolsDPEResolver;
  @NonNull private final DroolsDPResolver droolsDPResolver;
  @NonNull private final DroolsDResolver droolsDResolver;

  public DroolsObjectResolver()
  {
    this.droolsCResolver = new DroolsCResolver();
    this.droolsCEResolver = new DroolsCEResolver();
    this.droolsIResolver = new DroolsIResolver();
    this.droolsOPEResolver = new DroolsOPEResolver();
    this.droolsOPResolver = new DroolsOPResolver();
    this.droolsDPEResolver = new DroolsDPEResolver();
    this.droolsDPResolver = new DroolsDPResolver();
    this.droolsDResolver = new DroolsDResolver();
  }

  public void reset()
  {
    this.droolsCResolver.reset();
    this.droolsCEResolver.reset();
    this.droolsIResolver.reset();
    this.droolsOPEResolver.reset();
    this.droolsOPResolver.reset();
    this.droolsDPEResolver.reset();
    this.droolsDPResolver.reset();
    this.droolsDResolver.reset();
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

  @NonNull public Set<@NonNull C> getCs()
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

  @NonNull public Set<@NonNull CE> getCEs()
  {
    return this.droolsCEResolver.getCEs();
  }

  @NonNull public String generateCEID()
  {
    return this.droolsCEResolver.generateCEID();
  }

  public @NonNull I resolveI(@NonNull String id) { return this.droolsIResolver.resolveI(id); }

  public boolean recordsOPEID(@NonNull String peid)
  {
    return this.droolsOPEResolver.recordsOPEID(peid);
  }

  public void recordOPE(@NonNull OPE pe)
  {
    this.droolsOPEResolver.recordOPE(pe);
  }

  @NonNull public Set<@NonNull OPE> getOPEs()
  {
    return this.droolsOPEResolver.getOPEs();
  }

  @NonNull public  OP resolveOP(@NonNull String pid) { return this.droolsOPResolver.resolveOP(pid); }

  @NonNull public OPE resolveOPE(@NonNull String pid) { return this.droolsOPEResolver.resolveOPE(pid); }

  @NonNull public String generateOPEID()
  {
    return this.droolsOPEResolver.generateOPEID();
  }

  public boolean recordsDPEID(@NonNull String peid)
  {
    return this.droolsDPEResolver.recordsDPEID(peid);
  }

  @NonNull public DP resolveDP(@NonNull String pid) { return this.droolsDPResolver.resolveDP(pid); }

  @NonNull public DPE resolveDPE(@NonNull String pid) { return this.droolsDPEResolver.resolveDPE(pid); }

  public void recordDPE(@NonNull DPE pe)
  {
    this.droolsDPEResolver.recordDPE(pe);
  }

  @NonNull public Set<@NonNull DPE> getDPEs()
  {
    return this.droolsDPEResolver.getDPEs();
  }

  @NonNull public String generateDPEID()
  {
    return this.droolsDPEResolver.generateDPEID();
  }

  @NonNull public D resolveD(@NonNull String did) { return this.droolsDResolver.resolveD(did); }
}
