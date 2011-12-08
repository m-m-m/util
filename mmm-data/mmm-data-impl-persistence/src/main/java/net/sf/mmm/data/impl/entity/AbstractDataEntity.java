/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SecondaryTable;
import javax.persistence.Transient;

import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.data.api.entity.DataEntityView;
import net.sf.mmm.data.api.link.Link;
import net.sf.mmm.data.api.link.MutableLinkList;
import net.sf.mmm.data.base.AbstractDataObject;
import net.sf.mmm.data.impl.link.LinkImpl;
import net.sf.mmm.data.impl.link.LinkListHelper;
import net.sf.mmm.data.impl.link.MutableLinkListImpl;
import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;

/**
 * This is the abstract base implementation of {@link DataEntity}. It supports a
 * hybrid data model with statically typed custom java classes that can be
 * extended at runtime with dynamic classes that use the same implementation
 * based on generic and virtual {@link net.sf.mmm.data.api.reflection.DataField
 * fields}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@SecondaryTable(name = DataEntityView.CLASS_TITLE)
public abstract class AbstractDataEntity extends AbstractDataObject implements DataEntity {

  /** UID for serialization. */
  private static final long serialVersionUID = 8339596584622390190L;

  /** @see #getProxyTarget() */
  private AbstractDataEntity proxyTarget;

  /** @see #getAllLinkList() */
  private List<LinkImpl<? extends AbstractDataEntity>> allLinkList;

  /** @see #getLinklist(long, Class) */
  private Map<Long, MutableLinkListImpl<? extends AbstractDataEntity>> fieldId2LinklistMap;

  /** @see #getDataClassId() */
  private long dataClassId;

  /**
   * The constructor.
   */
  public AbstractDataEntity() {

    super();
    this.dataClassId = getStaticDataClassId();
  }

  /**
   * {@inheritDoc}
   */
  public long getDataClassId() {

    return this.dataClassId;
  }

  /**
   * This method sets the {@link #getDataClassId() class ID}.<br/>
   * <b>ATTENTION:</b><br/>
   * This is an internal method that should never be used by end users. It is
   * used for dynamic classes that are defined at runtime without having an
   * explicit java class as implementation.
   * 
   * @param dataClassId is the ID of the dynamic class to set.
   */
  protected void setDataClassId(long dataClassId) {

    this.dataClassId = dataClassId;
  }

  /**
   * This method gets the static {@link #getDataClassId() class ID}. It has to
   * be implemented by all non-abstract {@link DataEntity} implementations by
   * simply returning the <code>CLASS_ID</code> constant that is also used for
   * {@link net.sf.mmm.data.api.reflection.DataClassAnnotation#id()}.
   * 
   * @return the static class ID.
   */
  @Transient
  protected abstract long getStaticDataClassId();

  /**
   * <b>ATTENTION:</b><br/>
   * This is an internal method that is not intended to be used by end-users.
   * 
   * @return the {@link List} of all {@link Link}s.
   */
  @OneToMany(mappedBy = "source", orphanRemoval = true, cascade = CascadeType.ALL)
  List<LinkImpl<? extends AbstractDataEntity>> getAllLinkList() {

    if (this.allLinkList == null) {
      this.allLinkList = new ArrayList<LinkImpl<? extends AbstractDataEntity>>();
    }
    return this.allLinkList;
  }

  /**
   * @param allLinkList is the allLinkList to set
   */
  protected void setAllLinkList(List<LinkImpl<? extends AbstractDataEntity>> allLinkList) {

    this.allLinkList = allLinkList;
  }

  /**
   * This method gets a {@link net.sf.mmm.data.api.link.LinkList}
   * {@link net.sf.mmm.data.api.reflection.DataField property}. If it does not
   * yet exists (no links have been persisted for this property) it will be
   * created.<br/>
   * A {@link DataEntityView} may have multiple
   * {@link net.sf.mmm.data.api.link.LinkList}
   * {@link net.sf.mmm.data.api.reflection.DataField fields}. The
   * <code>fieldId</code> should be defined as a constant in a central location
   * (see {@link net.sf.mmm.data.api.reflection.DataFieldIds}). An according
   * getter needs to be implemented that simply delegates to this method using
   * the ID constant. This getter also needs to be annotated with
   * {@link net.sf.mmm.data.api.reflection.DataFieldAnnotation} assigning the ID
   * to {@link net.sf.mmm.data.api.reflection.DataFieldAnnotation#id()}. The
   * getter for that field should look as following:
   * 
   * <pre>
   * {@literal @}{@link net.sf.mmm.data.api.reflection.DataFieldAnnotation}(id={@link net.sf.mmm.data.api.reflection.DataFieldIds}.ID_SOMEENTITY_MY_ENTITIES) 
   * public {@link MutableLinkList}&lt;MyEntity> getMyEntities() {
   *   getLinklist({@link net.sf.mmm.data.api.reflection.DataFieldIds}.ID_SOMEENTITY_MY_ENTITIES, MyEntityImpl.class)
   * }
   * </pre>
   * 
   * @param <ENTITY> is the generic type of <code>entityClass</code>.
   * @param fieldId is the
   *        {@link net.sf.mmm.data.api.reflection.DataField#getId() ID} of the
   *        {@link net.sf.mmm.data.api.reflection.DataField} reflecting the
   *        {@link net.sf.mmm.data.api.link.LinkList} field.
   * @param entityClass is the class reflecting the {@link AbstractDataEntity
   *        entity} the {@link Link}s in this
   *        {@link net.sf.mmm.data.api.link.LinkList} point to.
   * @return the requested {@link MutableLinkList}.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  protected <ENTITY extends DataEntity> MutableLinkList<ENTITY> getLinklist(long fieldId,
      Class<ENTITY> entityClass) {

    if (this.fieldId2LinklistMap == null) {
      initializeFieldId2LinklistMap();
    }
    MutableLinkListImpl mutableLinkList = this.fieldId2LinklistMap.get(Long.valueOf(fieldId));
    if (mutableLinkList == null) {
      mutableLinkList = new MutableLinkListImpl(this, fieldId, this.allLinkList);
      this.fieldId2LinklistMap.put(Long.valueOf(fieldId), mutableLinkList);
    }
    return mutableLinkList;
  }

  /**
   * This method initializes the property {@link #fieldId2LinklistMap} based on
   * {@link #getAllLinkList()}.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  private synchronized void initializeFieldId2LinklistMap() {

    if (this.fieldId2LinklistMap == null) {
      this.fieldId2LinklistMap = new HashMap<Long, MutableLinkListImpl<? extends AbstractDataEntity>>();
      for (LinkImpl<? extends AbstractDataEntity> link : getAllLinkList()) {
        Long fieldId = Long.valueOf(link.getFieldId());
        MutableLinkListImpl<? extends AbstractDataEntity> linkList = this.fieldId2LinklistMap
            .get(fieldId);
        if (linkList == null) {
          linkList = new MutableLinkListImpl<AbstractDataEntity>(this, fieldId.longValue(),
              this.allLinkList);
          this.fieldId2LinklistMap.put(fieldId, linkList);
        }
        LinkListHelper.add(linkList, (Link) link);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Transient
  public DataEntityView getProxySource() {

    return this;
  }

  /**
   * {@inheritDoc}
   */
  @ManyToOne(optional = true, cascade = CascadeType.ALL)
  // @JoinColumn(table = DataEntity.CLASS_TITLE)
  @JoinColumn(name = "ProxyTargetId")
  public AbstractDataEntity getProxyTarget() {

    return this.proxyTarget;
  }

  /**
   * {@inheritDoc}
   */
  public void setProxyTarget(DataEntity proxyTarget) {

    if (proxyTarget != null) {
      if (proxyTarget.getDataClassId() != getDataClassId()) {
        // TODO dedicated exception
        throw new NlsIllegalArgumentException(proxyTarget, "proxyTarget");
      }
    }
    this.proxyTarget = (AbstractDataEntity) proxyTarget;
  }

}
