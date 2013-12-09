/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.link;

import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.util.lang.api.Datatype;
import net.sf.mmm.util.lang.api.SimpleDatatype;

/**
 * This is the interface for a link pointing from one {@link DataEntity} to {@link #getTarget() another}. A
 * link is an immutable object - it can not be modified.
 * 
 * TODO This is NOT a {@link Datatype}
 * 
 * @see #getTarget()
 * @see #getClassifier()
 * 
 * @param <TARGET> is the type of the linked {@link #getTarget() target entity}. See
 *        {@link net.sf.mmm.data.api.reflection.DataClass#getJavaClass()}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface Link<TARGET extends DataEntity> extends SimpleDatatype<TARGET> {

  /**
   * This method gets the {@link DataEntity} this link is pointing to.<br/>
   * It may be a proxy-object that is lazy loaded if specific methods are called. It is safe to get the
   * {@link net.sf.mmm.data.api.DataObject#getId() ID} without retrieving the actual entity from the
   * repository. This way you can filter {@link Link}s according to their
   * {@link net.sf.mmm.data.api.datatype.DataId#getClassId() type} extremely fast (see
   * {@link net.sf.mmm.data.api.link.LinkList#getLinks(net.sf.mmm.util.filter.api.Filter)} for details).
   * 
   * @return the target object.
   */
  TARGET getTarget();

  /**
   * This method gets the classification of the link. The classifier should be named in meaning of a
   * has-relation from the source to {@link #getTarget() target} {@link net.sf.mmm.data.api.entity.DataEntity
   * entity}. So e.g. if a person has a link to another person classified with "parent" this should be
   * understood in the sense that the linking person <b> <em>has</em></b> the linked person as parent (father
   * or mother).
   * 
   * @return the link classifier or <code>null</code> for no classification.
   */
  String getClassifier();

  // /**
  // * This method determines if this link is weak. If a link is weak, the
  // linked
  // * target content-object can be deleted and also the link will be removed
  // * (cascade on delete). A strong link (link that is NOT weak) will NOT allow
  // * the linked content-object to be deleted. All strong links pointing on a
  // * content-object have to be removed before it can be deleted.<br>
  // * Typically links are strong.
  // *
  // * @return <code>true</code> if this is a weak link.
  // */
  // boolean isWeak();

}
