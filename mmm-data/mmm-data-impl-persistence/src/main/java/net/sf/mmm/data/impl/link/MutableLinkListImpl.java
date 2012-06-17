/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.link;

import java.util.LinkedList;
import java.util.List;

import net.sf.mmm.data.api.link.Link;
import net.sf.mmm.data.api.link.MutableLinkList;
import net.sf.mmm.data.base.link.AbstractLinkList;
import net.sf.mmm.data.impl.entity.AbstractDataEntity;

/**
 * This is the default implementation of the {@link MutableLinkList} interface.
 * 
 * @param <TARGET> is the type of the linked {@link Link#getTarget() target
 *        entity}. See
 *        {@link net.sf.mmm.data.api.reflection.DataClass#getJavaClass()}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class MutableLinkListImpl<TARGET extends AbstractDataEntity> extends
    AbstractLinkList<TARGET> implements MutableLinkList<TARGET> {

  /** The {@link LinkImpl#getSource() source entity} owning the links. */
  private AbstractDataEntity entity;

  /**
   * The {@link net.sf.mmm.data.api.reflection.DataField#getId() ID} of the
   * {@link net.sf.mmm.data.api.reflection.DataField} this
   * {@link net.sf.mmm.data.api.link.LinkList} belongs to.
   */
  private long fieldId;

  /**
   * The {@link List} with all {@link Link links} from the {@link #entity}. A
   * {@link net.sf.mmm.data.api.entity.DataEntityView} may have multiple
   * {@link net.sf.mmm.data.api.link.LinkList} fields. This list will contain
   * the links for all of these fields, while this
   * {@link net.sf.mmm.data.api.link.LinkList} itself only represents a single
   * field.
   */
  private List<LinkImpl<? extends AbstractDataEntity>> allLinkList;

  /**
   * the {@link List} of links ({@link java.util.List} interface does not allow
   * insert.
   */
  private LinkedList<LinkImpl<TARGET>> linkList;

  /**
   * The constructor.
   * 
   * @param entity - see {@link LinkImpl#getSource()}.
   * @param fieldId - see {@link LinkImpl#getFieldId()}.
   * @param allLinkList is the {@link List} with all {@link Link links} from the
   *        {@link #entity}. A {@link net.sf.mmm.data.api.entity.DataEntityView}
   *        may have multiple {@link net.sf.mmm.data.api.link.LinkList} fields.
   *        This list will contain the links for all of these fields, while this
   *        {@link net.sf.mmm.data.api.link.LinkList} itself only represents a
   *        single field.
   */
  public MutableLinkListImpl(AbstractDataEntity entity, long fieldId,
      List<LinkImpl<? extends AbstractDataEntity>> allLinkList) {

    super();
    this.entity = entity;
    this.fieldId = fieldId;
    this.allLinkList = allLinkList;
    this.linkList = new LinkedList<LinkImpl<TARGET>>();
    // TODO: initalize linkList, NOPE - done in AbstractDataEntity
    // for (LinkImpl<? extends AbstractDataEntity> link : allLinkList) {
    // if (link.getFieldId() == this.fieldId) {
    // this.linkList.add((LinkImpl<TARGET>) link);
    // }
    // }
  }

  /**
   * This method creates a new {@link Link}.
   * 
   * @param targetEntity is the linked {@link Link#getTarget() target entity}.
   * @param classifier is the {@link Link#getClassifier() classifier}.
   * @return the new {@link Link}.
   */
  protected LinkImpl<TARGET> createLink(TARGET targetEntity, String classifier) {

    return new LinkImpl<TARGET>(this.entity, this.fieldId, targetEntity, classifier);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  protected List<Link<TARGET>> getLinkList() {

    return (List) this.linkList;
  }

  /**
   * {@inheritDoc}
   */
  public LinkImpl<TARGET> addLink(TARGET targetEntity, String classifier) {

    LinkImpl<TARGET> link = createLink(targetEntity, classifier);
    this.linkList.add(link);
    this.allLinkList.add(link);
    return link;
  }

  /**
   * {@inheritDoc}
   */
  public LinkImpl<TARGET> removeLink(int index) {

    LinkImpl<TARGET> link = this.linkList.remove(index);
    this.allLinkList.remove(link);
    return link;
  }

  /**
   * {@inheritDoc}
   */
  public LinkImpl<TARGET> setLink(int index, TARGET targetEntity, String classifier) {

    LinkImpl<TARGET> link = createLink(targetEntity, classifier);
    LinkImpl<TARGET> old = this.linkList.set(index, link);
    this.allLinkList.remove(old);
    this.allLinkList.add(link);
    return old;
  }

  /**
   * {@inheritDoc}
   */
  public LinkImpl<TARGET> insertLink(int index, TARGET targetEntity, String classifier) {

    LinkImpl<TARGET> link = createLink(targetEntity, classifier);
    this.linkList.add(index, link);
    this.allLinkList.add(link);
    return link;
  }

}
