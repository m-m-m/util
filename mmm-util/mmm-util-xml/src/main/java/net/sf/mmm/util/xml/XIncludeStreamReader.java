/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml;

import java.io.IOException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;

import net.sf.mmm.util.resource.DataResource;

/**
 * This is an implementation of the {@link XMLStreamReader} interface that
 * adapts an {@link XMLStreamReader} adding simple support for XInclude.<br>
 * For details about XInclude see: <a
 * href="http://www.w3.org/TR/xinclude/">http://www.w3.org/TR/xinclude/</a>
 * Please note that only plain inclusion is currently supported but no XPointer.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class XIncludeStreamReader extends StreamReaderDelegate {

  /** The factory used to create additional readers. */
  private final XMLInputFactory factory;

  /** The reader to the main document. */
  private final XMLStreamReader mainReader;

  /** The resource pointing to the main document. */
  private final DataResource resource;

  /**
   * The reader to the current XInclude document or <code>null</code> if we
   * currently have no active XInclude.
   */
  private XMLStreamReader includeReader;

  /**
   * The constructor.
   * 
   * @param factory
   * @param resource
   * @throws XMLStreamException
   * @throws IOException
   */
  public XIncludeStreamReader(XMLInputFactory factory, DataResource resource)
      throws XMLStreamException, IOException {

    super(factory.createXMLStreamReader(resource.openStream()));
    this.mainReader = getParent();
    this.factory = factory;
    this.resource = resource;
  }

  /**
   * The constructor.
   * 
   * @param factory
   * @param resource
   * @param delegate is the {@link #getParent() parent}.
   */
  public XIncludeStreamReader(XMLInputFactory factory, DataResource resource,
      XMLStreamReader delegate) {

    super(delegate);
    this.mainReader = delegate;
    this.factory = factory;
    this.resource = resource;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() throws XMLStreamException {

    try {
      this.mainReader.close();
    } finally {
      if (this.includeReader != null) {
        this.includeReader.close();
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int next() throws XMLStreamException {

    int eventType = super.next();
    if (this.includeReader == null) {
      if (eventType == XMLStreamConstants.START_ELEMENT) {
        // todo look for XInclude
        if (XmlUtil.NAMESPACE_URI_XINCLUDE.equals(getNamespaceURI())) {
          if ("include".equals(getLocalName())) {
            String href = getAttributeValue(null, "href");
            int depth = 1;
            while (depth > 0) {
              eventType = super.next();
              if (eventType == XMLStreamConstants.START_ELEMENT) {
                depth++;
              } else if (eventType == XMLStreamConstants.END_ELEMENT) {
                depth--;
              }
            }
            DataResource includeResource = this.resource.navigate(href);
            try {
              this.includeReader = new XIncludeStreamReader(this.factory, includeResource);
              setParent(this.includeReader);
              eventType = this.includeReader.nextTag();
            } catch (IOException e) {
              throw new XMLStreamException(e);
            }
          } else {
            // unknown XInclude tag!
            // is this an error?
          }
        }
      }
    } else if (eventType == XMLStreamConstants.END_DOCUMENT) {
      // we are in XInclude and the included document is completed...
      setParent(this.mainReader);
      this.includeReader.close();
      this.includeReader = null;
      eventType = next();
    }
    return eventType;
  }

}
