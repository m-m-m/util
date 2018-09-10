/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

import org.junit.Test;

import junit.framework.Assert;
import net.sf.mmm.util.NlsBundleUtilCoreRoot;
import net.sf.mmm.util.file.base.FileUtilImpl;
import net.sf.mmm.util.io.api.EncodingUtil;
import net.sf.mmm.util.io.base.StreamUtilImpl;

/**
 * This is the test-case for {@link ResourceBundleSynchronizer}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ResourceBundleSynchronizerTest {

  /** The path where properties are generated for the test. */
  private static final String TARGET_TEST = "target/test/bundles";

  public void checkValue(ResourceBundle bundle, Properties properties, String key, String locale) {

    String bundleValue = bundle.getString(key);
    String propertiesValue = properties.getProperty(key);
    assertNotNull(bundleValue);
    if (locale != null) {
      bundleValue = "TODO(" + locale + "):" + bundleValue;
    }
    assertEquals(bundleValue, propertiesValue);
  }

  public void checkBundle(ResourceBundle bundle, String resultFileBase, String locale) throws Exception {

    String suffix = ".properties";
    if (locale != null) {
      suffix = "_" + locale + suffix;
    }
    File bundleFile = new File(resultFileBase + suffix);
    assertTrue(bundleFile.exists());
    InputStream inputStream = new FileInputStream(bundleFile);
    Properties properties = StreamUtilImpl.getInstance().loadProperties(inputStream);

    for (Object key : properties.keySet()) {
      checkValue(bundle, properties, (String) key, locale);
    }

    Enumeration<String> keyEnum = bundle.getKeys();
    while (keyEnum.hasMoreElements()) {
      String key = keyEnum.nextElement();
      checkValue(bundle, properties, key, locale);
    }
  }

  @Test
  public void testSynchronizerResourceBundle() throws Exception {

    ResourceBundleSynchronizer synchronizer = new ResourceBundleSynchronizer();
    String targetPath = TARGET_TEST;
    FileUtilImpl.getInstance().deleteRecursive(new File(targetPath));
    Class<?> bundleClass = MyResourceBundle.class;
    String encoding = EncodingUtil.ENCODING_ISO_8859_1;
    String locale1 = "de";
    int exitCode = synchronizer.run(new String[] { ResourceBundleSynchronizer.OPTION_PATH, targetPath, ResourceBundleSynchronizer.OPTION_ENCODING, encoding,
        ResourceBundleSynchronizer.OPTION_BUNDLE_CLASS, bundleClass.getName(), ResourceBundleSynchronizer.OPTION_LOCALE, locale1 });
    Assert.assertEquals(0, exitCode);
    MyResourceBundle bundle = new MyResourceBundle();
    String resultFileBase = targetPath + "/" + bundleClass.getName().replace('.', '/');

    // checkBundle(bundle, resultFileBase, null);
    checkBundle(bundle, resultFileBase, locale1);
  }

  @Test
  public void testSynchronizerNlsBundle() throws Exception {

    NlsBundleHelper bundleHelper = NlsBundleHelper.getInstance();
    ResourceBundleSynchronizer synchronizer = new ResourceBundleSynchronizer();
    String targetPath = TARGET_TEST;
    FileUtilImpl.getInstance().deleteRecursive(new File(targetPath));
    Class<NlsBundleUtilCoreRoot> bundleClass = NlsBundleUtilCoreRoot.class;
    String encoding = EncodingUtil.ENCODING_ISO_8859_1;
    String locale1 = "de";
    int exitCode = synchronizer.run(new String[] { ResourceBundleSynchronizer.OPTION_PATH, targetPath, ResourceBundleSynchronizer.OPTION_ENCODING, encoding,
        ResourceBundleSynchronizer.OPTION_BUNDLE_CLASS, bundleClass.getName(), ResourceBundleSynchronizer.OPTION_LOCALE, locale1 });
    Assert.assertEquals(0, exitCode);

    ResourceBundle bundle = bundleHelper.toResourceBundle(bundleClass);
    String resultFileBase = targetPath + "/" + bundleHelper.getQualifiedLocation(bundleClass).replace('.', '/');

    // checkBundle(bundle, resultFileBase, null);
    checkBundle(bundle, resultFileBase, locale1);
  }

}
