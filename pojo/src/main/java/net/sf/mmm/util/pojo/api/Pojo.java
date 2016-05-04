/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.api;

/**
 * This interface is only used for documentation of what is meant by the term POJO:<br>
 * A <em>POJO</em> is a shortcut for <em>Plain Old Java Object</em> and simply means any Java object containing or
 * providing data. While the java beans specification is generally a good idea to follow, it is sometimes too
 * restrictive. E.g. you might want to name a boolean getter with the prefix "has" or want to have a primitive type as
 * setter argument while the getter has the according object type. A POJO is NOT limited by such restrictions. However
 * the following conventions should be considered:
 * <ul>
 * <li>You should NOT have two setter-methods with the same name in the same class. If you have {@code setFoo(Foo foo)}
 * do NOT add {@code setFoo(String foo)}. Simply add {@code setFooAsString(String fooAsString)} instead.</li>
 * <li>A {@link Pojo} should have a public non-argument constructor. Otherwise consider
 * {@link net.sf.mmm.util.lang.api.Datatype} or use {@link PojoFactory} to provide custom-logic to the implementation in
 * order to create instances of your {@link Pojo} with the utilities offered here.</li>
 * </ul>
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public abstract interface Pojo {

  // nothing to do here, just for documentation

}
