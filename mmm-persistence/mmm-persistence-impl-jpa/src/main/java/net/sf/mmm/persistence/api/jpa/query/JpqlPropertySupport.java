/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.jpa.query;

/**
 * This is the abstract interface for JPQL property path support.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface JpqlPropertySupport {

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
