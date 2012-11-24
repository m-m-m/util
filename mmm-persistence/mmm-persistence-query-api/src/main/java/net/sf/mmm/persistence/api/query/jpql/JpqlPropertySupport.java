/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.query.jpql;

/**
 * This is the abstract interface for JPQL property path support.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface JpqlPropertySupport {

  /**
   * This constant is used to document the concept of an <em>alias</em> or in official JPQL terminology an
   * <code>identification_variable_declaration</code>. An alias is like a variable used to identify an
   * {@link net.sf.mmm.util.entity.api.GenericEntity entity} or {@link #PROPERTY property} in statements like
   * FROM, JOIN, IN, etc.<br/>
   * E.g. in the JPQL query "SELECT DISTINCT alias2.author FROM Magazine alias1, IN(alias1.articles) alias2"
   * there are two aliases used:
   * <ul>
   * <li>alias1 is an identifier for any persistent instance of the entity <code>Magazine</code></li>.
   * <li>alias2 is an identifier for all values of the {@link #PROPERTY property} <code>articles</code> of
   * <code>alias1</code></li>.
   * </ul>
   */
  String ALIAS = "alias";

  /**
   * This constant is used to document the concept of a property. A property can either be provided in a
   * type-safe way as a {@link net.sf.mmm.util.pojo.path.api.TypedProperty} (recommended) or as a regular
   * {@link String}. Properties are addressed in JPQL by a path of java bean properties separated by a dot.
   * E.g. for the {@link JpqlBase#getEntityAlias alias} <code>myAlias</code> a final property may be
   * <code>myAlias.address.city</code> will refer to <code>getAddress().getCity()</code> invoked on
   * <code>myAlias</code>. If properties are provided as {@link String} you can still use
   * {@link net.sf.mmm.util.pojo.path.api.TypedProperty#createPath(String...)} to build that {@link String}
   * (e.g. <code>{@link net.sf.mmm.util.pojo.path.api.TypedProperty}{@literal
   * .}{@link net.sf.mmm.util.pojo.path.api.TypedProperty#createPath(String...) createPath}("contact", "address", "city")</code>
   * for "contact.address.city").<br/>
   * <b>ATTENTION:</b><br/>
   * Whenever a property is given in this API and will be appended to the query, the
   * {@link #getPropertyPrefix() property prefix} will automatically be added before the actual property.
   */
  String PROPERTY = "property";

  /**
   * This method gets the current property prefix that is automatically appended before a given property.
   * 
   * @return the empty string if {@link #getPropertyBasePath()} is empty. Otherwise
   *         {@link #getPropertyBasePath()} + ".".
   */
  String getPropertyPrefix();

  /**
   * This method gets the current property path used as {@link #getPropertyPrefix() prefix} for properties.
   * The default is {@link JpqlFragment#getEntityAlias()}.
   * 
   * @see #getPropertyPrefix()
   * 
   * @return the current property base path.
   */
  String getPropertyBasePath();

  /**
   * This method sets the value of {@link #getPropertyBasePath()}.
   * 
   * @param path is the new value of {@link #getPropertyBasePath()}. May be the empty {@link String}, an alias
   *        or path expression.
   * @return this instance.
   */
  JpqlPropertySupport setPropertyBasePath(String path);

}
