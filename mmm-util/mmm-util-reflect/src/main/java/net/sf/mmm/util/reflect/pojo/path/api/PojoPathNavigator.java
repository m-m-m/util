/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.path.api;

import net.sf.mmm.util.reflect.InstantiationFailedException;

/**
 * This is the interface for the navigator of a <em>POJO-path</em>. POJO is a
 * shortcut for Plain Old Java Object and simply means any Java object
 * containing or providing data. A <em>POJO-path</em> is a {@link String} that
 * acts as expression to navigate (traverse) the object-web spanned by an
 * initial POJO reflectively.<br>
 * <h3>Syntax</h3>
 * The syntax of a <em>POJO-path</em> is defined as follows:<br>
 * <code>Pojo-path = &#171;Segment&#187; | &#171;Pojo-path&#187;.&#171;Segment&#187;</code><br>
 * <code>Segment = &#171;Property&#187; | &#171;Index&#187; | &#171;Function&#187;</code><br>
 * <code>Property = [a-zA-Z][a-zA-Z0-9]*</code><br>
 * <code>Index = [0-9]+</code><br>
 * <code>Function = &#64;[a-zA-Z]+</code><br>
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
 * {@link PojoPathNavigator} will fail throwing an exception.</li>
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
 * &#171;Function&#187; represents a {@link PojoPathFunction}. The
 * &#171;Function&#187; is indicated by the
 * {@link PojoPathFunction#FUNCTION_NAME_PREFIX prefix} '&#64;' and is followed
 * by the
 * {@link PojoPathFunctionManager#getFunction(String) name of the function}.
 * {@link PojoPathFunction} can make assumptions on the actual POJO they operate
 * on. If the actual POJO does NOT follow these assumptions the
 * {@link PojoPathNavigator} will fail throwing an exception.
 * 
 * <h3>Design</h3>
 * The {@link PojoPathNavigator} is designed to be thread-safe and extendable.
 * Therefore the state is externalized to a {@link PojoPathContext context} that
 * is provided
 * 
 * <h3>Examples</h3>
 * TODO
 * 
 * @see net.sf.mmm.util.reflect.pojo.descriptor.api.PojoDescriptorBuilder
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PojoPathNavigator {

  /**
   * This method evaluates the given <code>pojoPath</code> for the given
   * <code>pojo</code> using the given <code>mode</code> and
   * <code>context</code>. It returns the result of the evaluation.
   * 
   * @param pojo
   * @param pojoPath
   * @param mode
   * @param context
   * @return
   * @throws PojoPathException
   * @throws InstantiationFailedException
   */
  Object get(Object pojo, String pojoPath, PojoPathMode mode, PojoPathContext context)
      throws PojoPathException, InstantiationFailedException;

  /**
   * 
   * @param pojo
   * @param pojoPath
   * @param mode
   * @param context
   * @param value
   * @return
   */
  Object set(Object pojo, String pojoPath, PojoPathMode mode, PojoPathContext context, Object value)
      throws PojoPathException, InstantiationFailedException;

}
