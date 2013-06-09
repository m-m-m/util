/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.api;

import net.sf.mmm.util.lang.api.attribute.AttributeReadTitle;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This class represents a typed property (typically from a java bean). The raw property name is called
 * {@link #getSegment() segment}. In case the property is not directly accessed but via traversing multiple
 * properties of an object-web, then {@link #getParentPath()} will return the path to the parent object
 * containing the actual property identified by {@link #getSegment()}. The entire path including the
 * {@link #getSegment() segment} is accessible via {@link #getPojoPath()}. The actual type of the property may
 * be returned by {@link #getPropertyType()} (if provided at construction time) but is typically omitted.<br/>
 * <b>Example:</b><br/>
 * 
 * <pre>
 * public interface MyObject {
 *
 *   {@link TypedProperty}&lt;String> PROPERTY_NAME = new {@link TypedProperty}&lt;>(String.class, "name");
 *
 *   {@link TypedProperty}&lt;Integer> PROPERTY_AGE = new {@link TypedProperty}&lt;>(Integer.class, "age");
 *
 *   {@link TypedProperty}&lt;String> PROPERTY_ADDRESS_CITY = new {@link TypedProperty}&lt;>(String.class, "address", MyAddress.TYPED_PROPERTY_CITY);
 *
 *   String getName();
 *
 *   // may also return int instead of Integer...
 *   Integer getAge();
 *
 *   MyAddress getAddress();
 * }
 * </pre>
 * 
 * Now assuming that you have a generic signature like this:
 * 
 * <pre>
 * public interface Query {
 *   &lt;T> addCondition({@link TypedProperty}&lt;T> property, Operator operator, T value);
 * }
 * </pre>
 * 
 * Then you can combine these two things as following:
 * 
 * <pre>
 * Query myQuery = newQuery();
 * myQuery.addCondition(MyObject.TYPED_PROPERTY_AGE, Operator.GREATER_THAN, Integer.valueOf(42));
 * </pre>
 * 
 * Now in case you have to change the type of the property "age" for whatever reason to some other type, then
 * you change the type of the getter in parallel with the according {@link TypedProperty} constant. In the
 * same moment the compiler will reject the above code to create the query and you will be able to fix all
 * places where generic access to the "age" property takes place. When using simple string references for
 * property access (<code>addCondition(String, Operator, Object)</code>) instead you would have to pray that
 * you did NOT miss anything in the code or you get errors in production at runtime.
 * 
 * @param <T> is the generic type of the property.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class TypedProperty<T> implements PojoPropertyPath, AttributeReadTitle<String> {

  /** @see #getSegment() */
  private final String segment;

  /** @see #getPojoPath() */
  private final String pojoPath;

  /** @see #getPropertyType() */
  private final Class<T> propertyType;

  /** @see #getParentPath() */
  private final String parentPath;

  /** @see #getTitle() */
  private final String title;

  /**
   * The constructor.
   * 
   * @param segment is the {@link #getSegment() segment} and also the entire {@link #getPojoPath() pojo path}.
   */
  public TypedProperty(String segment) {

    this((Class<T>) null, segment);
  }

  /**
   * The constructor.
   * 
   * @param segment - see {@link #getSegment()}.
   * @param parentPath - see {@link #getParentPath()} and {@link #createPath(String...)}.
   */
  public TypedProperty(String segment, String parentPath) {

    this(null, segment, parentPath);
  }

  /**
   * The constructor.
   * 
   * @param propertyType - see {@link #getPropertyType()}.
   * @param segment is the {@link #getSegment() segment} and also the entire {@link #getPojoPath() pojo path}.
   */
  public TypedProperty(Class<T> propertyType, String segment) {

    this(segment, propertyType, segment);
  }

  /**
   * The constructor.
   * 
   * @param title - see {@link #getTitle()}.
   * @param propertyType - see {@link #getPropertyType()}.
   * @param segment is the {@link #getSegment() segment} and also the entire {@link #getPojoPath() pojo path}.
   */
  public TypedProperty(String title, Class<T> propertyType, String segment) {

    super();
    this.title = title;
    this.propertyType = propertyType;
    this.pojoPath = segment;
    this.segment = segment;
    this.parentPath = null;
  }

  /**
   * The constructor.
   * 
   * @param property is the existing {@link TypedProperty} to create as nested property.
   * @param path is the parent path to the given <code>property</code>. See also
   *        {@link #createPath(String...)}.
   */
  public TypedProperty(TypedProperty<T> property, String path) {

    super();
    NlsNullPointerException.checkNotNull(TypedProperty.class, property);
    this.propertyType = property.propertyType;
    this.segment = property.segment;
    String propertyParentPath = property.getParentPath();
    if (propertyParentPath == null) {
      this.parentPath = path;
    } else {
      this.parentPath = path + SEPARATOR + propertyParentPath;
    }
    this.pojoPath = this.parentPath + SEPARATOR + this.segment;
    this.title = property.title;
  }

  /**
   * The constructor.
   * 
   * @param propertyType - see {@link #getPropertyType()}.
   * @param segment - see {@link #getSegment()}.
   * @param parentPath - see {@link #getParentPath()} and {@link #createPath(String...)}.
   */
  public TypedProperty(Class<T> propertyType, String segment, String parentPath) {

    this(segment, propertyType, segment, parentPath);
  }

  /**
   * The constructor.
   * 
   * @param title - see {@link #getTitle()}.
   * @param propertyType - see {@link #getPropertyType()}.
   * @param segment - see {@link #getSegment()}.
   * @param parentPath - see {@link #getParentPath()} and {@link #createPath(String...)}.
   */
  public TypedProperty(String title, Class<T> propertyType, String segment, String parentPath) {

    super();
    this.title = title;
    this.propertyType = propertyType;
    this.segment = segment;
    this.parentPath = parentPath;
    this.pojoPath = parentPath + SEPARATOR + segment;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getSegment() {

    return this.segment;
  }

  /**
   * This title is used to display the property name to end-users. If not explicitly specified, it will be the
   * same as the {@link #getSegment() segment}. For {@link net.sf.mmm.util.nls.api.NlsMessage NLS} the title
   * will be used as key for resource bundles (outside of this class) and should therefore be stable and not
   * contain special characters.
   * 
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return this.segment;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getPojoPath() {

    return this.pojoPath;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getParentPath() {

    return this.parentPath;
  }

  /**
   * This method gets the type of this property.<br/>
   * <b>ATTENTION:</b><br/>
   * The actual type is optional. The major concept is the generic that ensures type safe access as compile
   * time check. If some API takes a {@link TypedProperty} and requires the property type to be set this must
   * be explicitly documented. By default the type will be omitted, to lower the barrier of defining a
   * {@link TypedProperty} for each property of an entity.
   * 
   * @return the type or <code>null</code> if NOT available.
   */
  public Class<T> getPropertyType() {

    return this.propertyType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.pojoPath;
  }

  /**
   * This method creates a {@link #getPojoPath() pojo property path} specified by the given
   * <code>segments</code>.
   * 
   * @param segments is the array of properties to concat with dot (".").
   * @return the resulting property path as {@link String}.
   */
  public static String createPath(String... segments) {

    int length = 0;
    for (String s : segments) {
      if (length > 0) {
        length++;
      }
      length = length + s.length();
    }
    StringBuilder buffer = new StringBuilder(length);
    appendPath(buffer, segments);
    return buffer.toString();
  }

  /**
   * This method {@link StringBuilder#append(String) appends} the {@link #getPojoPath() pojo property path}
   * specified by the given <code>segments</code> to the given <code>buffer</code>.
   * 
   * @param buffer is the {@link StringBuilder} to append to.
   * @param segments are the path segments for the property.
   */
  public static void appendPath(StringBuilder buffer, String... segments) {

    for (int i = 0; i < segments.length; i++) {
      String s = segments[i];
      if (i > 0) {
        buffer.append(SEPARATOR);
      }
      buffer.append(s);
    }
  }

}
