/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import net.sf.mmm.content.parser.api.ContentParser;
import net.sf.mmm.content.parser.api.ContentParserService;
import net.sf.mmm.util.component.api.ResourceMissingException;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.exception.api.DuplicateObjectException;
import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;
import net.sf.mmm.util.exception.api.NlsNullPointerException;

/**
 * This is the abstract base implementation of the {@link ContentParserService}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentParserService extends AbstractLoggableComponent implements
    ContentParserService {

  /** @see #setContentParsers(List) */
  private List<AbstractContentParser> contentParsers;

  /** @see #getGenericParser() */
  private ContentParser genericParser;

  /**
   * @see #getParser(String)
   * @see ContentParser#getExtension()
   * @see ContentParser#getMimetype()
   */
  private final Map<String, ContentParser> primaryKey2parserMap;

  /**
   * @see #getParser(String)
   * @see AbstractContentParser#getPrimaryKeys()
   */
  private Map<String, ContentParser> secondaryKey2parserMap;

  /**
   * The constructor.
   */
  public AbstractContentParserService() {

    super();
    this.primaryKey2parserMap = new HashMap<String, ContentParser>();
    this.secondaryKey2parserMap = new HashMap<String, ContentParser>();
    this.contentParsers = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.contentParsers == null) {
      throw new ResourceMissingException("contentParsers");
    }
    for (AbstractContentParser parser : this.contentParsers) {
      String extension = parser.getExtension();
      String mimetype = parser.getMimetype();
      if (extension == null) {
        if (mimetype != null) {
          getLogger().warn("Illegal generic parser - both extension and mimetype must be null!");
        }
        if (this.genericParser == null) {
          this.genericParser = parser;
        } else {
          getLogger().warn("Multiple generic parsers injected!");
        }
      } else {
        Set<String> primaryKeys = parser.getPrimaryKeys();
        if (primaryKeys == null) {
          throw new NlsNullPointerException(ContentParser.class.getSimpleName() + "[" + extension
              + "].primaryKeys");
        }
        for (String key : primaryKeys) {
          ContentParser old = this.primaryKey2parserMap.put(key, parser);
          if (old != null) {
            throw new DuplicateObjectException(parser, key);
          }
        }
        assert primaryKeys.contains(extension);
        if (mimetype == null) {
          getLogger().debug("No mimetype defined for parser with extension " + extension);
        } else {
          assert primaryKeys.contains(mimetype);
        }
      }
      Set<String> secondaryKeys = parser.getSecondaryKeys();
      if (secondaryKeys != null) {
        for (String key : secondaryKeys) {
          ContentParser old = this.secondaryKey2parserMap.put(key, parser);
          if (old != null) {
            throw new DuplicateObjectException(parser, key);
          }
        }
      }
    }
    if (this.genericParser == null) {
      throw new ResourceMissingException("genericParser");
    }

  }

  /**
   * {@inheritDoc}
   */
  public ContentParser getGenericParser() {

    return this.genericParser;
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
      if (parser == null) {
        parser = this.genericParser;
      }
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
      throw new NlsIllegalArgumentException("keys.length (0)");
    }
    for (int i = 0; i < keys.length; i++) {
      if (this.primaryKey2parserMap.containsKey(keys[i])) {
        throw new DuplicateObjectException(parser, keys[i]);
      }
      this.primaryKey2parserMap.put(keys[i], parser);
    }
  }

}
