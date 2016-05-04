/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * This is the test-case for {@link EmptyIterator}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class EmptyIteratorTest {

  @Test
  public void testEmpty() {

    Iterator<String> it = EmptyIterator.getInstance();
    assertFalse(it.hasNext());
    try {
      it.next();
      fail();
    } catch (NoSuchElementException e) {
    }
    try {
      it.remove();
      fail();
    } catch (IllegalStateException e) {
    } catch (UnsupportedOperationException e) {
    }
  }

}
