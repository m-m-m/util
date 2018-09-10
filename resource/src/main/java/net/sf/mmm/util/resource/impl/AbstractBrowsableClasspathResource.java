/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.impl;

import java.util.Date;

import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.resource.api.BrowsableResource;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.ResourcePath;
import net.sf.mmm.util.resource.api.ResourceUriUndefinedException;
import net.sf.mmm.util.resource.base.AbstractBrowsableResource;
import net.sf.mmm.util.resource.base.ClasspathResource;

/**
 * Abstract base implementation of {@link BrowsableResource} for {@code ClasspathFile} or {@code ClasspathFolder}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.3.0 (moved from 7.0.0)
 */
@SuppressWarnings("javadoc")
abstract class AbstractBrowsableClasspathResource extends AbstractBrowsableResource implements ResourcePath {

  private static final String EXTENSION_CLASS = ".class";

  private final ClasspathFolder parent;

  final String name;

  private final String qualifiedName;

  /**
   * The constructor.
   *
   * @param parent - see {@link #getParent()}.
   * @param name - see {@link #getName()}.
   */
  AbstractBrowsableClasspathResource(ClasspathFolder parent, String name) {

    super();
    this.parent = parent;
    this.name = name;
    if (isData()) {
      if (name.endsWith(EXTENSION_CLASS)) {
        this.qualifiedName = parent.getQualifiedName() + "." + name.substring(0, name.length() - EXTENSION_CLASS.length());
      } else {
        this.qualifiedName = null;
      }
    } else {
      if (parent == null) {
        this.qualifiedName = "";
      } else if ((((AbstractBrowsableClasspathResource) parent).parent) == null) {
        this.qualifiedName = name;
      } else {
        this.qualifiedName = parent.getQualifiedName() + "." + name;
      }
    }
  }

  @Override
  public String getName() {

    return this.name;
  }

  /**
   * @return the qualified java name ({@link Package#getName() package name} or {@link Class#getName() class
   *         name}). Will be {@code null} for a {@link ClasspathFile} that does not point to a {@link Class}
   *         file (*.class).
   */
  String getQualifiedName() {

    return this.qualifiedName;
  }

  /**
   * @return the parent folder.
   */
  @Override
  public ClasspathFolder getParent() {

    return this.parent;
  }

  @Override
  public DataResource navigate(String resourcePath) throws ResourceUriUndefinedException {

    return this.parent.navigate(resourcePath);
  }

  @Override
  public Date getLastModificationDate() {

    // not supported
    return null;
  }

  @Override
  public String getSchemePrefix() {

    return ClasspathResource.SCHEME_PREFIX;
  }

  @Override
  public String getUri() {

    return getSchemePrefix() + getPath();
  }

  @Override
  public ClasspathFolder getRoot() {

    if (this.parent == null) {
      return (ClasspathFolder) this;
    }
    return this.parent.getRoot();
  }

  @Override
  public boolean isAbsolute() {

    return true;
  }

  @Override
  public boolean isRoot() {

    return this.parent == null;
  }

  @Override
  public String getPath() {

    StringBuilder buffer = new StringBuilder();
    getPath(buffer);
    return buffer.toString();
  }

  /**
   * @param buffer is the {@link StringBuilder} where to append the string representation to.
   */
  protected void getPath(StringBuilder buffer) {

    if (this.parent != null) {
      if (!this.parent.isRoot()) {
        this.parent.getPath(buffer);
        buffer.append('/');
      }
    }
    buffer.append(this.name);
  }

  @Override
  public abstract Iterable<? extends AbstractBrowsableClasspathResource> getChildResources();

  /**
   * Initializes this resource after it has been properly created.
   */
  protected void init() {

    for (AbstractBrowsableClasspathResource child : getChildResources()) {
      child.init();
    }
  }

  protected static Iterable<? extends BrowsableResource> getResources(Iterable<? extends BrowsableResource> iterable,
      Filter<? super BrowsableResource> filter) {

    return AbstractBrowsableResource.getResources(iterable, filter);
  }

}
