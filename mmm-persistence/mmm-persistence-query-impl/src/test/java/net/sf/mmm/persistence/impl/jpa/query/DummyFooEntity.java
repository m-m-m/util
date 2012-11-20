/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.query;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import net.sf.mmm.util.pojo.path.api.TypedProperty;

/**
 * This is a dummy entity for the tests.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Entity
public class DummyFooEntity {

  /** The name of the property {@link #getBar() bar}. */
  public static final String PROPERTY_BAR = "bar";

  /** The name of the property {@link #getNumber() number}. */
  public static final String PROPERTY_NUMBER = "number";

  /** The typed property for {@link #getNumber() number}. */
  public static final TypedProperty<Integer> TYPED_PROPERTY_NUMBER = new TypedProperty<Integer>(PROPERTY_NUMBER);

  /** @see #getNumber() */
  private int number;

  /** @see #getBar() */
  private DummyBarEntity bar;

  /**
   * The constructor.
   */
  public DummyFooEntity() {

    super();
  }

  /**
   * @return the {@link DummyBarEntity} or <code>null</code>.
   */
  @OneToOne
  public DummyBarEntity getBar() {

    return this.bar;
  }

  /**
   * @param bar is the bar to set
   */
  public void setBar(DummyBarEntity bar) {

    this.bar = bar;
  }

  /**
   * @return the number.
   */
  public int getNumber() {

    return this.number;
  }

  /**
   * @param number is the number to set
   */
  public void setNumber(int number) {

    this.number = number;
  }

}
