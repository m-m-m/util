/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect;

/**
 * This is the callback interface used to resolve a type by some logical name to
 * the {@link Class} reflecting that type.
 * 
 * @see #CLASS_FOR_NAME_RESOLVER
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ClassResolver {

  /**
   * This method resolves the class with the given <code>name</code>. In
   * addition to a {@link ClassLoader} this method may also find types via a
   * logical alias name.<br>
   * The <code>name</code> can be the physical (fully qualified) classname
   * (e.g. <code>org.foo.BlobImpl</code>) but this may also be a logical name
   * depending on the implementation of this interface (e.g. <code>Blob</code>
   * may point to the same type).
   * 
   * @param name is the logical or physical name of the requested type.
   * @return the class reflecting the type with the given <code>name</code>.
   * @throws ClassNotFoundException if the requested type was NOT found.
   */
  Class resolveClass(String name) throws ClassNotFoundException;

  /**
   * This is a singleton implementation of the {@link ClassResolver} interface
   * that simple delegates to {@link Class#forName(String)}.
   */
  ClassResolver CLASS_FOR_NAME_RESOLVER = new ClassResolver() {

    public Class resolveClass(String name) throws ClassNotFoundException {

      return Class.forName(name);
    }
  };

}
