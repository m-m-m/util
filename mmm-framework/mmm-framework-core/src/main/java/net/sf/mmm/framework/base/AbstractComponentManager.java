/* $Id$ */
package net.sf.mmm.framework.base;

import net.sf.mmm.framework.api.ComponentException;
import net.sf.mmm.framework.api.ComponentManager;
import net.sf.mmm.framework.api.ContainerException;

/**
 * This is the abstract base implementation of the {@link ComponentManager}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractComponentManager implements ComponentManager {

  /**
   * The constructor.
   */
  public AbstractComponentManager() {

    super();
  }

  /**
   * @see net.sf.mmm.framework.api.ComponentManager#requestComponent(java.lang.Class)
   * 
   */
  public final <S> S requestComponent(Class<S> specification) throws ComponentException,
      ContainerException {

    return requestComponent(specification, DEFAULT_INSTANCE_ID);
  }

}
