/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import junit.framework.Assert;
import net.sf.mmm.util.NlsBundleUtilCoreOld;
import net.sf.mmm.util.file.base.FileUtilImpl;
import net.sf.mmm.util.io.api.EncodingUtil;
import net.sf.mmm.util.io.base.StreamUtilImpl;

import org.junit.Test;

/**
 * This is the test-case for {@link ResourceBundleSynchronizer}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ResourceBundleSynchronizerTest {

  public void checkValue(AbstractResourceBundle bundle, Properties properties, String key, String locale) {

    String bundleValue = bundle.getString(key);
    String propertiesValue = properties.getProperty(key);
    assertNotNull(bundleValue);
    if (locale != null) {
      bundleValue = "TODO(" + locale + "):" + bundleValue;
    }
    assertEquals(bundleValue, propertiesValue);
  }

  public void checkBundle(AbstractResourceBundle bundle, String resultFileBase, String locale) throws Exception {

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

    for (String key : bundle.keySet()) {
      checkValue(bundle, properties, key, locale);
    }
  }

  @Test
  public void testSynchronizer() throws Exception {

    ResourceBundleSynchronizer synchronizer = new ResourceBundleSynchronizer();
    String targetPath = "target/test";
    FileUtilImpl.getInstance().deleteRecursive(new File(targetPath));
    Class<? extends AbstractResourceBundle> bundleClass = NlsBundleUtilCoreOld.class;
    String encoding = EncodingUtil.ENCODING_ISO_8859_1;
    String locale1 = "de";
    int exitCode = synchronizer.run(new String[] { ResourceBundleSynchronizer.OPTION_PATH, targetPath,
        ResourceBundleSynchronizer.OPTION_ENCODING, encoding, ResourceBundleSynchronizer.OPTION_BUNDLE_CLASS,
        bundleClass.getName(), ResourceBundleSynchronizer.OPTION_LOCALE, locale1 });
    Assert.assertEquals(0, exitCode);
    NlsBundleUtilCoreOld bundle = new NlsBundleUtilCoreOld();
    String resultFileBase = targetPath + "/" + bundleClass.getName().replace('.', '/');

    checkBundle(bundle, resultFileBase, null);
    checkBundle(bundle, resultFileBase, locale1);
  }
}
