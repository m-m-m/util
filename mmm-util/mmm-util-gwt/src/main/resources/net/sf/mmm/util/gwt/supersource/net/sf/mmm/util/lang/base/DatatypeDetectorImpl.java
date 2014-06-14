/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import javax.inject.Named;

import net.sf.mmm.util.lang.api.AbstractDatatype;
import net.sf.mmm.util.lang.api.Datatype;
import net.sf.mmm.util.lang.api.DatatypeDetector;

/**
 * This is the default implementation of {@link DatatypeDetector}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
@Named(DatatypeDetector.CDI_NAME)
public class DatatypeDetectorImpl extends AbstractDatatypeDetector {

  /**
   * The constructor.
   */
  public DatatypeDetectorImpl() {

    super();
    registerDefaultDatatypes();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDatatype(Class<?> type) {

    if (super.isDatatype(type)) {
      return true;
    }
    Class<?> supertype = type.getSuperclass();
    while ((supertype != Object.class) && (supertype != null)) {
      if (supertype == AbstractDatatype.class) {
        return true;
      }
      supertype = supertype.getSuperclass();
    }
    return false;
  }

}
