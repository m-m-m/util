/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.api;

import java.util.regex.Pattern;

import net.sf.mmm.util.lang.api.function.Function;
import net.sf.mmm.util.lang.api.function.VoidFunction;
import net.sf.mmm.util.resource.api.ResourcePathNode.ResourcePathRootHome;
import net.sf.mmm.util.resource.api.ResourcePathNode.ResourcePathRootUnc;
import net.sf.mmm.util.resource.api.ResourcePathNode.ResourcePathRootUrl;
import net.sf.mmm.util.resource.api.ResourcePathNode.ResourcePathRootWindows;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * This is the test-case for {@link ResourcePathNode}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.0.0
 */
public class ResourcePathNodeTest extends Assertions {

  /** Test of {@link ResourcePathNode#ROOT_RELATIVE}. */
  @Test
  public void testRootRelative() {

    assertThat(ResourcePathNode.ROOT_RELATIVE.toString()).isEqualTo("");
    assertThat(ResourcePathNode.ROOT_RELATIVE.getName()).isEqualTo("");
    assertThat(ResourcePathNode.ROOT_RELATIVE.isRoot()).isTrue();
    assertThat(ResourcePathNode.ROOT_RELATIVE.isAbsolute()).isFalse();
    assertThat(ResourcePathNode.ROOT_RELATIVE.getParent()).isNull();
    assertThat(ResourcePathNode.ROOT_RELATIVE.getData()).isNull();
    assertThat(ResourcePathNode.ROOT_RELATIVE.getRoot()).isSameAs(ResourcePathNode.ROOT_RELATIVE);
  }

  /** Test of {@link ResourcePathNode#ROOT_ABSOLUTE}. */
  @Test
  public void testRootAbsolute() {

    assertThat(ResourcePathNode.ROOT_ABSOLUTE.toString()).isEqualTo("/");
    assertThat(ResourcePathNode.ROOT_ABSOLUTE.getName()).isEqualTo("/");
    assertThat(ResourcePathNode.ROOT_ABSOLUTE.isRoot()).isTrue();
    assertThat(ResourcePathNode.ROOT_ABSOLUTE.isAbsolute()).isTrue();
    assertThat(ResourcePathNode.ROOT_ABSOLUTE.getParent()).isNull();
    assertThat(ResourcePathNode.ROOT_ABSOLUTE.getData()).isNull();
    assertThat(ResourcePathNode.ROOT_ABSOLUTE.getRoot()).isSameAs(ResourcePathNode.ROOT_ABSOLUTE);
  }

  /** Test of {@link ResourcePathNode#ROOT_HOME}. */
  @Test
  public void testRootHome() {

    assertThat(ResourcePathNode.ROOT_HOME.toString()).isEqualTo("~");
    assertThat(ResourcePathNode.ROOT_HOME.getName()).isEqualTo("~");
    assertThat(ResourcePathNode.ROOT_HOME.isRoot()).isTrue();
    assertThat(ResourcePathNode.ROOT_HOME.isAbsolute()).isTrue();
    assertThat(ResourcePathNode.ROOT_HOME.getParent()).isNull();
    assertThat(ResourcePathNode.ROOT_HOME.getData()).isNull();
    assertThat(ResourcePathNode.ROOT_HOME.getRoot()).isSameAs(ResourcePathNode.ROOT_HOME);
  }

  /** Test of {@link ResourcePathNode#create(String)} with {@code null}. */
  @Test
  public void testCreateNull() {

    assertThat(ResourcePathNode.create(null)).isNull();
  }

  /** Test of {@link ResourcePathNode#create(String)} with empty string. */
  @Test
  public void testCreateEmpty() {

    assertThat(ResourcePathNode.create("")).isSameAs(ResourcePathNode.ROOT_RELATIVE);
  }

  /** Test of {@link ResourcePathNode#create(String)} with simple segments. */
  @Test
  public void testCreateSimple() {

    verifySimplePath("a");
    verifySimplePath("foo");
  }

