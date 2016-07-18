/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;
import net.sf.mmm.util.exception.NlsBundleUtilExceptionRoot;
import net.sf.mmm.util.nls.NlsBundleUtilCoreTestRoot;
import net.sf.mmm.util.nls.base.AbstractNlsMessage;

/**
 * This is the test-case for {@link NlsMessage} and {@link NlsMessageFactory}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class NlsBundleTest extends Assertions {

  protected NlsBundleFactory getBundleFactory() {

    return NlsAccess.getBundleFactory();
  }

  /**
   * This method tests {@link NlsBundleFactory#createBundle(Class)} for a regular {@link NlsBundle}.
   */
  @Test
  public void testNlsBundleObjectMethods() {

    // given
    NlsBundleUtilCoreRoot bundle = getBundleFactory().createBundle(NlsBundleUtilCoreRoot.class);

    // then
    assertThat(bundle.toString()).isEqualTo("net.sf.mmm.util.NlsBundleUtilCore");
    assertThat(bundle.equals(null)).isFalse();
    assertThat(bundle.equals(bundle)).isTrue();
    assertThat(bundle.hashCode()).isNotNull();
  }

  /**
   * This method tests {@link NlsBundleFactory#createBundle(Class)} for a regular {@link NlsBundle}.
   */
  @Test
  public void testNlsBundleInfo() {

    NlsBundleUtilCoreRoot bundle = getBundleFactory().createBundle(NlsBundleUtilCoreRoot.class);

    // test without arguments
    NlsMessage infoAnd = bundle.infoAnd();
    verifyInfoAnd(infoAnd);
  }

  /**
   * This method tests {@link NlsBundleFactory#createBundle(Class)} for a regular {@link NlsBundle}.
   */
  @Test
  public void testNlsBundleError() {

    NlsBundleUtilExceptionRoot bundle = getBundleFactory().createBundle(NlsBundleUtilExceptionRoot.class);
    // test with arguments
    String object = "myObject";
    String key = "myKey";
    NlsMessage errorObjectNotFound = bundle.errorObjectNotFound(object, key);
    verifyErrorObjectNotFound(object, key, errorObjectNotFound);
  }

  /**
   * This method tests {@link NlsBundleFactory#createBundle(Class)} for a {@link NlsBundleWithLookup}.
   */
  @Test
  public void testNlsBundleWithLookup() {

    NlsBundleUtilCoreTestRoot bundle = getBundleFactory().createBundle(NlsBundleUtilCoreTestRoot.class);

    // test without arguments
    NlsMessage infoAnd = bundle.getMessage("infoAnd", null);
    verifyInfoAnd(infoAnd);

    // test with arguments
    String object = "myObject";
    String key = "myKey";
    Map<String, Object> arguments = new HashMap<String, Object>();
    arguments.put(NlsObject.KEY_OBJECT, object);
    arguments.put(NlsObject.KEY_KEY, key);
    NlsMessage errorObjectNotFound = bundle.getMessage("errorObjectNotFound", arguments);
    verifyErrorObjectNotFound(object, key, errorObjectNotFound);
  }

  private void verifyErrorObjectNotFound(String object, String key, NlsMessage errorObjectNotFound) {

    Assert.assertNotNull(errorObjectNotFound);
    Assert.assertEquals("Could NOT find object \"" + object + "\" for \"" + key + "\"!",
        errorObjectNotFound.getLocalizedMessage(AbstractNlsMessage.LOCALE_ROOT));
    Assert.assertEquals("Object \"" + object + "\" zu \"" + key + "\" konnte nicht gefunden werden!",
        errorObjectNotFound.getLocalizedMessage(Locale.GERMAN));
  }

  private void verifyInfoAnd(NlsMessage infoAnd) {

    Assert.assertNotNull(infoAnd);
    Assert.assertEquals("and", infoAnd.getInternationalizedMessage());
    Assert.assertEquals("and", infoAnd.getLocalizedMessage(AbstractNlsMessage.LOCALE_ROOT));
    Assert.assertEquals("und", infoAnd.getLocalizedMessage(Locale.GERMAN));
  }
}
