/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.base;

import java.io.InputStream;
import java.util.Properties;

import javax.annotation.Resource;

import net.sf.mmm.content.parser.api.ContentParser;
import net.sf.mmm.util.component.base.AbstractLoggable;

/**
 * This is the abstract base implementation of a {@link ContentParser} that
 * supports the {@link LimitBufferSize} feature.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentParser extends AbstractLoggable implements ContentParser,
    LimitBufferSize {

  /** @see #getMaximumBufferSize() */
  private int maximumBufferSize;

  /** @see #doInitialize() */
  private ContentParserRegistrar contentParserRegistrar;

  /**
   * The constructor.
   */
  public AbstractContentParser() {

    super();
    this.maximumBufferSize = DEFAULT_MAX_BUFFER_SIZE;
  }

  /**
   * @param contentParserRegistrar is the contentParserRegistrar to set
   */
  @Resource
  public void setContentParserRegistrar(ContentParserRegistrar contentParserRegistrar) {

    getInitializationState().requireNotInitilized();
    this.contentParserRegistrar = contentParserRegistrar;
  }

  /**
   * This method gets the keys used to register this {@link ContentParser}.
   * 
   * @see ContentParserRegistrar#addParser(ContentParser, String...)
   * 
   * @return the keys (extensions and mimetypes) of this {@link ContentParser}.
   */
  public abstract String[] getRegistryKeys();

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.contentParserRegistrar != null) {
      this.contentParserRegistrar.addParser(this, getRegistryKeys());
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

    this.maximumBufferSize = maxBytes;
  }

  /**
   * {@inheritDoc}
   */
  public final Properties parse(InputStream inputStream, long filesize) throws Exception {

    return parse(inputStream, filesize, null);
  }

  /**
   * {@inheritDoc}
   */
  public final Properties parse(InputStream inputStream, long filesize, String encoding)
      throws Exception {

    Properties properties = new Properties();
    try {
      parse(inputStream, filesize, encoding, properties);
    } finally {
      inputStream.close();
    }
    return properties;
  }

  /**
   * @see ContentParser#parse(InputStream, long)
   * 
   * @param inputStream is the fresh input stream of the content to parse.
   * @param filesize is the size (content-length) of the content to parse in
   *        bytes or <code>0</code> if NOT available (unknown). If available,
   *        the parser may use this value for optimized allocations.
   * @param encoding is the explicit encoding to use for text files or
   *        <code>null</code> to use smart guess.
   * @param properties are the properties where the extracted (meta-)data from
   *        the parsed <code>inputStream</code> will be added to.
   * @throws Exception if the operation fails for arbitrary reasons.
   */
  protected abstract void parse(InputStream inputStream, long filesize, String encoding,
      Properties properties) throws Exception;

}