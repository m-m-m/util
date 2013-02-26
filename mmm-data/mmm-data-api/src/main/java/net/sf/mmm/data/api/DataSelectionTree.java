/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api;

/**
 * A {@link DataSelectionGenericTree} where {@link #getChildren() children} and {@link #getParent() parents}
 * have the same type.
 * 
 * @param <NODE> is the generic type representing the {@link #getParent() parent tree node}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataSelectionTree<NODE extends DataSelectionTree<NODE>> extends DataSelectionGenericTree<NODE, NODE> {

  // nothing to add...

}
