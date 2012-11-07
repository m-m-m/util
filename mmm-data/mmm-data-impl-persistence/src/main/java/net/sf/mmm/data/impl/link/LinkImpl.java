/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.link;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.data.base.link.AbstractLink;
import net.sf.mmm.data.impl.entity.AbstractDataEntity;
import net.sf.mmm.util.entity.api.GenericEntity;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This is the implementation of {@link net.sf.mmm.data.api.link.Link}.
 * 
 * @param <TARGET> is the type of the linked {@link #getTarget() target entity}.
 *        See {@link net.sf.mmm.data.api.reflection.DataClass#getJavaClass()}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Entity(name = "DataEntiyLink")
public class LinkImpl<TARGET extends DataEntity> extends AbstractLink<TARGET> implements
    GenericEntity<Long> {

  /** UID for serialization. */
  private static final long serialVersionUID = -3818102518986171086L;

  /** @see #getId() */
  private Long id;

  /** @see #getFieldId() */
  private long fieldId;

  /** @see #getSortIndex() */
  private int sortIndex;

  /** @see #getSource() */
  private DataEntity source;

  /** @see #getTarget() */
  private TARGET target;

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
   * @param fieldId is the {@link #getFieldId() field ID}.
   * @param target is the {@link #getTarget() linked entity}.
   * @param classifier is the {@link #getClassifier() classifier}. May be
   *        <code>null</code>.
   */
  public LinkImpl(DataEntity source, long fieldId, TARGET target, String classifier) {

    super();
    NlsNullPointerException.checkNotNull("sourceEntity", source);
    NlsNullPointerException.checkNotNull("targetEntity", target);
    this.source = source;
    this.fieldId = fieldId;
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
   * This method gets the
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() ID} of the
   * {@link net.sf.mmm.data.api.reflection.DataField} containing this link
   * (typically in a {@link net.sf.mmm.data.api.link.LinkList}).
   * 
   * @return the field ID.
   */
  public long getFieldId() {

    return this.fieldId;
  }

  /**
   * @param fieldId is the fieldId to set
   */
  protected void setFieldId(long fieldId) {

    this.fieldId = fieldId;
  }

  /**
   * @return the sortIndex
   */
  public int getSortIndex() {

    return this.sortIndex;
  }

  /**
   * @param sortIndex is the sortIndex to set
   */
  protected void setSortIndex(int sortIndex) {

    this.sortIndex = sortIndex;
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
  @ManyToOne(optional = false, targetEntity = AbstractDataEntity.class)
  @JoinColumn(name = "SOURCE_ID")
  public DataEntity getSource() {

    return this.source;
  }

  /**
   * @param source is the source to set
   */
  protected void setSource(AbstractDataEntity source) {

    this.source = source;
  }

  /**
   * {@inheritDoc}
   */
  @ManyToOne(optional = false, targetEntity = AbstractDataEntity.class, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "TARGET_ID")
  public TARGET getTarget() {

    return this.target;
  }

  /**
   * @param target is the target to set
   */
  protected void setTarget(TARGET target) {

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
