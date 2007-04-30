/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.parser.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.glaforge.i18n.io.SmartEncodingInputStream;

import net.sf.mmm.search.parser.base.AbstractContentParser;

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
   * <code>{@link #getMaximumBufferSize() maximumBufferSize}/2</code> limited
   * to the <code>filesize</code>. If the <code>filesize</code> is
   * undefined, this value is used as limit.<br>
   * Since Java sources are typically quite small, you should NOT use a too
   * large value here. The default is <code>1024</code>.
   * 
   * @param newBufferSize
   *        the defaultBufferSize to set.
   */
  public void setDefaultCapacity(int newBufferSize) {

    this.defaultCapacity = newBufferSize;
  }

  /**
   * This method may be overridden to parse additional information from the
   * content.
   * 
   * @param properties
   *        are the properties with the collected metadata.
   * @param line
   *        is a single line read from the text.
   * @return the part of the given <code>line</code> that should be added to
   *         the text or <code>null</code> if the line should be ignored.
   */
  protected String parseLine(Properties properties, String line) {

    return line;
  }

  /**
   * This method tries to extract a property value using the given
   * <code>pattern</code> that has to produce it in the given
   * <code>{@link Matcher#group(int) group}</code>.
   * 
   * @param line
   *        is a single line read from the text.
   * @param pattern
   *        is the regular expression pattern.
   * @param group
   *        is the {@link Matcher#group(int) group} number of the property in
   *        the <code>pattern</code>.
   * @return the property or <code>null</code> if the <code>pattern</code>
   *         did NOT match.
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
   * <code>pattern</code> that has to produce it in the given
   * <code>{@link Matcher#group(int) group}</code>.
   * 
   * @param properties
   *        are the properties with the collected metadata.
   * @param line
   *        is a single line read from the text.
   * @param pattern
   *        is the regular expression pattern.
   * @param propertyName
   *        is the name of the property to extract.
   * @param group
   *        is the {@link Matcher#group(int) group} number of the property in
   *        the <code>pattern</code>.
   */
  protected void parseProperty(Properties properties, String line, Pattern pattern,
      String propertyName, int group) {

    String value = properties.getProperty(propertyName);
    if (value == null) {
      value = parseProperty(line, pattern, group);
      if (value != null) {
        properties.setProperty(propertyName, value);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public void parse(InputStream inputStream, long filesize, Properties properties) throws Exception {

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
    StringBuffer textBuffer = new StringBuffer(capacity);
    int bufferLength = 4096;
    if (bufferLength > maxBufferSize) {
      bufferLength = maxBufferSize;
    }
    if ((bufferLength > filesize) && (filesize > 0)) {
      bufferLength = (int) filesize;
    }
    SmartEncodingInputStream smartIS = new SmartEncodingInputStream(inputStream, bufferLength);
    Reader reader = smartIS.getReader();
    BufferedReader bufferedReader = new BufferedReader(reader);
    String line = bufferedReader.readLine();
    int charCount = 0;
    while (line != null) {
      line = parseLine(properties, line);
      if (line != null) {
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
      }
      line = bufferedReader.readLine();
    }
    properties.setProperty(PROPERTY_KEY_TEXT, textBuffer.toString());
  }

}
