/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl.text;

import java.io.BufferedReader;
import java.util.Properties;

import net.sf.mmm.util.xml.api.ParserState;
import net.sf.mmm.util.xml.api.XmlUtil;

/**
 * This is the abstract base implementation of a
 * {@link net.sf.mmm.content.parser.api.ContentParser} for text that may contain
 * markup.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentParserTextMarkupAware extends AbstractContentParserText {

  /**
   * The constructor.
   */
  public AbstractContentParserTextMarkupAware() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void parse(BufferedReader bufferedReader, Properties properties, StringBuilder textBuffer)
      throws Exception {

    long maxChars = getMaximumBufferSize() / 2;
    ParserState parserState = null;
    XmlUtil xmlUtil = getXmlUtil();
    String line = bufferedReader.readLine();
    while (line != null) {
      parseLine(properties, line);
      parserState = xmlUtil.extractPlainText(line, textBuffer, parserState);
      if (textBuffer.length() > maxChars) {
        break;
      }
      textBuffer.append('\n');
      line = bufferedReader.readLine();
    }
  }

}
