/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.repository.base;

import java.util.Map;

import javax.annotation.PostConstruct;

import net.sf.mmm.content.api.ContentException;
import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.repository.api.ContentObjectNotExistsException;
import net.sf.mmm.content.resource.api.ContentResource;
import net.sf.mmm.content.resource.base.AbstractContentResource;
import net.sf.mmm.content.value.api.ContentId;
import net.sf.mmm.content.value.base.SmartId;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
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

  /**
   * {@inheritDoc}
   */
  public ContentObject get(ContentId id) throws ContentException {

    ContentObject result = null;
    SmartId smartId = (SmartId) id;
    if (smartId.getObjectId() == SmartId.OID_CLASS) {
      result = getContentModel().getContentClass(id);
    } else if (smartId.getObjectId() == SmartId.OID_FIELD) {
      result = getContentModel().getContentField(id);
    } else if (smartId.getClassId() < ContentResource.CLASS_ID) {
      // internal entity
    } else {
      // resource
      AbstractContentResource resource = null;
      if (smartId.getRevision() == 0) {
        resource = this.latestResourceCache.get(id);
      } else {
        resource = this.closedResourceCache.get(id);
      }
      if (resource == null) {
        resource = readResource(smartId);
        if (smartId.getRevision() == 0) {
          this.latestResourceCache.put(smartId, resource);
        } else {
          this.closedResourceCache.put(smartId, resource);
        }
      }
      result = resource;
    }
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
