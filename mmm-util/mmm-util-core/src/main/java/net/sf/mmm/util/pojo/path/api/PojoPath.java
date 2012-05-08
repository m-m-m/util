/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.api;

/**
 * A {@link PojoPath} is a {@link String} that acts as expression to {@link PojoPathNavigator navigate}
 * (traverse) the object-web spanned by an initial {@link net.sf.mmm.util.pojo.api.Pojo} reflectively.<br>
 * As part of the API this interface is mainly used for documentation of what a {@link PojoPath} is all about.
 * For the API-user a {@link PojoPath} is just a {@link #getPojoPath() String} with a specific syntax and
 * semantic. <h3>Syntax</h3> The syntax of a {@link PojoPath} is defined as follows:<br>
 * <code>{@link PojoPath} = &#171;Segment&#187; | &#171;{@link PojoPath}&#187;.&#171;Segment&#187;</code> <br>
 * <code>{@link #getSegment() Segment} = &#171;Property&#187; | &#171;Index&#187; | &#171;Function&#187;</code>
 * <br>
 * <code>Property = [a-zA-Z][^.]*</code><br>
 * <code>{@link #getIndex() Index} = [0-9]+</code><br>
 * <code>{@link #getFunction() Function} = &#64;[^.]+</code><br>
 * 
 * <h3>Semantic</h3> &#171;Property&#187; stands for a property of the {@link net.sf.mmm.util.pojo.api.Pojo}.
 * <ul>
 * <li>If the actual {@link net.sf.mmm.util.pojo.api.Pojo} is a {@link java.util.Map}, the
 * &#171;Property&#187; is the key to {@link java.util.Map#get(Object) get} and
 * {@link java.util.Map#put(Object, Object) set} the value.</li>
 * <li>Otherwise the &#171;Property&#187; is the
 * {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor#getProperty(Object, String) name of a property}
 * of the actual {@link net.sf.mmm.util.pojo.api.Pojo}. If the actual {@link net.sf.mmm.util.pojo.api.Pojo}
 * does NOT have such property, the {@link PojoPath} will be illegal and cause a {@link PojoPathException}.</li>
 * </ul>
 * &#171;Index&#187; is an {@link Integer} that represents the position of an ordered container.<br>
 * <ul>
 * <li>If the actual {@link net.sf.mmm.util.pojo.api.Pojo} is an array, the &#171;Index&#187; represents the
 * array-index to access a contained value.</li>
 * <li>If the actual {@link net.sf.mmm.util.pojo.api.Pojo} is a {@link java.util.List}, the &#171;Index&#187;
 * is used as the list-index to {@link java.util.List#get(int) get} and
 * {@link java.util.List#set(int, Object) set} a value.</li>
 * </ul>
 * 
 * &#171;Function&#187; represents a {@link PojoPathFunction}.
 * <ul>
 * <li>The &#171;Function&#187; is indicated by the {@link PojoPathFunction#FUNCTION_NAME_PREFIX prefix}
 * '&#64;' and is followed by the {@link PojoPathFunctionManager#getFunction(String) name of the function}.
 * {@link PojoPathFunction} can make assumptions on the actual {@link net.sf.mmm.util.pojo.api.Pojo} they
 * operate on. If the actual {@link net.sf.mmm.util.pojo.api.Pojo} does NOT follow these assumptions the
 * {@link PojoPath} will be illegal and cause a {@link PojoPathException}.</li>
 * </ul>
 * 
 * <h3>Transitivity</h3> For two {@link PojoPath}-strings <code>path1</code> and <code>path2</code> and for<br>
 * <code>
 * Object pojo;<br>
 * {@link PojoPathNavigator} navigator;<br>
 * {@link PojoPathMode} mode;<br>
 * {@link PojoPathContext} context;<br>
 * </code> <br>
 * The following code<br>
 * <code>
 * Object result = navigator.{@link PojoPathNavigator#get(Object, String, PojoPathMode, PojoPathContext) get}(pojo, path1, mode, context);<br>
 * result = navigator.{@link PojoPathNavigator#get(Object, String, PojoPathMode, PojoPathContext) get}(result, path2, mode, context);<br>
 * </code> will produce the same <code>result</code> as:<br>
 * <code>
 * String path = path1 + "." + path2;<br>
 * result = navigator.{@link PojoPathNavigator#get(Object, String, PojoPathMode, PojoPathContext) get}(pojo, path, mode, context);<br>
 * </code>
 * 
 * <h3>Examples</h3> The following table gives some examples:<br>
 * <table border="1">
 * <tr>
 * <th>Initial Pojo</th>
 * <th>PojoPath</th>
 * <th>
 * {@link PojoPathNavigator#get(Object, String, PojoPathMode, PojoPathContext) Get}-Code</th>
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
public interface PojoPath {

  /**
   * The character that separates the {@link #getSegment() segments} of a {@link #getPojoPath() PojoPath}. The
   * value ({@value} ) will never change. It is NOT necessary to use this constant to construct a
   * {@link PojoPath}.
   */
  char SEPARATOR = '.';

  /**
   * This method gets the actual {@link PojoPath} represented by this object.
   * 
   * @return the actual {@link PojoPath}.
   */
  String getPojoPath();

  /**
   * This method gets the parent-path of this {@link PojoPath}.<br>
   * E.g. if this path represents <code>"foo.bar.property"</code> then this method would return
   * <code>"foo.bar"</code>.
   * 
   * @return the parent-path or <code>null</code> if this is the root-segment.
   */
  String getParentPath();

  /**
   * This method gets the last segment of this current {@link PojoPath}. E.g. if this path represents
   * <code>"foo.bar.property"</code> then this method would return <code>"property"</code>.
   * 
   * @return the last segment.
   */
  String getSegment();

  /**
   * This method gets the <em>index</em> given by the {@link #getSegment() segment} or <code>null</code> if it
   * is no index.<br>
   * An <em>index</em> is an integer that represents the position of an ordered container (array or
   * {@link java.util.List}).<br>
   * If a {@link #getSegment() segment} starts with a Latin digit, it is treated as {@link #getIndex() index}
   * and has to be a {@link Integer#parseInt(String) valid} integer-value. However parsing should be done when
   * this object is constructed and therefore this method should never cause an exception.
   * 
   * @return the index given by the {@link #getSegment() segment} or <code>null</code> if it is no index.
   */
  Integer getIndex();

  /**
   * This method gets the name of the <em>function</em> given by the {@link #getSegment() segment} or
   * <code>null</code> if it is no function.<br>
   * The <em>function</em>-name {@link PojoPathFunctionManager#getFunction(String) identifies} a
   * {@link PojoPathFunction} that will be used to evaluate the {@link #getSegment() segment}.<br>
   * If a {@link #getSegment() segment} starts with the character
   * {@link PojoPathFunction#FUNCTION_NAME_PREFIX} (<code>'&#64;'</code>), it is treated as
   * {@link #getFunction() function}.
   * 
   * @return the {@link #getSegment() segment} excluding the first character or <code>null</code> if the
   *         {@link #getSegment() segment} does NOT start with {@link PojoPathFunction#FUNCTION_NAME_PREFIX}.
   */
  String getFunction();

}
