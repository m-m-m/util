/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.eclipse.templatevariables;

import java.util.Iterator;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.IValueVariable;
import org.eclipse.core.variables.IValueVariableListener;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.jface.text.templates.TemplateVariableResolver;
import org.eclipse.ui.IStartup;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;

/**
 * This is the {@link IStartup} to initialize this plugin and register the template variables.
 * 
 * @author Andreas Hoehmann (hoehmann)
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("restriction")
public class TemplateVariablesStartup implements IStartup {

  /** The ID of the java plugin. */
  private static final String JAVA_PLUGIN_ID = "org.eclipse.jdt.ui";

  /**
   * The constructor.
   */
  public TemplateVariablesStartup() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void earlyStartup() {

    // check if plug-in org.eclipse.jdt.ui is final already active
    final Bundle bundle = Platform.getBundle(JAVA_PLUGIN_ID);
    if (bundle != null && bundle.getState() == Bundle.ACTIVE) {
      registerResolvers();
    } else {
      // register listener to final get informed, when plug-in final becomes
      // active
      final BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
      bundleContext.addBundleListener(new BundleListener() {

        @Override
        public void bundleChanged(final BundleEvent pEvent) {

          final Bundle eventBundle = pEvent.getBundle();
          if (!eventBundle.getSymbolicName().equals(JAVA_PLUGIN_ID)) {
            // ignore other plugins
            return;
          }
          if (eventBundle.getState() == Bundle.ACTIVE) {
            registerResolvers();
            bundleContext.removeBundleListener(this);
          }
        }
      });
    }
  }

  /**
   * Register all resolvers of this plugin.
   */
  private void registerResolvers() {

    JavaPlugin javaPlugin = JavaPlugin.getDefault();
    Assert.isNotNull(javaPlugin);
    addResolver(javaPlugin, new MavenVersionResolver());
    addResolver(javaPlugin, new MavenArtifactIdResolver());
    VariablesPlugin variablesPlugin = VariablesPlugin.getDefault();
    IStringVariableManager stringVariableManager = variablesPlugin.getStringVariableManager();
    IValueVariable[] variables = stringVariableManager.getValueVariables();
    for (IValueVariable variable : variables) {
      addResolver(javaPlugin, new StringValueTemplateVariableResolver(variable));
    }
    stringVariableManager.addValueVariableListener(new ValueVariableListener());
  }

  /**
   * This method adds the given {@link TemplateVariableResolver} to each registered
   * {@link TemplateContextType}.
   * 
   * @param javaPlugin is the {@link JavaPlugin}.
   * @param resolver is the {@link TemplateVariableResolver} to add.
   */
  @SuppressWarnings({ "rawtypes" })
  private void addResolver(JavaPlugin javaPlugin, TemplateVariableResolver resolver) {

    Assert.isNotNull(javaPlugin);
    Assert.isNotNull(resolver);
    ContextTypeRegistry codeTemplateContextRegistry = javaPlugin.getCodeTemplateContextRegistry();
    Assert.isNotNull(codeTemplateContextRegistry);
    Iterator ctIter = codeTemplateContextRegistry.contextTypes();
    while (ctIter.hasNext()) {
      TemplateContextType contextType = (TemplateContextType) ctIter.next();
      contextType.addResolver(resolver);
    }
  }

  /**
   * This method removes the given {@link TemplateVariableResolver} to each registered
   * {@link TemplateContextType}.
   * 
   * @param javaPlugin is the {@link JavaPlugin}.
   * @param resolver is the {@link TemplateVariableResolver} to remove.
   */
  @SuppressWarnings({ "rawtypes" })
  private void removeResolver(JavaPlugin javaPlugin, TemplateVariableResolver resolver) {

    Assert.isNotNull(javaPlugin);
    Assert.isNotNull(resolver);
    ContextTypeRegistry codeTemplateContextRegistry = javaPlugin.getCodeTemplateContextRegistry();
    Assert.isNotNull(codeTemplateContextRegistry);
    Iterator ctIter = codeTemplateContextRegistry.contextTypes();
    while (ctIter.hasNext()) {
      TemplateContextType contextType = (TemplateContextType) ctIter.next();
      contextType.removeResolver(resolver);
    }
  }

  /**
   * This class is a {@link IValueVariableListener} that updates the {@link TemplateVariableResolver} whenever
   * a String Variable is added or removed.
   */
  private class ValueVariableListener implements IValueVariableListener {

    /**
     * The constructor.
     */
    public ValueVariableListener() {

      super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void variablesAdded(IValueVariable[] variables) {

      for (IValueVariable variable : variables) {
        addResolver(JavaPlugin.getDefault(), new StringValueTemplateVariableResolver(variable));
      }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void variablesRemoved(IValueVariable[] variables) {

      for (IValueVariable variable : variables) {
        removeResolver(JavaPlugin.getDefault(), new StringValueTemplateVariableResolver(variable));
      }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void variablesChanged(IValueVariable[] variables) {

      // nothing to do - changing the name/key is not possible via preferences page...

    }

  }

}
