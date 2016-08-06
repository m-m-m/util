/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.impl;

import java.net.URL;
import java.util.Collections;

import net.sf.mmm.util.reflect.api.ClassResolver;
import net.sf.mmm.util.resource.api.BrowsableResource;
import net.sf.mmm.util.resource.api.ResourceNotAvailableException;
import net.sf.mmm.util.resource.base.ClasspathResource;

/**
 * A file on the classpath. Like {@link ClasspathResource} but {@link BrowsableResource browsable}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.3.0 (moved from 7.0.0)
 */
class ClasspathFile extends AbstractBrowsableClasspathResource {

  private URL url;

  private Class<?> javaClass;

  /**
   * The constructor.
   *
   * @param parent - see {@link #getParent()}.
   * @param name - see {@link #getName()}.
   */
  ClasspathFile(ClasspathFolder parent, String name) {

    super(parent, name);
  }

  @Override
  public Iterable<? extends AbstractBrowsableClasspathResource> getChildResources() {

    return Collections.emptyList();
  }

  @Override
  public boolean isFolder() {

    return false;
  }

  @Override
  public boolean isData() {

    return true;
  }

  @Override
  public BrowsableResource cd(String path) {

    return getParent().cd(path);
  }

  @Override
  public URL getUrl() throws ResourceNotAvailableException {

    if (this.url == null) {
      this.url = Thread.currentThread().getContextClassLoader().getResource(getPath());
    }
    return this.url;
  }

  /**
   * @return the corresponding Java {@link Class}. Will be loaded lazily.
   */
  public Class<?> getJavaClass() {

    if (this.javaClass == null) {
      String qualifiedName = getQualifiedName();
      if (qualifiedName == null) {
        throw new IllegalArgumentException(getUri());
      }
      synchronized (this) {
        if (this.javaClass == null) {
          this.javaClass = ClassResolver.CLASS_FOR_NAME_RESOLVER.resolveClass(qualifiedName);
        }
      }
    }
    return this.javaClass;
  }
}