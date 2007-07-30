/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import java.util.Set;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.model.api.ContentClass;

/**
 * This is the interface of a "classloader" for {@link ContentClass}es. It is
 * capable of reading a {@link ContentClass} from the {@link Class type}
 * reflecting the according entity.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface NativeContentClassLoader {

  /**
   * This method reads a {@link ContentClass} from the given <code>type</code>
   * reflecting an entity written in java.
   * 
   * @param type is the type representing the entity.
   * @param superClass is the {@link ContentClass#getSuperClass() super-class}
   *        of the {@link ContentClass} to read.
   * @return the according {@link ContentClass}.
   */
  ContentClass loadClass(Class<? extends ContentObject> type, ContentClass superClass);

  /**
   * This method reads all {@link ContentClass}es for the types given by
   * <code>typeSet</code>. All top-level types from the hierarchy given by
   * <code>typeSet</code> need to be derived from <code>superClass</code>
   * what will become their direct
   * {@link ContentClass#getSuperClass() super-class}.
   * 
   * @param typeSet is the set containing the types to read.
   */
  void loadClasses(Set<Class<? extends ContentObject>> typeSet);

}
