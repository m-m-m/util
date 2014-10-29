/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transferobject.base.example.persistence;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

import net.sf.mmm.util.entity.api.PersistenceEntity;
import net.sf.mmm.util.entity.base.AbstractRevisionedEntity;

/**
 * This is the abstract base-implementation of a {@link net.sf.mmm.util.entity.api.GenericEntity} using the
 * {@link javax.persistence JPA} (Java Persistence API). <br>
 * 
 * @param <ID> is the type of the {@link #getId() primary key}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@MappedSuperclass
public abstract class AbstractEntity<ID> extends AbstractRevisionedEntity<ID> implements PersistenceEntity<ID> {

  /** UID for serialization. */
  private static final long serialVersionUID = 3256209723131574786L;

  /**
   * The constructor.
   */
  public AbstractEntity() {

    super();
  }

  /**
   * <b>ATTENTION:</b><br>
   * If your ID is not {@link GeneratedValue generated}, you need to override this method. In this case you
   * can also override {@link #setId(Object)} to change the visibility to public. <br>
   * <br>
   * 
   * {@inheritDoc}
   */
  @Override
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  public ID getId() {

    return super.getId();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Version
  public int getModificationCounter() {

    return super.getModificationCounter();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transient
  public Number getRevision() {

    return super.getRevision();
  }

}
