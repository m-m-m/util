/* $Id: $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.api;

/**
 * This is the interface for a manager of {@link ContentType}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentTypeManager {

  /**
   * This method gets the
   * {@link net.sf.mmm.util.collection.api.TreeNode#getParent() root}
   * {@link net.sf.mmm.util.collection.api.TreeNode node} of the
   * {@link ContentType} tree.
   * 
   * @return the root {@link ContentType}.
   */
  ContentType getRootType();

  /**
   * This method gets the {@link ContentType} for the given
   * <code>{@link ContentType#getId() id}</code>.
   * 
   * @param id is the {@link ContentType#getId() id} of the requested
   *        {@link ContentType}.
   * @return the requested {@link ContentType} or <code>null</code> if none
   *         exists for the given <code>id</code>.
   */
  ContentType getContentType(String id);

  /**
   * This method gets the {@link ContentType#getTechnicalParent() technical
   * root} {@link net.sf.mmm.util.collection.api.TreeNode node} of the
   * {@link ContentType} tree.
   * 
   * @return the technical root {@link ContentType}.
   */
  ContentType getTechnicalRootType();

}
