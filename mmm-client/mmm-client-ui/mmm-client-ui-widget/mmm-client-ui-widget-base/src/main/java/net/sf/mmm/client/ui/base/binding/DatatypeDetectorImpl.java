/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.binding;

/**
 * This is the default implementation of {@link AbstractDatatypeDetector}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class DatatypeDetectorImpl extends AbstractDatatypeDetector {

  /**
   * The constructor.
   */
  public DatatypeDetectorImpl() {

    super();
    registerDefaultDatatypes();
  }

}
