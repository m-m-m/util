/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.trash;

import java.util.HashMap;
import java.util.Map;


/**
 * This is the abstract base implementation of the {@link MetaDataSet}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractMetaDataSet implements MetaDataSet {

  /** @see #getMetaData(String) */
  private final Map<String, MutableMetaData> metadataMap;

  /**
   * The constructor.
   */
  public AbstractMetaDataSet() {

    super();
    this.metadataMap = new HashMap<String, MutableMetaData>();
  }

  /**
   * This method sets the given <code>metaData</code> for the given
   * <code>namespace</code>.
   * 
   * @see #getMetaData(String)
   * 
   * @param namespace is the namespace to use.
   * @param metaData is the metadata to set.
   */
  public void setMetaData(String namespace, MutableMetaData metaData) {

    this.metadataMap.put(namespace, metaData);
  }

  /**
   * {@inheritDoc}
   */
  public MutableMetaData getMetaData(String namespace) {

    // TODO: synchronize / semaphore!
    // TODO: make metadata immutable if object locked!
    MutableMetaData metadata = this.metadataMap.get(namespace);
    if (metadata == null) {
      metadata = loadMetaData(namespace);
      this.metadataMap.put(namespace, metadata);
    }
    return metadata;
  }

  /**
   * This method creates the metadata for the given namespace.
   * 
   * @param namespace is the namespace for which the metadata is requested.
   * @return the metadata for the given namespace.
   */
  protected abstract MutableMetaData loadMetaData(String namespace);

}
