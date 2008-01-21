/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.repository.api;

import net.sf.mmm.content.api.ContentException;
import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.model.api.ContentModelService;
import net.sf.mmm.content.resource.api.ContentResource;
import net.sf.mmm.content.value.api.ContentId;

/**
 * This is the interface for the {@link ContentRepository}, the central
 * component of the system. It allows to read and write {@link ContentObject}s
 * and acts as persistence layer.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentRepository extends ContentObjectReadAccess, ContentObjectWriteAccess {

  /**
   * This method gets the {@link ContentModelService} that allows to reflect the
   * entities managed by this {@link ContentRepository}.
   * 
   * @return the content-model-service.
   */
  ContentModelService getContentModel();

  /**
   * This method gets the root-folder of the repository.
   * 
   * @see ContentObject#getParent()
   * @see net.sf.mmm.content.model.api.ContentClass#isFolderClass()
   * 
   * @return the root-folder.
   */
  ContentObject getRootFolder();

  /**
   * This method gets the {@link ContentObject} with the given <code>id</code>.
   * 
   * @see #get(ContentId)
   * 
   * @param <E> is the generic entity type of the requested object.
   * @param id is the unique identifier of the requested object.
   * @param entityClass is the java-class reflecting the type of the requested
   *        entity.
   * @return the object with the given identifier.
   * @throws ContentException if no object exists with the given ID.
   */
  <E extends ContentObject> E get(ContentId id, Class<E> entityClass) throws ContentException;

  /**
   * This method gets the raw instance of the given <code>entity</code>.<br>
   * An {@link ContentObject entity} retrieved from this
   * {@link ContentRepository repository} (e.g. via
   * <code>{@link #get(ContentId)}</code>) will be "manipulated" to add
   * support for {@link ContentResource#getProxyTarget() proxying} and
   * {@link ContentObject#getParent() inheritance}. Therefore this method
   * returns the original object without manipulations. This is needed e.g. for
   * the editor GUI.
   * 
   * @param <E> is the generic type of the entity.
   * @param entity is an entity retrieved from this
   *        {@link ContentRepository repository} (e.g. via
   *        <code>{@link #get(ContentId)}</code>).
   * @return the raw object with the original data.
   * @throws ContentException if the operation fails.
   */
  <E extends ContentResource> E getRawObject(E entity) throws ContentException;

  /**
   * This method gets the {@link ContentObject} with the given <code>path</code>.
   * 
   * @see #get(String)
   * 
   * @param <E> is the generic entity type of the requested object.
   * @param path is the {@link ContentObject#getPath() path} of the requested
   *        object.
   * @param entityClass is the java-class reflecting the type of the requested
   *        entity.
   * @return the object with the given <code>path</code>.
   * @throws ContentException if the operation failed. This can have one of the
   *         following reasons:
   *         <ul>
   *         <li>the path is illegal.</li>
   *         <li>no resource exists for the given <code>path</code>.</li>
   *         </ul>
   */
  <E extends ContentObject> E get(String path, Class<E> entityClass) throws ContentException;

  /**
   * This method renames the given <code>contentObject</code>.
   * 
   * @param entity is the {@link ContentObject} to rename.
   * @param newName is the new {@link ContentObject#getName() name} of the
   *        content-object.
   * @throws ContentException if the operation failed. This can have one of the
   *         following reasons:
   *         <ul>
   *         <li>the given <code>newName</code> is illegal.</li>
   *         <li>the given <code>entity</code> can NOT be renamed (e.g. it is
   *         a {@link net.sf.mmm.content.model.api.ContentClass content-class}
   *         and the underlying {@link #getContentModel() content-model-service}
   *         does NOT support renaming).</li>
   *         <li>the {@link ContentObject#getParent() parent} of the
   *         <code>entity</code> already has a
   *         {@link ContentObject#getChildren() child}
   *         {@link ContentObject#getName() named} <code>newName</code>.</li>
   *         </ul>
   */
  void rename(ContentObject entity, String newName) throws ContentException;

  /**
   * This method moves the given <code>entity</code> to the new
   * {@link ContentResource#getParent() parent} given by <code>newParent</code>.
   * 
   * TODO: operate on object or resource?
   * 
   * @param entity is the {@link ContentResource} to move.
   * @param newParent is the new {@link ContentObject#getParent() parent} where
   *        to move the <code>entity</code> to.
   * @throws ContentException if the operation fails. This can have one of the
   *         following reasons:
   *         <ul>
   *         <li>TODO</li>
   *         </ul>
   */
  void move(ContentResource entity, ContentResource newParent) throws ContentException;

}
