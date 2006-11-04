/* $Id$ */
package net.sf.mmm.content.value.base;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.mmm.content.value.api.MetaDataKey;
import net.sf.mmm.content.value.api.MutableMetaDataSetIF;

/**
 * This is the default implementation of the {@link MutableMetaDataSetIF}
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
  public MetaDataSetImpl(MutableMetaDataSetIF metaDataToCopy) {

    this();
    Iterator<MetaDataKey> keyIterator = metaDataToCopy.getKeys().iterator();
    while (keyIterator.hasNext()) {
      MetaDataKey key = keyIterator.next();
      setMetaData(key, metaDataToCopy.getMetaData(key));
    }
  }

  /**
   * @see net.sf.mmm.content.value.api.MetaDataSet#getKeys()
   */
  public Collection<MetaDataKey> getKeys() {

    return this.keys;
  }

  /**
   * @see net.sf.mmm.content.value.api.MetaDataSet#getMetaData(net.sf.mmm.content.value.api.MetaDataKey)
   */
  public String getMetaData(MetaDataKey key) {

    return this.metaData.get(key);
  }

  /**
   * @see net.sf.mmm.content.value.api.MutableMetaDataSetIF#setMetaData(net.sf.mmm.content.value.api.MetaDataKey,
   *      java.lang.String)
   */
  public String setMetaData(MetaDataKey key, String value) {

    return this.metaData.put(key, value);
  }

  /**
   * @see net.sf.mmm.content.value.api.MutableMetaDataSetIF#unsetMetaData(net.sf.mmm.content.value.api.MetaDataKey)
   */
  public String unsetMetaData(MetaDataKey key) {

    return this.metaData.remove(key);
  }

}
