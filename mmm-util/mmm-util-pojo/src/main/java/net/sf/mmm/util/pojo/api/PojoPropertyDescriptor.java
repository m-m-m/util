/* $Id$ */
package net.sf.mmm.util.pojo.api;

/**
 * This interface represents a property of a POJO. It is an alternative to
 * {@link java.beans.PropertyDescriptor} but only has focus on reflectively
 * modifying properties. Therefore it works on any POJO. A POJO (plain old java
 * object) in this manner is more or less any java object. It has no restrictive
 * specification such as a Java Bean where the getter and setter of a property
 * needs to have the exact same type. In advance boolean getters can also use
 * the prefix <code>has</code>. Further a list-type property can also define
 * an {@link #getAddAccess() add-method} allowing to add items to the list.<br>
 * <br>
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
 * <th>Access</th>
 * <th>Raw-Type</th>
 * <th>Method</th>
 * <th>Component-Type</th>
 * </tr>
 * <tr>
 * <td>fooBar</td>
 * <td>read</td>
 * <td>Integer</td>
 * <td>getFooBar()</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>fooBar</td>
 * <td>write</td>
 * <td>int</td>
 * <td>setFooBar(int)</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>someFlag</td>
 * <td>read</td>
 * <td>boolean</td>
 * <td>hasSomeFlag()</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>someFlag</td>
 * <td>write</td>
 * <td>Boolean</td>
 * <td>setSomeFlag(Boolean)</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>cool</td>
 * <td>read</td>
 * <td>boolean</td>
 * <td>isCool()</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>colors</td>
 * <td>read</td>
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
public interface PojoPropertyDescriptor {

  /**
   * This method gets the programmatic (technical) name of the according
   * property.<br>
   * E.g. for the accessor {@link #getReadAccess() read-accessor}
   * <code>public String getFooBar()</code> the property name would be
   * <code>fooBar</code>.
   * 
   * @see java.beans.PropertyDescriptor#getName()
   * 
   * @return the property name.
   */
  String getName();

  /**
   * This method gets the {@link PojoPropertyAccessor accessor} used to read
   * the property. It represents the getter method for the property.
   * 
   * @return the requested accessor or <code>null</code> if it is NOT
   *         available.
   */
  PojoPropertyAccessor getReadAccess();

  /**
   * This method gets the {@link PojoPropertyAccessor accessor} used to write
   * the property. It represents the setter method for the property.
   * 
   * @return the requested accessor or <code>null</code> if it is NOT
   *         available.
   */
  PojoPropertyAccessor getWriteAccess();

  /**
   * This method gets the {@link PojoPropertyAccessor accessor} used to add an
   * item to a list-type property.
   * 
   * @return the requested accessor or <code>null</code> if it is NOT
   *         available.
   */
  PojoPropertyAccessor getAddAccess();

}
