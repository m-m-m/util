/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.repository;

import net.sf.mmm.data.api.DataException;
import net.sf.mmm.data.api.entity.resource.DataEntityResource;
import net.sf.mmm.data.api.entity.resource.DataFolder;
import net.sf.mmm.data.api.reflection.DataReflectionService;
import net.sf.mmm.data.api.repository.access.DataObjectReadAccess;

/**
 * This is the interface for the {@link DataRepository}, the central component
 * of the system. It allows to read and write
 * {@link net.sf.mmm.data.api.DataObject}s and acts as persistence layer.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataRepository extends DataObjectReadAccess {

  /**
   * This method gets the {@link DataReflectionService} that allows to reflect
   * the entities managed by this {@link DataRepository}.
   * 
   * @return the {@link DataReflectionService}.
   */
  DataReflectionService getReflectionService();

  /**
   * This method gets the root-folder of the repository.
   * 
   * @see DataEntityResource#getParent()
   * 
   * @return the root-folder.
   */
  DataFolder getRootFolder();

  // /**
  // * This method gets the raw instance of the given <code>entity</code>.<br>
  // * An {@link DataObject entity} retrieved from this {@link DataRepository
  // * repository} (e.g. via <code>{@link #get(DataId)}</code>) will be
  // * "manipulated" to add support for {@link ContentResource#getProxyTarget()
  // * proxying} and {@link DataObject#getParent() inheritance}. Therefore this
  // * method returns the original object without manipulations. This is needed
  // * e.g. for the editor GUI.
  // *
  // * @param <E> is the generic type of the entity.
  // * @param entity is an entity retrieved from this {@link DataRepository
  // * repository} (e.g. via <code>{@link #get(DataId)}</code>).
  // * @return the raw object with the original data.
  // * @throws DataException if the operation fails.
  // */
  // <E extends ContentResource> E getRawObject(E entity) throws DataException;

  /**
   * This method renames the given <code>contentObject</code>.
   * 
   * @param resource is the {@link DataEntityResource} to rename.
   * @param newTitle is the new
   *        {@link net.sf.mmm.data.api.DataObject#getTitle() title} of the
   *        {@link DataEntityResource}.
   * @throws DataException if the operation failed. This can have one of the
   *         following reasons:
   *         <ul>
   *         <li>the given <code>newName</code> is illegal.</li>
   *         <li>the given <code>resource</code> can NOT be renamed (e.g. it is
   *         a {@link net.sf.mmm.data.api.reflection.DataClass} and the
   *         underlying {@link DataReflectionService} does NOT support
   *         renaming).</li>
   *         <li>the {@link DataEntityResource#getParent() parent} of the
   *         <code>entity</code> already has a {@link DataFolder#getChildren()
   *         child} {@link net.sf.mmm.data.api.DataObject#getTitle() titled}
   *         <code>newTitle</code>.</li>
   *         </ul>
   */
  void rename(DataEntityResource resource, String newTitle) throws DataException;

  /**
   * This method moves the given <code>resource</code> to the new
   * {@link DataEntityResource#getParent() parent} given by
   * <code>newParent</code>.
   * 
   * @param resource is the {@link DataEntityResource} to move.
   * @param newParent is the new {@link DataEntityResource#getParent() parent}
   *        where to move the <code>resource</code> to.
   * @throws DataException if the operation fails. This can have one of the
   *         following reasons:
   *         <ul>
   *         <li>the {@link DataFolder} <code>newParent</code> already has a
   *         {@link DataFolder#getChildren() child} with the same
   *         {@link net.sf.mmm.data.api.DataObject#getTitle() title}.</li>
   *         </ul>
   */
  void move(DataEntityResource resource, DataFolder newParent) throws DataException;

  /**
   * This method {@link #move(DataEntityResource, DataFolder) moves} and
   * {@link #rename(DataEntityResource, String) renames} the given
   * <code>resource</code> in a single operation.
   * 
   * @param resource is the {@link DataEntityResource} to move.
   * @param newParent is the new {@link DataEntityResource#getParent() parent}
   *        where to move the <code>resource</code> to.
   * @param newTitle is the new
   *        {@link net.sf.mmm.data.api.DataObject#getTitle() title} of the
   *        {@link DataEntityResource}.
   * @throws DataException if the operation fails. This can have one of the
   *         following reasons:
   *         <ul>
   *         <li>the {@link DataFolder} <code>newParent</code> already has a
   *         {@link DataFolder#getChildren() child} with the same
   *         {@link net.sf.mmm.data.api.DataObject#getTitle() title}.</li>
   *         </ul>
   */
  void move(DataEntityResource resource, DataFolder newParent, String newTitle)
      throws DataException;

}
