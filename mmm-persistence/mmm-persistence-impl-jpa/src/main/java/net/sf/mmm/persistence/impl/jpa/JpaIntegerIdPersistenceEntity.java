/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * This class extends {@link JpaPersistenceEntity} with a generated
 * {@link #getId() ID} of the type {@link Integer}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@MappedSuperclass
public abstract class JpaIntegerIdPersistenceEntity extends JpaPersistenceEntity<Integer> {

  /** @see #getId() */
  private Integer id;

  /**
   * The constructor.
   */
  public JpaIntegerIdPersistenceEntity() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Id
  @GeneratedValue
  public Integer getId() {

    return this.id;
  }

  /**
   * This method sets the {@link #getId() primary key}.<br>
   * <b>ATTENTION:</b><br>
   * This method is only intended for internal usage. It should NOT be set
   * manually outside this persistence-layer.
   * 
   * @param id is the ID to set.
   */
  protected void setId(Integer id) {

    this.id = id;
  }

}
