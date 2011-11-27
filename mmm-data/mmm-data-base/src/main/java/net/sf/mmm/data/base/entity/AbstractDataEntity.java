/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.data.base.AbstractDataObject;
import net.sf.mmm.data.impl.link.LinkImpl;
import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This is the abstract base implementation of {@link DataEntity}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Entity(name = DataEntity.CLASS_TITLE)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractDataEntity extends AbstractDataObject implements DataEntity {

  /** UID for serialization. */
  private static final long serialVersionUID = 8339596584622390190L;

  /** @see #getProxyTarget() */
  private AbstractDataEntity proxyTarget;

  /** @see #getAllLinkList() */
  private List<LinkImpl<AbstractDataEntity>> allLinkList;

  /**
   * The constructor.
   */
  public AbstractDataEntity() {

    super();
  }

  /**
   * @return the allLinkList
   */
  @OneToMany(mappedBy = "Source_ID", orphanRemoval = true, cascade = CascadeType.ALL)
  public List<LinkImpl<AbstractDataEntity>> getAllLinkList() {

    return this.allLinkList;
  }

  /**
   * @param allLinkList is the allLinkList to set
   */
  public void setAllLinkList(List<LinkImpl<AbstractDataEntity>> allLinkList) {

    this.allLinkList = allLinkList;
  }

  /**
   * {@inheritDoc}
   */
  @Transient
  public DataEntity getProxySource() {

    return this;
  }

  /**
   * {@inheritDoc}
   */
  public DataEntity getProxyTarget() {

    return this.proxyTarget;
  }

  /**
   * @param proxyTarget is the proxyTarget to set
   */
  public void setProxyTarget(AbstractDataEntity proxyTarget) {

    NlsNullPointerException.checkNotNull(DataEntity.class, proxyTarget);
    if (proxyTarget.getDataClassId() != getDataClassId()) {
      // TODO dedicated exception
      throw new NlsIllegalArgumentException(proxyTarget, "proxyTarget");
    }
    this.proxyTarget = proxyTarget;
  }

}
