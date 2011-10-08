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
 * @param <ID> is the type of the {@link PersistenceEntity#getId() primary key}
 *        of the managed {@link PersistenceEntity}.
 * @param <ENTITY> is the {@link #getEntityClass() type} of the managed entity.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPersistenceEntityManager<ID, ENTITY extends PersistenceEntity<ID>>
    extends AbstractComponent implements PersistenceEntityManager<ID, ENTITY> {

  /**
   * The constructor.
   */
  public AbstractPersistenceEntityManager() {

    super();
  }

}
