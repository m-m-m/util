/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.repository;

import net.sf.mmm.data.api.DataException;
import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.reflection.DataClass;
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
   * @throws DataException if the operation fails.
   */
  void save(DataObject object) throws DataException;

  /**
   * This method creates a new instance of the {@link DataObject object} with
   * the {@link DataObject#getContentClass() entity-type} given by
   * <code>contentClass</code>, the given
   * <code>{@link DataObject#getTitle() name}</code> and
   * <code>{@link DataObject#getParent() parent}</code>.
   * 
   * @param contentClass is the type of the resource to create.
   * @param name is the {@link DataObject#getTitle() name} of the resource to
   *        create.
   * @param parent is the {@link DataObject#getParent() parent} of the
   *        resource to create.
   * @return the created resource.
   * @throws DataException if the operation fails.
   */
  DataObject create(DataClass contentClass, String name, ContentResource parent)
      throws DataException;

  /**
   * This method creates a new version of the current resource.
   * 
   * @param <E> is the generic entity type of the resource.
   * @param resource is the resource to create a version of.
   * @return the new version of the resource.
   * @throws DataException if the operation fails. This can have one of the
   *         following reasons:
   *         <ul>
   *         <li>the given resource is a closed version (not a current
   *         resource).</li>
   *         <li>the entity type is NOT
   *         {@link DataClass#isRevisionControlled() revision-controlled}.</li>
   *         <li>the resource is locked by another user</li>
   *         </ul>
   */
  <E extends ContentResource> E createVersion(E resource) throws DataException;

}
