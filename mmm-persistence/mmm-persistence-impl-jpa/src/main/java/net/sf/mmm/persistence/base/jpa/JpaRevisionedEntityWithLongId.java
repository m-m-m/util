/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.base.jpa;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * This class extends {@link JpaRevisionedEntity} binding the {@link #getId() ID} to the type {@link Long}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@MappedSuperclass
public abstract class JpaRevisionedEntityWithLongId extends JpaRevisionedEntity<Long> {

  /**
   * The constructor.
   */
  public JpaRevisionedEntityWithLongId() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  public Long getId() {

    return super.getId();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setId(Long id) {

    super.setId(id);
  }

}
