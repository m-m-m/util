/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.gwt;

import net.sf.mmm.ui.toolkit.api.UiDevice;
import net.sf.mmm.ui.toolkit.api.UiDisplay;
import net.sf.mmm.ui.toolkit.api.common.MessageType;
import net.sf.mmm.ui.toolkit.api.feature.UiFileAccess;
import net.sf.mmm.ui.toolkit.api.view.UiImage;
import net.sf.mmm.ui.toolkit.api.view.window.UiWindow;
import net.sf.mmm.ui.toolkit.base.AbstractUiFactory;

import com.google.gwt.user.client.Window;

/**
 * This is the abstract base implementation of
 * {@link net.sf.mmm.ui.toolkit.api.UiFactory}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiFactoryGwt extends AbstractUiFactory {

  /** @see #getDisplay() */
  private final UiDisplay display;

  /** @see #getDisplay() */
  private final UiDevice device;

  /**
   * The constructor.
   * 
   * @param title is the title of this factory. Should be the name of the actual
   *        application creating the UI.
   */
  public AbstractUiFactoryGwt(String title) {

    super(title);
    this.device = new UiDeviceImpl();
    this.display = new UiDisplayImpl(this, this.device);
  }

  /**
   * {@inheritDoc}
   */
  public UiDisplay getDisplay() {

    return this.display;
  }

  /**
   * {@inheritDoc}
   */
  public UiImage createImage(UiFileAccess fileAccess) {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showMessage(UiWindow parent, String message, String title, MessageType messageType,
      Throwable throwable) {

    // TODO: default hack
    String msg = message;
    if (throwable != null) {
      msg = message + "\n" + throwable.getMessage();
    }
    Window.alert(msg);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean showQuestion(UiWindow parent, String question, String title) {

    return Window.confirm(question);
  }

}
