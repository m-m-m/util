/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.base.mapping;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanAccess;
import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.bean.api.EntityBean;
import net.sf.mmm.util.bean.api.mapping.DocumentBeanMapper;
import net.sf.mmm.util.exception.api.DuplicateObjectException;
import net.sf.mmm.util.exception.api.ObjectNotFoundException;

/**
 * This is the abstract base implementation of {@link DocumentBeanMapper}.
 *
 * @param <D> the type of the document (generic container object) to map (e.g. a document from a CMS or NoSQL-database).
 * @param <B> the base type of the {@link Bean} to map (e.g. {@link Bean} or {@link EntityBean}).
 * @param <M> the generic type of the internal {@link Mapping}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract class AbstractDocumentBeanMapper<D, B extends Bean, M extends AbstractDocumentBeanMapper.Mapping<D, B>>
    extends AbstractBeanMapper<D, B> implements DocumentBeanMapper<D, B> {

  private Map<String, M> beanMap;

  /**
   * The constructor.
   */
  public AbstractDocumentBeanMapper() {
    super();
    this.beanMap = new ConcurrentHashMap<>();
  }

  /**
   * @param mapping the {@link Mapping} to register (put into concurrent map to cache).
   */
  protected void registerMapping(M mapping) {

    addMapping(mapping.qualifiedName, mapping);
  }

  /**
   * @see #registerMapping(Mapping)
   * @param key the key used to store the {@link Mapping}.
   * @param mapping the {@link Mapping} to store.
   */
  protected final void addMapping(String key, M mapping) {

    M duplicate = this.beanMap.put(key, mapping);
    if (duplicate != null) {
      throw new DuplicateObjectException(mapping, key, duplicate);
    }
  }

  @Override
  public D fromBean(B bean) {

    if (bean == null) {
      return null;
    }
    BeanAccess access = bean.access();
    String key = access.getQualifiedName();
    M mapping = this.beanMap.computeIfAbsent(key, k -> createMapping(bean));
    D document = mapping.newDocument(bean);
    mapPropertiesFromBean(bean, access, document);
    return document;
  }

  /**
   * Override to create dynamic {@link Mapping}s on the fly.
   *
   * @param bean the {@link Bean} to map.
   * @return the {@link Mapping} that is not yet defined and will be {@link #registerMapping(Mapping) registered} and
   *         cached for further invocations.
   */
  protected M createMapping(B bean) {

    throw new ObjectNotFoundException("Mapping", bean.access().getQualifiedName());
  }

  /**
   * @param bean the {@link Bean} to map.
   * @param access the {@link Bean#access() according} {@link BeanAccess}.
   * @param document the document to map to.
   */
  protected abstract void mapPropertiesFromBean(B bean, BeanAccess access, D document);

  @SuppressWarnings("unchecked")
  @Override
  public <T extends B> T toBean(D document) {

    if (document == null) {
      return null;
    }
    String key = getQualifiedName(document);
    M mapping = this.beanMap.computeIfAbsent(key, k -> createMapping(key, document));
    B bean = getBeanPrototypeBuilder().create(mapping.prototype);
    mapPropertiesToBean(document, bean);
    return (T) bean;
  }

  /**
   * @param document the document to map.
   * @param bean the {@link Bean}-instance to map to.
   */
  protected abstract void mapPropertiesToBean(D document, B bean);

  /**
   * Override to create dynamic {@link Mapping}s on the fly.
   *
   * @param key the {@link #getQualifiedName(Object) qualified name} for the {@code document}.
   * @param document the document to map.
   * @return the {@link Mapping} that is not yet defined and will be {@link #registerMapping(Mapping) registered} and
   *         cached for further invocations.
   */
  protected M createMapping(String key, D document) {

    throw new ObjectNotFoundException("Mapping", key);
  }

  /**
   * @param document the document to map.
   * @return the {@link BeanAccess#getQualifiedName() qualified name} of the corresponding {@link Bean}.
   */
  protected abstract String getQualifiedName(D document);

  /**
   * Simple container for {@link Bean} meta-data.
   *
   * @param <D> the type of the document (generic container object) to map.
   * @param <B> the base type of the {@link Bean} to map.
   */
  public abstract static class Mapping<D, B extends Bean> {

    /** @see BeanAccess#getQualifiedName() */
    public final String qualifiedName;

    /** The {@link Bean} {@link BeanFactory#createPrototype(Class) prototype}. */
    public final B prototype;

    /**
     * The constructor.
     *
     * @param prototype the {@link Bean} prototype.
     */
    public Mapping(B prototype) {
      super();
      this.prototype = prototype;
      this.qualifiedName = prototype.access().getQualifiedName();
    }

    /**
     * @param bean the {@link Bean} to map.
     * @return a new document instance of the type {@literal <D>}.
     */
    public abstract D newDocument(B bean);
  }

}
