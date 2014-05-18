/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.showcase.client.gwt;

import net.sf.mmm.client.base.gwt.dialog.DialogManagerImplGwt;
import net.sf.mmm.client.ui.api.dialog.DialogConstants;
import net.sf.mmm.client.ui.api.dialog.DialogManager;
import net.sf.mmm.client.ui.base.ComponentContainerContextFallback;
import net.sf.mmm.client.ui.base.UiConfigurationBean;
import net.sf.mmm.client.ui.base.dialog.DialogControllerFactory;
import net.sf.mmm.client.ui.impl.gwt.UiContextGwt;
import net.sf.mmm.showcase.client.dialog.DialogControllerFactoryImpl;
import net.sf.mmm.showcase.client.nls.NlsBundleShowcaseLabelsRoot;
import net.sf.mmm.util.nls.base.NlsMessageLookupProxy;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorBuilderFactoryLimited;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;

/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class Showcase implements EntryPoint {

  /**
   * The constructor.
   */
  public Showcase() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onModuleLoad() {

    // SplitLayoutPanel p = new SplitLayoutPanel();
    // p.add(new HTML("navigation"));
    // p.add(new HTML("navigation"));
    //
    // // Attach the LayoutPanel to the RootLayoutPanel. The latter will listen for
    // // resize events on the window to ensure that its children are informed of
    // // possible size changes.
    // RootLayoutPanel rp = RootLayoutPanel.get();
    // rp.add(p);
    extracted();

  }

  private void extracted() {

    // public void onModuleLoadDeferred() {
    // super.onModuleLoadDeferred();
    Log.debug("Loaded");

    // temporary workaround without IoC/GIN
    new PojoDescriptorBuilderFactoryLimited();

    UiContextGwt context = new UiContextGwt();
    UiConfigurationBean configuration = new UiConfigurationBean();
    configuration.setLabelLookup(new NlsMessageLookupProxy(NlsBundleShowcaseLabelsRoot.class));
    context.setConfiguration(configuration);
    context.initialize();
    ComponentContainerContextFallback container = (ComponentContainerContextFallback) context.getContainer();

    DialogManagerImplGwt dialogManager = new DialogManagerImplGwt();
    DialogControllerFactory dialogControllerFactory = new DialogControllerFactoryImpl();
    dialogManager.setDialogControllerFactory(dialogControllerFactory);
    dialogManager.setContext(context);
    container.put(DialogManager.class, dialogManager);
    dialogManager.initialize(DialogConstants.PLACE_HOME);
  }

}
