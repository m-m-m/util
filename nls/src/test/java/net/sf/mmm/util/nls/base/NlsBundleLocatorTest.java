/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import net.sf.mmm.util.lang.NlsBundleUtilLangRoot;
import net.sf.mmm.util.nls.NlsBundleUtilCoreTestRoot;
import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleTestRoot;

/**
 * Test of {@link NlsBundleLocator}.
 */
public abstract class NlsBundleLocatorTest extends Assertions {

  /**
   * Test of {@link NlsBundleLocator#findBundles()}.
   */
  @SuppressWarnings("unchecked")
  @Test
  public void testFindBundles() {

    // given
    Class<? extends NlsBundle>[] expectedBundles = new Class[] { NlsBundleUtilLangRoot.class, NlsBundleTestRoot.class, NlsBundleUtilCoreTestRoot.class };

    // when
    Iterable<Class<? extends NlsBundle>> bundles = getBundleLocator().findBundles();

    // then
    assertThat(bundles).containsExactlyInAnyOrder(expectedBundles);
  }

  /**
   * @return the {@link NlsBundleLocator} to test.
   */
  protected abstract NlsBundleLocator getBundleLocator();

}
