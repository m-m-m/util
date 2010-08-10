/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl.xml;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Properties;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.content.parser.base.AbstractContentParser;
import net.sf.mmm.util.xml.api.ParserState;
import net.sf.mmm.util.xml.base.XmlUtilImpl;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.content.parser.api.ContentParser} interface for XML
 * documents (content with the mimetype "text/xml").<br>
 * It does NOT use a standard XML parser such as SAX or StAX to be fault
 * tolerant. Still it supports XML encoding declarations, entities, CDATA
 * sections, etc. Features like XInclude or entity-includes are (intentionally)
 * NOT supported.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Singleton
@Named
public class ContentParserXml extends AbstractContentParser {

  /** The mimetype. */
  public static final String KEY_MIMETYPE = "text/xml";

  /** The default extension. */
  public static final String KEY_EXTENSION = "xml";

  /**
   * The constructor.
   */
  public ContentParserXml() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String[] getRegistryKeys() {

    return new String[] { KEY_EXTENSION, KEY_MIMETYPE };
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void parse(InputStream inputStream, long filesize, String encoding, Properties properties)
      throws Exception {

    int maxChars = getMaximumBufferSize() / 2;
    StringBuilder textBuffer = new StringBuilder(maxChars);
    Charset defaultCharset;
    if (encoding == null) {
      defaultCharset = Charset.defaultCharset();
    } else {
      defaultCharset = Charset.forName(encoding);
    }
    Reader reader = XmlUtilImpl.getInstance().createXmlReader(inputStream, defaultCharset);
    BufferedReader bufferedReader = new BufferedReader(reader);
    parse(bufferedReader, properties, textBuffer);
    properties.setProperty(PROPERTY_KEY_TEXT, textBuffer.toString());
  }

  /**
   * This method parses the content of the given <code>bufferedReader</code> and
   * appends the textual content to the <code>textBuffer</code>. Additional
   * metadata can directly be set in the given <code>properties</code>.
   * 
   * @param bufferedReader is where to read the content from.
   * @param properties is where the metadata is collected.
   * @param textBuffer is the buffer where the textual content should be
   *        appended to.
   * @throws Exception if something goes wrong.
   */
  public void parse(BufferedReader bufferedReader, Properties properties, StringBuilder textBuffer)
      throws Exception {

    long maxChars = getMaximumBufferSize() / 2;
    ParserState parserState = null;
    String line = bufferedReader.readLine();
    while (line != null) {
      // TODO: use IoC
      parserState = XmlUtilImpl.getInstance().extractPlainText(line, textBuffer, parserState);
      if (textBuffer.length() > maxChars) {
        break;
      }
      textBuffer.append('\n');
      line = bufferedReader.readLine();
    }
  }

}
