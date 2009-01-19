/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.parser.impl;

import java.io.BufferedReader;
import java.util.Properties;

import javax.annotation.Resource;

import net.sf.mmm.util.xml.api.ParserState;
import net.sf.mmm.util.xml.api.XmlUtil;
import net.sf.mmm.util.xml.base.XmlUtilImpl;

/**
 * This is the abstract base implementation of a
 * {@link net.sf.mmm.search.parser.api.ContentParser} for text that may contain
 * markup.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentParserTextMarkupAware extends AbstractContentParserText {

  /** @see #getXmlUtil() */
  private XmlUtil xmlUtil;

  /**
   * The constructor.
   */
  public AbstractContentParserTextMarkupAware() {

    super();
  }

  /**
   * @return the xmlUtil
   */
  protected XmlUtil getXmlUtil() {

    return this.xmlUtil;
  }

  /**
   * @param xmlUtil is the xmlUtil to set
   */
  @Resource
  public void setXmlUtil(XmlUtil xmlUtil) {

    getInitializationState().requireNotInitilized();
    this.xmlUtil = xmlUtil;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.xmlUtil == null) {
      this.xmlUtil = XmlUtilImpl.getInstance();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void parse(BufferedReader bufferedReader, Properties properties, StringBuilder textBuffer)
      throws Exception {

    long maxChars = getMaximumBufferSize() / 2;
    ParserState parserState = null;
    String line = bufferedReader.readLine();
    while (line != null) {
      parseLine(properties, line);
      parserState = this.xmlUtil.extractPlainText(line, textBuffer, parserState);
      if (textBuffer.length() > maxChars) {
        break;
      }
      textBuffer.append('\n');
      line = bufferedReader.readLine();
    }
  }

}
