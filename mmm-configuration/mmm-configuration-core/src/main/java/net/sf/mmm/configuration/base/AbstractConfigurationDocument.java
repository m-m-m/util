/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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
import net.sf.mmm.term.api.TermParser;
import net.sf.mmm.util.event.ChangeEvent;

/**
 * This is the abstract base implementation of the {@link ConfigurationDocument}
 * interface.
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

  /** @see #getTermParser() */
  private TermParser termParser;

  /** the {@link #getConfiguration() root node} */
  private AbstractConfiguration root;

  /** the status of the {@link #isModified() modified-flag} */
  private boolean modifiedFlag;

  /**
   * The constructor.
   * 
   * @param configurationAccess
   *        is the {@link #getConfigurationAccess() access} to the raw
   *        configuration.
   * @param docContext
   *        is the initial {@link #getContext() context} to use.
   */
  public AbstractConfigurationDocument(ConfigurationAccess configurationAccess,
      MutableContext docContext) {

    this(configurationAccess, null, docContext);
  }

  /**
   * The constructor.
   * 
   * @param configurationAccess
   *        is the {@link #getConfigurationAccess() access} to the raw
   *        configuration.
   * @param parentConfiguration
   *        is the {@link #getParentConfiguration() parent-configuration}.
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
   *        is the {@link #getConfigurationAccess() access} to the raw
   *        configuration.
   * @param parentConfiguration
   *        is the {@link #getParentConfiguration() parent-configuration} or
   *        <code>null</code> if root-document.
   * @param docContext
   *        is the {@link #getContext() context} to use if root-document or
   *        <code>null</code> otherwise.
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
      this.listeners = new ArrayList<FilteredListener>();
    } else {
      this.context = this.parentDoc.getContext().createChildContext();
      this.listeners = null;
    }
    if (this.parentDoc != null) {
      this.termParser = this.parentDoc.getTermParser();
    } else {
      this.termParser = null;
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
          // TODO: NLS
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
        // TODO: NLS
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
   * This method registers the given <code>listener</code> for events in the
   * sub-tree of the given <code>configuration</code>.
   * 
   * @param configuration
   *        is the configuration where the listener was registered.
   * @param listener
   *        is the listener to add.
   */
  protected void addListener(Configuration configuration, ConfigurationChangeListener listener) {

    if (this.parentDoc == null) {
      synchronized (this.listeners) {
        this.listeners.add(new FilteredListener(configuration, listener));
      }
    } else {
      this.parentDoc.addListener(configuration, listener);
    }
  }

  /**
   * This method removes the given <code>listener</code>.
   * 
   * @param configuration
   *        is the configuration where the listener de-registered.
   * @param listener
   *        is the listener to remove.
   * @return <code>true</code> if the listener has successfully be removed,
   *         <code>false</code> otherwise (the listener has NOT been
   *         registered).
   */
  protected boolean removeListener(Configuration configuration, ConfigurationChangeListener listener) {

    if (this.parentDoc == null) {
      synchronized (this.listeners) {
        int len = this.listeners.size();
        for (int i = 0; i < len; i++) {
          FilteredListener filteredListener = this.listeners.get(i);
          if (filteredListener.listener == listener) {
            this.listeners.remove(i);
            return true;
          }
        }
        return false;
      }
    } else {
      return this.parentDoc.removeListener(configuration, listener);
    }
  }

  /**
   * This method sets the {@link #isModified() modified-flag} to
   * <code>true</code> and notifies all listeners about the change of the
   * given configuration.
   * 
   * @see #save()
   * @see #addListener(Configuration, ConfigurationChangeListener)
   * 
   * @param configuration
   *        is the configuration that has changed (been added/removed/updated).
   * @param type
   *        is the type of the change.
   */
  protected void configurationChanged(AbstractConfiguration configuration, ChangeEvent.Type type) {

    this.modifiedFlag = true;
    if (this.parentDoc == null) {
      if (this.listeners.size() > 0) {
        synchronized (this.listeners) {
          AbstractConfiguration topCause = configuration;
          if (type != ChangeEvent.Type.UPDATE) {
            topCause = configuration.getParent();
          }
          ConfigurationChangeEvent event = new ConfigurationChangeEvent(configuration, type,
              topCause);
          for (FilteredListener listener : this.listeners) {
            try {
              listener.handleEvent(event);
            } catch (RuntimeException e) {
              // TODO: do something here???
            }
          }
        }
      }
    } else {
      this.parentDoc.configurationChanged(configuration, type);
    }
  }

  /**
   * This method gets the parser used for expression terms in
   * {@link Configuration#getValue() values}.
   * 
   * @see AbstractConfiguration#getTermParser()
   * 
   * @return the term parser to use or <code>null</code>.
   */
  public TermParser getTermParser() {

    return this.termParser;
  }

  /**
   * This method sets the {@link #getTermParser() term parser}.
   * 
   * @param parser
   *        the term parser to use.
   */
  public void setTermParser(TermParser parser) {

    this.termParser = parser;
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

      if (event.getTopCause().getAncestorDistance(this.configuration) >= 0) {
        this.listener.handleEvent(event);
      }
    }
  }

}
