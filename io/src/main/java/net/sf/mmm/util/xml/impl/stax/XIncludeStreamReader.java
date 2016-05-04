/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.impl.stax;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.io.base.StreamUtilImpl;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.xml.api.XmlException;
import net.sf.mmm.util.xml.api.XmlUtil;
import net.sf.mmm.util.xml.base.StreamReaderProxy;
import net.sf.mmm.util.xml.base.XmlInvalidException;

/**
 * This is an implementation of the {@link XMLStreamReader} interface that adapts an {@link XMLStreamReader} adding
 * support for XInclude. <br>
 * For details about XInclude see: <a href="http://www.w3.org/TR/xinclude/">http://www.w3.org/TR/xinclude/</a>. <br>
 * <b>ATTENTION:</b><br>
 * Please note that currently only plain XML inclusion is currently supported and no XPointer.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class XIncludeStreamReader extends StreamReaderProxy {

  /** The {@link Logger} to use. */
  private static final Logger LOGGER = LoggerFactory.getLogger(XIncludeStreamReader.class);

  /** the parent reader or {@code null} if this is the root. */
  private final XIncludeStreamReader parent;

  /** The factory used to create additional readers. */
  private final XMLInputFactory factory;

  /** The reader to the main document. */
  private final XMLStreamReader mainReader;

  /** The resource pointing to the main document. */
  private final DataResource resource;

  /**
   * The StAX resource management sucks: we need to manage the underlying input stream ourselves.
   */
  private final InputStream inputStream;

  /**
   * The reader to the current XInclude document or {@code null} if we currently have no active XInclude.
   */
  private XMLStreamReader includeReader;

  /**
   * The included text or {@code null} if currently no text is to be included.
   */
  private String includeText;

  /**
   * The current depth in the XML tree relative to the first "include" tag of the XInclude namespace that is currently
   * active. Will be {@code 0} if we are outside of an XInclude.
   */
  private int depth;

  /**
   * This flag indicates whether we are actively inside a fallback section.
   */
  private boolean fallback;

  /**
   * The constructor.
   *
   * @param factory is the {@link XMLInputFactory} required to create new {@link XMLStreamReader} instances for
   *        includes.
   * @param resource is the {@link DataResource} pointing to the XML content.
   */
  public XIncludeStreamReader(XMLInputFactory factory, DataResource resource) {

    this(factory, resource, null);
  }

  /**
   * The constructor.
   *
   * @param factory is the input factory used to create raw XML-readers.
   * @param resource is where to read the XML from.
   * @param parent is the parent {@link XMLStreamReader}.
   * @throws XmlException in case of an XML error.
   * @throws RuntimeIoException is case of an input/output error.
   */
  protected XIncludeStreamReader(XMLInputFactory factory, DataResource resource, XIncludeStreamReader parent)
      throws XmlException, RuntimeIoException {

    super();
    this.parent = parent;
    this.factory = factory;
    this.resource = resource;

    this.inputStream = resource.openStream();
    try {
      this.mainReader = factory.createXMLStreamReader(this.inputStream);
    } catch (Exception e) {
      XmlInvalidException xmlEx = new XmlInvalidException(e);
      try {
        this.inputStream.close();
      } catch (IOException e1) {
        xmlEx.addSuppressed(e1);
      }
      throw xmlEx;
    }
    setParent(this.mainReader);
  }

  /**
   * This method detects if a recursive inclusion takes place. <br>
   *
   * TODO: Potentially the same resource could cause an inclusion cycle without causing an infinity loop by using
   * different XPointer expressions.
   *
   * @param dataResource is the current data-resource to include.
   * @throws XMLStreamException if the given {@code dataResource} has already been included causing an infinity loop.
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
   * The StAX API has a bad design mistake about the {@link #close()} method NOT to close the underlying input stream.
   * Besides the {@link XMLStreamReader} the user also has to manage the input stream what will lead in additional
   * programming mistakes ending up with open file-handles. Since many {@link XMLStreamReader} implementations have an
   * empty body for this method developers, may tend to take it NOT as serious as e.g. {@link InputStream#close()}.
   * Since this implementation has to open new streams behind the scenes the only senseful implementation of this method
   * is to close the underlying stream (and recursively closing all included streams). You have to ensure this reader is
   * safely closed via this method so remaining open streams are closed.
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
   * This method is called when an include tag of the XInclude namespace was started. It resolves the include and finds
   * a fallback on failure.
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
    LOGGER.trace("Resolving xi:include to href {}", href);
    String xpointer = getAttributeValue(null, "xpointer");
    // get the included resource...
    DataResource includeResource = this.resource.navigate(href);
    // and try to include it...
    boolean success = false;
    if (includeResource.isAvailable()) {
      // determine inclusion format type...
      String parse = getAttributeValue(null, "parse");
      if ((parse == null) || ("xml".equals(parse))) {
        this.includeReader = new XIncludeStreamReader(this.factory, includeResource, this);
        if (xpointer != null) {
          // shorthand form: id
          // scheme-based form: e.g. element(/1/*)
          this.includeReader = new XPointerStreamReader(this.includeReader, xpointer);
        }
        eventType = this.includeReader.nextTag();
        setParent(this.includeReader);
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
        // we ascend the XML until the initial include is closed.
        closeInitialInclude();
        return XMLStreamConstants.CHARACTERS;
      } else {
        throw new XMLStreamException("Unsupported XInclude parse type:" + parse);
      }
    }
    if (!success) {
      // search for fallback
      do {
        eventType = super.next();
      } while ((eventType != XMLStreamConstants.START_ELEMENT) && (eventType != XMLStreamConstants.END_ELEMENT));
      if (eventType == XMLStreamConstants.START_ELEMENT) {
        if ((XmlUtil.NAMESPACE_URI_XINCLUDE.equals(getNamespaceURI())) && ("fallback".equals(getLocalName()))) {
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

    LOGGER.trace("Closing xi:include");
    int eventType = -1;
    // we ascend the XML until the initial include is closed.
    while (this.depth > 0) {
      eventType = this.mainReader.next();
      if (eventType == XMLStreamConstants.START_ELEMENT) {
        LOGGER.trace("Closing loop: Start {}", this.mainReader.getLocalName());
        this.depth++;
      } else if (eventType == XMLStreamConstants.END_ELEMENT) {
        LOGGER.trace("Closing loop: End {}", this.mainReader.getLocalName());
        this.depth--;
      }
    }
    LOGGER.trace("Closing xi:include complete");
  }

  @Override
  public int next() throws XMLStreamException {

    this.includeText = null;
    int eventType = super.next();
    if (this.includeReader == null) {
      if ((eventType == XMLStreamConstants.START_ELEMENT) || (eventType == XMLStreamConstants.END_ELEMENT)) {
        String namespace = getNamespaceURI();
        String tag = getLocalName();
        if (XmlUtil.NAMESPACE_URI_XINCLUDE.equals(namespace)) {
          if (eventType == XMLStreamConstants.START_ELEMENT) {
            if ("include".equals(tag)) {
              eventType = resolveInclude();
            } else {
              // unknown/misplaced XInclude tag!
              // currently we just leave it alone...
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
        LOGGER.warn("Failed to close include reader.", e);
      }
      this.includeReader = null;
      this.depth = 0;
      eventType = next();
    }
    return eventType;
  }

  @Override
  public String getText() {

    if (this.includeText == null) {
      return super.getText();
    } else {
      return this.includeText;
    }
  }

  @Override
  public char[] getTextCharacters() {

    if (this.includeText == null) {
      return super.getTextCharacters();
    } else {
      return this.includeText.toCharArray();
    }
  }

  @Override
  public int getTextLength() {

    if (this.includeText == null) {
      return super.getTextLength();
    } else {
      return this.includeText.length();
    }
  }

  @Override
  public int getTextStart() {

    if (this.includeText == null) {
      return super.getTextStart();
    } else {
      return 0;
    }
  }

}
