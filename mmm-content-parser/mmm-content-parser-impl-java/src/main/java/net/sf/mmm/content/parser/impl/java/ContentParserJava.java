/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl.java;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.content.parser.impl.text.AbstractContentParserTextMarkupAware;
import net.sf.mmm.util.context.api.MutableGenericContext;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.content.parser.api.ContentParser} interface for Java
 * sources (content with the mimetype "text/java-source").
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Singleton
@Named
public class ContentParserJava extends AbstractContentParserTextMarkupAware {

  /** The mimetype. */
  @SuppressWarnings("hiding")
  public static final String KEY_MIMETYPE = "text/java-source";

  /** The default extension. */
  @SuppressWarnings("hiding")
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
  public String getExtension() {

    return KEY_EXTENSION;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getMimetype() {

    return KEY_MIMETYPE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void parseLine(MutableGenericContext context, String line) {

    String title = context.getVariable(VARIABLE_NAME_TITLE, String.class);
    if (title == null) {
      Matcher m = PACKAGE_PATTERN.matcher(line);
      if (m.matches()) {
        title = m.group(1) + PACKAGE_SEPARATOR;
        context.setVariable(VARIABLE_NAME_TITLE, title);
      }
    }
    if ((title != null) && (title.endsWith(PACKAGE_SEPARATOR))) {
      Matcher m = CLASS_PATTERN.matcher(line);
      if (m.matches()) {
        title = title + m.group(2);
        context.setVariable(VARIABLE_NAME_TITLE, title);
      }
      // author tag can only appear before class name was detected...

      String author = parseProperty(line, AUTHOR_PATTERN, 2);
      if (author != null) {
        String authorProperty = context.getVariable(VARIABLE_NAME_CREATOR, String.class);
        if (authorProperty == null) {
          authorProperty = author;
        } else {
          authorProperty = authorProperty + ", " + author;
        }
        context.setVariable(VARIABLE_NAME_CREATOR, authorProperty);
      }
    }
  }

}
