/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.test.impl;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import net.sf.mmm.persistence.base.jpa.AbstractJpaEntity;
import net.sf.mmm.persistence.impl.jpa.test.api.DummyBarEntity;
import net.sf.mmm.persistence.impl.jpa.test.api.DummyFooEntity;
import net.sf.mmm.util.version.api.VersionIdentifier;

/**
 * This is a simple entity for testing.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Entity
public class DummyFooEntityImpl extends AbstractJpaEntity<Integer> implements DummyFooEntity {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /** @see #getNumber() */
  private int number;

  /** @see #getVersionIdentifier() */
  private VersionIdentifier versionIdentifier;

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
  @Override
  public int getNumber() {

    return this.number;
  }

  /**
   * @param number is the number to set
   */
  @Override
  public void setNumber(int number) {

    this.number = number;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VersionIdentifier getVersionIdentifier() {

    return this.versionIdentifier;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setVersionIdentifier(VersionIdentifier version) {

    this.versionIdentifier = version;
  }

  /**
   * @return the bar
   */
  @Override
  @OneToOne
  public DummyBarEntityImpl getBar() {

    return this.bar;
  }

  /**
   * @param bar is the bar to set
   */
  @Override
  public void setBar(DummyBarEntity bar) {

    this.bar = (DummyBarEntityImpl) bar;
  }

}
