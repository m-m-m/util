/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.link;

import net.sf.mmm.data.api.entity.DataEntityView;

/**
 * This is the interface for a {@link LinkList} that can be modified.
 * 
 * @param <CLASS> is the type of the linked object. See
 *        {@link net.sf.mmm.data.api.reflection.DataClass#getJavaClass()}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface MutableLinkList<CLASS extends DataEntityView> extends LinkList<CLASS> {

  /**
   * This method adds a new link to this {@link LinkList}.
   * 
   * @param targetEntity is the {@link Link#getTarget() target} of the link.
   * @param classifier is the {@link Link#getClassifier() classifier} of the
   *        link.
   * @return the new link that has been created and added.
   */
  Link<CLASS> addLink(CLASS targetEntity, String classifier);

  /**
   * This method removes the link at the given index.
   * 
   * @param index is the position of the link to remove.
   * @return the link that has been removed.
   */
  Link<CLASS> removeLink(int index);

  /**
   * This method sets the link at the given index.
   * 
   * @param index is the position of the existing link to replace. Has to be in
   *        the range from <code>0</code> to
   *        <code>{@link #getLinkCount()}-1</code>.
   * @param targetEntity is the {@link Link#getTarget() target} of the link.
   * @param classifier is the {@link Link#getClassifier() classifier} of the
   *        link.
   * @return the link that has been replaced and was located at the given index
   *         before this operation.
   */
  Link<CLASS> setLink(int index, CLASS targetEntity, String classifier);

  /**
   * This method creates a {@link Link} and inserts it at the given index.
   * 
   * @param index is the position where to insert the link.
   * @param targetEntity is the {@link Link#getTarget() target} of the link.
   * @param classifier is the {@link Link#getClassifier() classifier} of the
   *        link.
   * @return the new link that has been created and inserted.
   */
  Link<CLASS> insertLink(int index, CLASS targetEntity, String classifier);

}
