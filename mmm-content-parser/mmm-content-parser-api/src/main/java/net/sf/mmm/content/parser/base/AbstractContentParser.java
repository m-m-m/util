/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.base;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import net.sf.mmm.content.parser.api.ContentParser;
import net.sf.mmm.content.parser.api.ContentParserOptions;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.context.api.GenericContext;
import net.sf.mmm.util.context.api.GenericContextFactory;
import net.sf.mmm.util.context.api.MutableGenericContext;
import net.sf.mmm.util.context.impl.GenericContextFactoryImpl;
import net.sf.mmm.util.lang.api.StringUtil;

/**
 * This is the abstract base implementation of a {@link ContentParser}. It
 * handles the delegation methods, the creation of the
 * {@link MutableGenericContext} and the safe {@link InputStream#close()
 * closing} of the {@link InputStream}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentParser extends AbstractLoggableComponent implements
    ContentParser {

  /** @see #setGenericContextFactory(GenericContextFactory) */
  private GenericContextFactory genericContextFactory;

  /** @see #getPrimaryKeys() */
  private final Set<String> primaryKeys;

  /** @see #getSecondaryKeys() */
  private final Set<String> secondaryKeys;

  /**
   * The constructor.
   */
  public AbstractContentParser() {

    super();
    Set<String> primarySet = new HashSet<String>();
    String extension = getExtension();
    if (extension != null) {
      primarySet.add(extension);
    }
    String mimetype = getMimetype();
    if (mimetype != null) {
      primarySet.add(mimetype);
    }
    String[] alternativeKeys = getAlternativeKeyArray();
    if (alternativeKeys != null) {
      for (String key : alternativeKeys) {
        primarySet.add(key);
      }
    }
    this.primaryKeys = Collections.unmodifiableSet(primarySet);
    Set<String> secondarySet = new HashSet<String>();
    this.secondaryKeys = Collections.unmodifiableSet(secondarySet);
  }

  /**
   * @param genericContextFactory is the genericContextFactory to set
   */
  @Inject
  public void setGenericContextFactory(GenericContextFactory genericContextFactory) {

    getInitializationState().requireNotInitilized();
    this.genericContextFactory = genericContextFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.genericContextFactory == null) {
      GenericContextFactoryImpl impl = new GenericContextFactoryImpl();
      impl.initialize();
      this.genericContextFactory = impl;
    }
  }

  /**
   * {@inheritDoc}
   */
  public final Set<String> getPrimaryKeys() {

    return this.primaryKeys;
  }

  /**
   * This method gets the alternative {@link #getPrimaryKeys() primary keys} in
   * addition to {@link #getExtension() extension} and {@link #getMimetype()
   * mimetype}.
   * 
   * @see #getPrimaryKeys()
   * 
   * @return an array with the alternative keys.
   */
  public String[] getAlternativeKeyArray() {

    return StringUtil.EMPTY_STRING_ARRAY;
  }

  /**
   * This method gets the {@link #getSecondaryKeys() secondary keys} as array.
   * This is just a convenience to make it easier for the implementors of
   * individual parsers not to deal with creating a {@link Set} and make it
   * unmodifiable.
   * 
   * @see #getPrimaryKeys()
   * 
   * @return an array with the alternative keys.
   */
  public String[] getSecondaryKeyArray() {

    return StringUtil.EMPTY_STRING_ARRAY;
  }

  /**
   * {@inheritDoc}
   */
  public final Set<String> getSecondaryKeys() {

    return this.secondaryKeys;
  }

  /**
   * {@inheritDoc}
   */
  public final GenericContext parse(InputStream inputStream, long filesize) throws Exception {

    return parse(inputStream, filesize, new ContentParserOptionsBean());
  }

  /**
   * {@inheritDoc}
   */
  public GenericContext parse(InputStream inputStream, long filesize, ContentParserOptions options)
      throws Exception {

    MutableGenericContext context = this.genericContextFactory.createContext();
    try {
      parse(inputStream, filesize, options, context);
    } finally {
      inputStream.close();
    }
    return context;
  }

  /**
   * @see ContentParser#parse(InputStream, long)
   * 
   * @param inputStream is the fresh input stream of the content to parse.
   * @param filesize is the size (content-length) of the content to parse in
   *        bytes or <code>0</code> if NOT available (unknown). If available,
   *        the parser may use this value for optimized allocations.
   * @param options are the {@link ContentParserOptions}.
   * @param context is the {@link MutableGenericContext} where the extracted
   *        metadata from the parsed <code>inputStream</code> will be
   *        {@link MutableGenericContext#setVariable(String, Object) added} to.
   * @throws Exception if the operation fails for arbitrary reasons.
   */
  protected abstract void parse(InputStream inputStream, long filesize,
      ContentParserOptions options, MutableGenericContext context) throws Exception;

}
