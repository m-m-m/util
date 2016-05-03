/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;

import javax.inject.Named;
import javax.inject.Singleton;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.i18n.client.Dictionary;

import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsMessageFactory;
import net.sf.mmm.util.nls.api.NlsResourceBundleRequestor;
import net.sf.mmm.util.nls.api.NlsTemplate;
import net.sf.mmm.util.nls.base.AbstractNlsResourceBundleJavaScriptServlet;
import net.sf.mmm.util.nls.base.NlsMessagePlain;
import net.sf.mmm.util.nls.base.NlsTemplateImplWithMessage;
import net.sf.mmm.util.nls.base.NlsTenmplateImplWithMessage;
import net.sf.mmm.util.nls.impl.NlsMessageImpl;

/**
 * This is the implementation of {@link NlsMessageFactory}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Named(NlsMessageFactory.CDI_NAME)
@Singleton
public class NlsMessageFactoryImpl extends AbstractNlsMessageFactoryImpl implements NlsResourceBundleRequestor {

  private final Map<String, DictionaryContainer> name2dictionaryContainerMap;

  private NlsTemplateImplWithMessage dummy = new NlsTemplateImplWithMessage("name", "key", "message");

  /**
   * The constructor.
   */
  public NlsMessageFactoryImpl() {

    super();
    this.name2dictionaryContainerMap = new HashMap<String, DictionaryContainer>();
  }

  @Override
  public NlsMessage create(NlsTemplate template, Map<String, Object> messageArguments) {

    if ((messageArguments == null) || (messageArguments.isEmpty())) {
      return new NlsMessagePlain(template.translate(Locale.getDefault()));
    }
    return new NlsMessageImpl(template, messageArguments);
  }

  @Override
  public NlsMessage create(String internationalizedMessage, Map<String, Object> messageArguments) {

    if ((messageArguments == null) || (messageArguments.isEmpty())) {
      return new NlsMessagePlain(internationalizedMessage);
    }
    return new NlsMessageImpl(internationalizedMessage, messageArguments);
  }

  @Override
  public NlsMessage createDirect(String bundleName, String key, Map<String, Object> messageArguments) {

    DictionaryContainer dictionaryContainer = getOrCreateDictionaryContainer(bundleName);
    String message = dictionaryContainer.resolve(key);
    return create(message, messageArguments);
  }

  private DictionaryContainer getOrCreateDictionaryContainer(String bundleName) {

    DictionaryContainer dictionaryContainer = this.name2dictionaryContainerMap.get(bundleName);
    if (dictionaryContainer == null) {
      dictionaryContainer = new DictionaryContainer(bundleName);
      this.name2dictionaryContainerMap.put(bundleName, dictionaryContainer);
    }
    return dictionaryContainer;
  }

  @Override
  public void requestBundlesAsynchron(Runnable callback, String... bundleNames) {

    CallbackAdapter adapter = new CallbackAdapter(callback);
    for (String name : bundleNames) {
      DictionaryContainer dictionaryContainer = getOrCreateDictionaryContainer(name);
      if (dictionaryContainer.dictionary == null) {
        // so the bundle/dictionary is not available and has to be loaded...
        adapter.bundleNameSet.add(name);
        if (dictionaryContainer.callbackAdapters == null) {
          // request the bundle/dictionary from server and ensure callback is invoked after all bundles are available.
          dictionaryContainer.callbackAdapters = new LinkedList<CallbackAdapter>();
          dictionaryContainer.callbackAdapters.add(adapter);
          String url = GWT.getModuleBaseURL() + AbstractNlsResourceBundleJavaScriptServlet.URL_PATH + AbstractNlsResourceBundleJavaScriptServlet.URL_PARAM_NAME_QUERY + name;
          ScriptInjector.fromUrl(url).setCallback(dictionaryContainer).inject();
        } else {
          // we already have a request ongoing to get the bundle/dictionary so we just append the adapter.
          dictionaryContainer.callbackAdapters.add(adapter);
        }
      }
    }
    if (adapter.bundleNameSet.isEmpty()) {
      // no async request had to be made - all bundles are already available...
      callback.run();
    }
  }

  protected class CallbackAdapter {

    private final Set<String> bundleNameSet;

    private final Runnable callback;

    /**
     * The constructor.
     */
    public CallbackAdapter(Runnable callback) {

      super();
      this.callback = callback;
      this.bundleNameSet = new HashSet<String>();
    }

    public void onSuccess(String bundleName) {

      boolean removed = this.bundleNameSet.remove(bundleName);
      assert (removed) : bundleName;
      if (this.bundleNameSet.isEmpty()) {
        this.callback.run();
      }
    }
  }

  protected class DictionaryContainer implements Callback<Void, Exception> {

    private String bundleName;

    private String dictionaryName;

    private Dictionary dictionary;

    private Collection<CallbackAdapter> callbackAdapters;

    /**
     * The constructor.
     */
    public DictionaryContainer(String name) {

      super();
      this.bundleName = name;
      this.dictionaryName = escapeBundleName(name);
      getDictionary();
    }

    private Dictionary getDictionary() {

      // already loaded?
      if (this.dictionary == null) {
        try {
          this.dictionary = Dictionary.getDictionary(this.dictionaryName);
        } catch (MissingResourceException e) {
          getLogger().error("Dictionary not found for " + this.dictionaryName, e);
        }
      }
      return this.dictionary;
    }

    protected String escapeBundleName(String name) {

      return name.replace('.', '$');
    }

    public String resolve(String key) {

      if (this.dictionary == null) {
        return key;
      }
      try {
        return this.dictionary.get(key);
      } catch (MissingResourceException e) {
        // This sucks - Dictionary should offer a method that does not throw an exception...
        return key;
      }
    }

    public void onFailure(Exception error) {

      getLogger().warn("Failed to load NLS bundle '" + this.bundleName + "'.", error);
    }

    public void onSuccess(Void result) {

      if ((this.callbackAdapters == null) || (this.callbackAdapters.isEmpty())) {
        assert false : "Successfully received '" + this.bundleName + "' but no callback is registered!";
      } else {
        for (CallbackAdapter adapter : this.callbackAdapters) {
          adapter.onSuccess(this.bundleName);
        }
      }
    }
  }

}
