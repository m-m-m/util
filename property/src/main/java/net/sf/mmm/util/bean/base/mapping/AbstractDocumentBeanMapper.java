/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.base.mapping;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

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
  protected void addMapping(M mapping) {

    addMapping(getKey(mapping.prototype), mapping);
  }

  /**
   * @see #addMapping(Mapping)
   * @param key the key used to store the {@link Mapping}.
   * @param mapping the {@link Mapping} to store.
   */
  protected final void addMapping(String key, M mapping) {

    M duplicate = this.beanMap.put(key, mapping);
    if (duplicate != null) {
      throw new DuplicateObjectException(mapping, key, duplicate);
    }
  }

  /**
   * @param key the {@link #getKey(Bean) key} of the requested {@link Mapping}.
   * @return the requested {@link Mapping}. May be {@code null}.
   */
  protected final M getMapping(String key) {

    return this.beanMap.get(key);
  }

  /**
   * @return the {@link Collection} of {@link Mapping}s.
   */
  protected final Collection<M> getMappings() {

    return this.beanMap.values();
  }

  @Override
  public D fromBean(B bean) {

    if (bean == null) {
      return null;
    }
    M mapping = getOrCreateMapping(bean);
    D document = mapping.newDocument(bean);
    mapPropertiesFromBean(bean, document);
    return document;
  }

  /**
   * @param bean the {@link Bean} to get the {@link Mapping} for.
   * @return the requested {@link Mapping}. Will be created by {@link #createMapping(Bean)} if not yet exists.
   */
  protected final M getOrCreateMapping(B bean) {

    String key = getKey(bean);
    return getOrCreateMapping(key, k -> createMapping(bean));
  }

  /**
   * @param bean the {@link Bean}.
   * @return the corresponding key used for {@link #getMapping(String)}.
   */
  protected String getKey(B bean) {

    return bean.access().getQualifiedName();
  }

  /**
   * Override to create dynamic {@link Mapping}s on the fly.
   *
   * @param bean the {@link Bean} to map.
   * @return the {@link Mapping} that is not yet defined and will be {@link #addMapping(Mapping) registered} and cached
   *         for further invocations.
   */
  protected M createMapping(B bean) {

    throw new ObjectNotFoundException("Mapping", bean.access().getQualifiedName());
  }

  /**
   * @param bean the {@link Bean} to map.
   * @param document the document to map to.
   */
  protected abstract void mapPropertiesFromBean(B bean, D document);

  @SuppressWarnings("unchecked")
  @Override
  public <T extends B> T toBean(D document) {

    if (document == null) {
      return null;
    }
    String key = getKey(document);
    M mapping = getOrCreateMapping(key, k -> createMapping(key, document));
    B bean = getBeanPrototypeBuilder().create(mapping.prototype);
    mapPropertiesToBean(document, bean);
    return (T) bean;
  }

  /**
   * @param key the {@link #getKey(Bean) key} of the requested {@link Mapping}.
   * @param function the {@link Function} to {@link Function#apply(Object) create} the {@link Mapping} in case it does
   *        not already exist.
   * @return the requested {@link Mapping}.
   */
  protected final M getOrCreateMapping(String key, Function<String, M> function) {

    M mapping = this.beanMap.computeIfAbsent(key, function);
    return mapping;
  }

  /**
   * @param document the document to map.
   * @param bean the {@link Bean}-instance to map to.
   */
  protected abstract void mapPropertiesToBean(D document, B bean);

  /**
   * Override to create dynamic {@link Mapping}s on the fly.
   *
   * @param key the {@link #getKey(Object) qualified name} for the {@code document}.
   * @param document the document to map.
   * @return the {@link Mapping} that is not yet defined and will be {@link #addMapping(Mapping) registered} and cached
   *         for further invocations.
   */
  protected M createMapping(String key, D document) {

    throw new ObjectNotFoundException("Mapping", key);
  }

  /**
   * @param document the document to map.
   * @return the {@link BeanAccess#getQualifiedName() qualified name} of the corresponding {@link Bean}.
   */
  protected abstract String getKey(D document);

  /**
   * Simple container for {@link Bean} meta-data.
   *
   * @param <D> the type of the document (generic container object) to map.
   * @param <B> the base type of the {@link Bean} to map.
   */
  public abstract static class Mapping<D, B extends Bean> {

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
    }

    /**
     * @param bean the {@link Bean} to map.
     * @return a new document instance of the type {@literal <D>}.
     */
    public abstract D newDocument(B bean);

    @Override
    public String toString() {

      BeanAccess access = this.prototype.access();
      return access.getSimpleName() + "(" + access.getBeanClass().getName() + ")";
    }
  }

}
