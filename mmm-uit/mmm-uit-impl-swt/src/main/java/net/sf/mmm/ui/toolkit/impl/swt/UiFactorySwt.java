/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt;

import net.sf.mmm.ui.toolkit.api.common.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.common.MessageType;
import net.sf.mmm.ui.toolkit.api.feature.UiAction;
import net.sf.mmm.ui.toolkit.api.feature.UiFileAccess;
import net.sf.mmm.ui.toolkit.api.model.data.UiListMvcModel;
import net.sf.mmm.ui.toolkit.api.model.data.UiTableMvcModel;
import net.sf.mmm.ui.toolkit.api.model.data.UiTreeMvcModel;
import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.UiImage;
import net.sf.mmm.ui.toolkit.api.view.composite.UiBorderPanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiGridPanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiGridRow;
import net.sf.mmm.ui.toolkit.api.view.composite.UiScrollPanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiSimplePanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiSplitPanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiTabPanel;
import net.sf.mmm.ui.toolkit.api.view.widget.UiButton;
import net.sf.mmm.ui.toolkit.api.view.widget.UiComboBox;
import net.sf.mmm.ui.toolkit.api.view.widget.UiDateBox;
import net.sf.mmm.ui.toolkit.api.view.widget.UiFileDownload;
import net.sf.mmm.ui.toolkit.api.view.widget.UiFileUpload;
import net.sf.mmm.ui.toolkit.api.view.widget.UiLabel;
import net.sf.mmm.ui.toolkit.api.view.widget.UiList;
import net.sf.mmm.ui.toolkit.api.view.widget.UiProgressBar;
import net.sf.mmm.ui.toolkit.api.view.widget.UiSlideBar;
import net.sf.mmm.ui.toolkit.api.view.widget.UiSpinBox;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTable;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTextField;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTree;
import net.sf.mmm.ui.toolkit.api.view.window.UiDialog;
import net.sf.mmm.ui.toolkit.api.view.window.UiFrame;
import net.sf.mmm.ui.toolkit.api.view.window.UiWindow;
import net.sf.mmm.ui.toolkit.api.view.window.UiWorkbench;
import net.sf.mmm.ui.toolkit.base.AbstractUiFactory;
import net.sf.mmm.ui.toolkit.base.view.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.swt.feature.PrintAction;
import net.sf.mmm.ui.toolkit.impl.swt.view.UiImageImpl;
import net.sf.mmm.ui.toolkit.impl.swt.view.composite.UiBorderPanelImpl;
import net.sf.mmm.ui.toolkit.impl.swt.view.composite.UiScrollPanelImpl;
import net.sf.mmm.ui.toolkit.impl.swt.view.composite.UiSimplePanelImpl;
import net.sf.mmm.ui.toolkit.impl.swt.view.composite.UiSplitPanelImpl;
import net.sf.mmm.ui.toolkit.impl.swt.view.composite.UiTabbedPanelImpl;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SwtWorkerThread;
import net.sf.mmm.ui.toolkit.impl.swt.view.widget.UIButtonImpl;
import net.sf.mmm.ui.toolkit.impl.swt.view.widget.UIComboBoxImpl;
import net.sf.mmm.ui.toolkit.impl.swt.view.widget.UIDateEditorImpl;
import net.sf.mmm.ui.toolkit.impl.swt.view.widget.UIFileDownloadImpl;
import net.sf.mmm.ui.toolkit.impl.swt.view.widget.UIFileUploadImpl;
import net.sf.mmm.ui.toolkit.impl.swt.view.widget.UILabelImpl;
import net.sf.mmm.ui.toolkit.impl.swt.view.widget.UIListImpl;
import net.sf.mmm.ui.toolkit.impl.swt.view.widget.UIProgressBarImpl;
import net.sf.mmm.ui.toolkit.impl.swt.view.widget.UISlideBarImpl;
import net.sf.mmm.ui.toolkit.impl.swt.view.widget.UISpinBoxImpl;
import net.sf.mmm.ui.toolkit.impl.swt.view.widget.UITableImpl;
import net.sf.mmm.ui.toolkit.impl.swt.view.widget.UITextFieldImpl;
import net.sf.mmm.ui.toolkit.impl.swt.view.widget.UITreeImpl;
import net.sf.mmm.ui.toolkit.impl.swt.view.window.AbstractUiWindowSwt;
import net.sf.mmm.ui.toolkit.impl.swt.view.window.UiDialogImpl;
import net.sf.mmm.ui.toolkit.impl.swt.view.window.UiFrameImpl;
import net.sf.mmm.ui.toolkit.impl.swt.view.window.UiWorkbenchImpl;
import net.sf.mmm.util.lang.api.Orientation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

