/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.test.api;

/**
 * This is a test entity.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DummyFooEntity extends DummyFooEntityView {

  /**
   * @param bar the {@link DummyBarEntity} to set.
   */
  void setBar(DummyBarEntity bar);

  /**
   * @param number the number to set.
   */
  void setNumber(int number);

  /**
   * {@inheritDoc}
   */
  @Override
  DummyBarEntity getBar();

}