  /**
   * Verifies {@link ResourcePathNode#create(String)} with a single segment both absolute and relative.
   *
   * @param segment is the segment. Must not be <code>null</code>, empty, ".", ".." or contain "/" or "\\".
   */
  public void verifySimplePath(String segment) {

    ResourcePathNode<Void> relativePath = ResourcePathNode.create(segment);
    assertThat(relativePath.getName()).isEqualTo(segment);
    assertThat(relativePath.toString()).isEqualTo(segment);
    assertThat(relativePath.isAbsolute()).isFalse();
    assertThat(relativePath.getParent()).isSameAs(ResourcePathNode.ROOT_RELATIVE);
    assertThat(relativePath.getRoot()).isSameAs(relativePath.getParent());

    String segmentAbsoulte = "/" + segment;
    ResourcePathNode<Void> absolutePath = ResourcePathNode.create(segmentAbsoulte);
    assertThat(absolutePath.getName()).isEqualTo(segment);
    assertThat(absolutePath.toString()).isEqualTo(segmentAbsoulte);
    assertThat(absolutePath.isAbsolute()).isTrue();
    assertThat(absolutePath.getParent()).isSameAs(ResourcePathNode.ROOT_ABSOLUTE);
    assertThat(absolutePath.getRoot()).isSameAs(absolutePath.getParent());
  }

  /** Test of {@link ResourcePathNode#create(String)} with a complex relative path. */
  @Test
  public void testCreateComplexRelative() {

    ResourcePathNode<Void> path = ResourcePathNode.create("..\\./..//./a\\../b//c/./../.foo///");
    assertThat(path.isAbsolute()).isFalse();
    assertThat(path.toString()).isEqualTo("../../b/.foo");
    assertThat(path.getName()).isEqualTo(".foo");
    assertThat(path.isRoot()).isFalse();
    ResourcePathNode<Void> p1 = path.getParent();
    assertThat(p1.getName()).isEqualTo("b");
    ResourcePathNode<Void> p2 = p1.getParent();
    assertThat(p2.getName()).isEqualTo("..");
    ResourcePathNode<Void> p3 = p2.getParent();
    assertThat(p3.getName()).isEqualTo("..");
    ResourcePathNode<Void> p4 = p3.getParent();
    assertThat(p4).isSameAs(ResourcePathNode.ROOT_RELATIVE);
    assertThat(path.getRoot()).isSameAs(p4);
  }

  /** Test of {@link ResourcePathNode#create(String)} with a complex relative path. */
  @Test
  public void testCreateComplexAbsolute() {

    ResourcePathNode<Void> path = ResourcePathNode.create("/..//./a\\../b//c/./../.foo///");
    assertThat(path.isAbsolute()).isTrue();
    assertThat(path.toString()).isEqualTo("/b/.foo");
    assertThat(path.getName()).isEqualTo(".foo");
    assertThat(path.isRoot()).isFalse();
    ResourcePathNode<Void> p1 = path.getParent();
    assertThat(p1.getName()).isEqualTo("b");
    ResourcePathNode<Void> p2 = p1.getParent();
    assertThat(p2).isSameAs(ResourcePathNode.ROOT_ABSOLUTE);
    assertThat(path.getRoot()).isSameAs(p2);
  }

  /** Test of {@link ResourcePathNode#create(String)} with an absolute home path. */
  @Test
  public void testCreateComplexHome() {

    ResourcePathNode<Void> path = ResourcePathNode.create("~/.ssh/");
    assertThat(path.isAbsolute()).isTrue();
    assertThat(path.toString()).isEqualTo("~/.ssh");
    assertThat(path.getName()).isEqualTo(".ssh");
    assertThat(path.isRoot()).isFalse();
    ResourcePathNode<Void> root = path.getParent();
    assertThat(root.isRoot()).isTrue();
    assertThat(root).isSameAs(ResourcePathNode.ROOT_HOME);
    assertThat(root.getName()).isEqualTo("~");
    assertThat(path.getRoot()).isSameAs(root);
  }

