/* $Id$ */
package net.sf.mmm.framework.base;

import net.sf.mmm.framework.NlsResourceBundle;
import net.sf.mmm.framework.api.ComponentProviderIF;
import net.sf.mmm.framework.api.DependencyException;
import net.sf.mmm.framework.api.IocContainerIF;

/**
 * A {@link DependencyCycleException} is thrown by the
 * {@link IocContainerIF IoC container} if some
 * {@link ComponentProviderIF components} cause a cyclic dependency.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DependencyCycleException extends DependencyException {

  /** UID for serialization */
  private static final long serialVersionUID = 2162762136391685441L;

  /**
   * The constructor.
   * 
   * @param cycle
   *        is a textual representation of the cycle (e.g. "A->B->C->A").
   */
  public DependencyCycleException(String cycle) {

    super(NlsResourceBundle.ERR_DEPENDENCY_CYCLE, cycle);
  }

}
