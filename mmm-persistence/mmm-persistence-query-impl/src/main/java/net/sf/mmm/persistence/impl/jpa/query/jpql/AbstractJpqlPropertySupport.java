/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.query.jpql;

import net.sf.mmm.persistence.api.query.jpql.JpqlPropertySupport;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This is the implementation of {@link JpqlPropertySupport}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractJpqlPropertySupport extends AbstractLoggableComponent implements JpqlPropertySupport {

  /** @see #getPropertyPrefix() */
  private String propertyPrefix;

  /** @see #getPropertyBasePath() */
  private String propertyBasePath;

  /**
   * The constructor.
   */
  public AbstractJpqlPropertySupport() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getPropertyPrefix() {

    return this.propertyPrefix;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getPropertyBasePath() {

    return this.propertyBasePath;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JpqlPropertySupport setPropertyBasePath(String path) {

    NlsNullPointerException.checkNotNull("path", path);
    this.propertyBasePath = path;
    if (path.length() == 0) {
      this.propertyPrefix = path;
    } else {
      this.propertyPrefix = path + ".";
    }
    return this;
  }

}
