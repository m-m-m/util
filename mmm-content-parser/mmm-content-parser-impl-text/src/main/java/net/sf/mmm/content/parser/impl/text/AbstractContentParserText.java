/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl.text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import net.sf.mmm.content.parser.api.ContentParserOptions;
import net.sf.mmm.content.parser.base.AbstractContentParser;
import net.sf.mmm.util.context.api.MutableGenericContext;
import net.sf.mmm.util.io.api.EncodingUtil;
import net.sf.mmm.util.io.base.EncodingUtilImpl;
import net.sf.mmm.util.xml.api.XmlUtil;
import net.sf.mmm.util.xml.base.XmlUtilImpl;

/**
 * This is the abstract base implementation of a
 * {@link net.sf.mmm.content.parser.api.ContentParser} for plain text.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentParserText extends AbstractContentParser {

  /** The mimetype. */
  public static final String KEY_MIMETYPE = "text/plain";

  /** The default extension. */
  public static final String KEY_EXTENSION = "txt";

  /** @see #getEncodingUtil() */
  private EncodingUtil encodingUtil;

  /** @see #getXmlUtil() */
  private XmlUtil xmlUtil;

  /**
   * The constructor.
   */
  public AbstractContentParserText() {

    super();
  }

  /**
   * This method gets the {@link EncodingUtil} to use.
   * 
   * @return the {@link EncodingUtil} to use.
   */
  protected EncodingUtil getEncodingUtil() {

    return this.encodingUtil;
  }

  /**
   * @param encodingUtil is the encodingUtil to set
   */
  @Inject
  public void setEncodingUtil(EncodingUtil encodingUtil) {

    getInitializationState().requireNotInitilized();
    this.encodingUtil = encodingUtil;
  }

  /**
   * This method gets the {@link XmlUtil}.
   * 
   * @return the xmlUtil
   */
  protected XmlUtil getXmlUtil() {

    return this.xmlUtil;
  }

  /**
   * This method sets the {@link XmlUtil} to use.
   * 
   * @param xmlUtil is the {@link XmlUtil} to use.
   */
  @Inject
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
    if (this.encodingUtil == null) {
      this.encodingUtil = EncodingUtilImpl.getInstance();
    }
    if (this.xmlUtil == null) {
      this.xmlUtil = XmlUtilImpl.getInstance();
    }
  }

  /**
   * This method tries to extract a property value using the given
   * <code>pattern</code> that has to produce it in the given
   * <code>{@link Matcher#group(int) group}</code>.
   * 
   * @param line is a single line read from the text.
   * @param pattern is the regular expression pattern.
   * @param group is the {@link Matcher#group(int) group} number of the property
   *        in the <code>pattern</code>.
   * @return the property or <code>null</code> if the <code>pattern</code> did
   *         NOT match.
   */
  protected String parseProperty(String line, Pattern pattern, int group) {

    Matcher m = pattern.matcher(line);
    if (m.matches()) {
      return m.group(group).trim();
    } else {
      return null;
    }
  }

  /**
   * This method checks if the property identified by <code>propertyName</code>
   * already exists. If NOT, it tries to extract it using the given
   * <code>pattern</code> that produces the property in the given
   * <code>{@link Matcher#group(int) group}</code>.
   * 
   * @param context are the properties with the collected metadata.
   * @param line is a single line read from the text.
   * @param pattern is the regular expression pattern.
   * @param propertyName is the name of the property to extract.
   * @param group is the {@link Matcher#group(int) group} number of the property
   *        in the <code>pattern</code>.
   */
  protected void parseProperty(MutableGenericContext context, String line, Pattern pattern,
      String propertyName, int group) {

    String value = context.getVariable(propertyName, String.class);
    if (value == null) {
      value = parseProperty(line, pattern, group);
      if (value != null) {
        StringBuilder buffer = new StringBuilder(value.length());
        this.xmlUtil.extractPlainText(value, buffer, null);
        context.setVariable(propertyName, buffer.toString());
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void parse(InputStream inputStream, long filesize, ContentParserOptions options,
      MutableGenericContext context) throws Exception {

    int maxBufferSize = options.getMaximumBufferSize();
    int capacity = maxBufferSize;
    if (filesize > 0) {
      if (filesize < maxBufferSize) {
        capacity = (int) filesize;
      }
    } else {
      int defaultCapacity = 128;
      if (capacity > defaultCapacity) {
        capacity = defaultCapacity;
      }
    }
    int charCapacity = capacity / 2;
    StringBuilder textBuffer = new StringBuilder(charCapacity);
    Reader reader;
    if (options.isDisableUtfDetection()) {
      reader = new InputStreamReader(inputStream, options.getEncoding());
    } else {
      reader = getEncodingUtil().createUtfDetectionReader(inputStream, options.getEncoding());
    }
    BufferedReader bufferedReader = new BufferedReader(reader);
    parse(bufferedReader, options, context, textBuffer);
    if ((filesize > 0) && (textBuffer.length() < charCapacity)) {
      getLogger().debug(
          "Wrong capacity: " + charCapacity + " text-length only: " + textBuffer.length());
    }
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

    int maxChars = options.getMaximumBufferSize() / 2;
    int charCount = 0;
    String line = bufferedReader.readLine();
    while (line != null) {
      int len = line.length();
      charCount += len;
      if (charCount > maxChars) {
        getLogger().debug("maximum buffer size reached, content truncated.");
        break;
      }
      // omit empty lines...
      if ((len > 8) || (line.trim().length() > 0)) {
        textBuffer.append(line);
        textBuffer.append('\n');
      }
      line = bufferedReader.readLine();
    }
  }

  /**
   * This method may be overridden to parse additional metadata from the
   * content.
   * 
   * @param context are the properties with the collected metadata.
   * @param line is a single line read from the text.
   */
  protected void parseLine(MutableGenericContext context, String line) {

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

}
