/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api.state;

import java.io.Flushable;

import net.sf.mmm.util.xml.base.jaxb.JaxbBeanHolder;

/**
 * This is the interface for a container {@link #getBean() holding} the
 * {@link SearchIndexerState}.
 * 
 * @see JaxbBeanHolder
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchIndexerStateHolder extends JaxbBeanHolder<SearchIndexerState>, Flushable {

  /**
   * This method saves the {@link SearchIndexerState}.
   */
  void flush();

}
