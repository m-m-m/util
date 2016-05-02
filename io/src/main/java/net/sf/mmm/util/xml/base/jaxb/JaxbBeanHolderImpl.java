/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base.jaxb;

import java.io.Flushable;
import java.util.Date;

import net.sf.mmm.util.component.base.AbstractLoggableObject;
import net.sf.mmm.util.exception.api.NlsUnsupportedOperationException;
import net.sf.mmm.util.resource.api.DataResource;

/**
 * This is the implementation of {@link JaxbBeanHolder}.
 * 
 * @param <VIEW> is the generic type for the API of the {@link #getBean() JAXB bean}.
 * @param <BEAN> is the generic type for the implementation of the {@link #getBean() JAXB bean}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class JaxbBeanHolderImpl<VIEW, BEAN extends VIEW> extends AbstractLoggableObject implements
    JaxbBeanHolder<VIEW>, Flushable {

  /** The {@link DataResource} where the bean was loaded from. */
  private final DataResource dataResource;

  /** The {@link XmlBeanMapper}. */
  private final XmlBeanMapper<BEAN> beanMapper;

  /** @see #isAllowSave() */
  private final boolean allowSave;

  /** @see #getBean() */
  private BEAN bean;

  /** @see #refresh() */
  private Date modificationDate;

  /**
   * The constructor.
   * 
   * @param bean is the JAXB {@link #getBean() bean}.
   * @param resource is the {@link #getDataResource() resource}.
   * @param beanMapper is the {@link XmlBeanMapper} used for {@link #flush()} and {@link #refresh()}.
   */
  public JaxbBeanHolderImpl(BEAN bean, DataResource resource, XmlBeanMapper<BEAN> beanMapper) {

    this(bean, resource, beanMapper, true);
  }

  /**
   * The constructor.
   * 
   * @param bean is the JAXB {@link #getBean() bean}.
   * @param resource is the {@link #getDataResource() resource}.
   * @param beanMapper is the {@link XmlBeanMapper} used for {@link #flush()} and {@link #refresh()}.
   * @param allowSave - see {@link #isAllowSave()}.
   */
  public JaxbBeanHolderImpl(BEAN bean, DataResource resource, XmlBeanMapper<BEAN> beanMapper, boolean allowSave) {

    super();
    this.bean = bean;
    this.dataResource = resource;
    this.beanMapper = beanMapper;
    this.allowSave = allowSave;
    if (this.dataResource != null) {
      this.modificationDate = this.dataResource.getLastModificationDate();
    }
  }

  /**
   * This method determines if this implementation allows to {@link #flush() save}.
   * 
   * @return {@code true} if {@link #flush() saving} is allowed, {@code false} otherwise.
   */
  public boolean isAllowSave() {

    return this.allowSave;
  }

  /**
   * This method gets the current JAXB bean.
   * 
   * @return the current JAXB bean.
   */
  public BEAN getBean() {

    return this.bean;
  }

  /**
   * This method gets the {@link DataResource} where the {@link #getBean() bean} was loaded from.
   * 
   * @return the {@link DataResource}.
   */
  protected DataResource getDataResource() {

    return this.dataResource;
  }

  /**
   * {@inheritDoc}
   */
  public synchronized void flush() {

    if (!this.allowSave) {
      throw new NlsUnsupportedOperationException("save");
    }
    if (this.beanMapper != null) {
      this.beanMapper.saveXml(this.bean, this.dataResource);
    }
  }

  /**
   * {@inheritDoc}
   */
  public synchronized boolean refresh() {

    // Date modificationDate = jaxbBean.getModificationDate();
    if (this.beanMapper != null) {
      boolean reload = true;
      if (this.modificationDate != null) {
        Boolean modified = this.dataResource.isModifiedSince(this.modificationDate);
        if (Boolean.FALSE.equals(modified)) {
          reload = false;
        }
      }
      if (reload) {
        getLogger().debug("Releoading configuration from " + this.dataResource.getUri());
        this.bean = this.beanMapper.loadXml(this.dataResource);
        this.modificationDate = this.dataResource.getLastModificationDate();
      }
      return reload;
    }
    return false;
  }

}
