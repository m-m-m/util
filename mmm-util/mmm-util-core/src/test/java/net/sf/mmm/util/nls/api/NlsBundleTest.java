/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;
import net.sf.mmm.util.nls.base.AbstractNlsMessage;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link NlsMessage} and {@link NlsMessageFactory}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class NlsBundleTest {

  protected NlsBundleFactory getBundleFactory() {

    return NlsAccess.getBundleFactory();
  }

  /**
   * This method tests {@link NlsBundleFactory#createBundle(Class)} for a regular {@link NlsBundle}.
   */
  @Test
  public void testNlsBundle() {

    NlsBundleUtilCoreRoot bundle = getBundleFactory().createBundle(NlsBundleUtilCoreRoot.class);

    // test without arguments
    NlsMessage infoAnd = bundle.infoAnd();
    verifyInfoAnd(infoAnd);

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

    NlsBundleUtilCoreTest bundle = getBundleFactory().createBundle(NlsBundleUtilCoreTest.class);

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

  // This is a hack only for testing. Anti-pattern should NOT be copied.
  @NlsBundleLocation(bundleName = "NlsBundleUtilCore", bundlePackage = "net.sf.mmm.util")
  public interface NlsBundleUtilCoreTest extends NlsBundleUtilCoreRoot, NlsBundleWithLookup {

  }
}
