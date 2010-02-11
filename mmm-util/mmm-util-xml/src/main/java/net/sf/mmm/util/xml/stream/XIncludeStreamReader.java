/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;

import net.sf.mmm.util.io.base.StreamUtilImpl;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.xml.api.StaxUtil;
import net.sf.mmm.util.xml.api.XmlUtil;
import net.sf.mmm.util.xml.base.StaxUtilImpl;

/**
 * This is an implementation of the {@link XMLStreamReader} interface that
 * adapts an {@link XMLStreamReader} adding simple support for XInclude.<br>
 * For details about XInclude see: <a
 * href="http://www.w3.org/TR/xinclude/">http://www.w3.org/TR/xinclude/</a>.<br>
 * Please note that only plain XML inclusion is currently supported and no
 * XPointer.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class XIncludeStreamReader extends StreamReaderDelegate {

  /** the parent reader or <code>null</code> if this is the root. */
  private final XIncludeStreamReader parent;

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

  /** @see #getStaxUtil() */
  private StaxUtil staxUtil;

  /**
   * The reader to the current XInclude document or <code>null</code> if we
   * currently have no active XInclude.
   */
  private XMLStreamReader includeReader;

  /**
   * The included text or <code>null</code> if currently no text is to be
   * included.
   */
  private String includeText;

  /**
   * The current depth in the XML tree relative to the first "include" tag of
   * the XInclude namespace that is currently active. Will be <code>0</code> if
   * we are outside of an XInclude.
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

    this(factory, resource, null);
  }

  /**
   * The constructor.
   * 
   * @param factory is the input factory used to create raw XML-readers.
   * @param resource is where to read the XML from.
   * @param parent is the parent {@link XMLStreamReader}.
   * @throws XMLStreamException
   * @throws IOException
   */
  protected XIncludeStreamReader(XMLInputFactory factory, DataResource resource,
      XIncludeStreamReader parent) throws XMLStreamException, IOException {

    super();
    this.parent = parent;
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
   * @return the staxUtil
   */
  public StaxUtil getStaxUtil() {

    if (this.staxUtil == null) {
      this.staxUtil = StaxUtilImpl.getInstance();
    }
    return this.staxUtil;
  }

  /**
   * @param staxUtil the staxUtil to set
   */
  public void setStaxUtil(StaxUtil staxUtil) {

    this.staxUtil = staxUtil;
  }

  /**
   * This method detects if a recursive inclusion takes place.<br>
   * 
   * TODO: Potentially the same resource could cause an inclusion cycle without
   * causing an infinity loop by using different XPointer expressions.
   * 
   * @param dataResource is the current data-resource to include.
   * @throws XMLStreamException if the given <code>dataResource</code> has
   *         already been included causing an infinity loop.
   */
  protected void detectRecursiveInclusion(DataResource dataResource) throws XMLStreamException {

    if (this.resource.equals(dataResource)) {
      throw new XMLStreamException("Recursive inclusion: " + this.resource.getPath());
    }
    if (this.parent != null) {
      try {
        detectRecursiveInclusion(dataResource);
      } catch (RuntimeException e) {
        throw new XMLStreamException("Recursive inclusion: " + this.resource.getPath(), e);
      }
    }
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
    String xpointer = getAttributeValue(null, "xpointer");
    // get the included resource...
    DataResource includeResource = this.resource.navigate(href);
    // and try to include it...
    boolean success = false;
    if (includeResource.isAvailable()) {
      try {
        // determine inclusion format type...
        String parse = getAttributeValue(null, "parse");
        if ((parse == null) || ("xml".equals(parse))) {
          this.includeReader = new XIncludeStreamReader(this.factory, includeResource, this);
          eventType = this.includeReader.nextTag();
          setParent(this.includeReader);
          if (xpointer != null) {
            // shorthand form: id
            // scheme-based form: e.g. element(/1/*)
          }
          // we ascend the XML until the initial include is closed.
          closeInitialInclude();
          success = true;
        } else if ("text".equals(parse)) {
          String encoding = getAttributeValue(null, "encoding");
          Charset charset;
          if (encoding == null) {
            charset = Charset.defaultCharset();
          } else {
            charset = Charset.forName(encoding);
          }
          InputStream textInputStream = includeResource.openStream();
          Reader reader = new InputStreamReader(textInputStream, charset);
          this.includeText = StreamUtilImpl.getInstance().read(reader);
          return XMLStreamConstants.CHARACTERS;
        } else {
          throw new XMLStreamException("Unsupported XInclude parse type:" + parse);
        }
      } catch (IOException e) {
        // TODO: log error!
        e.printStackTrace();
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

    this.includeText = null;
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
   * We override this method to get sure that it delegates to our
   * {@link #next()} instead of the {@link #next()} of the delegate.
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
      throw new XMLStreamException("found: " + getStaxUtil().getEventTypeName(eventType)
          + ", expected " + getStaxUtil().getEventTypeName(XMLStreamConstants.START_ELEMENT)
          + " or " + getStaxUtil().getEventTypeName(XMLStreamConstants.END_ELEMENT), getLocation());
    }

    return eventType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getText() {

    if (this.includeText == null) {
      return super.getText();
    } else {
      return this.includeText;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public char[] getTextCharacters() {

    if (this.includeText == null) {
      return super.getTextCharacters();
    } else {
      return this.includeText.toCharArray();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getTextLength() {

    if (this.includeText == null) {
      return super.getTextLength();
    } else {
      return this.includeText.length();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getTextStart() {

    if (this.includeText == null) {
      return super.getTextStart();
    } else {
      return 0;
    }
  }

}
