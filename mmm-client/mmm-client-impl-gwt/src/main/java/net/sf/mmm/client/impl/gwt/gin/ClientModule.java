/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.impl.gwt.gin;

import net.sf.mmm.client.base.gwt.dialog.DialogManagerImplGwt;
import net.sf.mmm.client.impl.gwt.busy.BusyManagerImplGwt;
import net.sf.mmm.client.ui.api.UiPopupHelper;
import net.sf.mmm.client.ui.api.busy.BusyManager;
import net.sf.mmm.client.ui.api.dialog.DialogManager;
import net.sf.mmm.client.ui.impl.gwt.UiPopupHelperDummy;

import com.google.gwt.inject.client.AbstractGinModule;

/**
 * This is the {@link AbstractGinModule} that configures which implementation to use for the components
 * offered by {@link ClientGinjector}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ClientModule extends AbstractGinModule {

  /**
   * The constructor.
   */
  public ClientModule() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void configure() {

    // NOTE: Components not configured here will be created as singletons via GWT.create()
    bind(BusyManager.class).to(BusyManagerImplGwt.class).asEagerSingleton();
    bind(DialogManager.class).to(DialogManagerImplGwt.class).asEagerSingleton();
    bind(UiPopupHelper.class).to(UiPopupHelperDummy.class).asEagerSingleton();
  }

}