  /** Test of {@link ResourcePathNode#create(String)} with an absolute home path. */
  @Test
  public void testCreateComplexHomeParent() {

    ResourcePathNode<Void> path = ResourcePathNode.create("~admin/../.././foo/../..");
    assertThat(path.isAbsolute()).isTrue();
    assertThat(path.toString()).isEqualTo("~admin/../../..");
    assertThat(path.getName()).isEqualTo("..");
    assertThat(path.isRoot()).isFalse();
    ResourcePathNode<Void> p1 = path.getParent();
    assertThat(p1.getName()).isEqualTo("..");
    assertThat(p1.isRoot()).isFalse();
    ResourcePathNode<Void> p2 = p1.getParent();
    assertThat(p2.getName()).isEqualTo("..");
    assertThat(p2.isRoot()).isFalse();
    ResourcePathNode<Void> p3 = p2.getParent();
    assertThat(p3.isRoot()).isTrue();
    assertThat(p3).isInstanceOf(ResourcePathRootHome.class);
    assertThat(p3.getName()).isEqualTo("~admin");
    assertThat(path.getRoot()).isSameAs(p3);
  }

  /** Test of {@link ResourcePathNode#create(String)} with an absolute UNC path. */
  @Test
  public void testCreateComplexUnc() {

    ResourcePathNode<Void> path = ResourcePathNode.create("\\\\fileserver.org\\..\\Share\\.\\Downloads\\");
    assertThat(path.isAbsolute()).isTrue();
    assertThat(path.toString('\\')).isEqualTo("\\\\fileserver.org\\Share\\Downloads");
    assertThat(path.getName()).isEqualTo("Downloads");
    assertThat(path.isRoot()).isFalse();
    assertThat(path.getParent().getName()).isEqualTo("Share");
    ResourcePathNode<Void> root = path.getParent().getParent();
    assertThat(root.isRoot()).isTrue();
    assertThat(root).isInstanceOf(ResourcePathRootUnc.class);
    assertThat(root.getName()).isEqualTo("\\\\fileserver.org");
    assertThat(path.getRoot()).isSameAs(root);
  }

  /** Test of {@link ResourcePathNode#create(String)} with an absolute windows drive letter path. */
  @Test
  public void testCreateComplexWindowsDriveLetter() {

    ResourcePathNode<Void> path = ResourcePathNode.create("C:\\Users\\mylogin\\.\\AppData\\");
    assertThat(path.isAbsolute()).isTrue();
    assertThat(path.toString('\\')).isEqualTo("C:\\Users\\mylogin\\AppData");
    assertThat(path.getName()).isEqualTo("AppData");
    assertThat(path.isRoot()).isFalse();
    ResourcePathNode<Void> myloginPath = path.getParent();
    assertThat(myloginPath.getName()).isEqualTo("mylogin");
    ResourcePathNode<Void> usersPath = myloginPath.getParent();
    assertThat(usersPath.getName()).isEqualTo("Users");
    ResourcePathNode<Void> root = usersPath.getParent();
    assertThat(root.isRoot()).isTrue();
    assertThat(root).isInstanceOf(ResourcePathRootWindows.class);
    assertThat(root.getName()).isEqualTo("C:");
    assertThat(path.getRoot()).isSameAs(root);

    root = ResourcePathNode.create("C:");
    assertThat(root.isRoot()).isTrue();
    assertThat(root).isInstanceOf(ResourcePathRootWindows.class);
    assertThat(root.getName()).isEqualTo("C:");

    root = ResourcePathNode.create("C:D/.");
    assertThat(root.isRoot()).isFalse();
    assertThat(root.isAbsolute()).isFalse();
    assertThat(root.getName()).isEqualTo("C:D");

  }

