/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.repository;

import java.util.Map;

import net.sf.mmm.data.api.DataException;
import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.reflection.DataField;
import net.sf.mmm.data.base.AbstractDataObject;
import net.sf.mmm.data.base.entity.resource.AbstractDataEntityResource;
import net.sf.mmm.data.resource.api.ContentResource;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractCachedDataRepository extends AbstractDataRepository {

  /** The cache for the latest resources. */
  private Map<DataId, AbstractDataEntityResource> latestResourceCache;

  /** The cache for the closed revisions of resources. */
  private Map<DataId, AbstractDataEntityResource> closedResourceCache;

  /** @see #getLatestResourceCacheSize() */
  private int latestResourceCacheSize;

  /** @see #getClosedResourceCacheSize() */
  private int closedResourceCacheSize;

  /**
   * The constructor.
   */
  public AbstractCachedDataRepository() {

    super();
    this.latestResourceCacheSize = 5000;
    this.closedResourceCacheSize = 1000;
  }

  /**
   * @return the closedResourceCacheSize
   */
  public int getClosedResourceCacheSize() {

    return this.closedResourceCacheSize;
  }

  /**
   * @param closedResourceCacheSize the closedResourceCacheSize to set
   */
  public void setClosedResourceCacheSize(int closedResourceCacheSize) {

    this.closedResourceCacheSize = closedResourceCacheSize;
  }

  /**
   * @return the latestResourceCacheSize
   */
  public int getLatestResourceCacheSize() {

    return this.latestResourceCacheSize;
  }

  /**
   * @param latestResourceCacheSize the latestResourceCacheSize to set
   */
  public void setLatestResourceCacheSize(int latestResourceCacheSize) {

    this.latestResourceCacheSize = latestResourceCacheSize;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doInitialize() {

    super.doInitialize();
    // TODO: use LRU / LFU Cache
    // this.latestResourceCache = new java.util.WeakHashMap<SmartId,
    // AbstractContentResource>(
    // this.latestResourceCacheSize);
    // this.closedResourceCache = new java.util.WeakHashMap<SmartId,
    // AbstractContentResource>(
    // this.closedResourceCacheSize);

  }

  protected AbstractDataObject getFromCache(DataId id) throws DataException {

    AbstractDataObject result = null;
    long oid = id.getObjectId();
    if (oid < DataId.OBJECT_ID_MINIMUM_CUSTOM) {
      if (oid == DataClass.CLASS_ID) {
        result = getReflectionService().getDataClass(id);
      } else if (oid == DataField.CLASS_ID) {
        result = getReflectionService().getDataField(id);
      } else {
        // TODO: illegal ID
        throw new ObjectNotFoundException(DataObject.class, id);
      }
    } else {
      // resource
      long cid = id.getClassId();
      if (cid < ContentResource.CLASS_ID) {
        // internal entity
        // TODO: should this be handled different at all?
      } else {
        // resource
        AbstractDataEntityResource resource = null;
        if (id.getRevision() == 0) {
          resource = this.latestResourceCache.get(id);
        } else {
          resource = this.closedResourceCache.get(id);
        }
      }
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  public DataObject getById(DataId id) throws DataException {

    DataObject result = getFromCache(id);
    // TODO: read object from persistent store...
    if (result == null) {
      throw new ObjectNotFoundException(DataObject.class, id);
    }
    return result;
  }

  protected abstract AbstractDataEntityResource readResource(DataId id) throws DataException;

  /**
   * {@inheritDoc}
   */
  public void move(ContentResource entity, ContentResource newParent) throws DataException {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  public void rename(DataObject entity, String newName) throws DataException {

    // TODO Auto-generated method stub

  }

}
