/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.api;

import java.util.regex.Pattern;

import net.sf.mmm.util.lang.api.function.Function;
import net.sf.mmm.util.lang.api.function.VoidFunction;
import net.sf.mmm.util.resource.api.ResourcePath.ResourcePathRootHome;
import net.sf.mmm.util.resource.api.ResourcePath.ResourcePathRootUnc;
import net.sf.mmm.util.resource.api.ResourcePath.ResourcePathRootUrl;
import net.sf.mmm.util.resource.api.ResourcePath.ResourcePathRootWindows;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * This is the test-case for {@link ResourcePath}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.0.0
 */
public class ResourcePathTest extends Assertions {

  /** Test of {@link ResourcePath#ROOT_RELATIVE}. */
  @Test
  public void testRootRelative() {

    assertThat(ResourcePath.ROOT_RELATIVE.toString()).isEqualTo("");
    assertThat(ResourcePath.ROOT_RELATIVE.getName()).isEqualTo("");
    assertThat(ResourcePath.ROOT_RELATIVE.isRoot()).isTrue();
    assertThat(ResourcePath.ROOT_RELATIVE.isAbsolute()).isFalse();
    assertThat(ResourcePath.ROOT_RELATIVE.getParent()).isNull();
    assertThat(ResourcePath.ROOT_RELATIVE.getData()).isNull();
    assertThat(ResourcePath.ROOT_RELATIVE.getRoot()).isSameAs(ResourcePath.ROOT_RELATIVE);
  }

  /** Test of {@link ResourcePath#ROOT_ABSOLUTE}. */
  @Test
  public void testRootAbsolute() {

    assertThat(ResourcePath.ROOT_ABSOLUTE.toString()).isEqualTo("/");
    assertThat(ResourcePath.ROOT_ABSOLUTE.getName()).isEqualTo("/");
    assertThat(ResourcePath.ROOT_ABSOLUTE.isRoot()).isTrue();
    assertThat(ResourcePath.ROOT_ABSOLUTE.isAbsolute()).isTrue();
    assertThat(ResourcePath.ROOT_ABSOLUTE.getParent()).isNull();
    assertThat(ResourcePath.ROOT_ABSOLUTE.getData()).isNull();
    assertThat(ResourcePath.ROOT_ABSOLUTE.getRoot()).isSameAs(ResourcePath.ROOT_ABSOLUTE);
  }

  /** Test of {@link ResourcePath#ROOT_HOME}. */
  @Test
  public void testRootHome() {

    assertThat(ResourcePath.ROOT_HOME.toString()).isEqualTo("~");
    assertThat(ResourcePath.ROOT_HOME.getName()).isEqualTo("~");
    assertThat(ResourcePath.ROOT_HOME.isRoot()).isTrue();
    assertThat(ResourcePath.ROOT_HOME.isAbsolute()).isTrue();
    assertThat(ResourcePath.ROOT_HOME.getParent()).isNull();
    assertThat(ResourcePath.ROOT_HOME.getData()).isNull();
    assertThat(ResourcePath.ROOT_HOME.getRoot()).isSameAs(ResourcePath.ROOT_HOME);
  }

  /** Test of {@link ResourcePath#create(String)} with {@code null}. */
  @Test
  public void testCreateNull() {

    assertThat(ResourcePath.create(null)).isNull();
  }

  /** Test of {@link ResourcePath#create(String)} with empty string. */
  @Test
  public void testCreateEmpty() {

    assertThat(ResourcePath.create("")).isSameAs(ResourcePath.ROOT_RELATIVE);
  }

  /** Test of {@link ResourcePath#create(String)} with simple segments. */
  @Test
  public void testCreateSimple() {

    verifySimplePath("a");
    verifySimplePath("foo");
  }

  /**
   * Verifies {@link ResourcePath#create(String)} with a single segment both absolute and relative.
   *
   * @param segment is the segment. Must not be <code>null</code>, empty, ".", ".." or contain "/" or "\\".
   */
  public void verifySimplePath(String segment) {

    ResourcePath<Void> relativePath = ResourcePath.create(segment);
    assertThat(relativePath.getName()).isEqualTo(segment);
    assertThat(relativePath.toString()).isEqualTo(segment);
    assertThat(relativePath.isAbsolute()).isFalse();
    assertThat(relativePath.getParent()).isSameAs(ResourcePath.ROOT_RELATIVE);
    assertThat(relativePath.getRoot()).isSameAs(relativePath.getParent());

    String segmentAbsoulte = "/" + segment;
    ResourcePath<Void> absolutePath = ResourcePath.create(segmentAbsoulte);
    assertThat(absolutePath.getName()).isEqualTo(segment);
    assertThat(absolutePath.toString()).isEqualTo(segmentAbsoulte);
    assertThat(absolutePath.isAbsolute()).isTrue();
    assertThat(absolutePath.getParent()).isSameAs(ResourcePath.ROOT_ABSOLUTE);
    assertThat(absolutePath.getRoot()).isSameAs(absolutePath.getParent());
  }

