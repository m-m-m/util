/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.dialog.api;

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
    DialogPlace place = new DialogPlace(dialogId);
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
    DialogPlace place = new DialogPlace(dialogId);
    Assert.assertSame(dialogId, place.getDialogId());
    Assert.assertEquals(dialogId, place.toString());
    Assert.assertFalse(place.hasParameter("name"));
    Assert.assertNull(place.getParameter("name"));

    DialogPlace copy = DialogPlace.fromString(place.toString());
    Assert.assertEquals(place, copy);
  }

}
