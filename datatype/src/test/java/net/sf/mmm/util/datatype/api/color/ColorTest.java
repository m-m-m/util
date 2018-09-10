/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.color;

import org.junit.Assert;
import org.junit.Test;

import net.sf.mmm.util.lang.api.BasicHelper;

/**
 * This is the test-case for {@link Color}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ColorTest extends Assert {

  /**
   * Positive tests of {@link Color} for legal RGB values.
   */
  @Test
  public void testRgbColor() {

    // int step = 1;
    // for speed and performance increase step...
    int step = 7;
    for (int i = 0; i <= 0xFFFFFF; i = i + step) {
      int rgba = i | 0xFF000000;
      Color color = new Color(rgba);
      Integer integer = Integer.valueOf(rgba);
      assertEquals(integer, color.getValue());

      // test string representation
      String colorString = color.toString();
      String hex = BasicHelper.toUpperCase(Integer.toString(i, 16));
      while (hex.length() < 6) {
        hex = "0" + hex;
      }
      assertEquals("#" + hex, colorString);
      assertEquals(colorString, color.toString());

      checkColorGeneric(color, colorString);
    }
  }

  /**
   * Positive tests of {@link Color} for legal RGBA values.
   */
  @Test
  public void testRgbaColor() {

    for (int i = 0; i <= 0xFF; i++) {
      int rgba = (i << 24) | 0x1F2F3F;
      Color color = new Color(rgba);
      Integer rgbaInteger = Integer.valueOf(rgba);
      assertEquals(rgbaInteger, color.getValue());
      assertEquals(i, color.getAlpha());
      assertEquals(0x1F, color.getRed());
      assertEquals(0x2F, color.getGreen());
      assertEquals(0x3F, color.getBlue());

      // test string representation
      String colorString = color.toString();
      if (i == 255) {
        assertEquals("#1F2F3F", colorString);
      } else {
        assertEquals("rgba(31,47,63," + i / 255.0 + ")", colorString);
      }

      checkColorGeneric(color, colorString);
    }
  }

  /**
   * Performs generic checks on the given {@link Color}.
   *
   * @param color is the {@link Color} to test.
   * @param colorString is the expected {@link Color#toString() string representation} of the {@link Color}.
   */
  private void checkColorGeneric(Color color, String colorString) {

    // test copy from RGBA values
    Color copy = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    assertEquals(color, copy);
    // test copy from String representation
    copy = Color.valueOf(colorString);
    assertEquals(color, copy);
    // test copy from RGBA String representation
    String rgbString = "rgba(" + color.getRed() + "," + color.getGreen() + ", " + color.getBlue() + "," + new Alpha(color.getAlpha()) + ")";
    copy = Color.valueOf(rgbString);
    assertEquals(color, copy);
    // // test copy from HSBA values
    // copy = Color.fromHsba(color.getHue(), color.getSaturationHsb(), color.getBrightness(),
    // color.getAlphaRate());
    // assertEquals(color, copy);
    // // test copy from HSLA values
    // copy = Color.fromHsla(color.getHue(), color.getSaturationHsl(), color.getLightness(),
    // color.getAlphaRate());
    // // assertEquals(color, copy);
    // if (!color.equals(copy)) {
    // System.out.println(color + " -> " + copy);
    // }
    // regression against java.awt.Color
    // test RGB
    java.awt.Color awtColor = new java.awt.Color(color.getValue().intValue());
    assertEquals(colorString, awtColor.getRed(), color.getRed());
    assertEquals(colorString, awtColor.getGreen(), color.getGreen());
    assertEquals(colorString, awtColor.getBlue(), color.getBlue());
    // test HSB
    // float[] hsb = java.awt.Color.RGBtoHSB(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue(),
    // null);
    // // double delta = 0.0000000001;
    // double delta = 0.000001;
    // // float hue = color.getHue();
    // // assertEquals(colorString, hsb[0], hue, delta);
    // // float saturation = color.getSaturationHsb();
    // // assertEquals(colorString, hsb[1], saturation, delta);
    // // float brightness = color.getBrightness();
    // // assertEquals(colorString, hsb[2], brightness, delta);
  }
}
