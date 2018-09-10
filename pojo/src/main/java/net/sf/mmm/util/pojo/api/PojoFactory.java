/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.api;

import net.sf.mmm.util.reflect.api.InstantiationFailedException;

/**
 * This is the interface for a generic factory used to {@link #newInstance(Class) create new instances} of
 * {@link net.sf.mmm.util.pojo.api.Pojo}s. <br>
 * Typically a {@link net.sf.mmm.util.pojo.api.Pojo} has a public non-arg constructor. However there can be
 * arbitrary reasons why {@link Class#newInstance()} might NOT do it and you want some abstraction like this
 * interface. Here are just a few of them.
 * <ul>
 * <li>Reflection is NOT available (e.g. in GWT environments)</li>
 * <li>The provided {@link Class} is an {@link Class#isInterface() interface} such as
 * {@link java.util.List}.</li>
 * <li>The provided {@link Class} does not have a non-arg constructor.</li>
 * <li>{@link Class#newInstance()} is throwing ugly checked exceptions.</li>
 * <li>You might need specific permissions to instantiate the provided {@link Class}.</li>
 * </ul>
 * This library comes with simple implementations of {@link PojoFactory}. You might want to hide
 * <a href="http://code.google.com/p/objenesis/">objenesis</a> behind this interface to combine its features
 * with the power of {@code mmm}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public interface PojoFactory {

  /**
   * This method creates a new instance of the given {@code pojoType}. <br>
   * The simplest implementation may just delegate to {@link Class#newInstance()}. However implementations can
   * solve arbitrary problems such as if the given {@code pojoType} has no non-arg constructor, or it is an
   * interface or abstract class.
   *
   * @param <POJO> is the generic type of the {@link net.sf.mmm.util.pojo.api.Pojo} to create.
   * @param pojoType is the {@link Class} reflecting the {@link net.sf.mmm.util.pojo.api.Pojo} to create.
   * @return the new instance of the given {@code pojoType}.
   * @throws InstantiationFailedException if the instantiation failed.
   */
  <POJO> POJO newInstance(Class<POJO> pojoType) throws InstantiationFailedException;

}
