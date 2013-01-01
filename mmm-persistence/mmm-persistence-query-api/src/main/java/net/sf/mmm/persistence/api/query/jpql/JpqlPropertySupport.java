/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.query.jpql;

/**
 * This is the abstract interface for JPQL property path support.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 0.9.0
 */
public abstract interface JpqlPropertySupport extends JpqlCore {

  /**
   * This method gets the current default {@link #PROPERTY_BASE_PATH base path} used as prefix for properties.
   * The default is {@link JpqlFragment#getEntityAlias()}.
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
