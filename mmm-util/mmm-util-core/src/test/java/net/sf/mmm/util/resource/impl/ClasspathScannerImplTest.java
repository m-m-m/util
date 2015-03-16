/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.mmm.test.ExceptionHelper;
import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;
import net.sf.mmm.util.resource.api.BrowsableResource;
import net.sf.mmm.util.resource.api.ClasspathScanner;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.ResourceNotAvailableException;
import net.sf.mmm.util.resource.api.ResourcePathNode;
import net.sf.mmm.util.resource.base.ClasspathResource;

import org.assertj.core.api.Assertions;
import org.junit.AfterClass;
import org.junit.Test;

/**
 * Test-case for {@link ClasspathScannerImpl}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.0.0
 */
public class ClasspathScannerImplTest extends Assertions {

  private static final String CLASSPATH_NLS_BUNDLES = "META-INF/net.sf.mmm/nls-bundles";

  private static final String CLASSPATH_HYPHENATION_XML = "net/sf/mmm/util/text/hyphenation.xml";

  private static ClasspathScanner classpathScanner;

  /**
   * @return the {@link ClasspathScanner} to test.
   */
  protected final ClasspathScanner getClasspathScanner() {

    if (classpathScanner == null) {
      classpathScanner = createClasspathScanner();
    }
    return classpathScanner;
  }

  /**
   * @return the created {@link ClasspathScanner} implementation.
   */
  protected ClasspathScanner createClasspathScanner() {

    ClasspathScannerImpl impl = new ClasspathScannerImpl();
    impl.setReflectionUtil(ReflectionUtilImpl.getInstance());
    impl.initialize();
    return impl;
  }

  /** Test of {@link ClasspathScanner#getClasspathResource()}. */
  @Test
  public void testRoot() {

    ClasspathScanner scanner = getClasspathScanner();
    BrowsableResource rootResource = scanner.getClasspathResource();
    assertThat(rootResource).isNotNull();
    assertThat(rootResource.isFolder()).isTrue();
    assertThat(rootResource.isData()).isFalse();
    assertThat(rootResource.isAvailable()).isFalse();
    assertThat(rootResource.getLastModificationDate()).isNull();
    assertThat(rootResource.isModifiedSince(new Date())).isNull();
    assertThat(rootResource.getChildResources()).isNotEmpty();
    try {
      rootResource.getUrl();
      ExceptionHelper.failExceptionExpected();
    } catch (ResourceNotAvailableException e) {
      assertThat(e).hasMessageContaining("resource \"\" is not available");
    }
    Filter<BrowsableResource> filter = new Filter<BrowsableResource>() {

      @Override
      public boolean accept(BrowsableResource value) {

        return "net".equals(value.getName());
      }
    };
    assertThat(rootResource.getChildResources(filter)).hasSize(1);
    // dump(rootResource);
  }

  /** Test of {@link ClasspathScanner#getClasspathResource(String)}. */
  @Test
  public void testGetByName() {

    checkFile(CLASSPATH_HYPHENATION_XML);
    checkFile(CLASSPATH_NLS_BUNDLES);
    ClasspathScanner scanner = getClasspathScanner();
    assertThat(scanner.getClasspathResource("")).isSameAs(scanner.getClasspathResource());
  }

  /**
   * @param classpathFile the classpath to an existing resource file.
   */
  protected void checkFile(String classpathFile) {

    ClasspathScanner scanner = getClasspathScanner();
    BrowsableResource fileResource = scanner.getClasspathResource(classpathFile);
    assertThat(fileResource).isNotNull();
    assertThat(fileResource.isFolder()).isFalse();
    assertThat(fileResource.isData()).isTrue();
    assertThat(fileResource.getName()).isEqualTo(ResourcePathNode.create(classpathFile).getName());
    assertThat(fileResource.isAvailable()).isTrue();
    assertThat(fileResource.getUri()).isEqualTo(ClasspathResource.SCHEME_PREFIX + classpathFile);
    assertThat(fileResource.getChildResources()).isEmpty();
  }

  /** Test of {@link ClasspathScanner#getClasspathResource(Package)}. */
  @Test
  public void testGetByPackage() {

    checkClass(ClasspathScanner.class);
    checkClass(ClasspathScannerImpl.class);
  }

