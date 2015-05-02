/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.music;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link TonePitch}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class TonePitchTest {

  /**
   * Test for {@link TonePitch#transpose(int)}.
   */
  @Test
  public void testTranspose() {

    Assert.assertEquals(TonePitch.C, TonePitch.C.transpose(0));
    Assert.assertEquals(TonePitch.C, TonePitch.C.transpose(12));
    Assert.assertEquals(TonePitch.C, TonePitch.C.transpose(-12));
    Assert.assertEquals(TonePitch.C, TonePitch.C.transpose(240));
    Assert.assertEquals(TonePitch.C, TonePitch.C.transpose(-240));
    Assert.assertEquals(TonePitch.CIS, TonePitch.C.transpose(1));
    Assert.assertEquals(TonePitch.D, TonePitch.C.transpose(2));
    Assert.assertEquals(TonePitch.DIS, TonePitch.C.transpose(3));
    Assert.assertEquals(TonePitch.E, TonePitch.C.transpose(4));
    Assert.assertEquals(TonePitch.F, TonePitch.C.transpose(5));
    Assert.assertEquals(TonePitch.FIS, TonePitch.C.transpose(6));
    Assert.assertEquals(TonePitch.G, TonePitch.C.transpose(7));
    Assert.assertEquals(TonePitch.GIS, TonePitch.C.transpose(8));
    Assert.assertEquals(TonePitch.A, TonePitch.C.transpose(9));
    Assert.assertEquals(TonePitch.B_FLAT, TonePitch.C.transpose(10));
    Assert.assertEquals(TonePitch.B_NATURAL, TonePitch.C.transpose(11));

    Assert.assertEquals(TonePitch.B_NATURAL, TonePitch.C.transpose(-1));
    Assert.assertEquals(TonePitch.B_FLAT, TonePitch.C.transpose(-2));
    Assert.assertEquals(TonePitch.A, TonePitch.C.transpose(-3));
    Assert.assertEquals(TonePitch.GIS, TonePitch.C.transpose(-4));
    Assert.assertEquals(TonePitch.G, TonePitch.C.transpose(-5));
    Assert.assertEquals(TonePitch.FIS, TonePitch.C.transpose(-6));
    Assert.assertEquals(TonePitch.F, TonePitch.C.transpose(-7));
    Assert.assertEquals(TonePitch.E, TonePitch.C.transpose(-8));
    Assert.assertEquals(TonePitch.DIS, TonePitch.C.transpose(-9));
    Assert.assertEquals(TonePitch.D, TonePitch.C.transpose(-10));
    Assert.assertEquals(TonePitch.CIS, TonePitch.C.transpose(-11));
  }

}
