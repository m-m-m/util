/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.base.jpa;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * This class extends {@link AbstractJpaEntity} binding the {@link #getId() ID} to the type {@link Long}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@MappedSuperclass
public abstract class JpaEntity extends AbstractJpaEntity<Long> {

  /** UID for serialization. */
  private static final long serialVersionUID = 4280571250879388596L;

  /**
   * The constructor.
   */
  public JpaEntity() {

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
  public void setId(Long id) {

    super.setId(id);
  }

}
