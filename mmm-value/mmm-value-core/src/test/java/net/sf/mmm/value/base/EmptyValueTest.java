/* $Id$ */
package net.sf.mmm.value.base;

import org.junit.Test;

import net.sf.mmm.value.api.GenericValueIF;
import net.sf.mmm.value.api.MutableGenericValueIF;
import net.sf.mmm.value.api.ValueNotEditableException;
import net.sf.mmm.value.api.ValueNotSetException;

import junit.framework.TestCase;

/**
 * This is the {@link junit.framework.TestCase} for testing the class
 * {@link EmptyValue}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class EmptyValueTest extends TestCase {

  /**
   * The constructor.
   */
  public EmptyValueTest() {

    super();
  }

  @Test
  public void testEmptyValue() {

    MutableGenericValueIF value = EmptyValue.getInstance();
    assertNotNull(value);
    assertTrue(value.isEmpty());
    try {
      value.getObject();
      fail("Exception expected");
    } catch (ValueNotSetException e) {
    }
    try {
      value.setObject(value);
      fail("Exception expected");
    } catch (ValueNotEditableException e) {
    }
    assertTrue(value.isEmpty());
    String s = "yipiieh!";
    assertEquals(s, value.getString(s));
    assertTrue(value.isEmpty());

  }

}
