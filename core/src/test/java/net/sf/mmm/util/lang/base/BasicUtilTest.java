/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import net.sf.mmm.util.lang.api.BasicUtil;

/**
 * This is the test-case for {@link BasicUtilImpl}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class BasicUtilTest extends Assertions {

  /**
   * @return the {@link BasicUtil} to test.
   */
  public BasicUtil getBasicUtil() {

    return BasicUtilImpl.getInstance();
  }

  /**
   * Test of {@link BasicUtil#isEqual(Object, Object)}.
   */
  @Test
  public void testIsEqual() {

    assertThat(getBasicUtil().isEqual(null, null)).isTrue();
    assertThat(getBasicUtil().isEqual("", null)).isFalse();
    assertThat(getBasicUtil().isEqual(null, Boolean.TRUE)).isFalse();
    assertThat(getBasicUtil().isEqual(Boolean.TRUE, Boolean.TRUE)).isTrue();
    String string = "test42";
    String newString = new String(string);
    assertThat(newString).isNotSameAs(string);
    assertThat(getBasicUtil().isEqual(string, newString)).isTrue();
  }

  /**
   * Test of {@link BasicUtil#isEqual(Object[], Object[])}.
   */
  @Test
  public void testIsEqualArray() {

    assertThat(getBasicUtil().isEqual((Object[]) null, (Object[]) null)).isTrue();
    assertThat(getBasicUtil().isEqual(new Object[0], new Object[0])).isTrue();
    assertThat(getBasicUtil().isEqual((Object[]) null, new Object[0])).isFalse();
    assertThat(getBasicUtil().isEqual(new Object[0], (Object[]) null)).isFalse();
    String[] array1 = new String[] { "foo", "bar", "42" };
    Object[] array2 = new Object[array1.length];
    for (int i = 0; i < array1.length; i++) {
      if ((i % 2) == 0) {
        array2[i] = array1[i];
      } else {
        array2[i] = new String(array1[i]);
      }
    }
    assertThat(getBasicUtil().isEqual(array1, array2)).isTrue();
  }

  /**
   * Test of {@link BasicUtil#isDeepEqual(Object, Object)}.
   */
  @Test
  public void testIsDeepEqual() {

    assertThat(getBasicUtil().isDeepEqual(null, null)).isTrue();
    assertThat(getBasicUtil().isDeepEqual("", null)).isFalse();
    assertThat(getBasicUtil().isDeepEqual(null, Boolean.TRUE)).isFalse();
    int[] intArray1 = new int[] { 42, 1, 7, -42 };
    int[] intArray2 = new int[intArray1.length];
    for (int i = 0; i < intArray1.length; i++) {
      intArray2[i] = intArray1[i];
    }
    Object[] subArray1 = new Object[] { intArray1, intArray2 };
    int[][] subArray2 = new int[][] { intArray2, intArray1 };
    Object[] array1 = new Object[] { intArray1, null, subArray1 };
    Object[] array2 = new Object[] { intArray2, null, subArray2 };
    assertThat(getBasicUtil().isDeepEqual(array1, array2)).isTrue();

  }

  /**
   * Test of {@link BasicUtil#isDeepEqual(Object, Object)} for primitive arrays.
   */
  @Test
  public void testIsDeepEqualPrimitiveArrays() {

    byte len = 42;
    {
      // byte arrays
      byte[] array1 = new byte[len];
      byte[] array2 = new byte[len];
      for (byte i = 0; i < len; i++) {
        array1[i] = i;
        array2[i] = i;
      }
      assertThat(getBasicUtil().isDeepEqual(array1, array2)).isTrue();
    }
    {
      // short arrays
      short[] array1 = new short[len];
      short[] array2 = new short[len];
      for (short i = 0; i < len; i++) {
        array1[i] = i;
        array2[i] = i;
      }
      assertThat(getBasicUtil().isDeepEqual(array1, array2)).isTrue();
    }
    {
      // int arrays
      int[] array1 = new int[len];
      int[] array2 = new int[len];
      for (int i = 0; i < len; i++) {
        array1[i] = i;
        array2[i] = i;
      }
      assertThat(getBasicUtil().isDeepEqual(array1, array2)).isTrue();
    }
    {
      // long arrays
      long[] array1 = new long[len];
      long[] array2 = new long[len];
      for (int i = 0; i < len; i++) {
        array1[i] = i;
        array2[i] = i;
      }
      assertThat(getBasicUtil().isDeepEqual(array1, array2)).isTrue();
    }
    {
      // float arrays
      float[] array1 = new float[len];
      float[] array2 = new float[len];
      for (int i = 0; i < len; i++) {
        array1[i] = i;
        array2[i] = i;
      }
      assertThat(getBasicUtil().isDeepEqual(array1, array2)).isTrue();
    }
    {
      // double arrays
      double[] array1 = new double[len];
      double[] array2 = new double[len];
      for (int i = 0; i < len; i++) {
        array1[i] = i;
        array2[i] = i;
      }
      assertThat(getBasicUtil().isDeepEqual(array1, array2)).isTrue();
    }
    {
      // char arrays
      char[] array1 = new char[len];
      char[] array2 = new char[len];
      for (char i = 0; i < len; i++) {
        array1[i] = i;
        array2[i] = i;
      }
      assertThat(getBasicUtil().isDeepEqual(array1, array2)).isTrue();
    }
    {
      // boolean arrays
      boolean[] array1 = new boolean[len];
      boolean[] array2 = new boolean[len];
      for (int i = 0; i < len; i++) {
        array1[i] = ((i % 3) == 0);
        array2[i] = array1[i];
      }
      assertThat(getBasicUtil().isDeepEqual(array1, array2)).isTrue();
    }
  }

}