  /** Test of {@link ResourcePath#create(String)} with a complex relative path. */
  @Test
  public void testCreateComplexRelative() {

    ResourcePath<Void> path = ResourcePath.create("..\\./..//./a\\../b//c/./../.foo///");
    assertThat(path.isAbsolute()).isFalse();
    assertThat(path.toString()).isEqualTo("../../b/.foo");
    assertThat(path.getName()).isEqualTo(".foo");
    assertThat(path.isRoot()).isFalse();
    ResourcePath<Void> p1 = path.getParent();
    assertThat(p1.getName()).isEqualTo("b");
    ResourcePath<Void> p2 = p1.getParent();
    assertThat(p2.getName()).isEqualTo("..");
    ResourcePath<Void> p3 = p2.getParent();
    assertThat(p3.getName()).isEqualTo("..");
    ResourcePath<Void> p4 = p3.getParent();
    assertThat(p4).isSameAs(ResourcePath.ROOT_RELATIVE);
    assertThat(path.getRoot()).isSameAs(p4);
  }

  /** Test of {@link ResourcePath#create(String)} with a complex relative path. */
  @Test
  public void testCreateComplexAbsolute() {

    ResourcePath<Void> path = ResourcePath.create("/..//./a\\../b//c/./../.foo///");
    assertThat(path.isAbsolute()).isTrue();
    assertThat(path.toString()).isEqualTo("/b/.foo");
    assertThat(path.getName()).isEqualTo(".foo");
    assertThat(path.isRoot()).isFalse();
    ResourcePath<Void> p1 = path.getParent();
    assertThat(p1.getName()).isEqualTo("b");
    ResourcePath<Void> p2 = p1.getParent();
    assertThat(p2).isSameAs(ResourcePath.ROOT_ABSOLUTE);
    assertThat(path.getRoot()).isSameAs(p2);
  }

  /** Test of {@link ResourcePath#create(String)} with an absolute home path. */
  @Test
  public void testCreateComplexHome() {

    ResourcePath<Void> path = ResourcePath.create("~/.ssh/");
    assertThat(path.isAbsolute()).isTrue();
    assertThat(path.toString()).isEqualTo("~/.ssh");
    assertThat(path.getName()).isEqualTo(".ssh");
    assertThat(path.isRoot()).isFalse();
    ResourcePath<Void> root = path.getParent();
    assertThat(root.isRoot()).isTrue();
    assertThat(root).isSameAs(ResourcePath.ROOT_HOME);
    assertThat(root.getName()).isEqualTo("~");
    assertThat(path.getRoot()).isSameAs(root);
  }

  /** Test of {@link ResourcePath#create(String)} with an absolute home path. */
  @Test
  public void testCreateComplexHomeParent() {

    ResourcePath<Void> path = ResourcePath.create("~admin/../.././foo/../..");
    assertThat(path.isAbsolute()).isTrue();
    assertThat(path.toString()).isEqualTo("~admin/../../..");
    assertThat(path.getName()).isEqualTo("..");
    assertThat(path.isRoot()).isFalse();
    ResourcePath<Void> p1 = path.getParent();
    assertThat(p1.getName()).isEqualTo("..");
    assertThat(p1.isRoot()).isFalse();
    ResourcePath<Void> p2 = p1.getParent();
    assertThat(p2.getName()).isEqualTo("..");
    assertThat(p2.isRoot()).isFalse();
    ResourcePath<Void> p3 = p2.getParent();
    assertThat(p3.isRoot()).isTrue();
    assertThat(p3).isInstanceOf(ResourcePathRootHome.class);
    assertThat(p3.getName()).isEqualTo("~admin");
    assertThat(path.getRoot()).isSameAs(p3);
  }

  /** Test of {@link ResourcePath#create(String)} with an absolute UNC path. */
  @Test
  public void testCreateComplexUnc() {

    ResourcePath<Void> path = ResourcePath.create("\\\\fileserver.org\\..\\Share\\.\\Downloads\\");
    assertThat(path.isAbsolute()).isTrue();
    assertThat(path.toString('\\')).isEqualTo("\\\\fileserver.org\\Share\\Downloads");
    assertThat(path.getName()).isEqualTo("Downloads");
    assertThat(path.isRoot()).isFalse();
    assertThat(path.getParent().getName()).isEqualTo("Share");
    ResourcePath<Void> root = path.getParent().getParent();
    assertThat(root.isRoot()).isTrue();
    assertThat(root).isInstanceOf(ResourcePathRootUnc.class);
    assertThat(root.getName()).isEqualTo("\\\\fileserver.org");
    assertThat(path.getRoot()).isSameAs(root);
  }

