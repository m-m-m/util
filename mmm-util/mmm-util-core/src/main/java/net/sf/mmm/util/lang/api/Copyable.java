/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This is the interface for a {@link #copyPropertiesFrom(Object, boolean) copy-able} Object.
 * 
 * @see Cloneable
 * @see PropertiesCopier
 * 
 * @param <SELF> is the generic type for the copy-able object itself. To avoid generics that make your objects
 *        too complex, it is acceptable to bind &lt;SELF&gt; is a base class of an object hierarchy and use
 *        casts in sub-classes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.1
 */
public interface Copyable<SELF> {

  /**
   * This method copies all properties from <code>source</code> to this object. If a property is copied whose
   * value is a mutable object (not a {@link net.sf.mmm.util.lang.api.Datatype} or the like), that object also
   * has to be copied/cloned.
   * 
   * @param source is the source object where to copy the properties from.
   * @param overwrite - <code>true</code> if all properties shall be copied, <code>false</code> if only the
   *        properties shall be copied that are <code>null</code> in this object.
   */
  void copyPropertiesFrom(SELF source, boolean overwrite);

}
