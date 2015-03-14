/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.api;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface for a component that scans the classpath for resources.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.0.0
 */
@ComponentSpecification
public interface ClasspathScanner {

  /**
   * @return the {@link BrowsableResource} for the root (or default) package.
   */
  BrowsableResource getClasspathResource();

  /**
   * @param classpath is the classpath location pointing to a {@link Package} or
   *        {@link net.sf.mmm.util.resource.base.ClasspathResource}.
   * @return the {@link BrowsableResource} for the given <code>classpath</code>.
   */
  BrowsableResource getClasspathResource(String classpath);

  /**
   * @param pkg is the {@link Package} to get.
   * @return the {@link BrowsableResource} pointing to the given package.
   */
  BrowsableResource getClasspathResource(Package pkg);

}
