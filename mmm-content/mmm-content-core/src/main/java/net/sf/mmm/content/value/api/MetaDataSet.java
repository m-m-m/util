/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.value.api;

/**
 * This is the interface for a set of {@link MetaData}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface MetaDataSet {

  /** the namespace used for project internal metadata. */
  String METADATA_NAMESPACE_MMM = "mmm";

  /** the namespace used to reflect the fields as metadata. */
  String METADATA_NAMESPACE_NONE = "";

  /**
   * This method gets the meta-data for a given <code>namespace</code>. The
   * metadata can store arbitrary informations as string that is NOT specified
   * via the content-model.
   * 
   * @param namespace is the namespace (prefix) for which the metadata is
   *        requested. The value needs to consist only of the characters
   *        <code>'a-Z', '0-9', '.'</code> and <code>'-'</code>. Especially
   *        it must NOT contain the character <code>':'</code>. Please note
   *        that {@link #METADATA_NAMESPACE_MMM mmm} and
   *        {@link #METADATA_NAMESPACE_NONE} are reserved and can NOT be used
   *        for custom metadata.
   * @return the meta-data.
   */
  MutableMetaData getMetaData(String namespace);

}
