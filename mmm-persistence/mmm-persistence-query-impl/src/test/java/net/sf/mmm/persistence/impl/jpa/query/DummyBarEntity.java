/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.query;

import javax.persistence.Entity;

/**
 * This is a dummy entity for the tests.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Entity
public class DummyBarEntity {

  /** The name of the property {@link #getValue() value}. */
  public static final String PROPERTY_VALUE = "value";

  /** @see #getValue() */
  private String value;

  /**
   * The constructor.
   */
  public DummyBarEntity() {

    super();
  }

  /**
   * @return the value or <code>null</code>.
   */
  public String getValue() {

    return this.value;
  }

  /**
   * @param value is the value to set
   */
  public void setValue(String value) {

    this.value = value;
  }

}