  /**
   * @param clazz the {@link Class} to test via {@link ClasspathScanner}.
   */
  protected void checkClass(Class<?> clazz) {

    ClasspathScanner scanner = getClasspathScanner();
    BrowsableResource packageResource = scanner.getClasspathResource(clazz.getPackage());
    assertThat(packageResource).isNotNull();
    assertThat(packageResource.isFolder()).isTrue();
    assertThat(packageResource.isData()).isFalse();
    assertThat(packageResource.getPath()).isEqualTo(clazz.getPackage().getName().replace('.', '/'));
    assertThat(packageResource.isAvailable()).isFalse();
    assertThat(packageResource.getChildResources()).contains(
        scanner.getClasspathResource(clazz.getName().replace('.', '/') + ".class"));

  }

  /** Test of {@link ClasspathScanner#getClasspathResourceFiles()}. */
  @Test
  public void testClasspathScanner() {

    // given
    ClasspathScanner scanner = getClasspathScanner();
    // when
    Iterable<? extends BrowsableResource> files = scanner.getClasspathResourceFiles();
    // then
    assertThat(files).isInstanceOf(List.class);
    List<? extends BrowsableResource> fileList = (List<? extends BrowsableResource>) files;
    assertThat(fileList.size()).isBetween(Integer.valueOf(1000), Integer.valueOf(2000));
    assertThat(fileList).contains(scanner.getClasspathResource(CLASSPATH_HYPHENATION_XML),
        scanner.getClasspathResource(CLASSPATH_NLS_BUNDLES));
  }

  /** Test of {@link ClasspathScanner#getClasspathResource()} with navigation to non-existent resource. */
  @Test
  public void testNavigateNonExistentRelative() {

    // given
    ClasspathScanner scanner = getClasspathScanner();
    // when
    BrowsableResource utilResource = scanner.getClasspathResource("net/sf/mmm/util/");
    DataResource nonExistentResource = utilResource.navigate("util/non-existent/path");
    // then
    assertThat(nonExistentResource).isNotNull();
    assertThat(nonExistentResource).isInstanceOf(ClasspathResource.class);
    assertThat(nonExistentResource.getPath()).isEqualTo("net/sf/mmm/util/non-existent/path");
  }

  /**
   * @return {@link ClasspathScanner#getClasspathResourceFiles()} as {@link List}.
   */
  protected List<? extends BrowsableResource> getFileResourceList() {

    Iterable<? extends BrowsableResource> iterable = getClasspathScanner().getClasspathResourceFiles();
    if (iterable instanceof List) {
      return (List<? extends BrowsableResource>) iterable;
    }
    List<BrowsableResource> result = new ArrayList<>();
    for (BrowsableResource resource : iterable) {
      result.add(resource);
    }
    return result;
  }

  /** Test of {@link ClasspathScanner#clearCaches()}. */
  @Test
  public void testClearCaches() {

    // given
    ClasspathScanner scanner = getClasspathScanner();
    int classpathFileSize = getFileResourceList().size();
    BrowsableResource rootResource = scanner.getClasspathResource();
    // when
    scanner.clearCaches();
    // then
    BrowsableResource newRootResource = scanner.getClasspathResource();
    assertThat(newRootResource).isNotNull();
    assertThat(newRootResource).isNotSameAs(rootResource);
    assertThat(newRootResource).isEqualTo(rootResource);
    assertThat(getFileResourceList().size()).isEqualTo(classpathFileSize);
  }

  /** Test of {@link ClasspathScanner#getClasspathResource()} with navigation to non-existent resource. */
  @Test
  public void testNavigateNonExistentAbsolute() {

    // given
    ClasspathScanner scanner = getClasspathScanner();
    // when
    BrowsableResource rootResource = scanner.getClasspathResource();
    DataResource nonExistentResource = rootResource.navigate("/util/non-existent/path");
    // then
    assertThat(nonExistentResource).isNotNull();
    assertThat(nonExistentResource).isInstanceOf(ClasspathResource.class);
    assertThat(nonExistentResource.getPath()).isEqualTo("util/non-existent/path");
  }

  /**
   * @param resource the resource to dump out on console recursively for debugging.
   */
  protected void dump(BrowsableResource resource) {

    for (BrowsableResource child : resource.getChildResources()) {
      System.out.println(child.getPath());
      dump(child);
    }
  }

  /** Cleans up allocated resources. */
  @AfterClass
  public static void cleanup() {

    classpathScanner = null;
  }

}
