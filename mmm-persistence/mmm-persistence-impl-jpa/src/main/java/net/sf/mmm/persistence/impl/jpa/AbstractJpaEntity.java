/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import net.sf.mmm.persistence.base.AbstractGenericEntity;

/**
 * This is the abstract base-implementation of a {@link net.sf.mmm.util.entity.api.GenericEntity} using the
 * {@link javax.persistence JPA} (Java Persistence API).<br>
 * 
 * @param <ID> is the type of the {@link #getId() primary key}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@MappedSuperclass
public abstract class AbstractJpaEntity<ID> extends AbstractGenericEntity<ID> {

  /** @see #getId() */
  private ID id;

  /**
   * The constructor.
   */
  public AbstractJpaEntity() {

    super();
  }

  /**
   * <b>ATTENTION:</b><br/>
   * If your ID is not {@link GeneratedValue generated}, you need to override this method. In this case you
   * can also override {@link #setId(Object)} to change the visibility to public.<br/>
   * <br/>
   * 
   * {@inheritDoc}
   */
  @Override
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  public ID getId() {

    return this.id;
  }

  /**
   * @param id is the id to set
   */
  protected void setId(ID id) {

    this.id = id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transient
  public int getModificationCounter() {

    return 0;
  }

}
