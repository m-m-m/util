/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.value.base;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.mmm.content.value.api.MetaDataKey;
import net.sf.mmm.content.value.api.MutableMetaDataSet;
import net.sf.mmm.value.api.ValueParseException;
import net.sf.mmm.value.base.AbstractValueManager;

/**
 * This is the default implementation of the {@link MutableMetaDataSet}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class MetaDataSetImpl extends AbstractMetaDataSet {

  /** uid for serialization */
  private static final long serialVersionUID = 3257846601736400944L;

  /** the meta-data */
  private final Map<MetaDataKey, String> metaData;

  /** @see #getKeys() */
  private final Collection<MetaDataKey> keys;

  /**
   * The constructor.
   */
  public MetaDataSetImpl() {

    super();
    this.metaData = new HashMap<MetaDataKey, String>();
    this.keys = Collections.unmodifiableCollection(this.metaData.keySet());
  }

  /**
   * The constructor.
   * 
   * @param metaDataToCopy
   *        is a map containing the meta-data.
   */
  public MetaDataSetImpl(MutableMetaDataSet metaDataToCopy) {

    this();
    Iterator<MetaDataKey> keyIterator = metaDataToCopy.getKeys().iterator();
    while (keyIterator.hasNext()) {
      MetaDataKey key = keyIterator.next();
      setMetaData(key, metaDataToCopy.getMetaData(key));
    }
  }

  /**
   * {@inheritDoc}
   */
  public Collection<MetaDataKey> getKeys() {

    return this.keys;
  }

  /**
   * {@inheritDoc}
   */
  public String getMetaData(MetaDataKey key) {

    return this.metaData.get(key);
  }

  /**
   * {@inheritDoc}
   */
  public String setMetaData(MetaDataKey key, String value) {

    return this.metaData.put(key, value);
  }

  /**
   * {@inheritDoc}
   */
  public String unsetMetaData(MetaDataKey key) {

    return this.metaData.remove(key);
  }

  /**
   * This inner class is the manager for the value.
   */
  public static class Manager extends AbstractValueManager<MutableMetaDataSet> {
    
    /**
     * The constructor
     */
    public Manager() {

      super();
    }

    /**
     * {@inheritDoc}
     */
    public String getName() {

      return VALUE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    public Class<? extends MutableMetaDataSet> getValueClass() {

      return MetaDataSetImpl.class;
    }

    /**
     * {@inheritDoc}
     */
    public MutableMetaDataSet parse(String valueAsString) throws ValueParseException {

      return null;
    }
    
  }
  
}
