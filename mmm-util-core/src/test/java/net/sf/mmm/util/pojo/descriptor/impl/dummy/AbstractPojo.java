/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.dummy;

/**
 * This is a dummy pojo for testing.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public abstract class AbstractPojo implements PojoInterface {

  private String name;

  /**
   * The constructor.
   */
  public AbstractPojo() {

    super();
    this.name = null;
  }

  public CharSequence getName() {

    return this.name;
  }

  public void setName(String name) {

    this.name = name;
  }

}
