/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api;

/**
 * This is the interface of a mutable {@link DataSelectionTree}.
 * 
 * @param <NODE> is the generic type representing this type itself
 *        (self-reference).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface MutableDataSelectionTree<NODE extends MutableDataSelectionTree<NODE>> extends
    DataSelectionTree<NODE> {

  /**
   * This method sets the {@link #isAbstract() abstract} flag.
   * 
   * @param isAbstract - <code>true</code> if the node should be abstract,
   *        <code>false</code> otherwise.
   */
  void setAbstract(boolean isAbstract);

}
