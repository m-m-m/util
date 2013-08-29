/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.common;

import net.sf.mmm.util.lang.base.StringUtilImpl;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link Color}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ColorTest extends Assert {

  /**
   * Positive tests of {@link Color} for all legal values.
   */
  @Test
  public void testColor() {

    for (int i = 0; i < 0xFFFFFF; i++) {
      Color color = new Color(i);
      Integer integer = Integer.valueOf(i);
      assertEquals(integer, color.getValue());

      // test string representation
      String colorString = color.toString();
      assertEquals("#" + StringUtilImpl.getInstance().padNumber(i, 6, 16), colorString);
      assertEquals(colorString, color.getTitle());

      // test RGB
      java.awt.Color awtColor = new java.awt.Color(i);
      assertEquals(colorString, awtColor.getRed(), color.getRed());
      assertEquals(colorString, awtColor.getGreen(), color.getGreen());
      assertEquals(colorString, awtColor.getBlue(), color.getBlue());
      Color copy = new Color(color.getRed(), color.getGreen(), color.getBlue());
      assertEquals(integer, copy.getValue());
      assertEquals(color, copy);

      // test HSB
      float[] hsb = java.awt.Color.RGBtoHSB(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue(), null);
      double delta = 0.0000000001;
      float hue = color.getHue();
      assertEquals(colorString, hsb[0], hue, delta);
      float saturation = color.getSaturation();
      assertEquals(colorString, hsb[1], saturation, delta);
      float brightness = color.getBrightness();
      assertEquals(colorString, hsb[2], brightness, delta);
      copy = Color.fromHsb(hue, saturation, brightness);
      assertEquals(integer, copy.getValue());
      assertEquals(color, copy);
    }
  }

}
