/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test;

import javax.inject.Named;

import net.sf.mmm.client.ui.base.binding.DatatypeDetectorImpl;
import net.sf.mmm.util.datatype.api.address.PostalCode;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Named
public class DatatypeDetectorTest extends DatatypeDetectorImpl {

  /**
   * The constructor.
   */
  public DatatypeDetectorTest() {

    super();
    registerDatatype(PostalCode.class);
  }

}
