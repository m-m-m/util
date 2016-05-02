/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.api.accessor;

/**
 * This type contains the available {@link PojoPropertyAccessorMode}s to have them in one place and make the
 * API easier to use.<br/>
 * In case you want to extend and create your own {@link PojoPropertyAccessorMode mode} and
 * {@link PojoPropertyAccessor accessor} you need to reference it explicitly.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.0.0
 */
public interface PojoPropertyAccessorModes {

  /** Mode to read a regular property (e.g. <code>getFoo()</code>). */
  PojoPropertyAccessorNonArgMode GET = PojoPropertyAccessorNonArgMode.GET;

  /** Mode to write a regular property (e.g. <code>setFoo(Foo)</code>). */
  PojoPropertyAccessorOneArgMode SET = PojoPropertyAccessorOneArgMode.SET;

  /**
   * Mode to read the size of a container property (e.g. <code>getFooLength()</code> or
   * <code>getFoos().size()</code>).
   */
  PojoPropertyAccessorNonArgMode GET_SIZE = PojoPropertyAccessorNonArgMode.GET_SIZE;

  /**
   * Mode to {@link java.util.Collection#add(Object) add} an element to a container property (e.g.
   * <code>addFoo(Foo)</code>).
   */
  PojoPropertyAccessorOneArgMode ADD = PojoPropertyAccessorOneArgMode.ADD;

  /**
   * Mode to {@link java.util.Collection#remove(Object) remove} an element from a container property (e.g.
   * <code>removeFoo(Foo)</code>).
   */
  PojoPropertyAccessorOneArgMode REMOVE = PojoPropertyAccessorOneArgMode.REMOVE;

  /** Mode to read an indexed property (e.g. <code>getFoo(int)</code>). */
  PojoPropertyAccessorIndexedNonArgMode GET_INDEXED = PojoPropertyAccessorIndexedNonArgMode.GET_INDEXED;

  /** Mode to write an indexed property (e.g. <code>getFoo(int)</code>). */
  PojoPropertyAccessorIndexedOneArgMode SET_INDEXED = PojoPropertyAccessorIndexedOneArgMode.SET_INDEXED;

  /**
   * Mode to {@link java.util.Map#get(Object) read} an element from a {@link java.util.Map mapped} property
   * (e.g. <code>getFoo(String)</code> or <code>getFoo().get(String)</code>).
   */
  PojoPropertyAccessorOneArgMode GET_MAPPED = PojoPropertyAccessorOneArgMode.GET_MAPPED;

  /**
   * Mode to {@link java.util.Map#put(Object, Object) write} an element to a {@link java.util.Map mapped}
   * property (e.g. <code>setFoo(String, Foo)</code> or <code>getFoo().put(String, Foo)</code>).
   */
  PojoPropertyAccessorTwoArgMode SET_MAPPED = PojoPropertyAccessorTwoArgMode.SET_MAPPED;

}
