/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.datatype;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;

import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.data.base.datatype.AbstractLink;
import net.sf.mmm.data.base.entity.AbstractDataEntity;
import net.sf.mmm.persistence.api.PersistenceEntity;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This is the implementation of {@link net.sf.mmm.data.api.datatype.Link}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Entity(name = "DataEntiyLink")
public class LinkImpl extends AbstractLink<AbstractDataEntity> implements PersistenceEntity<Long> {

  /** UID for serialization. */
  private static final long serialVersionUID = -3818102518986171086L;

  /** @see #getId() */
  private Long id;

  /** @see #getSource() */
  private AbstractDataEntity source;

  /** @see #getTarget() */
  private AbstractDataEntity target;

  /**
   * The constructor.
   */
  public LinkImpl() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param source is the {@link #getSource() linking entity}.
   * @param target is the {@link #getTarget() linked entity}.
   * @param classifier is the {@link #getClassifier() classifier}. May be
   *        <code>null</code>.
   */
  public LinkImpl(AbstractDataEntity source, AbstractDataEntity target, String classifier) {

    super();
    NlsNullPointerException.checkNotNull(DataEntity.class, target);
    this.target = target;
    setClassifier(classifier);
  }

  /**
   * {@inheritDoc}
   */
  @Id
  @GeneratedValue
  public Long getId() {

    return this.id;
  }

  /**
   * @param id is the id to set
   */
  protected void setId(Long id) {

    this.id = id;
  }

  /**
   * {@inheritDoc}
   */
  @Transient
  public int getModificationCounter() {

    return 0;
  }

  /**
   * {@inheritDoc}
   */
  @Transient
  public Date getModificationTimestamp() {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Transient
  public boolean isPersistent() {

    return (this.id != null);
  }

  /**
   * @return the source
   */
  @ManyToOne(optional = false)
  @PrimaryKeyJoinColumn(name = "Source_ID")
  public AbstractDataEntity getSource() {

    return this.source;
  }

  /**
   * @param source is the source to set
   */
  public void setSource(AbstractDataEntity source) {

    this.source = source;
  }

  /**
   * {@inheritDoc}
   */
  @ManyToOne(optional = false)
  @PrimaryKeyJoinColumn(name = "Target_ID")
  public AbstractDataEntity getTarget() {

    return this.target;
  }

  /**
   * @param target is the target to set
   */
  protected void setTarget(AbstractDataEntity target) {

    this.target = target;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getClassifier() {

    return super.getClassifier();
  }

}
