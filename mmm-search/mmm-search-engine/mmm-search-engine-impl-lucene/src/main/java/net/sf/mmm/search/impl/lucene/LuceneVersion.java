/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.impl.lucene;

import org.apache.lucene.util.Version;

/**
 * This is the interface of a micro-component that {@link #getLuceneVersion()
 * defines} the version of apache lucene.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface LuceneVersion {

  /**
   * This method gets the lucene {@link Version} to use.
   * 
   * @return the {@link Version}.
   */
  Version getLuceneVersion();

}
