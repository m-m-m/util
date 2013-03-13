/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is the interface for a generic util to copy the attributes from one object to another. An
 * implementation may rely on {@link Copyable} or on reflection.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.1
 */
@ComponentSpecification
public interface PropertiesCopier {

  /**
   * This method copies the properties from <code>source</code> to <code>target</code>. If a property is
   * copied whose value is a mutable object (not a {@link net.sf.mmm.util.lang.api.Datatype} or the like),
   * that object also has to be copied/cloned.
   * 
   * @see Copyable#copyPropertiesFrom(Object, boolean)
   * 
   * @param <V> is the generic type of the objects to copy from/to.
   * @param source is the source object. This object must NOT be modified by this method. Only properties will
   *        be read (typically via invoking getters).
   * @param target is the target object to modify. The property values from <code>source</code> will be set in
   *        this object.
   * @param overwrite - <code>true</code> if all properties shall be copied, <code>false</code> if only the
   *        properties shall be copied that are <code>null</code> in <code>target</code>.
   */
  <V> void copyProperties(V source, V target, boolean overwrite);

}
