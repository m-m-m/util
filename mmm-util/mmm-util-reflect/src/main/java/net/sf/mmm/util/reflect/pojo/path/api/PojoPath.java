/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.path.api;

/**
 * A {@link PojoPath} is a {@link String} that acts as expression to
 * {@link PojoPathNavigator navigate} (traverse) the object-web spanned by an
 * initial {@link net.sf.mmm.util.reflect.pojo.Pojo} reflectively.<br>
 * As part of the API this interface is mainly used for documentation of what a
 * {@link PojoPath} is all about. For the API-user a {@link PojoPath} is just a
 * {@link #getPojoPath() String} with a specific syntax and semantic.
 * <h3>Syntax</h3>
 * The syntax of a {@link PojoPath} is defined as follows:<br>
 * <code>{@link PojoPath} = &#171;Segment&#187; | &#171;{@link PojoPath}&#187;.&#171;Segment&#187;</code><br>
 * <code>{@link #getSegment() Segment} = &#171;Property&#187; | &#171;Index&#187; | &#171;Function&#187;</code><br>
 * <code>Property = [a-zA-Z][a-zA-Z0-9]*</code><br>
 * <code>Index = [0-9]+</code><br>
 * <code>Function = &#64;[a-zA-Z0-9]+</code><br>
 * 
 * <h3>Semantic</h3>
 * &#171;Property&#187; stands for the name of a property.
 * <ul>
 * <li>If the actual POJO is a {@link java.util.Map}, the &#171;Property&#187;
 * is the key to {@link java.util.Map#get(Object) get} and
 * {@link java.util.Map#put(Object, Object) set} the value.</li>
 * <li>Otherwise the &#171;Property&#187; is the
 * {@link net.sf.mmm.util.reflect.pojo.descriptor.api.PojoPropertyDescriptor#getName() name of a property}
 * of the actual POJO. If the actual POJO does NOT have such property, the
 * {@link PojoPath} will be illegal and cause a {@link PojoPathException}.</li>
 * </ul>
 * &#171;Index&#187; is an {@link Integer} that represents the position of an
 * ordered container.<br>
 * <ul>
 * <li>If the actual POJO is an array, the &#171;Index&#187; represents the
 * array-index to access a contained value.</li>
 * <li>If the actual POJO is a {@link java.util.List}, the &#171;Index&#187;
 * is used as the list-index to {@link java.util.List#get(int) get} and
 * {@link java.util.List#set(int, Object) set} a value.</li>
 * </ul>
 * 
 * &#171;Function&#187; represents a {@link PojoPathFunction}.
 * <ul>
 * <li>The &#171;Function&#187; is indicated by the
 * {@link PojoPathFunction#FUNCTION_NAME_PREFIX prefix} '&#64;' and is followed
 * by the
 * {@link PojoPathFunctionManager#getFunction(String) name of the function}.
 * {@link PojoPathFunction} can make assumptions on the actual POJO they operate
 * on. If the actual POJO does NOT follow these assumptions the {@link PojoPath}
 * will be illegal and cause a {@link PojoPathException}.</li>
 * </ul>
 * 
 * <h3>Examples</h3>
 * TODO
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PojoPath {

  /**
   * This method gets the actual {@link PojoPath} represented by this object.
   * 
   * @return the actual {@link PojoPath}.
   */
  String getPojoPath();

  /**
   * This method gets the parent-path of this {@link PojoPath}.<br>
   * E.g. if this path represents <code>"foo.bar.property"</code> then this
   * method would return <code>"foo.bar"</code>.
   * 
   * @return the parent-path or <code>null</code> if this is the root-segment.
   */
  String getParentPath();

  /**
   * This method gets the last segment of this current {@link PojoPath}. E.g.
   * if this path represents <code>"foo.bar.property"</code> then this method
   * would return <code>"property"</code>.
   * 
   * @return the last segment.
   */
  String getSegment();

}
