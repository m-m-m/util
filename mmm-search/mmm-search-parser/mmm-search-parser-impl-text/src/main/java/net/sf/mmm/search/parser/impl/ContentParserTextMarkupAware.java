/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.parser.impl;

import java.io.BufferedReader;
import java.util.Properties;

import net.sf.mmm.util.xml.MarkupUtil;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.parser.api.ContentParser} interface for plain text
 * (e.g. content with the mimetype "text/plain") that may contain markup.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentParserTextMarkupAware extends ContentParserText {

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
  public void parse(BufferedReader bufferedReader, Properties properties, StringBuffer textBuffer)
      throws Exception {

    long maxChars = getMaximumBufferSize() / 2;
    MarkupUtil.ParserState parserState = null;
    String line = bufferedReader.readLine();
    while (line != null) {
      parseLine(properties, line);
      parserState = MarkupUtil.extractPlainText(line, textBuffer, parserState);
      if (textBuffer.length() > maxChars) {
        break;
      }
      textBuffer.append('\n');
      line = bufferedReader.readLine();
    }
  }

}