  /** Test of {@link ResourcePathNode#create(String)} with an URL. */
  @Test
  public void testCreateComplexUrl() {

    ResourcePathNode<Void> path = ResourcePathNode.create("https://github.com/m-m-m/mmm");
    assertThat(path.isAbsolute()).isTrue();
    assertThat(path.toString()).isEqualTo("https://github.com/m-m-m/mmm");
    assertThat(path.getName()).isEqualTo("mmm");
    assertThat(path.isRoot()).isFalse();
    ResourcePathNode<Void> m_m_mPath = path.getParent();
    assertThat(m_m_mPath.getName()).isEqualTo("m-m-m");
    ResourcePathNode<Void> root = m_m_mPath.getParent();
    assertThat(root.isRoot()).isTrue();
    assertThat(root).isInstanceOf(ResourcePathRootUrl.class);
    assertThat(path.getRoot()).isSameAs(root);
    ResourcePathRootUrl<Void> rootUrl = (ResourcePathRootUrl<Void>) root;
    assertThat(rootUrl.getScheme()).isEqualTo("https");
    assertThat(rootUrl.getAuthority()).isEqualTo("github.com");
    assertThat(rootUrl.getName()).isEqualTo("https://github.com");
  }

  /**
   * Test of {@link ResourcePathNode#createPattern(String)} and
   * {@link ResourcePathNode#matches(ResourcePathNode)}.
   */
  @Test
  public void testCreatePatternAndMatches() {

    // given
    ResourcePathNode<Pattern> patternPath = ResourcePathNode.createPattern("a.c/foo*/*/a?c/foo/..");
    // the "foo" vs. "bar" is intentionally to make it explicit.
    ResourcePathNode<Pattern> path1 = ResourcePathNode.createPattern("a.c/foo42/some/abc/bar/..");
    ResourcePathNode<Pattern> path2 = ResourcePathNode.createPattern("a.c/foo/some/abc/");
    ResourcePathNode<Pattern> path3 = ResourcePathNode.createPattern("a.c/foo/some/ac/");

    // when
    boolean path1Matches = path1.matches(patternPath);
    boolean path2Matches = path2.matches(patternPath);
    boolean path3Matches = path3.matches(patternPath);

    // then
    assertThat(path1Matches).isTrue();
    assertThat(path2Matches).isTrue();
    assertThat(path3Matches).isFalse();
  }

  /** Test of {@link ResourcePathNode#navigateTo(ResourcePathNode)} with relative paths. */
  @Test
  public void testNavigateToRelative() {

    ResourcePathNode<Void> base = ResourcePathNode.create("../../foo/bar");
    ResourcePathNode<Void> navigator = ResourcePathNode.create("../../../some");
    ResourcePathNode<Void> result = base.navigateTo(navigator);
    assertThat(result.toString()).isEqualTo("../../../some");
  }

  /** Test of {@link ResourcePathNode#navigateTo(ResourcePathNode)} with absolute path. */
  @Test
  public void testNavigateToAbsolute() {

    ResourcePathNode<Void> base = ResourcePathNode.create("https://github.com/foo/bar");
    ResourcePathNode<Void> navigator = ResourcePathNode.create("/some");
    ResourcePathNode<Void> result = base.navigateTo(navigator);

    navigator = ResourcePathNode.create("../../../some");
    result = base.navigateTo(navigator);
    assertThat(result.toString()).isEqualTo("https://github.com/some");

    assertThat(base.navigateTo(ResourcePathNode.ROOT_RELATIVE)).isSameAs(base);
  }

  /** Test of {@link ResourcePathNode#ResourcePathNode(ResourcePathNode, String, Object)} with empty name. */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithEmptyName() {

    new ResourcePathNode<Void>(ResourcePathNode.ROOT_ABSOLUTE, "", (Void) null);
  }

  /** Test of {@link ResourcePathNode#ResourcePathNode(ResourcePathNode, String, Function)} with empty name. */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithEmptyNameFunction() {

    Function<ResourcePathNode<Void>, Void> function = VoidFunction.getInstance();
    new ResourcePathNode<Void>(ResourcePathNode.ROOT_ABSOLUTE, "", function);
  }

}
