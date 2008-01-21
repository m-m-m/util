/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.api;

import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessor;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorMode;
import net.sf.mmm.util.reflect.pojo.api.attribute.PojoAttributeName;

/**
 * This interface represents a property of a POJO. It is an alternative to
 * {@link java.beans.PropertyDescriptor} but only has focus on reflectively
 * accessing objects. Therefore it works on any POJO. A POJO (plain old java
 * object) in this manner is more or less any java object.<br>
 * Look at the following example:
 * 
 * <pre>
 * public interface Pojo {
 *   Integer getFooBar();
 *   void setFooBar(int s);
 *   
 *   boolean hasSomeFlag();
 *   void setSomeFlag(Boolean flag);
 *   
 *   boolean isCool();
 *   void setCool();
 *   
 *   List&lt;String&gt; getColors();
 *   void addColor(String color);
 * }
 * </pre>
 * 
 * This interface does NOT completely follow the JAVA-Beans specification. The
 * properties "fooBar" and "someFlag" do NOT have the same type for reading and
 * writing. Therefore {@link java.beans} or
 * <code>org.apache.commons.beanutils</code> can NOT be used to read and write
 * these properties. Using this utility the properties can be accessed as
 * described in the following table:<br>
 * <br>
 * <table>
 * <tr>
 * <th>Name</th>
 * <th>Mode</th>
 * <th>Property-Class</th>
 * <th>Method</th>
 * <th>Component-Type</th>
 * </tr>
 * <tr>
 * <td>fooBar</td>
 * <td>get</td>
 * <td>Integer</td>
 * <td>getFooBar()</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>fooBar</td>
 * <td>set</td>
 * <td>int</td>
 * <td>setFooBar(int)</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>someFlag</td>
 * <td>get</td>
 * <td>boolean</td>
 * <td>hasSomeFlag()</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>someFlag</td>
 * <td>set</td>
 * <td>Boolean</td>
 * <td>setSomeFlag(Boolean)</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>cool</td>
 * <td>get</td>
 * <td>boolean</td>
 * <td>isCool()</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>colors</td>
 * <td>get</td>
 * <td>List&lt;String&gt;</td>
 * <td>getColors()</td>
 * <td>String</td>
 * </tr>
 * <tr>
 * <td>color</td>
 * <td>add</td>
 * <td>String</td>
 * <td>addColor(String)</td>
 * <td>-</td>
 * </tr>
 * </table>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PojoPropertyDescriptor extends PojoAttributeName {

  /**
   * This method gets the {@link PojoPropertyAccessor accessor} to access the
   * property in the way given by <code>mode</code>.
   * 
   * @param <ACCESSOR> is the type of the requested accessor.
   * @param mode is the {@link PojoPropertyAccessor#getMode() mode} of the
   *        requested accessor.
   * @return the accessor for the given <code>mode</code> or <code>null</code>
   *         if no such accessor exists.
   */
  <ACCESSOR extends PojoPropertyAccessor> ACCESSOR getAccessor(
      PojoPropertyAccessorMode<ACCESSOR> mode);

}
