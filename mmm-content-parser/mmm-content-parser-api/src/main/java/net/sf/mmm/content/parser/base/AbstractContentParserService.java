/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import net.sf.mmm.content.parser.api.ContentParser;
import net.sf.mmm.content.parser.api.ContentParserService;
import net.sf.mmm.util.component.api.ResourceMissingException;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;

/**
 * This is the abstract base implementation of the {@link ContentParserService}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentParserService extends AbstractLoggableComponent implements
    ContentParserService, LimitBufferSize {

  /** @see #getGenericParser() */
  private ContentParserGeneric genericParser;

  /** @see #setContentParsers(List) */
  private List<AbstractContentParser> contentParsers;

  /** @see #getParser(String) */
  private final Map<String, ContentParser> primaryKey2parserMap;

  /** @see #doInitialize() */
  private Map<String, ContentParser> secondaryKey2parserMap;

  /** @see #getMaximumBufferSize() */
  private int maximumBufferSize;

  /**
   * The constructor.
   */
  public AbstractContentParserService() {

    super();
    this.primaryKey2parserMap = new HashMap<String, ContentParser>();
    this.secondaryKey2parserMap = new HashMap<String, ContentParser>();
    this.maximumBufferSize = DEFAULT_MAX_BUFFER_SIZE;
    this.genericParser = null;
    this.contentParsers = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.genericParser == null) {
      throw new ResourceMissingException("genericParser");
    }
    if (this.contentParsers == null) {
      throw new ResourceMissingException("contentParsers");
    }
    for (AbstractContentParser parser : this.contentParsers) {
      parser.setMaximumBufferSize(this.maximumBufferSize);
      if (!(parser instanceof ContentParserGeneric)) {
        String[] primaryKeys = parser.getRegistryKeysPrimary();
        if ((primaryKeys == null) || (primaryKeys.length == 0)) {
          throw new NlsIllegalArgumentException(parser.getClass().getName());
        }
        for (String key : primaryKeys) {
          ContentParser old = this.primaryKey2parserMap.put(key, parser);
          if (old != null) {
            throw new DuplicateObjectException(parser, key);
          }
        }
        String[] secondaryKeys = parser.getRegistryKeysSecondary();
        if (secondaryKeys != null) {
          for (String key : secondaryKeys) {
            ContentParser old = this.secondaryKey2parserMap.put(key, parser);
            if (old != null) {
              throw new DuplicateObjectException(parser, key);
            }
          }
        }
      }
    }

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

    getInitializationState().requireNotInitilized();
    this.maximumBufferSize = maxBytes;
  }

  /**
   * {@inheritDoc}
   */
  public ContentParserGeneric getGenericParser() {

    return this.genericParser;
  }

  /**
   * @param genericParser is the generic Parser to set.
   */
  @Inject
  public void setGenericParser(ContentParserGeneric genericParser) {

    getInitializationState().requireNotInitilized();
    this.genericParser = genericParser;
  }

  /**
   * @param contentParsers is the contentParsers to set
   */
  @Inject
  public void setContentParsers(List<AbstractContentParser> contentParsers) {

    getInitializationState().requireNotInitilized();
    this.contentParsers = contentParsers;
  }

  /**
   * {@inheritDoc}
   */
  public ContentParser getParser(String key) {

    ContentParser parser = this.primaryKey2parserMap.get(key);
    if (parser == null) {
      parser = this.secondaryKey2parserMap.get(key);
    }
    return parser;
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

    // This method is called for injection after initialization
    // can NOT do: getInitializationState().requireNotInitilized();
    if (keys.length == 0) {
      // TODO NLS
      throw new NlsIllegalArgumentException("At least one extension is required!");
    }
    for (int i = 0; i < keys.length; i++) {
      if (this.primaryKey2parserMap.containsKey(keys[i])) {
        throw new DuplicateObjectException(parser, keys[i]);
      }
      if (parser instanceof LimitBufferSize) {
        ((LimitBufferSize) parser).setMaximumBufferSize(this.maximumBufferSize);
      }
      this.primaryKey2parserMap.put(keys[i], parser);
    }
  }

}
