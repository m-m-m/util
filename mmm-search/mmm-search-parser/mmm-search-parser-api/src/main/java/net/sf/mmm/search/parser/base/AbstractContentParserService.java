/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.parser.base;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.search.parser.api.ContentParser;
import net.sf.mmm.search.parser.api.ContentParserService;
import net.sf.mmm.util.component.base.AbstractLoggable;
import net.sf.mmm.util.nls.api.DuplicateObjectException;

/**
 * This is the abstract base implementation of the {@link ContentParserService}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentParserService extends AbstractLoggable implements
    ContentParserService, LimitBufferSize {

  /** @see #getGenericParser() */
  private ContentParser genericParser;

  /** @see #getParser(String) */
  private final Map<String, ContentParser> key2parserMap;

  /** @see #getMaximumBufferSize() */
  private int maximumBufferSize;

  /**
   * The constructor.
   */
  public AbstractContentParserService() {

    super();
    this.key2parserMap = new HashMap<String, ContentParser>();
    this.maximumBufferSize = DEFAULT_MAX_BUFFER_SIZE;
  }

  /**
   * {@inheritDoc}
   */
  public int getMaximumBufferSize() {

    return this.maximumBufferSize;
  }

  /**
   * {@inheritDoc}
   */
  public void setMaximumBufferSize(int maxBytes) {

    this.maximumBufferSize = maxBytes;
    updateBufferSize();
  }

  /**
   * This method updates the {@link #setMaximumBufferSize(int) buffer size}.
   */
  private void updateBufferSize() {

    int bufferSize = this.maximumBufferSize;
    for (ContentParser parser : this.key2parserMap.values()) {
      if (parser instanceof LimitBufferSize) {
        ((LimitBufferSize) parser).setMaximumBufferSize(bufferSize);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public ContentParser getGenericParser() {

    return this.genericParser;
  }

  /**
   * @param defaultParser is the generic Parser to set.
   */
  public void setGenericParser(ContentParser defaultParser) {

    this.genericParser = defaultParser;
  }

  /**
   * {@inheritDoc}
   */
  public ContentParser getParser(String key) {

    return this.key2parserMap.get(key);
  }

  /**
   * This method registers the given <code>parser</code> for the given
   * <code>extension</code>.
   * 
   * @see #getParser(String)
   * 
   * @param parser is the parser to register.
   * @param keys are the extensions the parser will be associated with.
   */
  public void addParser(ContentParser parser, String... keys) {

    // getInitializationState().requireNotInitilized();
    if (keys.length == 0) {
      throw new IllegalArgumentException("At least one extension is required!");
    }
    for (int i = 0; i < keys.length; i++) {
      if (this.key2parserMap.containsKey(keys[i])) {
        throw new DuplicateObjectException(parser, keys[i]);
      }
      if (parser instanceof LimitBufferSize) {
        ((LimitBufferSize) parser).setMaximumBufferSize(this.maximumBufferSize);
      }
      this.key2parserMap.put(keys[i], parser);
    }
  }

}
