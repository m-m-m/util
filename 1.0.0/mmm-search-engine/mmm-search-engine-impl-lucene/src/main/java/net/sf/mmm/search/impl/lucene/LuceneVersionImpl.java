/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.impl.lucene;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.component.base.AbstractComponent;

import org.apache.lucene.util.Version;

/**
 * This is the implementation of the {@link LuceneVersion} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named
public class LuceneVersionImpl extends AbstractComponent implements LuceneVersion {

  /** @see #getLuceneVersion() */
  private Version luceneVersion;

  /**
   * The constructor.
   */
  public LuceneVersionImpl() {

    super();
    this.luceneVersion = Version.LUCENE_30;
  }

  /**
   * {@inheritDoc}
   */
  public Version getLuceneVersion() {

    return this.luceneVersion;
  }

  /**
   * @param luceneVersion is the luceneVersion to set
   */
  public void setLuceneVersion(Version luceneVersion) {

    getInitializationState().requireNotInitilized();
    this.luceneVersion = luceneVersion;
  }

}
