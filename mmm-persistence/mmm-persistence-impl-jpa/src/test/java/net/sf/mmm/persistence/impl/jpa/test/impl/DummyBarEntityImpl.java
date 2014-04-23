/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.test.impl;

import javax.persistence.Entity;

import net.sf.mmm.persistence.base.jpa.AbstractJpaEntity;
import net.sf.mmm.persistence.impl.jpa.test.api.DummyBarEntity;

/**
 * This is a simple entity for testing.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Entity
public class DummyBarEntityImpl extends AbstractJpaEntity<Long> implements DummyBarEntity {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /** @see #getValue() */
  private String value;

  /**
   * The constructor.
   */
  public DummyBarEntityImpl() {

    super();
  }

  /**
   * @return the bar
   */
  @Override
  public String getValue() {

    return this.value;
  }

  /**
   * @param bar is the bar to set
   */
  @Override
  public void setValue(String bar) {

    this.value = bar;
  }

}
