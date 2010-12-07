/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base.jaxb;

import java.util.concurrent.Callable;

import javax.xml.bind.ValidationEventHandler;

import org.xml.sax.SAXException;

import com.sun.xml.bind.IDResolver;

/**
 * This is an implementation of {@link IDResolver} validating that no IDs are
 * duplicated or missing (IDRef is pointing to an undefined ID).<br/>
 * <b>ATTENTION:</b><br>
 * This class only works if <code>jaxb-impl</code> (com.sun.xml.bind) is on your
 * classpath!
 * 
 * @see InternalValidatingIdResolver
 * @see XmlBeanMapper#getOrCreateUnmarshaller()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.1
 */
public class ExternalValidatingIdResolver extends IDResolver {

  /** The {@link IdResolverContext}. */
  private IdResolverContext context;

  /**
   * The constructor.
   */
  public ExternalValidatingIdResolver() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void startDocument(ValidationEventHandler eventHandler) throws SAXException {

    super.startDocument(eventHandler);
    this.context = new IdResolverContext();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void endDocument() throws SAXException {

    super.endDocument();
    this.context.disposeAndValidate();
    this.context = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void bind(String id, Object value) throws SAXException {

    this.context.put(id, value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("rawtypes")
  public Callable<?> resolve(String id, Class targetType) throws SAXException {

    return this.context.get(id, targetType);
  }

}
