/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.api;

/**
 * A {@link PojoPath} is a {@link String} that acts as expression to {@link PojoPathNavigator navigate} (traverse) the
 * object-web spanned by an initial {@link net.sf.mmm.util.pojo.api.Pojo} reflectively. <br>
 * As part of the API this interface is mainly used for documentation of what a {@link PojoPath} is all about. For the
 * API-user a {@link PojoPath} is just a {@link #getPojoPath() String} with a specific syntax and semantic.
 * <h3>Syntax</h3> The syntax of a {@link PojoPath} is defined as follows:<br>
 *
 * <pre>
 * {@link PojoPath} = «Segment» | «{@link PojoPath}».«Segment»
 * {@link #getSegment() Segment} = «Property» | «Index» | «Function»
 * Property = [a-zA-Z][^.]*
 * {@link #getIndex() Index} = [0-9]+
 * {@link #getFunction() Function} = @[^. ]+
 * </pre>
 *
 * <h3>Semantic</h3> «Property» stands for a property of the {@link net.sf.mmm.util.pojo.api.Pojo}.
 * <ul>
 * <li>If the actual {@link net.sf.mmm.util.pojo.api.Pojo} is a {@link java.util.Map}, the «Property» is the key to
 * {@link java.util.Map#get(Object) get} and {@link java.util.Map#put(Object, Object) set} the value.</li>
 * <li>Otherwise the «Property» is the
 * {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor#getProperty(Object, String) name of a property} of the
 * actual {@link net.sf.mmm.util.pojo.api.Pojo}. If the actual {@link net.sf.mmm.util.pojo.api.Pojo} does NOT have such
 * property, the {@link PojoPath} will be illegal and cause a {@link PojoPathException}.</li>
 * </ul>
 * «Index» is an {@link Integer} that represents the position of an ordered container. <br>
 * <ul>
 * <li>If the actual {@link net.sf.mmm.util.pojo.api.Pojo} is an array, the «Index» represents the array-index to access
 * a contained value.</li>
 * <li>If the actual {@link net.sf.mmm.util.pojo.api.Pojo} is a {@link java.util.List}, the «Index» is used as the
 * list-index to {@link java.util.List#get(int) get} and {@link java.util.List#set(int, Object) set} a value.</li>
 * </ul>
 *
 * «Function» represents a {@link PojoPathFunction}.
 * <ul>
 * <li>The «Function» is indicated by the {@link PojoPathFunction#FUNCTION_NAME_PREFIX prefix} {@code '@'} and is
 * followed by the {@link PojoPathFunctionManager#getFunction(String) name of the function}. {@link PojoPathFunction}
 * can make assumptions on the actual {@link net.sf.mmm.util.pojo.api.Pojo} they operate on. If the actual
 * {@link net.sf.mmm.util.pojo.api.Pojo} does NOT follow these assumptions the {@link PojoPath} will be illegal and
 * cause a {@link PojoPathException}.</li>
 * </ul>
 *
 * <h3>Transitivity</h3> For two {@link PojoPath}-strings {@code path1} and {@code path2} and for<br>
 *
 * <pre>
 * {@link PojoPathNavigator} navigator;
 * {@link PojoPathMode} mode;
 * {@link PojoPathContext} context;
 * </pre>
 *
 * The following code
 *
 * <pre>
 * Object result = navigator.{@link PojoPathNavigator#get(Object, String, PojoPathMode, PojoPathContext) get}(pojo, path1, mode, context);<br>
 * result = navigator.{@link PojoPathNavigator#get(Object, String, PojoPathMode, PojoPathContext) get}(result, path2, mode, context);<br>
 * </pre>
 *
 * will produce the same {@code result} as:
 *
 * <pre>
 * String path = path1 + "." + path2;<br>
 * result = navigator.{@link PojoPathNavigator#get(Object, String, PojoPathMode, PojoPathContext) get}(pojo, path, mode, context);<br>
 * </pre>
 *
 * <h3>Examples</h3> The following table gives some examples:<br>
 * <table border="1">
 * <tr>
 * <th>Initial Pojo</th>
 * <th>PojoPath</th>
 * <th>{@link PojoPathNavigator#get(Object, String, PojoPathMode, PojoPathContext) Get}-Code</th>
 * </tr>
 * <tr>
 * <td>{@link Object} object</td>
 * <td>class</td>
 * <td>object.getClass()</td>
 * </tr>
 * <tr>
 * <td>{@link String} string</td>
 * <td>bytes</td>
 * <td>string.getBytes()</td>
 * </tr>
 * <tr>
 * <td>{@link Object}[] array</td>
 * <td>0</td>
 * <td>array[0]</td>
 * </tr>
 * <tr>
 * <td>{@link java.util.List} list</td>
 * <td>0</td>
 * <td>list.get(0)</td>
 * </tr>
 * <tr>
 * <td>{@link java.util.Map} map</td>
 * <td>key</td>
 * <td>map.get("key")</td>
 * </tr>
 * <tr>
 * <td>{@link Throwable} t</td>
 * <td>cause.cause.message.bytes.0</td>
 * <td>t.getCause().getCause().getMessage().getBytes()[0]</td>
 * </tr>
 * </table>
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public interface PojoPath extends PojoPropertyPath {

  /**
   * This method gets the <em>index</em> given by the {@link #getSegment() segment} or {@code null} if it is no index.
   * <br>
   * An <em>index</em> is an integer that represents the position of an ordered container (array or
   * {@link java.util.List}). <br>
   * If a {@link #getSegment() segment} starts with a Latin digit, it is treated as {@link #getIndex() index} and has to
   * be a {@link Integer#parseInt(String) valid} integer-value. However parsing should be done when this object is
   * constructed and therefore this method should never cause an exception.
   *
   * @return the index given by the {@link #getSegment() segment} or {@code null} if it is no index.
   */
  Integer getIndex();

  /**
   * This method gets the name of the <em>function</em> given by the {@link #getSegment() segment} or {@code null} if it
   * is no function. <br>
   * The <em>function</em>-name {@link PojoPathFunctionManager#getFunction(String) identifies} a
   * {@link PojoPathFunction} that will be used to evaluate the {@link #getSegment() segment}. <br>
   * If a {@link #getSegment() segment} starts with the character {@link PojoPathFunction#FUNCTION_NAME_PREFIX} (
   * {@code '@'}), it is treated as {@link #getFunction() function}.
   *
   * @return the {@link #getSegment() segment} excluding the first character or {@code null} if the {@link #getSegment()
   *         segment} does NOT start with {@link PojoPathFunction#FUNCTION_NAME_PREFIX}.
   */
  String getFunction();

}
