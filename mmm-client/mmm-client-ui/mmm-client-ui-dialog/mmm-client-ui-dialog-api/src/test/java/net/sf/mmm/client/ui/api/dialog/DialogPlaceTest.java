/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.dialog;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link DialogPlace}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class DialogPlaceTest {

  /**
   * Tests {@link DialogPlace} without parameters.
   */
  @Test
  public void testWithoutParameters() {

    String dialogId = "myDialogId";
    DialogPlace place = DialogPlace.fromString(dialogId);
    Assert.assertSame(dialogId, place.getDialogId());
    Assert.assertEquals(dialogId, place.toString());
    Assert.assertFalse(place.hasParameter("name"));
    Assert.assertNull(place.getParameter("name"));

    DialogPlace copy = DialogPlace.fromString(place.toString());
    Assert.assertEquals(place, copy);
  }

  /**
   * Tests {@link DialogPlace} with parameters.
   */
  @Test
  public void testWithParameters() {

    String dialogId = "myDialogId";
    String placeString = dialogId + ":key1=value1;key2=value2";
    DialogPlace place = DialogPlace.fromString(placeString);
    Assert.assertEquals(dialogId, place.getDialogId());
    Assert.assertEquals(placeString, place.toString());
    Assert.assertFalse(place.hasParameter("name"));
    Assert.assertNull(place.getParameter("name"));
    Assert.assertTrue(place.hasParameter("key1"));
    Assert.assertEquals("value1", place.getParameter("key1"));
    Assert.assertTrue(place.hasParameter("key2"));
    Assert.assertEquals("value2", place.getParameter("key2"));

    DialogPlace copy = DialogPlace.fromString(place.toString());
    Assert.assertEquals(place, copy);
  }

}
