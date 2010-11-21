/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl.opendoc;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import net.sf.mmm.content.parser.api.ContentParser;
import net.sf.mmm.content.parser.api.ContentParserOptions;
import net.sf.mmm.content.parser.base.AbstractContentParser;
import net.sf.mmm.util.context.api.MutableGenericContext;
import net.sf.mmm.util.io.base.NonClosingInputStream;

/**
 * This is the abstract base-implementation of a
 * {@link net.sf.mmm.content.parser.api.ContentParser} for content of the
 * open-document format (content with the mimetypes
 * "application/vnd.oasis.opendocument.*").
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentParserOpenDoc extends AbstractContentParser {

  /** the content.xml entry of the document */
  private static final String ENTRY_CONTENT_XML = "content.xml";

  /** the meta.xml entry of the document */
  private static final String ENTRY_META_XML = "meta.xml";

  /** the styles.xml entry of the document */
  private static final String ENTRY_STYLES_XML = "styles.xml";

  /** The {@link Set} of XML tags that contain relevant text. */
  private static final Set<String> TEXT_TAGS;

  static {
    TEXT_TAGS = new HashSet<String>();
    TEXT_TAGS.add("p");
    TEXT_TAGS.add("span");
    TEXT_TAGS.add("h");
    TEXT_TAGS.add("a");
  }

  /**
   * The constructor.
   */
  public AbstractContentParserOpenDoc() {

    super();
  }

  /**
   * This method parses the metadata of the document.
   * 
   * @param inputStream is the {@link InputStream} where to read the metadata
   *        from.
   * @param options are the {@link ContentParserOptions}
   * @param context is the {@link MutableGenericContext} where the extracted
   *        metadata from the parsed <code>inputStream</code> will be
   *        {@link MutableGenericContext#setVariable(String, Object) added} to.
   * @throws Exception if the operation fails for arbitrary reasons.
   */
  protected void parseMetadata(InputStream inputStream, ContentParserOptions options,
      MutableGenericContext context) throws Exception {

    XMLStreamReader streamReader = XMLInputFactory.newInstance().createXMLStreamReader(inputStream);
    int type;
    do {
      type = streamReader.next();
      if (type == XMLStreamConstants.START_ELEMENT) {
        String localName = streamReader.getLocalName();
        if ("creator".equals(localName)) {
          context.setVariable(ContentParser.VARIABLE_NAME_CREATOR, streamReader.getElementText());
        } else if ("title".equals(localName)) {
          context.setVariable(ContentParser.VARIABLE_NAME_TITLE, streamReader.getElementText());
        } else if ("keyword".equals(localName)) {
          context.setVariable(ContentParser.VARIABLE_NAME_KEYWORDS, streamReader.getElementText());
        } else if ("language".equals(localName)) {
          context.setVariable(ContentParser.VARIABLE_NAME_LANGUAGE, streamReader.getElementText());
        }
      }
    } while (type != XMLStreamConstants.END_DOCUMENT);
  }

  /**
   * This method parses the content of the document.
   * 
   * @param inputStream is the {@link InputStream} where to read the metadata
   *        from.
   * @param options are the {@link ContentParserOptions}
   * @param context is the {@link MutableGenericContext} where the extracted
   *        metadata from the parsed <code>inputStream</code> will be
   *        {@link MutableGenericContext#setVariable(String, Object) added} to.
   * @param buffer is the {@link StringBuilder} where to append the text.
   * @throws Exception if the operation fails for arbitrary reasons.
   */
  protected void parseContent(InputStream inputStream, ContentParserOptions options,
      MutableGenericContext context, StringBuilder buffer) throws Exception {

    int maxChars = options.getMaximumBufferSize() / 2;
    XMLStreamReader streamReader = XMLInputFactory.newInstance().createXMLStreamReader(inputStream);
    int type;
    String tag = "";
    do {
      type = streamReader.next();
      if (type == XMLStreamConstants.START_ELEMENT) {
        tag = streamReader.getLocalName();
      } else if ((type == XMLStreamConstants.CHARACTERS) || (type == XMLStreamConstants.CDATA)) {
        if (TEXT_TAGS.contains(tag)) {
          String text = streamReader.getText();
          buffer.append(text);
          if (buffer.length() > maxChars) {
            break;
          }
        }
      }
    } while (type != XMLStreamConstants.END_DOCUMENT);
    context.setVariable(VARIABLE_NAME_TEXT, buffer.toString());
  }

  /**
   * This method parses the content of the document.
   * 
   * @param inputStream is the {@link InputStream} where to read the metadata
   *        from.
   * @param options are the {@link ContentParserOptions}
   * @param context is the {@link MutableGenericContext} where the extracted
   *        metadata from the parsed <code>inputStream</code> will be
   *        {@link MutableGenericContext#setVariable(String, Object) added} to.
   * @param buffer is the {@link StringBuilder} where to append the text.
   * @throws Exception if the operation fails for arbitrary reasons.
   */
  protected void parseStyles(InputStream inputStream, ContentParserOptions options,
      MutableGenericContext context, StringBuilder buffer) throws Exception {

    parseContent(inputStream, options, context, buffer);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void parse(InputStream inputStream, long filesize, ContentParserOptions options,
      MutableGenericContext context) throws Exception {

    ZipInputStream zipInputStream = new ZipInputStream(inputStream);
    // new BufferedInputStream(zipInputStream);
    StringBuilder buffer = new StringBuilder(128);
    ZipEntry zipEntry = zipInputStream.getNextEntry();
    while (zipEntry != null) {
      if (zipEntry.getName().equals(ENTRY_CONTENT_XML)) {
        parseContent(new NonClosingInputStream(zipInputStream), options, context, buffer);
      } else if (zipEntry.getName().equals(ENTRY_META_XML)) {
        parseMetadata(new NonClosingInputStream(zipInputStream), options, context);
      } else if (zipEntry.getName().equals(ENTRY_STYLES_XML)) {
        parseStyles(new NonClosingInputStream(zipInputStream), options, context, buffer);
      }
      zipInputStream.closeEntry();
      zipEntry = zipInputStream.getNextEntry();
    }
    zipInputStream.close();
  }
}
