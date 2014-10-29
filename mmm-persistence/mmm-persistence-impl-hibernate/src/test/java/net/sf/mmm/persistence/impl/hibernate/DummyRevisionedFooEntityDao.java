/* $Id: $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.hibernate;

import javax.inject.Named;

/**
 * DAO for {@link DummyRevisionedFooEntity}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Named
public class DummyRevisionedFooEntityDao extends AbstractRevisionedDaoEnvers<Long, DummyRevisionedFooEntity> {

  /**
   * The constructor.
   */
  public DummyRevisionedFooEntityDao() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Class<DummyRevisionedFooEntity> getEntityClass() {

    return DummyRevisionedFooEntity.class;
  }

}
