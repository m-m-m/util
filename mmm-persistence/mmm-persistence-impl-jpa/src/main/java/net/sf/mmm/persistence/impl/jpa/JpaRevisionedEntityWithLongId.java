/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa;

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
   * This method sets the {@link #getId() primary key}.<br>
   * <b>ATTENTION:</b><br>
   * This method is only intended for internal usage. It should NOT be set manually outside this
   * persistence-layer.
   * 
   * @param id is the ID to set.
   */
  @Override
  public void setId(Long id) {

    super.setId(id);
  }

}
