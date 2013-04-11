/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import net.sf.mmm.util.lang.api.BasicUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link BasicUtilImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class BasicUtilTest extends Assert {

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

    assertTrue(getBasicUtil().isEqual(null, null));
    assertFalse(getBasicUtil().isEqual("", null));
    assertFalse(getBasicUtil().isEqual(null, Boolean.TRUE));
    assertTrue(getBasicUtil().isEqual(Boolean.TRUE, Boolean.TRUE));
    String string = "test42";
    String newString = new String(string);
    assertNotSame(string, newString);
    assertTrue(getBasicUtil().isEqual(string, newString));
  }

  /**
   * Test of {@link BasicUtil#isEqual(Object[], Object[])}.
   */
  @Test
  public void testIsEqualArray() {

    assertTrue(getBasicUtil().isEqual((Object[]) null, (Object[]) null));
    assertTrue(getBasicUtil().isEqual(new Object[0], new Object[0]));
    assertFalse(getBasicUtil().isEqual((Object[]) null, new Object[0]));
    assertFalse(getBasicUtil().isEqual(new Object[0], (Object[]) null));
    String[] array1 = new String[] { "foo", "bar", "42" };
    Object[] array2 = new Object[array1.length];
    for (int i = 0; i < array1.length; i++) {
      if ((i % 2) == 0) {
        array2[i] = array1[i];
      } else {
        array2[i] = new String(array1[i]);
      }
    }
    assertTrue(getBasicUtil().isEqual(array1, array2));
  }

  /**
   * Test of {@link BasicUtil#isDeepEqual(Object, Object)}.
   */
  @Test
  public void testIsDeepEqual() {

    assertTrue(getBasicUtil().isDeepEqual(null, null));
    assertFalse(getBasicUtil().isDeepEqual("", null));
    assertFalse(getBasicUtil().isDeepEqual(null, Boolean.TRUE));
    int[] intArray1 = new int[] { 42, 1, 7, -42 };
    int[] intArray2 = new int[intArray1.length];
    for (int i = 0; i < intArray1.length; i++) {
      intArray2[i] = intArray1[i];
    }
    Object[] subArray1 = new Object[] { intArray1, intArray2 };
    int[][] subArray2 = new int[][] { intArray2, intArray1 };
    Object[] array1 = new Object[] { intArray1, null, subArray1 };
    Object[] array2 = new Object[] { intArray2, null, subArray2 };
    assertTrue(getBasicUtil().isDeepEqual(array1, array2));

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
      assertTrue(getBasicUtil().isDeepEqual(array1, array2));
    }
    {
      // short arrays
      short[] array1 = new short[len];
      short[] array2 = new short[len];
      for (short i = 0; i < len; i++) {
        array1[i] = i;
        array2[i] = i;
      }
      assertTrue(getBasicUtil().isDeepEqual(array1, array2));
    }
    {
      // int arrays
      int[] array1 = new int[len];
      int[] array2 = new int[len];
      for (int i = 0; i < len; i++) {
        array1[i] = i;
        array2[i] = i;
      }
      assertTrue(getBasicUtil().isDeepEqual(array1, array2));
    }
    {
      // long arrays
      long[] array1 = new long[len];
      long[] array2 = new long[len];
      for (int i = 0; i < len; i++) {
        array1[i] = i;
        array2[i] = i;
      }
      assertTrue(getBasicUtil().isDeepEqual(array1, array2));
    }
    {
      // float arrays
      float[] array1 = new float[len];
      float[] array2 = new float[len];
      for (int i = 0; i < len; i++) {
        array1[i] = i;
        array2[i] = i;
      }
      assertTrue(getBasicUtil().isDeepEqual(array1, array2));
    }
    {
      // double arrays
      double[] array1 = new double[len];
      double[] array2 = new double[len];
      for (int i = 0; i < len; i++) {
        array1[i] = i;
        array2[i] = i;
      }
      assertTrue(getBasicUtil().isDeepEqual(array1, array2));
    }
    {
      // char arrays
      char[] array1 = new char[len];
      char[] array2 = new char[len];
      for (char i = 0; i < len; i++) {
        array1[i] = i;
        array2[i] = i;
      }
      assertTrue(getBasicUtil().isDeepEqual(array1, array2));
    }
    {
      // boolean arrays
      boolean[] array1 = new boolean[len];
      boolean[] array2 = new boolean[len];
      for (int i = 0; i < len; i++) {
        array1[i] = ((i % 3) == 0);
        array2[i] = array1[i];
      }
      assertTrue(getBasicUtil().isDeepEqual(array1, array2));
    }
  }

}
