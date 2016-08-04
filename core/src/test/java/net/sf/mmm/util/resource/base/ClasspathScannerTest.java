/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.AfterClass;
import org.junit.Test;

import net.sf.mmm.test.ExceptionHelper;
import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.resource.api.BrowsableResource;
import net.sf.mmm.util.resource.api.ClasspathScanner;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.ResourceNotAvailableException;
import net.sf.mmm.util.resource.api.ResourceNotWritableException;
import net.sf.mmm.util.resource.api.ResourcePathNode;
import net.sf.mmm.util.resource.api.ResourceUriUndefinedException;
import net.sf.mmm.util.resource.impl.ClasspathScannerImpl;

/**
 * Test-case for {@link ClasspathScanner}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.0.0
 */
public class ClasspathScannerTest extends Assertions {

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
    assertThat(packageResource.getChildResources()).contains(scanner.getClasspathResource(clazz.getName().replace('.', '/') + ".class"));

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
    assertThat(fileList.size()).isBetween(Integer.valueOf(600), Integer.valueOf(1200));
    assertThat(fileList).contains(scanner.getClasspathResource(CLASSPATH_HYPHENATION_XML), scanner.getClasspathResource(CLASSPATH_NLS_BUNDLES));
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

  /** Test of {@link ClasspathScanner#getClasspathResourceClasses(Filter, Filter)}. */
  @Test
  public void testGetClasspathResourceClasses() {

    // given
    ClasspathScanner scanner = getClasspathScanner();
    Filter<String> classnameFilter = new Filter<String>() {

      @Override
      public boolean accept(String value) {

        return value.startsWith(ClasspathScanner.class.getPackage().getName() + ".Resource");
      }
    };
    Filter<Class<?>> classFilter = new Filter<Class<?>>() {

      @Override
      public boolean accept(Class<?> value) {

        return Exception.class.isAssignableFrom(value);
      }
    };
    // when
    Iterable<Class<?>> classes = scanner.getClasspathResourceClasses(classnameFilter, classFilter);
    // then
    assertThat(classes).containsExactlyInAnyOrder(ResourceNotAvailableException.class, ResourceNotWritableException.class, ResourceUriUndefinedException.class);
  }

  /** Test of {@link ClasspathScanner#getClasspathResourceFiles(Filter)}. */
  @Test
  public void testGetClasspathResourceFiles() {

    // given
    ClasspathScanner scanner = getClasspathScanner();
    final Class<?>[] classes = new Class<?>[] { ClasspathScannerTest.class, ClasspathResourceTest.class };
    final Set<String> classNames = new HashSet<>(classes.length);
    for (Class<?> type : classes) {
      classNames.add(type.getName().replace('.', '/') + ".class");
    }
    Filter<BrowsableResource> filter = new Filter<BrowsableResource>() {

      @Override
      public boolean accept(BrowsableResource value) {

        return classNames.contains(value.getPath());
      }
    };
    // when
    Iterable<? extends BrowsableResource> files = scanner.getClasspathResourceFiles(filter);
    // then
    Set<Class<?>> classSet = new HashSet<>(classes.length);
    for (BrowsableResource resource : files) {
      assertThat(resource.isData()).isTrue();
      classSet.add(scanner.loadClass(resource));
    }
    assertThat(classSet).containsExactlyInAnyOrder(classes);
  }

  /** Test of {@link ClasspathScanner#getQualifiedName(DataResource)}. */
  @Test
  public void testGetQualifiedName() {

    // given
    ClasspathScanner scanner = getClasspathScanner();
    Class<ClasspathScanner> javaClass = ClasspathScanner.class;
    String packageName = javaClass.getPackage().getName();
    String qualifiedName = javaClass.getName();
    // when
    BrowsableResource packageResource = scanner.getClasspathResource(packageName.replace('.', '/'));
    DataResource classResource = scanner.getClasspathResource(qualifiedName.replace('.', '/') + ".class");
    BrowsableResource fileResource = scanner.getClasspathResource(CLASSPATH_HYPHENATION_XML);
    // then
    assertThat(scanner.getQualifiedName(packageResource)).isEqualTo(packageName);
    assertThat(scanner.getQualifiedName(classResource)).isEqualTo(qualifiedName);
    assertThat(scanner.getQualifiedName(fileResource)).isNull();
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
