/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.api;

import net.sf.mmm.util.component.base.ComponentSpecification;
import net.sf.mmm.util.reflect.api.InstantiationFailedException;

/**
 * This is the interface for a generic factory used to
 * {@link #newInstance(Class) create new instances} of
 * {@link net.sf.mmm.util.pojo.api.Pojo}s.<br> {@link net.sf.mmm.util.pojo.api.Pojo}
 * is a shortcut for <em>Plain Old Java Object</em> and simply means any Java
 * object. Typically a {@link net.sf.mmm.util.pojo.api.Pojo} has a public
 * non-arg constructor.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@ComponentSpecification
public interface PojoFactory {

  /**
   * This method creates a new instance of the given <code>pojoType</code>.<br>
   * The simplest implementation may just delegate to
   * {@link Class#newInstance()}. However implementations can solve arbitrary
   * problems such as if the given <code>pojoType</code> has no non-arg
   * constructor, or it is an interface or abstract class.
   * 
   * @param <POJO> is the generic type of the
   *        {@link net.sf.mmm.util.pojo.api.Pojo} to create.
   * @param pojoType is the {@link Class} reflecting the
   *        {@link net.sf.mmm.util.pojo.api.Pojo} to create.
   * @return the new instance of the given <code>pojoType</code>.
   * @throws InstantiationFailedException if the instantiation failed.
   */
  <POJO> POJO newInstance(Class<POJO> pojoType) throws InstantiationFailedException;

}
