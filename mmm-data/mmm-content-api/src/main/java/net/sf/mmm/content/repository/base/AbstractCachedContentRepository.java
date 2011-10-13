/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.repository.base;

import java.util.Map;

import javax.annotation.PostConstruct;

import net.sf.mmm.content.api.ContentException;
import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.base.AbstractContentObject;
import net.sf.mmm.content.datatype.api.ContentId;
import net.sf.mmm.content.resource.api.ContentResource;
import net.sf.mmm.content.resource.base.AbstractContentResource;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractCachedContentRepository extends AbstractContentRepository {

  /** The cache for the latest resources. */
  private Map<SmartId, AbstractContentResource> latestResourceCache;

  /** The cache for the closed revisions of resources. */
  private Map<SmartId, AbstractContentResource> closedResourceCache;

  /** @see #getLatestResourceCacheSize() */
  private int latestResourceCacheSize;

  /** @see #getClosedResourceCacheSize() */
  private int closedResourceCacheSize;

  /**
   * The constructor.
   */
  public AbstractCachedContentRepository() {

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
   * This method initializes this class. It has to be called after construction
   * and injection is completed.
   */
  @PostConstruct
  public void initialize() {

    // TODO: use LRU / LFU Cache
    // this.latestResourceCache = new java.util.WeakHashMap<SmartId,
    // AbstractContentResource>(
    // this.latestResourceCacheSize);
    // this.closedResourceCache = new java.util.WeakHashMap<SmartId,
    // AbstractContentResource>(
    // this.closedResourceCacheSize);

  }

  protected AbstractContentObject getFromCache(SmartId id) throws ContentException {

    AbstractContentObject result = null;
    long oid = id.getObjectId();
    if (oid < SmartId.OID_MINIMUM_RESOURCE) {
      if (oid == SmartId.OID_CLASS) {
        result = getContentModel().getContentClass(id);
      } else if (oid == SmartId.OID_FIELD) {
        result = getContentModel().getContentField(id);
      } else {
        // TODO: illegal ID
        throw new ContentObjectNotExistsException(id);
      }
    } else {
      // resource
      int cid = id.getContentClassId();
      if (cid < ContentResource.CLASS_ID) {
        // internal entity
        // TODO: should this be handled different at all?
      } else {
        // resource
        AbstractContentResource resource = null;
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
  public ContentObject get(ContentId id) throws ContentException {

    SmartId smartId = (SmartId) id;
    ContentObject result = getFromCache(smartId);
    // TODO: read object from persistent store...
    if (result == null) {
      throw new ContentObjectNotExistsException(id);
    }
    return result;
  }

  protected abstract AbstractContentResource readResource(SmartId id) throws ContentException;

  /**
   * {@inheritDoc}
   */
  public void move(ContentResource entity, ContentResource newParent) throws ContentException {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  public void rename(ContentObject entity, String newName) throws ContentException {

    // TODO Auto-generated method stub

  }

}
