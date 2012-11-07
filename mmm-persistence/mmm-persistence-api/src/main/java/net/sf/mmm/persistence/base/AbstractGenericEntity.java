/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.base;

import net.sf.mmm.util.entity.api.GenericEntity;

/**
 * This is the abstract base-class that implementations of {@link GenericEntity} should inherit from (if
 * possible).
 * 
 * @param <ID> is the type of the {@link #getId() primary key}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractGenericEntity<ID> implements GenericEntity<ID> {

  /**
   * The constructor.
   */
  public AbstractGenericEntity() {

    super();
  }

}
