/* $Id$ */
package net.sf.mmm.value.impl;

import org.junit.Test;

import net.sf.mmm.value.api.GenericValue;

/**
 * This is the {@link junit.framework.TestCase} for testing the class
 * {@link ObjectValue}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ObjectValueTest extends AbstractGenericValueTest {

  /**
   * The constructor.
   */
  public ObjectValueTest() {

    super();
  }

  /**
   * @see net.sf.mmm.value.impl.AbstractGenericValueTest#convert(java.lang.Object)
   */
  @Override
  protected GenericValue convert(Object plainValue) {

    return new ImmutableObjectValue(plainValue);
  }

}
