/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.value.api;

/**
 * This is the interface for the manager of {@link ContentId} instances.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentIdManager {

  /**
   * This method parses the given <code>idAsString</code> as a
   * {@link ContentId}. It is the inverse operation of
   * {@link ContentId#toString()}.
   * 
   * @param idAsString is the string representation of a {@link ContentId}.
   * @return the parsed ID.
   */
  ContentId getId(String idAsString);

}
