/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.test.impl;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import net.sf.mmm.persistence.base.jpa.JpaEntity;
import net.sf.mmm.persistence.impl.jpa.test.api.DummyBarEntity;
import net.sf.mmm.persistence.impl.jpa.test.api.DummyFooEntity;

/**
 * This is a simple entity for testing.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Entity
public class DummyFooEntityImpl extends JpaEntity<Integer> implements DummyFooEntity {

  /** @see #getNumber() */
  private int number;

  /** @see #getBar() */
  private DummyBarEntityImpl bar;

  /**
   * The constructor.
   */
  public DummyFooEntityImpl() {

    super();
  }

  /**
   * @return the number
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

  /**
   * @return the bar
   */
  @OneToOne
  public DummyBarEntityImpl getBar() {

    return this.bar;
  }

  /**
   * @param bar is the bar to set
   */
  public void setBar(DummyBarEntity bar) {

    this.bar = (DummyBarEntityImpl) bar;
  }

}
