/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl.xml;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.content.parser.api.ContentParserOptions;
import net.sf.mmm.content.parser.base.AbstractContentParser;
import net.sf.mmm.util.context.api.MutableGenericContext;
import net.sf.mmm.util.xml.api.ParserState;
import net.sf.mmm.util.xml.api.XmlUtil;
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

  /** @see #getXmlUtil() */
  private XmlUtil xmlUtil;

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
  protected void doInitialize() {

    super.doInitialize();
    if (this.xmlUtil == null) {
      this.xmlUtil = XmlUtilImpl.getInstance();
    }
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
  @Inject
  public void setXmlUtil(XmlUtil xmlUtil) {

    getInitializationState().requireNotInitilized();
    this.xmlUtil = xmlUtil;
  }

  /**
   * {@inheritDoc}
   */
  public String getExtension() {

    return KEY_EXTENSION;
  }

  /**
   * {@inheritDoc}
   */
  public String getMimetype() {

    return KEY_MIMETYPE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String[] getAlternativeKeyArray() {

    return new String[] { "application/xml" };
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String[] getSecondaryKeyArray() {

    return new String[] { "xsl", "text/xslt", "text/xslt+xml" };
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void parse(InputStream inputStream, long filesize, ContentParserOptions options,
      MutableGenericContext context) throws Exception {

    int maxChars = options.getMaximumBufferSize() / 2;
    StringBuilder textBuffer = new StringBuilder(maxChars);
    Charset defaultCharset;
    String encoding = options.getEncoding();
    if (encoding == null) {
      defaultCharset = Charset.defaultCharset();
    } else {
      defaultCharset = Charset.forName(encoding);
    }
    Reader reader = XmlUtilImpl.getInstance().createXmlReader(inputStream, defaultCharset);
    BufferedReader bufferedReader = new BufferedReader(reader);
    parse(bufferedReader, options, context, textBuffer);
    context.setVariable(VARIABLE_NAME_TEXT, textBuffer.toString());
  }

  /**
   * This method parses the content of the given <code>bufferedReader</code> and
   * appends the textual content to the <code>textBuffer</code>. Additional
   * metadata can directly be set in the given <code>properties</code>.
   * 
   * @param bufferedReader is where to read the content from.
   * @param options are the {@link ContentParserOptions}.
   * @param context is where the metadata is collected.
   * @param textBuffer is the buffer where the textual content should be
   *        appended to.
   * @throws Exception if something goes wrong.
   */
  public void parse(BufferedReader bufferedReader, ContentParserOptions options,
      MutableGenericContext context, StringBuilder textBuffer) throws Exception {

    long maxChars = options.getMaximumBufferSize() / 2;
    ParserState parserState = null;
    String line = bufferedReader.readLine();
    while (line != null) {
      int len = textBuffer.length();
      line = line.trim();
      parserState = this.xmlUtil.extractPlainText(line, textBuffer, parserState);
      if (textBuffer.length() > maxChars) {
        break;
      }
      if (textBuffer.length() > len) {
        textBuffer.append('\n');
      }
      line = bufferedReader.readLine();
    }
  }

}
