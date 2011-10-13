/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.trash;

import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.util.lang.api.Datatype;

/**
 * This is the interface for a link pointing from one content-object to
 * {@link #getTargetObject() another}. A link is an immutable object - it can
 * not be modified.
 * 
 * @see #getTargetObject()
 * @see #getClassifier()
 * 
 * @param <CLASS> is the type of the linked object. See
 *        {@link net.sf.mmm.data.api.reflection.DataClass#getJavaClass()}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface Link<CLASS> extends Datatype<CLASS> {

  // /**
  // * This method gets the unique ID of the content-object that is linked.
  // *
  // * @return the ID of the linked content-object.
  // */
  // ContentId getTargetId();

  /**
   * This method gets the object (entity) this link is pointing to.<br/>
   * It may be a proxy-object that is lazy loaded if specific methods are
   * called. If {@literal <CLASS>} is bound to
   * {@link net.sf.mmm.data.api.DataObject} it is safe to get the
   * {@link net.sf.mmm.data.api.DataObject#getContentId() content ID}
   * without retrieving the actual object from the repository. This way you can
   * filter {@link Link}s according to their
   * {@link net.sf.mmm.data.api.datatype.DataId#getClassId() type}
   * extremely fast (@see
   * {@link LinkList#getLinks(net.sf.mmm.util.filter.api.Filter)}).
   * 
   * @return the target object.
   */
  CLASS getTargetObject();

  /**
   * This method gets the classification of the link.
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
