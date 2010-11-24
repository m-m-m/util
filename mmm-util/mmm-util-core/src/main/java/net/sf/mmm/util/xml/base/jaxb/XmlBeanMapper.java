/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base.jaxb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.MarshalException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.nls.api.NlsIllegalStateException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.resource.api.BrowsableResource;
import net.sf.mmm.util.resource.api.BrowsableResourceFactory;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.DataResourceFactory;
import net.sf.mmm.util.resource.api.ResourceNotAvailableException;
import net.sf.mmm.util.resource.impl.BrowsableResourceFactoryImpl;
import net.sf.mmm.util.xml.base.XmlInvalidException;

import com.sun.xml.bind.IDResolver;

/**
 * This class is a little helper for the simple but common use of JAXB where you
 * simply want to {@link #loadXml(InputStream, Object) read} or
 * {@link #saveXml(Object, OutputStream) write} the XML for a single JAXB
 * annotated java bean.
 * 
 * @param <T> is the generic type of the JAXB bean.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class XmlBeanMapper<T> extends AbstractLoggableComponent {

  /** @see #getJaxbContext() */
  private final JAXBContext jaxbContext;

  /** @see #loadXml(InputStream, Object) */
  private final Class<T> xmlBeanClass;

  /** @see #getResourceFactory() */
  private BrowsableResourceFactory resourceFactory;

  /**
   * The constructor.
   * 
   * @param xmlBeanClass is the JAXB-annotated-{@link Class} that should be
   *        mapped.
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
   * @return the browsableResourceFactory
   */
  protected DataResourceFactory getResourceFactory() {

    return this.resourceFactory;
  }

  /**
   * @param browsableResourceFactory is the {@link BrowsableResourceFactory} to
   *        set.
   */
  @Inject
  public void setResourceFactory(BrowsableResourceFactory browsableResourceFactory) {

    getInitializationState().requireNotInitilized();
    this.resourceFactory = browsableResourceFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.resourceFactory == null) {
      BrowsableResourceFactoryImpl resourceFactoryImpl = new BrowsableResourceFactoryImpl();
      resourceFactoryImpl.initialize();
      this.resourceFactory = resourceFactoryImpl;
    }
  }

  /**
   * This method gets a {@link Marshaller} instance. This method potentially
   * allows reusing the {@link Marshaller} (if it is thread-safe).
   * 
   * @return the {@link Marshaller}.
   */
  protected Marshaller getOrCreateMarshaller() {

    try {
      return this.jaxbContext.createMarshaller();
    } catch (JAXBException e) {
      throw new NlsIllegalStateException(e);
    }
  }

  /**
   * This method gets a {@link Marshaller} instance. This method potentially
   * allows reusing the {@link Marshaller} (if it is thread-safe).
   * 
   * @return the {@link Unmarshaller}.
   */
  protected Unmarshaller getOrCreateUnmarshaller() {

    try {
      Unmarshaller unmarshaller = this.jaxbContext.createUnmarshaller();
      unmarshaller.setProperty(IDResolver.class.getName(), new ValidatingIdResolver());
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
   * This method is invoked after the <code>jaxbBean</code> has been loaded and
   * before it is saved. It does nothing by default but can be overridden to
   * implement custom validation logic.
   * 
   * @param jaxbBean is the JAXB bean to validate.
   */
  protected void validate(T jaxbBean) {

    NlsNullPointerException.checkNotNull(this.xmlBeanClass, jaxbBean);
  }

  /**
   * This method loads the JAXB-bean as XML from the given
   * <code>inputStream</code>.
   * 
   * @param inputStream is the {@link InputStream} with the XML to parse.
   * @return the parsed XML converted to the according JAXB-bean.
   * @param source describes the source of the invalid XML. Typically this will
   *        be the filename where the XML was read from. It is used in in the
   *        exception message. This will help to find the problem easier.
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
   * This method loads the JAXB-bean as XML from the given <code>location</code>
   * .
   * 
   * @param locationUrl is the location URL for the {@link DataResource
   *        resource} pointing to the XML to parse.
   * @return the parsed XML converted to the according JAXB-bean.
   */
  public T loadXml(String locationUrl) {

    DataResource resource = this.resourceFactory.createDataResource(locationUrl);
    return loadXml(resource);
  }

  /**
   * This method loads the JAXB-bean as XML from the given
   * <code>inputStream</code>.
   * 
   * @param file is the {@link File} with the XML to parse.
   * @return the parsed XML converted to the according JAXB-bean.
   */
  public T loadXml(File file) {

    try {
      FileInputStream inputStream = new FileInputStream(file);
      try {
        return loadXml(inputStream, file);
      } finally {
        try {
          inputStream.close();
        } catch (Exception e) {
          // ignore...
        }
      }
    } catch (FileNotFoundException e) {
      throw new ResourceNotAvailableException(e, file.getPath());
    }
  }

  /**
   * This method loads the JAXB-bean as XML from the given <code>resource</code>
   * .
   * 
   * @param resource is the {@link DataResource} with the XML to parse.
   * @return the parsed XML converted to the according JAXB-bean.
   */
  public T loadXml(DataResource resource) {

    InputStream inputStream = resource.openStream();
    try {
      return loadXml(inputStream, resource);
    } finally {
      try {
        inputStream.close();
      } catch (Exception e) {
        // ignore...
      }
    }
  }

  /**
   * This method saves the given <code>jaxbBean</code> as XML to the given
   * <code>outputStream</code>.<br/>
   * <b>ATTENTION:</b><br>
   * The caller of this method has to {@link OutputStream#close() close} the
   * <code>outputStream</code>.
   * 
   * @param jaxbBean is the JAXB-bean to save as XML.
   * @param resource is the {@link DataResource} where to
   *        {@link DataResource#openOutputStream() write} to.
   */
  public void saveXml(T jaxbBean, DataResource resource) {

    OutputStream outputStream = resource.openOutputStream();
    saveXml(jaxbBean, outputStream);
  }

  /**
   * This method saves the given <code>jaxbBean</code> as XML to the given
   * <code>outputStream</code>.<br/>
   * <b>ATTENTION:</b><br>
   * The caller of this method has to {@link OutputStream#close() close} the
   * <code>outputStream</code>.
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
   * This method saves the given <code>jaxbBean</code> as XML to the given
   * <code>locationUrl</code>.<br/>
   * 
   * @param jaxbBean is the JAXB-bean to save as XML.
   * @param locationUrl is the location URL for the {@link BrowsableResource
   *        resource} where to write the XML to. Typically a file-URL.
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

}
