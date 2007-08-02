/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml;

import java.io.IOException;
import java.io.InputStream;

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
 * TODO
 * 
 * <pre>
 * &lt;include&gt; depth=1, fallback=false
 *   &lt;fallback&gt; depth=2, fallback=true
 *     &lt;include&gt; depth=3, fallback=false
 *       &lt;fallback&gt; depth=4, fallback=true
 *         
 *       &lt;/fallback&gt; depth=4
 *     &lt;/include&gt; depth=3
 *   &lt;/fallback&gt; depth=2
 * &lt;/include&gt; depth=1
 * </pre>
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
   * The StAX resource management sucks: we need to manage the underlying input
   * stream ourselves.
   */
  private final InputStream inputStream;

  /**
   * The reader to the current XInclude document or <code>null</code> if we
   * currently have no active XInclude.
   */
  private XMLStreamReader includeReader;

  /**
   * The current depth in the XML tree relative to the first "include" tag of
   * the XInclude namespace that is currently active. Will be <code>0</code>
   * if we are outside of an XInclude.
   */
  private int depth;

  /**
   * This flag indicates whether we are actively inside a fallback section.
   */
  private boolean fallback;

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

    super();
    this.factory = factory;
    this.resource = resource;
    this.inputStream = resource.openStream();
    try {
      this.mainReader = factory.createXMLStreamReader(this.inputStream);
    } catch (RuntimeException e) {
      this.inputStream.close();
      throw e;
    }
    setParent(this.mainReader);
  }

  /**
   * {@inheritDoc}
   * 
   * <b>ATTENTION:</b><br>
   * This method violates the StAX API and closes the underlying input stream!<br>
   * The StAX API has a bad design mistake about the {@link #close()} method NOT
   * to close the underlying input stream. Besides the {@link XMLStreamReader}
   * the user also has to manage the input stream what will lead in additional
   * programming mistakes ending up with open file-handles. Since many
   * {@link XMLStreamReader} implementations have an empty body for this method
   * developers, may tend to take it NOT as serious as e.g.
   * {@link InputStream#close()}. Since this implementation has to open new
   * streams behind the scenes the only senseful implementation of this method
   * is to close the underlying stream (and recursively closing all included
   * streams). You have to ensure this reader is safely closed via this method
   * so remaining open streams are closed.
   */
  @Override
  public void close() throws XMLStreamException {

    try {
      this.mainReader.close();
    } finally {
      try {
        if (this.includeReader != null) {
          this.includeReader.close();
        }
      } finally {
        try {
          this.inputStream.close();
        } catch (IOException e) {
          throw new XMLStreamException(e);
        }
      }
    }
  }

  /**
   * This method is called when an include tag of the XInclude namespace was
   * started. It resolves the include and finds a fallback on failure.
   * 
   * @return the next event type.
   * @throws XMLStreamException if the XML stream processing caused an error.
   */
  protected int resolveInclude() throws XMLStreamException {

    // we are no more in fallback mode
    this.fallback = false;
    this.depth++;
    int eventType = -1;
    // read attributes...
    String href = getAttributeValue(null, "href");
    String parse = getAttributeValue(null, "parse");
    if ((parse != null) && (!"xml".equals(parse))) {
      // TODO: support for "text", honor "encoding" attribute.
      throw new XMLStreamException("Unsupported XInclude parse type:" + parse);
    }
    // get the included resource...
    DataResource includeResource = this.resource.navigate(href);
    // and try to include it...
    boolean success = false;
    if (includeResource.isAvailable()) {
      try {
        this.includeReader = new XIncludeStreamReader(this.factory, includeResource);
        setParent(this.includeReader);
        eventType = this.includeReader.nextTag();
        // we ascend the XML until the initial include is closed.
        closeInitialInclude();
        success = true;
      } catch (IOException e) {
        e.printStackTrace();
        // TODO: log error!
      }
    }
    if (!success) {
      // search for fallback
      do {
        eventType = super.next();
      } while ((eventType != XMLStreamConstants.START_ELEMENT)
          && (eventType != XMLStreamConstants.END_ELEMENT));
      if (eventType == XMLStreamConstants.START_ELEMENT) {
        if ((XmlUtil.NAMESPACE_URI_XINCLUDE.equals(getNamespaceURI()))
            && ("fallback".equals(getLocalName()))) {
          // found fallback
          this.fallback = true;
          return next();
        }
      }
      // no fallback available, ignore include...
      closeInitialInclude();
      return next();
    }
    return eventType;
  }

  /**
   * This method ascends the XML until the initial include is closed.
   * 
   * @throws XMLStreamException if the XML stream processing caused an error.
   */
  protected void closeInitialInclude() throws XMLStreamException {

    int eventType = -1;
    // we ascend the XML until the initial include is closed.
    while (this.depth > 0) {
      eventType = this.mainReader.next();
      if (eventType == XMLStreamConstants.START_ELEMENT) {
        this.depth++;
      } else if (eventType == XMLStreamConstants.END_ELEMENT) {
        this.depth--;
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
      if ((eventType == XMLStreamConstants.START_ELEMENT)
          || (eventType == XMLStreamConstants.END_ELEMENT)) {
        String namespace = getNamespaceURI();
        String tag = getLocalName();
        if (XmlUtil.NAMESPACE_URI_XINCLUDE.equals(namespace)) {
          if (eventType == XMLStreamConstants.START_ELEMENT) {
            if ("include".equals(tag)) {
              eventType = resolveInclude();
            } else {
              // unknown/misplaced XInclude tag!
              // is this an error?
              // TODO: we should eat this garbage
            }
          } else {
            if (this.fallback && ("fallback".equals(tag))) {
              // so we have completed our fallback
              this.fallback = false;
              closeInitialInclude();
              eventType = next();
            } else {
              // unknown/misplaced XInclude tag!
              // is this an error?
              // TODO: we should eat this garbage
            }
          }
        }
      }
    } else if (eventType == XMLStreamConstants.END_DOCUMENT) {
      // we are in XInclude and the included document is completed...
      setParent(this.mainReader);
      try {
        this.includeReader.close();
      } catch (Exception e) {
        e.printStackTrace();
        // TODO: use logger!
      }
      this.includeReader = null;
      this.depth = 0;
      eventType = next();
    }
    return eventType;
  }

  /**
   * {@inheritDoc}
   * 
   * We override this method to get sure that it delegates to {@link #next()}.
   */
  @Override
  public int nextTag() throws javax.xml.stream.XMLStreamException {

    int eventType = next();
    while ((eventType == XMLStreamConstants.CHARACTERS && isWhiteSpace())
        || (eventType == XMLStreamConstants.CDATA && isWhiteSpace())
        || eventType == XMLStreamConstants.SPACE
        || eventType == XMLStreamConstants.PROCESSING_INSTRUCTION
        || eventType == XMLStreamConstants.COMMENT) {
      eventType = next();
    }

    if (eventType != XMLStreamConstants.START_ELEMENT
        && eventType != XMLStreamConstants.END_ELEMENT) {
      throw new XMLStreamException("found: " + StaxUtil.getEventTypeName(eventType) + ", expected "
          + StaxUtil.getEventTypeName(XMLStreamConstants.START_ELEMENT) + " or "
          + StaxUtil.getEventTypeName(XMLStreamConstants.END_ELEMENT), getLocation());
    }

    return eventType;
  }

}
