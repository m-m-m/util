/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.resource.api;

import net.sf.mmm.content.api.ContentException;
import net.sf.mmm.content.value.api.Version;

/**
 * This interface gives write access to the content resources.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentResourceWriteAccess {

  /**
   * This method stores the given resource so its state is made persistent.
   * 
   * @param resource is the resource to save.
   * @throws ContentException if the operation fails.
   */
  void updateResource(ContentResource resource) throws ContentException;

  /**
   * This method creates a new version of the current resource.
   * 
   * @param resource is the resource to create a version of.
   * @param version is the explicit version to use.
   * @return the new version of the resource.
   * @throws ContentException if the operation fails. This can have one of the
   *         following reasons:
   *         <ul>
   *         <li>the given resource is a versioned resource (not a current
   *         resource).</li>
   *         <li>the resource is locked</li>
   *         <li>the given version is no successor of the latest version of the
   *         resource.</li>
   *         </ul>
   */
  ContentResource createVersion(ContentResource resource, Version version) throws ContentException;

  /**
   * This method creates a new version of the current resource.
   * 
   * @param resource is the resource to create a version of.
   * @return the new version of the resource.
   * @throws ContentException if the operation fails. This can have one of the
   *         following reasons:
   *         <ul>
   *         <li>the given resource is a versioned resource (not a current
   *         resource).</li>
   *         <li>the resource is locked</li>
   *         </ul>
   */
  ContentResource createVersion(ContentResource resource) throws ContentException;

}
