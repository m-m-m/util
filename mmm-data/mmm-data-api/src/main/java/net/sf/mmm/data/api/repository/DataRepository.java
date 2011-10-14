/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.repository;

import net.sf.mmm.data.api.DataException;
import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.data.api.reflection.DataReflectionService;
import net.sf.mmm.data.api.repository.access.ContentObjectReadAccess;
import net.sf.mmm.data.resource.api.ContentResource;

/**
 * This is the interface for the {@link DataRepository}, the central
 * component of the system. It allows to read and write {@link DataObject}s
 * and acts as persistence layer.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataRepository extends ContentObjectReadAccess, DataObjectWriteAccess {

  /**
   * This method gets the {@link DataReflectionService} that allows to reflect the
   * entities managed by this {@link DataRepository}.
   * 
   * @return the content-model-service.
   */
  DataReflectionService getContentModel();

  /**
   * This method gets the root-folder of the repository.
   * 
   * @see DataObject#getParent()
   * @see net.sf.mmm.data.api.DataObject#isFolder()
   * 
   * @return the root-folder.
   */
  DataObject getRootFolder();

  /**
   * This method gets the {@link DataObject} with the given <code>id</code>.
   * 
   * @see #get(DataId)
   * 
   * @param <E> is the generic entity type of the requested object.
   * @param id is the unique identifier of the requested object.
   * @param entityClass is the java-class reflecting the type of the requested
   *        entity.
   * @return the object with the given identifier.
   * @throws DataException if no object exists with the given ID.
   */
  <E extends DataObject> E get(DataId id, Class<E> entityClass) throws DataException;

  /**
   * This method gets the raw instance of the given <code>entity</code>.<br>
   * An {@link DataObject entity} retrieved from this
   * {@link DataRepository repository} (e.g. via
   * <code>{@link #get(DataId)}</code>) will be "manipulated" to add support
   * for {@link ContentResource#getProxyTarget() proxying} and
   * {@link DataObject#getParent() inheritance}. Therefore this method
   * returns the original object without manipulations. This is needed e.g. for
   * the editor GUI.
   * 
   * @param <E> is the generic type of the entity.
   * @param entity is an entity retrieved from this {@link DataRepository
   *        repository} (e.g. via <code>{@link #get(DataId)}</code>).
   * @return the raw object with the original data.
   * @throws DataException if the operation fails.
   */
  <E extends ContentResource> E getRawObject(E entity) throws DataException;

  /**
   * This method gets the {@link DataObject} with the given <code>path</code>
   * .
   * 
   * @see #get(String)
   * 
   * @param <E> is the generic entity type of the requested object.
   * @param path is the {@link DataObject#getPath() path} of the requested
   *        object.
   * @param entityClass is the java-class reflecting the type of the requested
   *        entity.
   * @return the object with the given <code>path</code>.
   * @throws DataException if the operation failed. This can have one of the
   *         following reasons:
   *         <ul>
   *         <li>the path is illegal.</li>
   *         <li>no resource exists for the given <code>path</code>.</li>
   *         </ul>
   */
  <E extends DataObject> E get(String path, Class<E> entityClass) throws DataException;

  /**
   * This method renames the given <code>contentObject</code>.
   * 
   * @param entity is the {@link DataObject} to rename.
   * @param newName is the new {@link DataObject#getTitle() name} of the
   *        content-object.
   * @throws DataException if the operation failed. This can have one of the
   *         following reasons:
   *         <ul>
   *         <li>the given <code>newName</code> is illegal.</li>
   *         <li>the given <code>entity</code> can NOT be renamed (e.g. it is a
   *         {@link net.sf.mmm.data.api.reflection.DataClass content-class} and
   *         the underlying {@link #getContentModel() content-model-service}
   *         does NOT support renaming).</li>
   *         <li>the {@link DataObject#getParent() parent} of the
   *         <code>entity</code> already has a
   *         {@link DataObject#getChildren() child}
   *         {@link DataObject#getTitle() named} <code>newName</code>.</li>
   *         </ul>
   */
  void rename(DataObject entity, String newName) throws DataException;

  /**
   * This method moves the given <code>entity</code> to the new
   * {@link ContentResource#getParent() parent} given by <code>newParent</code>.
   * 
   * TODO: operate on object or resource?
   * 
   * @param entity is the {@link ContentResource} to move.
   * @param newParent is the new {@link DataObject#getParent() parent} where
   *        to move the <code>entity</code> to.
   * @throws DataException if the operation fails. This can have one of the
   *         following reasons:
   *         <ul>
   *         <li>TODO</li>
   *         </ul>
   */
  void move(ContentResource entity, ContentResource newParent) throws DataException;

}
