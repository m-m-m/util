/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.impl;

import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;
import net.sf.mmm.util.resource.api.BrowsableResource;
import net.sf.mmm.util.resource.api.ClasspathScanner;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Test-case for {@link ClasspathScannerImpl}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.0.0
 */
public class ClasspathScannerImplTest extends Assertions {

  /**
   * TODO: javadoc
   *
   * @return
   */
  protected ClasspathScanner getClasspathScanner() {

    ClasspathScannerImpl scanner = new ClasspathScannerImpl();
    scanner.setReflectionUtil(ReflectionUtilImpl.getInstance());
    scanner.initialize();
    return scanner;
  }

  @Test
  public void test() {

    ClasspathScanner scanner = getClasspathScanner();
    BrowsableResource resource = scanner.getClasspathResource();
    dump(resource);
  }

  private void dump(BrowsableResource resource) {

    for (BrowsableResource child : resource.getChildResources()) {
      System.out.println(child.getPath());
      dump(child);
    }
  }

}
