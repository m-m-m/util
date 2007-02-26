/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import net.sf.mmm.configuration.NlsBundleConfigCore;
import net.sf.mmm.configuration.api.ConfigurationDocument;
import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.access.ConfigurationAccessFactory;
import net.sf.mmm.configuration.api.access.ConfigurationAccess;
import net.sf.mmm.configuration.api.event.ConfigurationChangeEvent;
import net.sf.mmm.configuration.api.event.ConfigurationChangeListener;
import net.sf.mmm.configuration.base.access.ConfigurationDocumentCollector;
import net.sf.mmm.context.api.Context;
import net.sf.mmm.context.api.MutableContext;

/**
 * This is the abstract base implementation of the
 * {@link ConfigurationDocument} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractConfigurationDocument implements ConfigurationDocument,
    ConfigurationDocumentCollector {

  /** the {@link #getContext() context} */
  private final MutableContext context;

  /** the list of child documents */
  private final List<ConfigurationDocument> childDocuments;

  /** the list of all registered listeners */
  private final List<FilteredListener> listeners;

  /** the {@link #isImmutable() immutable} flag */
  private final boolean immutableFlag;

  /** the parent of the root or <code>null</code> */
  private final AbstractConfiguration parentConf;

  /** the parent document or <code>null</code> */
  private final AbstractConfigurationDocument parentDoc;

  /** this object gives r/w access to the configuration data */
  private final ConfigurationAccess access;

  /** the root node */
  private AbstractConfiguration root;

  /** the status of the {@link #isModified() modified-flag} */
  private boolean modifiedFlag;

  /**
   * The constructor.
   * 
   * @param configurationAccess
   * @param docContext
   */
  public AbstractConfigurationDocument(ConfigurationAccess configurationAccess,
      MutableContext docContext) {

    this(configurationAccess, null, docContext);
  }

  /**
   * The constructor.
   * 
   * @param configurationAccess
   * @param parentConfiguration
   */
  public AbstractConfigurationDocument(ConfigurationAccess configurationAccess,
      AbstractConfiguration parentConfiguration) {

    this(configurationAccess, parentConfiguration, null);
  }

  /**
   * The constructor.
   * 
   * @see ConfigurationUtil#createDefaultContext()
   * 
   * @param configurationAccess
   * @param parentConfiguration
   * @param docContext
   */
  private AbstractConfigurationDocument(ConfigurationAccess configurationAccess,
      AbstractConfiguration parentConfiguration, MutableContext docContext) {

    super();
    this.access = configurationAccess;
    this.parentConf = parentConfiguration;
    if (this.parentConf == null) {
      this.parentDoc = null;
    } else {
      this.parentDoc = parentConfiguration.getOwnerDocument();
    }
    this.childDocuments = new ArrayList<ConfigurationDocument>();
    this.modifiedFlag = false;
    this.immutableFlag = this.access.isReadOnly();
    this.root = null;
    if (this.parentDoc == null) {
      this.context = docContext;
      this.listeners = new Vector<FilteredListener>();
    } else {
      this.context = this.parentDoc.getContext().createChildContext();
      this.listeners = null;
    }

    String accessPrefix = this.access.getContextPrefix();
    if (accessPrefix != null) {
      String hrefKey = accessPrefix + ConfigurationAccessFactory.CONTEXT_VARIABLE_SUFFIX_PARENT;
      this.context.setObject(hrefKey, this.access);
    }

    detectInclusionCycle();
  }

  /**
   * This method checks this document to ensure that is was NOT caused by a
   * cyclic inclusion.
   * 
   * @throws ConfigurationException
   *         if a cyclic inclusion was detected.
   */
  protected void detectInclusionCycle() throws ConfigurationException {

    AbstractConfigurationDocument doc = this.parentDoc;
    String accessId = this.access.getUniqueUri();
    while (doc != null) {
      if (accessId.equals(doc.access.getUniqueUri())) {
        throw new ConfigurationException(NlsBundleConfigCore.ERR_INCLUDE_CYCLE, accessId, this);
      }
      doc = doc.parentDoc;
    }
  }

  /**
   * This method gets the parent configuration of the
   * {@link #getConfiguration() root}.
   * 
   * @return the parent configuration or <code>null</code> if this is a
   *         root-document.
   */
  public AbstractConfiguration getParentConfiguration() {

    return this.parentConf;
  }

  /**
   * This method gets the parent document.
   * 
   * @return the parent document or <code>null</code> if this is the
   *         root-document.
   */
  public ConfigurationDocument getParentDocument() {

    return this.parentDoc;
  }

  /**
   * @see net.sf.mmm.configuration.api.ConfigurationDocument#getContext()
   */
  public Context getContext() {

    if (this.root == null) {
      getConfiguration();
    }
    return this.context.getImmutableContext();
  }

  /**
   * This method gets the mutable {@link #getContext() context} of this
   * document.
   * 
   * @return the mutable context.
   */
  protected MutableContext getMutableContext() {

    return this.context;
  }

  /**
   * @see net.sf.mmm.configuration.api.ConfigurationDocument#isImmutable()
   */
  public boolean isImmutable() {

    return this.immutableFlag;
  }

  /**
   * This method determines if this document has been modified. It is modified
   * if its configuration data has been modified without being saved. The flag
   * will be reseted (set <code>false</code>) if the document is
   * {@link #save() saved}.
   * 
   * @return <code>true</code> if this document has been modified,
   *         <code>false</code> otherwise.
   */
  public boolean isModified() {

    return this.modifiedFlag;
  }

  /**
   * This method sets the {@link #isModified() modified-flag} to
   * <code>true</code> and
   * {@link #configurationChanged(Configuration) sends} a
   * {@link ConfigurationChangeEvent change-event}. This method must be called
   * by the {@link Configuration configurations} of this document on any
   * change.
   * 
   * @see #save()
   * 
   * @param configuration
   *        is the configuration that has been modified ({@link Configuration#getValue() value}
   *        changed or
   *        {@link AbstractConfiguration#getChild(String, String) child} has
   *        been added or removed).
   */
  protected void setModified(Configuration configuration) {

    this.modifiedFlag = true;
    configurationChanged(configuration);
  }

  /**
   * @see net.sf.mmm.configuration.api.ConfigurationDocument#getConfiguration()
   */
  public synchronized AbstractConfiguration getConfiguration() {

    if (this.root == null) {
      try {
        this.root = load();
        this.root.initialize();
      } catch (Exception e) {
        throw new ConfigurationException(e, "Failed to initialize " + toString());
      }
    }
    return this.root;
  }

  /**
   * This method access to read/write the configuration data.
   * 
   * @return the configuration data access.
   */
  protected ConfigurationAccess getConfigurationAccess() {

    return this.access;
  }

  /**
   * @see net.sf.mmm.configuration.api.ConfigurationDocument#save()
   */
  public void save() throws ConfigurationException {

    List<ConfigurationException> exceptions = new ArrayList<ConfigurationException>();
    if (isModified() && !isImmutable()) {
      OutputStream outputStream = getConfigurationAccess().getWriteAccess();
      try {
        save(outputStream);
        this.modifiedFlag = false;
      } finally {
        try {
          outputStream.close();
        } catch (IOException e) {
          // TODO
          exceptions.add(new ConfigurationException(e.getMessage()));
        }
      }
    }
    // save the child documents
    for (int i = 0; i < this.childDocuments.size(); i++) {
      try {
        this.childDocuments.get(i).save();
      } catch (ConfigurationException e) {
        exceptions.add(e);
      }
    }
    if (exceptions.size() > 0) {
      if (exceptions.size() == 1) {
        throw exceptions.get(0);
      } else {
        throw new CompositeConfigurationException(exceptions);
      }
    }

  }

  /**
   * This method saves the configuration to the given output stream. The stream
   * must NOT be closed by this method.
   * 
   * @param outputStream
   *        is the output stream where to save the configuration to.
   * @throws ConfigurationException
   *         if the data can not be written.
   */
  protected abstract void save(OutputStream outputStream) throws ConfigurationException;

  /**
   * This method loads the configuration.
   * 
   * @return the {@link #getConfiguration() root-node} of the loaded and parsed
   *         configuration.
   * @throws ConfigurationException
   *         if the data can not be read or is illegal.
   */
  protected final AbstractConfiguration load() throws ConfigurationException {

    InputStream inputStream = getConfigurationAccess().getReadAccess();
    try {
      return load(inputStream);
    } finally {
      try {
        inputStream.close();
      } catch (IOException e) {
        throw new ConfigurationException(e.getMessage());
      }
    }
  }

  /**
   * @see net.sf.mmm.configuration.api.ConfigurationDocument#reload()
   */
  public void reload() throws ConfigurationException {

    // TODO
    throw new ConfigurationException("Currently Unsupported!");
  }

  /**
   * This method loads the configuration from the given input stream. The stream
   * must NOT be closed by this method.
   * 
   * @param inputStream
   *        is the input stream where to load the configuration from.
   * @return the {@link #getConfiguration() root-node} of the loaded and parsed
   *         configuration.
   * @throws ConfigurationException
   *         if the data can NOT be accessed or is illegal.
   */
  protected abstract AbstractConfiguration load(InputStream inputStream)
      throws ConfigurationException;

  /**
   * @see net.sf.mmm.configuration.base.access.ConfigurationDocumentCollector#addDocument(net.sf.mmm.configuration.api.ConfigurationDocument)
   */
  public void addDocument(ConfigurationDocument document) {

    this.childDocuments.add(document);
  }

  /**
   * 
   * @param configuration
   * @param listener
   */
  protected void addListener(Configuration configuration, ConfigurationChangeListener listener) {

    if (this.parentDoc == null) {
      this.listeners.add(new FilteredListener(configuration, listener));
    } else {
      this.parentDoc.addListener(configuration, listener);
    }
  }

  /**
   * 
   * @param configuration
   * @param listener
   */
  protected void removeListener(Configuration configuration,
      ConfigurationChangeListener listener) {

    if (this.parentDoc == null) {
      this.listeners.remove(new FilteredListener(configuration, listener));
    } else {
      this.parentDoc.removeListener(configuration, listener);
    }
  }

  /**
   * This method notifies all listeners about the change of the given
   * configuration.
   * 
   * @param configuration
   *        is the configuration that has changed.
   */
  protected void configurationChanged(Configuration configuration) {

    if (this.parentDoc == null) {
      int length = this.listeners.size();
      if (length > 0) {
        ConfigurationChangeEvent event = new ConfigurationChangeEvent(configuration);
        for (int i = 0; i < length; i++) {
          try {
            this.listeners.get(i).handleEvent(event);
          } catch (RuntimeException e) {

          }
        }
      }
    } else {
      this.parentDoc.configurationChanged(configuration);
    }
  }

  /**
   * @see java.lang.Object#toString() 
   */
  @Override
  public String toString() {

    StringBuffer sb = new StringBuffer("document[");
    sb.append(this.access);
    sb.append(']');
    return sb.toString();
  }

  /**
   * This inner class represents a container for a {@link #configuration} and a
   * {@link #listener}. Additionally it is a
   * {@link ConfigurationChangeListener listener} itself that delegates all
   * events about configurations in the
   * {@link Configuration#isDescendantOf(Configuration) subtree} of the
   * {@link #configuration} to the {@link #listener}. All other events are
   * filtered (ignored).
   */
  private static class FilteredListener implements ConfigurationChangeListener {

    /** the configuration from where to start listening */
    private final Configuration configuration;

    /** the listener delegate */
    private final ConfigurationChangeListener listener;

    /**
     * The constructor.
     * 
     * @param conf
     *        is the configuration from where the listener starts listening.
     * @param changeListener
     *        is the actual listener that handles the events.
     */
    public FilteredListener(Configuration conf, ConfigurationChangeListener changeListener) {

      super();
      this.configuration = conf;
      this.listener = changeListener;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object) 
     */
    @Override
    public boolean equals(Object obj) {

      if ((obj != null) && (obj.getClass() == FilteredListener.class)) {
        FilteredListener other = (FilteredListener) obj;
        if ((this.configuration == other.configuration) && (this.listener == other.listener)) {
          return true;
        }
      }
      return false;
    }

    /**
     * @see java.lang.Object#hashCode() 
     */
    @Override
    public int hashCode() {

      return (this.configuration.hashCode() ^ this.listener.hashCode());
    }

    /**
     * @see net.sf.mmm.util.event.EventListener#handleEvent(net.sf.mmm.util.event.Event)
     */
    public void handleEvent(ConfigurationChangeEvent event) {

      Configuration changedConfiguration = event.getConfiguration();
      if ((changedConfiguration == this.configuration)
          || changedConfiguration.isDescendantOf(this.configuration)) {
        this.listener.handleEvent(event);
      }
    }
  }

}
