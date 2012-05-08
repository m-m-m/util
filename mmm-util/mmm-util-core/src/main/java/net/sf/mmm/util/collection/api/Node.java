/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.api;

/**
 * This is the interface for a node that has a {@link #getParent() parent}.
 * 
 * @param <PARENT> the generic type of the parent. Typically a self reference (see {@link TreeNode}).
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.2
 */
public interface Node<PARENT> {

  /**
   * This method gets the parent of this node.
   * 
   * @return the parent node. May be <code>null</code> (e.g. for the root node of a tree).
   */
  PARENT getParent();

}
