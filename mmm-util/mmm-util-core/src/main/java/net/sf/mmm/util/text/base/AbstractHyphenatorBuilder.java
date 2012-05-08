/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.nls.api.NlsResourceLocator;
import net.sf.mmm.util.nls.base.DefaultNlsResourceLocator;
import net.sf.mmm.util.text.api.Hyphenator;
import net.sf.mmm.util.text.api.HyphenatorBuilder;

/**
 * This is the abstract base-implementation of the {@link HyphenatorBuilder} interface.<br>
 * It uses a cache with {@link WeakReference}s to the {@link Hyphenator}s that have already been build. If a
 * {@link Hyphenator} is requested several times, it can typically be served from the cache.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class AbstractHyphenatorBuilder extends AbstractLoggableComponent implements HyphenatorBuilder {

  /** Cache for {@link #getHyphenator(Locale)}. */
  private final ConcurrentHashMap<String, WeakReference<Hyphenator>> hyphenatorCache;

  /** The resource locator. */
  private NlsResourceLocator resourceLocator;

  /**
   * The constructor.
   */
  public AbstractHyphenatorBuilder() {

    super();
    this.hyphenatorCache = new ConcurrentHashMap<String, WeakReference<Hyphenator>>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.resourceLocator == null) {
      DefaultNlsResourceLocator impl = new DefaultNlsResourceLocator();
      impl.initialize();
      this.resourceLocator = impl;
    }
  }

  /**
   * {@inheritDoc}
   */
  public Hyphenator getHyphenator() {

    return getHyphenator(Locale.getDefault());
  }

  /**
   * {@inheritDoc}
   */
  public Hyphenator getHyphenator(Locale locale) {

    Hyphenator hyphenator = null;
    String[] localeInfixes = this.resourceLocator.getLocaleInfixes(locale);
    for (int i = 0; i < localeInfixes.length; i++) {
      WeakReference<Hyphenator> reference = this.hyphenatorCache.get(localeInfixes[i]);
      if (reference != null) {
        hyphenator = reference.get();
      }
      if (hyphenator == null) {
        hyphenator = createHyphenator(localeInfixes[i]);
        if (hyphenator != null) {
          reference = new WeakReference<Hyphenator>(hyphenator);
          // put to cache including cache misses...
          for (int j = i; j >= 0; j--) {
            this.hyphenatorCache.put(localeInfixes[j], reference);
          }
        }
      }
      if (hyphenator != null) {
        break;
      }
    }
    return hyphenator;
  }

  /**
   * This method creates a new {@link Hyphenator}.
   * 
   * @see #getHyphenator(Locale)
   * 
   * @param localeInfix is the {@link NlsResourceLocator#getLocaleInfixes(Locale) locale-infix} of the
   *        requested {@link Hyphenator}.
   * @return the requested {@link Hyphenator} or <code>null</code> if no hyphenation configuration is
   *         available for the given <code>localeInfix</code>.
   */
  protected abstract Hyphenator createHyphenator(String localeInfix);

  /**
   * @return the resourceLocator
   */
  protected NlsResourceLocator getResourceLocator() {

    return this.resourceLocator;
  }

  /**
   * @param resourceLocator is the resourceLocator to set
   */
  @Inject
  public void setResourceLocator(NlsResourceLocator resourceLocator) {

    getInitializationState().requireNotInitilized();
    this.resourceLocator = resourceLocator;
  }
}
