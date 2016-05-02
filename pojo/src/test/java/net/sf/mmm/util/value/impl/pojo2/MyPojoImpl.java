/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl.pojo2;

import java.util.List;

/**
 * This is some code only used for {@link net.sf.mmm.util.value.impl.ComposedValueConverterTest}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
@SuppressWarnings("all")
public class MyPojoImpl implements MyPojo {

  private List<SubPojo> subPojos;

  /**
   * {@inheritDoc}
   */
  public List<SubPojo> getSubPojos() {

    return this.subPojos;
  }

  public void setSubPojos(List<SubPojo> subPojos) {

    this.subPojos = subPojos;
  }

}
