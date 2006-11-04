/* $Id$ */
package net.sf.mmm.content.value.api;

import java.util.Collection;

import net.sf.mmm.util.xml.api.XmlSerializable;

/**
 * This is the interface for a set of meta-data values. Each meta-data value is
 * uniquely identified by a {@link MetaDataKey}that is a tuple of a namespace
 * and an unqualified key. <br>
 * As example <code>m-m-m.sf.net::author</code> is a qualified meta-data key
 * that uniquely identifies a meta-data value that could be the name of the
 * author of the object that owns the according meta-data set.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface MetaDataSet extends XmlSerializable {

  /**
   * The {@link net.sf.mmm.value.api.ValueManager#getName() name} of this
   * value type.
   */
  String VALUE_NAME = "Metadata";

  /**
   * The top-level tag of the XML representation. The only children allowed are
   * 0-N {@link MutableMetaDataSetIF#XML_TAG_VALUE}elements.
   */
  String XML_TAG_META_DATA = "metaData";

  /**
   * The value tag of the XML representation. Must have exactly one occurance of
   * the attributes {@link MutableMetaDataSetIF#XML_ATR_VALUE_NAMESPACE}and
   * {@link MutableMetaDataSetIF#XML_ATR_VALUE_KEY}. The value of the meta-data
   * set specified by the namespace and the key attribute must be embeded as
   * text inside the value element.
   */
  String XML_TAG_VALUE = "value";

  /** The namespace attribute of the value tag. */
  String XML_ATR_VALUE_NAMESPACE = "namespace";

  /** The key attribute of the value tag. */
  String XML_ATR_VALUE_KEY = "key";

  /**
   * This method gets a meta-data value by the given qualified meta-data key.
   * 
   * @param key
   *        is the qualified key of the requested meta-data value.
   * @return the meta data value for the given qualified key or
   *         <code>null</code> if no value is set.
   */
  String getMetaData(MetaDataKey key);

  /**
   * This method gets the list of all namespaces for which meta-data values are
   * defined.
   * 
   * @return an read-only iterator of all defined namespaces.
   */
  Collection<MetaDataKey> getKeys();

}
