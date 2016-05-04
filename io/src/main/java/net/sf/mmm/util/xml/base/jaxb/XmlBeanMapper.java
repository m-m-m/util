/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base.jaxb;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.MarshalException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.exception.api.NlsIllegalStateException;
import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.resource.api.BrowsableResource;
import net.sf.mmm.util.resource.api.BrowsableResourceFactory;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.DataResourceFactory;
import net.sf.mmm.util.resource.base.FileResource;
import net.sf.mmm.util.resource.impl.BrowsableResourceFactoryImpl;
import net.sf.mmm.util.xml.api.StaxUtil;
import net.sf.mmm.util.xml.base.StaxUtilImpl;
import net.sf.mmm.util.xml.base.XmlInvalidException;

/**
 * This class is a little helper for the simple but common use of JAXB where you simply want to
 * {@link #loadXml(InputStream, Object) read} or {@link #saveXml(Object, OutputStream) write} the XML for a
 * single JAXB annotated java bean. <br>
 * <b>ATTENTION:</b><br>
 * This class uses an {@code IDValidator} to validate duplicate or unresolved
 * {@link javax.xml.bind.annotation.XmlID}s on {@link #getOrCreateUnmarshaller() un-marshaling}. This is
 * unfortunately not the default for JAXB and also NOT part of the JAXB-API. So this feature depends on the
 * actual JAXB implementation you are using. We support the default implementation build into the JRE
 * (com.sun.internal.xml.bind). However if {@code jaxb-impl} (com.sun.xml.bind) is NOT on your classpath
 * it will replace the default implementation. For this reason we also support {@code jaxb-impl} as
 * fallback. Other implementations are NOT supported (everything should work but ID-validation is turned off
 * then).
 *
 * @param <T> is the generic type of the JAXB bean.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@SuppressWarnings("restriction")
public class XmlBeanMapper<T> extends AbstractLoggableComponent implements ValidationEventHandler {

  private  final JAXBContext jaxbContext;

  /** @see #loadXml(InputStream, Object) */
  private final Class<T> xmlBeanClass;

  private  BrowsableResourceFactory resourceFactory;

  private  StaxUtil staxUtil;

  private  boolean xIncludeAware;

  /**
   * The constructor.
   *
   * @param xmlBeanClass is the JAXB-annotated-{@link Class} that should be mapped.
   */
  public XmlBeanMapper(Class<T> xmlBeanClass) {

    super();
    this.xmlBeanClass = xmlBeanClass;
    try {
      this.jaxbContext = JAXBContext.newInstance(xmlBeanClass);
    } catch (JAXBException e) {
      throw new NlsIllegalStateException(e);
    }
  }

  /**
   * @return the staxUtil
   */
  protected StaxUtil getStaxUtil() {

    return this.staxUtil;
  }

  /**
   * @param staxUtil is the staxUtil to set
   */
  @Inject
  public void setStaxUtil(StaxUtil staxUtil) {

    getInitializationState().requireNotInitilized();
    this.staxUtil = staxUtil;
  }

  /**
   * @return the browsableResourceFactory
   */
  protected DataResourceFactory getResourceFactory() {

    return this.resourceFactory;
  }

  /**
   * @param browsableResourceFactory is the {@link BrowsableResourceFactory} to set.
   */
  @Inject
  public void setResourceFactory(BrowsableResourceFactory browsableResourceFactory) {

    getInitializationState().requireNotInitilized();
    this.resourceFactory = browsableResourceFactory;
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.resourceFactory == null) {
      BrowsableResourceFactoryImpl resourceFactoryImpl = new BrowsableResourceFactoryImpl();
      resourceFactoryImpl.initialize();
      this.resourceFactory = resourceFactoryImpl;
    }
    if (this.staxUtil == null) {
      this.staxUtil = StaxUtilImpl.getInstance();
    }
  }

  /**
   * @return {@code true} if undefined tags and attributes should be ignored (may be useful for
   *         compatibility), {@code false} if they shall cause an exception (default is
   *         {@code false}).
   */
  protected boolean isIgnoreUndefinedNodes() {

    return false;
  }

  /**
   * @return {@code true} if {@link #loadXml(DataResource)} should support XIncludes when reading the
   *         XML, {@code false} otherwise.
   */
  public boolean isXIncludeAware() {

    return this.xIncludeAware;
  }

  /**
   * @param isXIncludeAware is the new value of {@link #isXIncludeAware()}.
   */
  public void setXIncludeAware(boolean isXIncludeAware) {

    this.xIncludeAware = isXIncludeAware;
  }

  /**
   * This method gets a {@link Marshaller} instance. This method potentially allows reusing the
   * {@link Marshaller} (if it is thread-safe).
   *
   * @return the {@link Marshaller}.
   */
  protected Marshaller getOrCreateMarshaller() {

    try {
      Marshaller marshaller = this.jaxbContext.createMarshaller();
      marshaller.setEventHandler(this);
      return marshaller;
    } catch (JAXBException e) {
      throw new NlsIllegalStateException(e);
    }
  }

  /**
   * This method gets a {@link Marshaller} instance. This method potentially allows reusing the
   * {@link Marshaller} (if it is thread-safe).
   *
   * @return the {@link Unmarshaller}.
   */
  protected Unmarshaller getOrCreateUnmarshaller() {

    try {
      Unmarshaller unmarshaller = this.jaxbContext.createUnmarshaller();
      unmarshaller.setEventHandler(this);
      try {
        unmarshaller.setProperty(com.sun.xml.internal.bind.IDResolver.class.getName(),
            new InternalValidatingIdResolver());
      } catch (Throwable e) {
        try {
          getLogger().debug("No default JAXB implementation found ({0})- trying jaxb-impl (com.sun.xml.bind).",
              e.toString());
          unmarshaller.setProperty("com.sun.xml.bind.IDResolver", new ExternalValidatingIdResolver());
        } catch (Exception e2) {
          getLogger().error("ID-validation will not work! Please check your JAXB implementation!", e2);
        }
      }
      return unmarshaller;
    } catch (JAXBException e) {
      throw new NlsIllegalStateException(e);
    }
  }

  /**
   * @return the jaxbContext
   */
  protected JAXBContext getJaxbContext() {

    return this.jaxbContext;
  }

  /**
   * This method is invoked after the {@code jaxbBean} has been loaded and before it is saved. It does
   * nothing by default but can be overridden to implement custom validation logic.
   *
   * @param jaxbBean is the JAXB bean to validate.
   */
  protected void validate(T jaxbBean) {

    NlsNullPointerException.checkNotNull(this.xmlBeanClass, jaxbBean);
  }

  /**
   * This method loads the JAXB-bean as XML from the given {@code inputStream}.
   *
   * @param inputStream is the {@link InputStream} with the XML to parse.
   * @return the parsed XML converted to the according JAXB-bean.
   * @param source describes the source of the invalid XML. Typically this will be the filename where the XML
   *        was read from. It is used in in the exception message. This will help to find the problem easier.
   */
  public T loadXml(InputStream inputStream, Object source) {

    try {
      Object unmarshalledObject = getOrCreateUnmarshaller().unmarshal(inputStream);
      T jaxbBean = this.xmlBeanClass.cast(unmarshalledObject);
      validate(jaxbBean);
      return jaxbBean;
    } catch (JAXBException e) {
      throw new XmlInvalidException(e, source);
    }
  }

  /**
   * This method loads the JAXB-bean as XML from the given {@code location} .
   *
   * @param locationUrl is the location URL for the {@link DataResource resource} pointing to the XML to
   *        parse.
   * @return the parsed XML converted to the according JAXB-bean.
   */
  public T loadXml(String locationUrl) {

    DataResource resource = this.resourceFactory.createDataResource(locationUrl);
    return loadXml(resource);
  }

  /**
   * This method loads the JAXB-bean as XML from the given {@code inputStream}.
   *
   * @param file is the {@link File} with the XML to parse.
   * @return the parsed XML converted to the according JAXB-bean.
   */
  public T loadXml(File file) {

    FileResource resource = new FileResource(file);
    return loadXml(resource);
  }

  /**
   * This method loads the JAXB-bean as XML from the given {@code resource} .
   *
   * @param resource is the {@link DataResource} with the XML to parse.
   * @return the parsed XML converted to the according JAXB-bean.
   */
  public T loadXml(DataResource resource) {

    if (isXIncludeAware()) {
      XMLStreamReader streamReader = getStaxUtil().createXmlStreamReader(resource, true);
      try {
        Object unmarshalledObject = getOrCreateUnmarshaller().unmarshal(streamReader);
        T jaxbBean = this.xmlBeanClass.cast(unmarshalledObject);
        validate(jaxbBean);
        return jaxbBean;
      } catch (Exception e) {
        throw new XmlInvalidException(e, resource);
      } finally {
        try {
          streamReader.close();
        } catch (XMLStreamException e) {
          throw new RuntimeIoException(e, IoMode.CLOSE);
        }
      }
    } else {
      InputStream inputStream = resource.openStream();
      try {
        return loadXml(inputStream, resource);
      } catch (RuntimeException e) {
        throw new XmlInvalidException(e, resource.getUri());
      } finally {
        try {
          inputStream.close();
        } catch (Exception e) {
          // ignore...
        }
      }
    }
  }

  /**
   * This method saves the given {@code jaxbBean} as XML to the given {@code outputStream}. <br>
   * <b>ATTENTION:</b><br>
   * The caller of this method has to {@link OutputStream#close() close} the {@code outputStream}.
   *
   * @param jaxbBean is the JAXB-bean to save as XML.
   * @param resource is the {@link DataResource} where to {@link DataResource#openOutputStream() write} to.
   */
  public void saveXml(T jaxbBean, DataResource resource) {

    OutputStream outputStream = resource.openOutputStream();
    saveXml(jaxbBean, outputStream);
  }

  /**
   * This method saves the given {@code jaxbBean} as XML to the given {@code outputStream}. <br>
   * <b>ATTENTION:</b><br>
   * The caller of this method has to {@link OutputStream#close() close} the {@code outputStream}.
   *
   * @param jaxbBean is the JAXB-bean to save as XML.
   * @param outputStream is the {@link OutputStream} where to write the XML to.
   */
  public void saveXml(T jaxbBean, OutputStream outputStream) {

    try {
      validate(jaxbBean);
      getOrCreateMarshaller().marshal(jaxbBean, outputStream);
    } catch (MarshalException e) {
      throw new XmlInvalidException(e, jaxbBean);
    } catch (JAXBException e) {
      throw new NlsIllegalStateException(e);
    }
  }

  /**
   * This method saves the given {@code jaxbBean} as XML to the given {@code locationUrl}. <br>
   *
   * @param jaxbBean is the JAXB-bean to save as XML.
   * @param locationUrl is the location URL for the {@link BrowsableResource resource} where to write the XML
   *        to. Typically a file-URL.
   */
  public void saveXml(T jaxbBean, String locationUrl) {

    BrowsableResource resource = this.resourceFactory.createBrowsableResource(locationUrl);
    OutputStream outputStream = resource.openOutputStream();
    try {
      saveXml(jaxbBean, outputStream);
    } finally {
      try {
        outputStream.close();
      } catch (IOException e) {
        throw new RuntimeIoException(e, IoMode.CLOSE);
      }
    }
  }

  @Override
  public boolean handleEvent(ValidationEvent event) {

    Throwable exception = event.getLinkedException();
    String message = event.getMessage();
    ValidationEventLocator locator = event.getLocator();
    if (locator != null) {
      message = message + " at " + locator.toString();
    }
    switch (event.getSeverity()) {
      case ValidationEvent.WARNING:
        if (exception == null) {
          getLogger().warn(message);
        } else {
          getLogger().warn(message, exception);
        }
        break;
      case ValidationEvent.ERROR:
        if (message.startsWith("Undefined ID")) {
          // ignore as this is handled by our ID resolver...
        } else if (!isIgnoreUndefinedNodes() || !message.startsWith("unexpected ")) {
          throw new XmlInvalidException(exception, message);
        }
        break;
      case ValidationEvent.FATAL_ERROR:
        throw new XmlInvalidException(exception, message);
      default :
        // ignore unknown severity...
    }
    return true;
  }
}
