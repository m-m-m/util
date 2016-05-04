/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.api;

import org.junit.Assert;
import org.junit.Test;

import net.sf.mmm.util.reflect.api.ClassNameTest.Inner1.Inner2;
import net.sf.mmm.util.reflect.api.ClassNameTest.Inner1.Inner2.Inner3;

/**
 * This is the test-case for {@link ClassName}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ClassNameTest extends Assert {

  /**
   * Test instantiation of {@link ClassName} from {@link String}s and testing all custom methods.
   */
  @Test
  public void testByStrings() {

    String simpleName = "Foo";
    ClassName className = new ClassName("", simpleName);
    assertSame(simpleName, className.getSimpleName());
    assertEquals(simpleName, className.getName());
    assertEquals(simpleName, className.getCanonicalName());
    assertEquals(simpleName, className.toString());
    assertEquals("", className.getPackageName());
    assertNull(className.getEnclosingClass());
    String pkg = "org.bar";
    className = new ClassName(pkg, simpleName);
    assertSame(simpleName, className.getSimpleName());
    String qualifiedName = pkg + "." + simpleName;
    assertEquals(qualifiedName, className.getName());
    assertEquals(qualifiedName, className.getCanonicalName());
    assertEquals(qualifiedName, className.toString());
    assertEquals(pkg, className.getPackageName());
    assertNull(className.getEnclosingClass());
    String inner = "Inner";
    ClassName innerClass = new ClassName(pkg, inner, className);
    assertSame(inner, innerClass.getSimpleName());
    qualifiedName = pkg + "." + simpleName + "$" + inner;
    assertEquals(qualifiedName, innerClass.getName());
    assertEquals(qualifiedName, innerClass.toString());
    qualifiedName = pkg + "." + simpleName + "." + inner;
    assertEquals(qualifiedName, innerClass.getCanonicalName());
    assertEquals(pkg, innerClass.getPackageName());
    assertSame(className, innerClass.getEnclosingClass());
  }

  /**
   * Test instantiation of {@link ClassName} by {@link Class} and testing all custom methods.
   */
  @Test
  public void testByClass() {

    check(ClassName.class);
    check(Inner1.class);
    check(Inner2.class);
    check(Inner3.class);
  }

  /**
   * Instantiates {@link ClassName} for given {@code type} and assert equals for all name related methods.
   *
   * @param type the {@link Class} to check.
   */
  private void check(Class<?> type) {

    ClassName className = new ClassName(type);
    assertEquals(type.getSimpleName(), className.getSimpleName());
    assertEquals(type.getPackage().getName(), className.getPackageName());
    assertEquals(type.getName(), className.getName());
    assertEquals(type.getCanonicalName(), className.getCanonicalName());
    assertEquals(type.getName(), className.toString());

    ClassName classNameByString = createRecursive(type);
    assertEquals(className, classNameByString);
    assertEquals(className.hashCode(), classNameByString.hashCode());

    ClassName enclosingClass = className.getEnclosingClass();

    ClassName other = new ClassName(className.getPackageName(), className.getSimpleName() + "x", enclosingClass);
    assertFalse(className.equals(other));
    assertFalse(other.equals(className));
    assertFalse(className.hashCode() == other.hashCode());

    if (enclosingClass == null) {
      other = new ClassName(className.getPackageName() + "x", className.getSimpleName(), enclosingClass);
      assertFalse(className.equals(other));
      assertFalse(other.equals(className));
      assertFalse(className.hashCode() == other.hashCode());
    } else {
      other = new ClassName(className.getPackageName(), className.getSimpleName(),
          new ClassName(enclosingClass.getPackageName(), enclosingClass.getSimpleName() + "x",
              enclosingClass.getEnclosingClass()));
      assertFalse(className.equals(other));
      assertFalse(other.equals(className));
      assertFalse(className.hashCode() == other.hashCode());
    }
  }

  /**
   * @param type is the {@link Class}.
   * @return the {@link ClassName} for the given {@code type}.
   */
  private ClassName createRecursive(Class<?> type) {

    Class<?> enclosing = type.getEnclosingClass();
    ClassName enclosingName = null;
    if (enclosing != null) {
      enclosingName = createRecursive(enclosing);
    }
    ClassName result = new ClassName(type.getPackage().getName(), type.getSimpleName(), enclosingName);
    return result;
  }

  /**
   * A dummy class to test.
   */
  public static class Inner1 {

    /**
     * A dummy class to test.
     */
    public class Inner2 {

      /**
       * A dummy class to test.
       */
      public class Inner3 {

        // nothing to do, just a test...
      }
    }
  }

}
