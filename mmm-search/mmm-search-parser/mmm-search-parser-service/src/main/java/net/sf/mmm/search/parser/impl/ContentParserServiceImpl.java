/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.parser.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.search.parser.api.ContentParser;
import net.sf.mmm.search.parser.base.AbstractContentParserService;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.search.parser.api.ContentParserService} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentParserServiceImpl extends AbstractContentParserService {

  /** @see #setAlias2keyMap(Map) */
  private Map<String, String> alias2keyMap;

  /**
   * The constructor.
   */
  public ContentParserServiceImpl() {

    super();
  }

  /**
   * @param alias2keyMap is the alias2keyMap to set
   */
  public void setAlias2keyMap(Map<String, String> alias2keyMap) {

    this.alias2keyMap = alias2keyMap;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ContentParser getParser(String key) {

    ContentParser parser = super.getParser(key);
    if (parser == null) {
      String keyForAlias = this.alias2keyMap.get(key);
      if (keyForAlias != null) {
        parser = super.getParser(keyForAlias);
      }
    }
    return parser;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (getGenericParser() == null) {
      setGenericParser(new ContentParserGeneric());
    }
    if (this.alias2keyMap == null) {
      this.alias2keyMap = new HashMap<String, String>();
      this.alias2keyMap.put("htm", "html");
    }
  }

}