  /** Test of {@link ResourcePath#create(String)} with an absolute windows drive letter path. */
  @Test
  public void testCreateComplexWindowsDriveLetter() {

    ResourcePath<Void> path = ResourcePath.create("C:\\Users\\mylogin\\.\\AppData\\");
    assertThat(path.isAbsolute()).isTrue();
    assertThat(path.toString('\\')).isEqualTo("C:\\Users\\mylogin\\AppData");
    assertThat(path.getName()).isEqualTo("AppData");
    assertThat(path.isRoot()).isFalse();
    ResourcePath<Void> myloginPath = path.getParent();
    assertThat(myloginPath.getName()).isEqualTo("mylogin");
    ResourcePath<Void> usersPath = myloginPath.getParent();
    assertThat(usersPath.getName()).isEqualTo("Users");
    ResourcePath<Void> root = usersPath.getParent();
    assertThat(root.isRoot()).isTrue();
    assertThat(root).isInstanceOf(ResourcePathRootWindows.class);
    assertThat(root.getName()).isEqualTo("C:");
    assertThat(path.getRoot()).isSameAs(root);

    root = ResourcePath.create("C:");
    assertThat(root.isRoot()).isTrue();
    assertThat(root).isInstanceOf(ResourcePathRootWindows.class);
    assertThat(root.getName()).isEqualTo("C:");

    root = ResourcePath.create("C:D/.");
    assertThat(root.isRoot()).isFalse();
    assertThat(root.isAbsolute()).isFalse();
    assertThat(root.getName()).isEqualTo("C:D");

  }

  /** Test of {@link ResourcePath#create(String)} with an URL. */
  @Test
  public void testCreateComplexUrl() {

    ResourcePath<Void> path = ResourcePath.create("https://github.com/m-m-m/mmm");
    assertThat(path.isAbsolute()).isTrue();
    assertThat(path.toString()).isEqualTo("https://github.com/m-m-m/mmm");
    assertThat(path.getName()).isEqualTo("mmm");
    assertThat(path.isRoot()).isFalse();
    ResourcePath<Void> m_m_mPath = path.getParent();
    assertThat(m_m_mPath.getName()).isEqualTo("m-m-m");
    ResourcePath<Void> root = m_m_mPath.getParent();
    assertThat(root.isRoot()).isTrue();
    assertThat(root).isInstanceOf(ResourcePathRootUrl.class);
    assertThat(path.getRoot()).isSameAs(root);
    ResourcePathRootUrl<Void> rootUrl = (ResourcePathRootUrl<Void>) root;
    assertThat(rootUrl.getScheme()).isEqualTo("https");
    assertThat(rootUrl.getAuthority()).isEqualTo("github.com");
    assertThat(rootUrl.getName()).isEqualTo("https://github.com");
  }

  /** Test of {@link ResourcePath#createPattern(String)} and {@link ResourcePath#matches(ResourcePath)}. */
  @Test
  public void testCreatePatternAndMatches() {

    // given
    ResourcePath<Pattern> patternPath = ResourcePath.createPattern("a.c/foo*/*/a?c/foo/..");
    // the "foo" vs. "bar" is intentionally to make it explicit.
    ResourcePath<Pattern> path1 = ResourcePath.createPattern("a.c/foo42/some/abc/bar/..");
    ResourcePath<Pattern> path2 = ResourcePath.createPattern("a.c/foo/some/abc/");
    ResourcePath<Pattern> path3 = ResourcePath.createPattern("a.c/foo/some/ac/");

    // when
    boolean path1Matches = path1.matches(patternPath);
    boolean path2Matches = path2.matches(patternPath);
    boolean path3Matches = path3.matches(patternPath);

    // then
    assertThat(path1Matches).isTrue();
    assertThat(path2Matches).isTrue();
    assertThat(path3Matches).isFalse();
  }

  /** Test of {@link ResourcePath#navigateTo(ResourcePath)} with relative paths. */
  @Test
  public void testNavigateToRelative() {

    ResourcePath<Void> base = ResourcePath.create("../../foo/bar");
    ResourcePath<Void> navigator = ResourcePath.create("../../../some");
    ResourcePath<Void> result = base.navigateTo(navigator);
    assertThat(result.toString()).isEqualTo("../../../some");
  }

  /** Test of {@link ResourcePath#navigateTo(ResourcePath)} with absolute path. */
  @Test
  public void testNavigateToAbsolute() {

    ResourcePath<Void> base = ResourcePath.create("https://github.com/foo/bar");
    ResourcePath<Void> navigator = ResourcePath.create("/some");
    ResourcePath<Void> result = base.navigateTo(navigator);

    navigator = ResourcePath.create("../../../some");
    result = base.navigateTo(navigator);
    assertThat(result.toString()).isEqualTo("https://github.com/some");

    assertThat(base.navigateTo(ResourcePath.ROOT_RELATIVE)).isSameAs(base);
  }

  /** Test of {@link ResourcePath#ResourcePath(ResourcePath, String, Object)} with empty name. */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithEmptyName() {

    new ResourcePath<Void>(ResourcePath.ROOT_ABSOLUTE, "", (Void) null);
  }

  /** Test of {@link ResourcePath#ResourcePath(ResourcePath, String, Function)} with empty name. */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithEmptyNameFunction() {

    Function<ResourcePath<Void>, Void> function = VoidFunction.getInstance();
    new ResourcePath<Void>(ResourcePath.ROOT_ABSOLUTE, "", function);
  }

}
