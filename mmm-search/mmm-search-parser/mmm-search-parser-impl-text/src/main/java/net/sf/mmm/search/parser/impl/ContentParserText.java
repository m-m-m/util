/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.parser.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import com.glaforge.i18n.io.SmartEncodingInputStream;

import net.sf.mmm.search.parser.base.AbstractContentParser;
import net.sf.mmm.util.io.api.EncodingUtil;
import net.sf.mmm.util.io.base.EncodingUtilImpl;
import net.sf.mmm.util.xml.base.XmlUtilImpl;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.parser.api.ContentParser} interface for text
 * (content with the mimetype "text/plain").
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentParserText extends AbstractContentParser {

  /** @see #getDefaultCapacity() */
  private int defaultCapacity;

  /** @see #getEncodingUtil() */
  private EncodingUtil encodingUtil;

  /**
   * The constructor.
   */
  public ContentParserText() {

    super();
    this.defaultCapacity = 1024;
  }

  /**
   * @see #setDefaultCapacity(int)
   * 
   * @return the defaultBufferSize.
   */
  public int getDefaultCapacity() {

    return this.defaultCapacity;
  }

  /**
   * If content is {@link #parse(InputStream, long) parsed} the capacity of the
   * used {@link StringBuffer} is set to
   * <code>{@link #getMaximumBufferSize() maximumBufferSize}/2</code> limited to
   * the <code>filesize</code>. If the <code>filesize</code> is undefined, this
   * value is used as limit.<br>
   * Since textual files are typically quite small, you should NOT use a too
   * large value here. The default is <code>1024</code>.
   * 
   * @param newBufferSize the defaultBufferSize to set.
   */
  public void setDefaultCapacity(int newBufferSize) {

    getInitializationState().requireNotInitilized();
    this.defaultCapacity = newBufferSize;
  }

  /**
   * This method gets the {@link EncodingUtilImpl} to use.
   * 
   * @return the {@link EncodingUtilImpl} to use.
   */
  protected EncodingUtil getEncodingUtil() {

    return this.encodingUtil;
  }

  /**
   * @param encodingUtil is the encodingUtil to set
   */
  @Resource
  public void setEncodingUtil(EncodingUtil encodingUtil) {

    getInitializationState().requireNotInitilized();
    this.encodingUtil = encodingUtil;
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
   * @param properties are the properties with the collected metadata.
   * @param line is a single line read from the text.
   * @param pattern is the regular expression pattern.
   * @param propertyName is the name of the property to extract.
   * @param group is the {@link Matcher#group(int) group} number of the property
   *        in the <code>pattern</code>.
   */
  protected void parseProperty(Properties properties, String line, Pattern pattern,
      String propertyName, int group) {

    String value = properties.getProperty(propertyName);
    if (value == null) {
      value = parseProperty(line, pattern, group);
      if (value != null) {
        StringBuilder buffer = new StringBuilder(value.length());
        // TODO: use IoC
        XmlUtilImpl.getInstance().extractPlainText(value, buffer, null);
        properties.setProperty(propertyName, buffer.toString());
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void parse(InputStream inputStream, long filesize, String encoding, Properties properties)
      throws Exception {

    int maxBufferSize = getMaximumBufferSize();
    int maxChars = maxBufferSize / 2;
    int capacity = maxChars;
    if (filesize > 0) {
      if (filesize < capacity) {
        capacity = (int) filesize;
      }
    } else {
      if (capacity > this.defaultCapacity) {
        capacity = this.defaultCapacity;
      }
    }
    StringBuilder textBuffer = new StringBuilder(capacity);
    int bufferLength = 4096;
    if (bufferLength > maxBufferSize) {
      bufferLength = maxBufferSize;
    }
    if ((bufferLength > filesize) && (filesize > 0)) {
      bufferLength = (int) filesize;
    }
    Reader reader;
    if (encoding == null) {
      SmartEncodingInputStream smartIS = new SmartEncodingInputStream(inputStream, bufferLength);
      reader = smartIS.getReader();
    } else {
      reader = new InputStreamReader(inputStream, encoding);
    }
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

    int maxChars = getMaximumBufferSize() / 2;
    int charCount = 0;
    String line = bufferedReader.readLine();
    while (line != null) {
      int len = line.length();
      charCount += len;
      if (charCount > maxChars) {
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
   * @param properties are the properties with the collected metadata.
   * @param line is a single line read from the text.
   */
  protected void parseLine(Properties properties, String line) {

  }

}
