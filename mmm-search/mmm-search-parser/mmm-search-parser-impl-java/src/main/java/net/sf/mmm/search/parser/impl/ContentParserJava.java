/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.parser.impl;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.parser.api.ContentParser} interface for Java sources
 * (content with the mimetype "text/java-source").
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentParserJava extends AbstractContentParserTextMarkupAware {

  /** The mimetype. */
  public static final String KEY_MIMETYPE = "text/java-source";

  /** The default extension. */
  public static final String KEY_EXTENSION = "java";

  /** the string used to separate package names */
  private static final String PACKAGE_SEPARATOR = ".";

  /** pattern to extract the package name */
  private static final Pattern PACKAGE_PATTERN = Pattern.compile("package\\s+([\\w\\.]*).*");

  /** pattern to extract the class name */
  private static final Pattern CLASS_PATTERN = Pattern
      .compile("[\\sa-z]*(class|interface)\\s+(\\w*).*");

  /** pattern to extract the author */
  private static final Pattern AUTHOR_PATTERN = Pattern
      .compile("[\\s/*]*@author\\s+(<[^>]*>)?\"?([^(</\"]*).*");

  /**
   * The constructor.
   */
  public ContentParserJava() {

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
  protected void parseLine(Properties properties, String line) {

    String title = properties.getProperty(PROPERTY_KEY_TITLE);
    if (title == null) {
      Matcher m = PACKAGE_PATTERN.matcher(line);
      if (m.matches()) {
        title = m.group(1) + PACKAGE_SEPARATOR;
        properties.setProperty(PROPERTY_KEY_TITLE, title);
      }
    }
    if ((title != null) && (title.endsWith(PACKAGE_SEPARATOR))) {
      Matcher m = CLASS_PATTERN.matcher(line);
      if (m.matches()) {
        title = title + m.group(2);
        properties.setProperty(PROPERTY_KEY_TITLE, title);
      }
      // author tag can only appear before class name was detected...

      String author = parseProperty(line, AUTHOR_PATTERN, 2);
      if (author != null) {
        String authorProperty = properties.getProperty(PROPERTY_KEY_AUTHOR);
        if (authorProperty == null) {
          authorProperty = author;
        } else {
          authorProperty = authorProperty + ", " + author;
        }
        properties.setProperty(PROPERTY_KEY_AUTHOR, authorProperty);
      }
    }
  }

}
