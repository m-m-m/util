/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.parser.base;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.search.parser.api.ContentParser;
import net.sf.mmm.search.parser.api.ContentParserService;

/**
 * This is the abstract base implemenation of the {@link ContentParserService}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentParserService implements ContentParserService, LimitBufferSize {

  /** @see #getGenericParser() */
  private ContentParser genericParser;

  /** @see #getParser(String) */
  private final Map<String, ContentParser> ext2parserMap;

  /** @see #getMaximumBufferSize() */
  private int maximumBufferSize;

  /**
   * The constructor.
   */
  public AbstractContentParserService() {

    super();
    this.ext2parserMap = new HashMap<String, ContentParser>();
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
    for (ContentParser parser : this.ext2parserMap.values()) {
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
   * @param defaultParser
   *        is the generic Parser to set.
   */
  public void setGenericParser(ContentParser defaultParser) {

    this.genericParser = defaultParser;
  }

  /**
   * {@inheritDoc}
   */
  public ContentParser getParser(String fileExtension) {

    return this.ext2parserMap.get(fileExtension);
  }

  /**
   * This method registeres the given <code>parser</code> for the given
   * <code>extension</code>.
   * 
   * @see #getParser(String)
   * 
   * @param parser
   *        is the parser to register.
   * @param extensions
   *        are the extensions the parser will be associated with.
   */
  public void addParser(ContentParser parser, String... extensions) {

    if (extensions.length == 0) {
      throw new IllegalArgumentException("At least one extension is required!");
    }
    for (int i = 0; i < extensions.length; i++) {
      if (this.ext2parserMap.containsKey(extensions[i])) {
        throw new IllegalArgumentException("Parser for extension '" + extensions[i]
            + "'already registered!");
      }
      if (parser instanceof LimitBufferSize) {
        ((LimitBufferSize) parser).setMaximumBufferSize(this.maximumBufferSize);
      }
      this.ext2parserMap.put(extensions[i], parser);
    }
  }

}