/**
 * This class is the implementation of the UIFactory interface using SWT as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class UiFactorySwt extends AbstractUiFactory {

  /**
   * {@inheritDoc}
   */
  public UiGridPanel<? extends UiGridRow<? extends UiElement>> createGridPanel() {

    // TODO Auto-generated method stub
    return null;
  }

  /** the display */
  private UiDisplayImpl display;

  /** the actual SWT display */
  private Display swtDisplay;

  /** the worker thread */
  private SwtWorkerThread worker;

  /**
   * The dummy constructor.
   * 
   * This constructor may be used for testing if an instance is required for the default display without using
   * the {@link net.sf.mmm.ui.toolkit.api.UiService UIService}.
   * 
   * @param title is the title of this factory. Should be the name of the actual application creating the UI.
   */
  public UiFactorySwt(String title) {

    super(title);
    this.worker = new SwtWorkerThread();
    this.worker.start();
    this.swtDisplay = this.worker.getDisplay();
    Monitor m = this.worker.getMonitor();
    this.display = new UiDisplayImpl(this, new UiDeviceImpl(m), this.swtDisplay);
  }

  /**
   * The constructor.
   * 
   * @param title is the title of this factory. Should be the name of the actual application creating the UI.
   * @param swtDisplay is the display to use.
   */
  public UiFactorySwt(String title, Display swtDisplay) {

    this(title, swtDisplay, new UiDeviceImpl(swtDisplay.getPrimaryMonitor()));
  }

  /**
   * The constructor.
   * 
   * @param title is the title of this factory. Should be the name of the actual application creating the UI.
   * @param swtDisplay is the display to use.
   * @param uiDevice is the graphics device the display of this factory belongs to.
   */
  public UiFactorySwt(String title, Display swtDisplay, UiDeviceImpl uiDevice) {

    super(title);
    this.swtDisplay = swtDisplay;
    this.display = new UiDisplayImpl(this, uiDevice, this.swtDisplay);
  }

  /**
   * This method gets the dummy parent used for UI objects that should have no parent (<code>null</code>).
   * 
   * @return the SWT dummy parent.
   * @deprecated this hack should be removed!
   */
  @Deprecated
  public Shell getDummyParent() {

    return this.worker.getDummyParent();
  }

  /**
   * {@inheritDoc}
   */
  public UiFrame createFrame(String title, boolean resizeable) {

    UiFrameImpl frame = new UiFrameImpl(this, null, resizeable);
    frame.setTitle(title);
    addWindow(frame);
    return frame;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiFrameImpl createFrame(UiFrame parent, String title, boolean resizable) {

    UiFrameImpl frame = new UiFrameImpl(this, (UiFrameImpl) parent, resizable);
    frame.setTitle(title);
    addWindow(frame);
    return frame;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiDialog createDialog(UiWindow parent, String title, boolean modal, boolean resizeable) {

    UiDialogImpl dialog = new UiDialogImpl(this, (AbstractUiWindowSwt) parent, modal, resizeable);
    dialog.setTitle(title);
    addWindow(dialog);
    return dialog;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showMessage(final UiWindow parent, String message, final String title, MessageType messageType,
      Throwable throwable) {

    int style = SWT.ICON_INFORMATION;
    if (messageType == MessageType.ERROR) {
      style = SWT.ICON_ERROR;
    } else if (messageType == MessageType.WARNING) {
      style = SWT.ICON_WARNING;
    } else if (messageType == MessageType.INFO) {
      style = SWT.ICON_INFORMATION;
    }
    style |= SWT.OK;
    final int swtStyle = style;
    final String msg;
    if (throwable != null) {
      // TODO: hack for the moment...
      msg = message + "\n" + throwable.getMessage();
    } else {
      msg = message;
    }
    getDisplay().invokeSynchron(new Runnable() {

      public void run() {

        MessageBox messageBox = new MessageBox(((AbstractUiWindowSwt) parent).getSwtWindow(), swtStyle);
        messageBox.setText(title);
        messageBox.setMessage(msg);
        messageBox.open();
      }
    });

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean showQuestion(UiWindow parent, String question, String title) {

    MessageBox messageBox = new MessageBox(((AbstractUiWindowSwt) parent).getSwtWindow(), SWT.ICON_QUESTION | SWT.YES
        | SWT.NO);
    messageBox.setText(title);
    messageBox.setMessage(question);
    int result = messageBox.open();
    return (result == SWT.YES);
  }

  /**
   * {@inheritDoc}
   */
  public UiButton createButton(String text, ButtonStyle style) {

    UiButton button = new UIButtonImpl(this, style);
    button.setValue(text);
    return button;
  }

  /**
   * {@inheritDoc}
   */
  public <CHILD extends UiElement> UiScrollPanel<CHILD> createScrollPanel(CHILD child) {

    UiScrollPanel panel = new UiScrollPanelImpl(this);
    if (child != null) {
      panel.setChild(child);
    }
    return panel;
  }

  /**
   * {@inheritDoc}
   */
  public <CHILD extends UiElement> UiSplitPanel<CHILD> createSplitPanel(Orientation orientation) {

    UiSplitPanel splitPanel = new UiSplitPanelImpl(this, orientation);
    return splitPanel;
  }

  /**
   * {@inheritDoc}
   */
  public <E> UiList<E> createList(UiListMvcModel<E> model, boolean multiSelection) {

    UiList<E> list = new UIListImpl<E>(this, multiSelection, model);
    return list;
  }

  /**
   * {@inheritDoc}
   */
  public <N> UiTree<N> createTree(boolean multiSelection, UiTreeMvcModel<N> model) {

    UITreeImpl<N> tree = new UITreeImpl<N>(this, multiSelection);
    if (model != null) {
      tree.setModel(model);
    }
    return tree;
  }

  /**
   * {@inheritDoc}
   */
  public <C> UiTable<C> createTable(boolean multiSelection, UiTableMvcModel<C> model) {

    UITableImpl<C> table = new UITableImpl<C>(this, multiSelection);
    if (model != null) {
      table.setModel(model);
    }
    return table;
  }

  /**
   * {@inheritDoc}
   */
  public UiDisplayImpl getDisplay() {

    return this.display;
  }

  /**
   * {@inheritDoc}
   */
  public UiLabel createLabel(String text) {

    UiLabel label = new UILabelImpl(this);
    label.setValue(text);
    return label;
  }

  /**
   * {@inheritDoc}
   */
  public UiTextField createTextField() {

    UITextFieldImpl textField = new UITextFieldImpl(this);
    return textField;
  }

  /**
   * {@inheritDoc}
   */
  public <E> UiSpinBox<E> createSpinBox(UiListMvcModel<E> model) {

    return new UISpinBoxImpl<E>(this, model);
  }

  /**
   * {@inheritDoc}
   */
  public UiFileDownload createFileDownload(UiFileAccess access) {

    return new UIFileDownloadImpl(this, access);
  }

  /**
   * {@inheritDoc}
   */
  public UiFileUpload createFileUpload() {

    return new UIFileUploadImpl(this);
  }

  /**
   * {@inheritDoc}
   */
  public UiImage createImage(UiFileAccess fileAccess) {

    return new UiImageImpl(this, fileAccess);
  }

  /**
   * {@inheritDoc}
   */
  public <E> UiComboBox<E> createComboBox(UiListMvcModel<E> model, boolean editableFlag) {

    return new UIComboBoxImpl<E>(this, editableFlag, model);
  }

  /**
   * {@inheritDoc}
   */
  public <CHILD extends UiElement> UiTabPanel<CHILD> createTabbedPanel() {

    return new UiTabbedPanelImpl(this);
  }

  /**
   * {@inheritDoc}
   */
  public <E> UiSlideBar<E> createSlideBar(UiListMvcModel<E> model, Orientation orientation) {

    // TODO
    return new UISlideBarImpl<E>(this, orientation, model);
  }

  /**
   * {@inheritDoc}
   */
  public UiProgressBar createProgressBar(Orientation orientation) {

    return new UIProgressBarImpl(this, orientation, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWorkbench createWorkbench(String title) {

    UiWorkbenchImpl workbench = new UiWorkbenchImpl(this);
    workbench.setTitle(title);
    return workbench;
  }

  /**
   * {@inheritDoc}
   */
  public UiAction createPrintUiAction(UiElement component, String actionName, String jobName) {

    return new PrintAction((AbstractUiElement) component, actionName, jobName);
  }

  /**
   * {@inheritDoc}
   */
  public UiDateBox createDateEditor() {

    return new UIDateEditorImpl(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {

    this.worker.dispose();
    super.dispose();
  }

  /**
   * This method converts the given <code>buttonStyle</code> to the according {@link org.eclipse.swt.SWT}
   * constant for a {@link org.eclipse.swt.widgets.MenuItem}.
   * 
   * @param buttonStyle is the button-style to convert.
   * @return the according SWT button style.
   */
  public static int convertButtonStyleForMenuItem(ButtonStyle buttonStyle) {

    return convertButtonStyle(buttonStyle);
    /*
     * if (buttonStyle == ButtonStyle.DEFAULT) { return SWT.CASCADE; } else { return
     * convertButtonStyle(buttonStyle); }
     */
  }

  /**
   * This method converts the given <code>buttonStyle</code> to the according {@link org.eclipse.swt.SWT}
   * constant for a {@link org.eclipse.swt.widgets.Button}.
   * 
   * @param buttonStyle is the button-style to convert.
   * @return the according SWT button style.
   */
  public static int convertButtonStyle(ButtonStyle buttonStyle) {

    switch (buttonStyle) {
      case DEFAULT:
        return SWT.PUSH;
      case CHECK:
        return SWT.CHECK;
      case RADIO:
        return SWT.RADIO;
      case TOGGLE:
        return SWT.TOGGLE;
      default :
        throw new IllegalArgumentException("Unknown style " + buttonStyle);
    }
  }

  /**
   * This method converts the given <code>orientation</code> to the according {@link org.eclipse.swt.SWT}
   * constant.
   * 
   * @param orientation is the orientation to convert.
   * @return the according SWT style.
   */
  public static int convertOrientation(Orientation orientation) {

    switch (orientation) {
      case HORIZONTAL:
        return SWT.HORIZONTAL;
      case VERTICAL:
        return SWT.VERTICAL;
      default :
        throw new IllegalArgumentException("Unknown orientation " + orientation);
    }
  }

  /**
   * This method gets the given <code>baseStyle</code> adjusted with the global settings of the factory.
   * 
   * @see #setScriptOrientation(net.sf.mmm.ui.toolkit.api.common.ScriptOrientation)
   * 
   * @param baseStyle is the basic style.
   * @return the given <code>baseStyle</code> with additional options from this factory.
   */
  public int adjustStyle(int baseStyle) {

    if (getScriptOrientation().isLeftToRight()) {
      return baseStyle | SWT.LEFT_TO_RIGHT;
    } else {
      return baseStyle | SWT.RIGHT_TO_LEFT;
    }
  }

  /**
   * {@inheritDoc}
   */
  public <CHILD extends UiElement> UiBorderPanel<CHILD> createBorderPanel(String title) {

    UiBorderPanelImpl panel = new UiBorderPanelImpl(this);
    panel.setTitle(title);
    return panel;
  }

  /**
   * {@inheritDoc}
   */
  public <CHILD extends UiElement> UiSimplePanel<CHILD> createSimplePanel(Orientation orientation) {

    return new UiSimplePanelImpl(this, orientation);
  }

}
