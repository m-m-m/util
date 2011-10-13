/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.repository.api;

import net.sf.mmm.data.api.ContentException;
import net.sf.mmm.data.api.ContentObject;
import net.sf.mmm.data.reflection.api.ContentClass;
import net.sf.mmm.data.resource.api.ContentResource;

/**
 * This interface gives write access to the content resources.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface ContentObjectWriteAccess {

  /**
   * This method stores the given object so its state is made persistent.
   * 
   * @param object is the object to save.
   * @throws ContentException if the operation fails.
   */
  void save(ContentObject object) throws ContentException;

  /**
   * This method creates a new instance of the {@link ContentObject object} with
   * the {@link ContentObject#getContentClass() entity-type} given by
   * <code>contentClass</code>, the given
   * <code>{@link ContentObject#getTitle() name}</code> and
   * <code>{@link ContentObject#getParent() parent}</code>.
   * 
   * @param contentClass is the type of the resource to create.
   * @param name is the {@link ContentObject#getTitle() name} of the resource to
   *        create.
   * @param parent is the {@link ContentObject#getParent() parent} of the
   *        resource to create.
   * @return the created resource.
   * @throws ContentException if the operation fails.
   */
  ContentObject create(ContentClass contentClass, String name, ContentResource parent)
      throws ContentException;

  /**
   * This method creates a new version of the current resource.
   * 
   * @param <E> is the generic entity type of the resource.
   * @param resource is the resource to create a version of.
   * @return the new version of the resource.
   * @throws ContentException if the operation fails. This can have one of the
   *         following reasons:
   *         <ul>
   *         <li>the given resource is a closed version (not a current
   *         resource).</li>
   *         <li>the entity type is NOT
   *         {@link ContentClass#isRevisionControlled() revision-controlled}.</li>
   *         <li>the resource is locked by another user</li>
   *         </ul>
   */
  <E extends ContentResource> E createVersion(E resource) throws ContentException;

}
