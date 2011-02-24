/* $Id: UIWorkbench.java 153 2007-02-22 22:52:41Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.window;

/**
 * This is the interface of a workbench window. This is a master window for an
 * application that can contain {@link UiFrame}s within its content-area.<br/>
 * In swing this is called {@link javax.swing.JDesktopPane}, in web applications
 * this will be the browser itself and for SWT this might be the eclipse RCP
 * platform.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UiWorkbench extends UiFrame {

  /** the type of this object */
  String TYPE = "Workbench";

}
