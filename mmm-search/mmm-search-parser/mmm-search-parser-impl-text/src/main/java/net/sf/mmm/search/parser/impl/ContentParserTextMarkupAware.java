/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.parser.impl;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.parser.api.ContentParser} interface for text that
 * may contain markup (e.g. content of the type "jsp" or "php").
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentParserTextMarkupAware extends AbstractContentParserText {

  /**
   * The constructor.
   */
  public ContentParserTextMarkupAware() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String[] getRegistryKeys() {

    return new String[] { "php", "jsp", "jinc", "asp" };
  }

}
