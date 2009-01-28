/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.base;

import net.sf.mmm.persistence.api.PersistenceEntity;
import net.sf.mmm.persistence.api.PersistenceEntityManager;
import net.sf.mmm.util.component.base.AbstractComponent;

/**
 * This is the abstract base-implementation of the
 * {@link PersistenceEntityManager} interface.
 * 
 * @param <ENTITY> is the {@link #getEntityClass() type} of the managed entity.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPersistenceEntityManager<ENTITY extends PersistenceEntity> extends
    AbstractComponent implements PersistenceEntityManager<ENTITY> {

  /**
   * The constructor.
   */
  public AbstractPersistenceEntityManager() {

    super();
  }

}
