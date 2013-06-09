/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.app.client;

import java.util.Collection;
import java.util.Locale;
import java.util.function.Consumer;

import net.sf.mmm.app.client.dialog.DialogControllerFactoryImpl;
import net.sf.mmm.app.shared.GreetingService;
import net.sf.mmm.client.base.gwt.dialog.DialogManagerImplGwt;
import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.common.SizeUnit;
import net.sf.mmm.client.ui.api.common.UiMode;
import net.sf.mmm.client.ui.api.dialog.DialogConstants;
import net.sf.mmm.client.ui.api.feature.UiFeatureClick;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventClick;
import net.sf.mmm.client.ui.api.handler.object.UiHandlerObjectSave;
import net.sf.mmm.client.ui.api.widget.UiWidgetFactory;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetCollapsableSection;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetImage;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetLabel;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetTab;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetDateField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetRichTextArea;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetTextField;
import net.sf.mmm.client.ui.api.widget.menu.UiWidgetMenu;
import net.sf.mmm.client.ui.api.widget.menu.UiWidgetMenuBar;
import net.sf.mmm.client.ui.api.widget.menu.UiWidgetMenuItemClickable;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetBorderPanel;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetHorizontalPanel;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetTabPanel;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetVerticalPanel;
import net.sf.mmm.client.ui.api.widget.window.UiWidgetMainWindow;
import net.sf.mmm.client.ui.api.widget.window.UiWidgetPopup;
import net.sf.mmm.client.ui.base.dialog.DialogControllerFactory;
import net.sf.mmm.client.ui.impl.gwt.UiContextGwt;
import net.sf.mmm.service.api.client.RemoteInvocationServiceCaller;
import net.sf.mmm.service.api.client.RemoteInvocationServiceQueue;
import net.sf.mmm.util.filter.api.CharFilter;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorBuilderFactoryLimited;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Mmm implements EntryPoint {// extends AbstractEntryPoint<ClientGinjector> {

  /**
   * The message displayed to the user when the server cannot be reached or returns an error.
   */
  private static final String SERVER_ERROR = "An error occurred while "
      + "attempting to contact the server. Please check your network connection and try again.";

  /**
   * The constructor.
   */
  public Mmm() {

    super();
  }

  // /**
  // * {@inheritDoc}
  // */
  // @Override
  // protected ClientGinjector createGinjector() {
  //
  // return GWT.create(ClientGinjector.class);
  // }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onModuleLoad() {

    // public void onModuleLoadDeferred() {
    // super.onModuleLoadDeferred();
    Log.debug("Loaded");

    // temporary workaround without IoC/GIN
    new PojoDescriptorBuilderFactoryLimited();

    UiContextGwt context = new UiContextGwt();
    context.initialize();

    DialogManagerImplGwt dialogManager = new DialogManagerImplGwt();
    DialogControllerFactory dialogControllerFactory = new DialogControllerFactoryImpl();
    dialogManager.setDialogControllerFactory(dialogControllerFactory);
    dialogManager.setUiContext(context);
    dialogManager.initialize(DialogConstants.PLACE_HOME);

    PojoDescriptorBuilderFactoryLimited descriptorBuilderFactory = new PojoDescriptorBuilderFactoryLimited();
    descriptorBuilderFactory.initialize();
    PojoDescriptorBuilder descriptorBuilder = descriptorBuilderFactory.createPublicMethodDescriptorBuilder();
    PojoDescriptor<ContactBean> descriptor = descriptorBuilder.getDescriptor(ContactBean.class);
    Collection<? extends PojoPropertyDescriptor> propertyDescriptors = descriptor.getPropertyDescriptors();
    for (PojoPropertyDescriptor propertyDescriptor : propertyDescriptors) {
      Log.info(propertyDescriptor.getName());
    }
    doSomethingElse(context);
  }

  private void doSomethingElse(UiContext context) {

    final UiWidgetFactory factory = context.getWidgetFactory();

    final UiWidgetMainWindow mainWindow = factory.getMainWindow();
    UiWidgetMenuBar menuBar = mainWindow.getMenuBar();

    UiWidgetMenu fileMenu = menuBar.addMenu("File");
    UiWidgetMenuItemClickable exitMenuItem = factory.create(UiWidgetMenuItemClickable.class);
    exitMenuItem.setLabel("Exit");
    fileMenu.addChild(exitMenuItem);

    UiWidgetMenu settingsMenu = menuBar.addMenu("Settings");
    UiWidgetMenu prefernecesSubmenu = settingsMenu.addSubmenu("Preferences");
    UiWidgetMenuItemClickable fooMenuItem = factory.create(UiWidgetMenuItemClickable.class);
    fooMenuItem.setLabel("Foo");
    prefernecesSubmenu.addChild(fooMenuItem);

    settingsMenu.addSeparator();
    UiWidgetMenuItemClickable optionsMenuItem = factory.create(UiWidgetMenuItemClickable.class);
    optionsMenuItem.setLabel("Options");
    settingsMenu.addChild(optionsMenuItem);

    UiWidgetTabPanel tabPanel = factory.create(UiWidgetTabPanel.class);

    UiWidgetVerticalPanel verticalPanel1 = factory.create(UiWidgetVerticalPanel.class);
    UiWidgetLabel label1 = factory.create(UiWidgetLabel.class);
    label1.setLabel("label1");
    verticalPanel1.addChild(label1);
    final UiWidgetTab tab1 = tabPanel.addChild(verticalPanel1, "Tab1");

    // UiWidgetGridPanel gridPanel = factory.create(UiWidgetGridPanel.class);
    // final UiWidgetTextField textField1 = factory.create(UiWidgetTextField.class);
    // textField1.setFieldLabel("Label1");
    // textField1.setValidationFailure("Totally nonse. Please provide corrent data.");
    // final UiWidgetComboBox<Boolean> comboBox = factory.create(UiWidgetComboBox.class);
    // comboBox.setFieldLabel("Label2");
    // comboBox.setOptions(Arrays.asList(Boolean.TRUE, Boolean.FALSE));
    // gridPanel.addChildren(textField1, comboBox);
    UiWidgetBorderPanel borderPanel = factory.create(UiWidgetBorderPanel.class);
    borderPanel.setLabel("Hello World");

    // borderPanel.setChild(gridPanel);
    SaveAdapter<ContactBean> handlerObjectSave = new SaveAdapter<ContactBean>();
    final ContactEditor contactEditor = new ContactEditor(context, handlerObjectSave);
    handlerObjectSave.delegate = new UiHandlerObjectSave<ContactBean>() {

      @Override
      public void onSave(ContactBean object, Object variant) {

        contactEditor.setValue(object);
        Window.alert("Contact " + object + " saved.");
      }
    };
    borderPanel.setChild(contactEditor);
    verticalPanel1.addChild(borderPanel);
    UiWidgetCollapsableSection section = factory.create(UiWidgetCollapsableSection.class);
    section.setLabel("Foo");
    section.addCollapseWidget(contactEditor);
    verticalPanel1.addChild(section);
    verticalPanel1.setMode(UiMode.EDIT);

    UiWidgetVerticalPanel verticalPanel2 = factory.create(UiWidgetVerticalPanel.class);
    final UiWidgetLabel label2 = factory.create(UiWidgetLabel.class);
    final UiWidgetTab tab2 = tabPanel.addChild(verticalPanel2, "Tab2");
    label2.setLabel("label2");
    verticalPanel2.addChild(label2);
    UiWidgetButton button1 = factory.create(UiWidgetButton.class);
    button1.setLabel("Button1");
    verticalPanel2.addChild(button1);
    button1.addClickHandler(new UiHandlerEventClick() {

      @Override
      public void onClick(UiFeatureClick source, boolean programmatic) {

        final UiWidgetPopup popup = factory.create(UiWidgetPopup.class);
        popup.setTitle("Popup Test");
        // popup.setSize(600, 400, SizeUnit.PIXEL);
        UiWidgetVerticalPanel verticalPanel3 = factory.create(UiWidgetVerticalPanel.class);
        UiWidgetLabel label3 = factory.create(UiWidgetLabel.class);
        label3.setLabel("Hello World!");
        UiWidgetButton button = factory.create(UiWidgetButton.class);
        button.setLabel("Close");
        UiHandlerEventClick handler = new UiHandlerEventClick() {

          @Override
          public void onClick(UiFeatureClick source, boolean programmatic) {

            popup.setVisible(false);
          }
        };
        button.addClickHandler(handler);
        verticalPanel3.addChild(label3);
        verticalPanel3.addChild(button);
        popup.addChild(verticalPanel3);
        popup.centerWindow();
        popup.setVisible(true);
      }
    });
    UiWidgetButton button2 = factory.create(UiWidgetButton.class);
    button2.setLabel("button2:" + Locale.getDefault());
    verticalPanel2.addChild(button2);
    button2.addClickHandler(new UiHandlerEventClick() {

      @Override
      public void onClick(UiFeatureClick source, boolean programmatic) {

        label2.setLabel(mainWindow.getWidthInPixel() + "x" + mainWindow.getHeightInPixel());
        mainWindow.setPosition(mainWindow.getPositionX() - 5, mainWindow.getPositionY() - 5);
        mainWindow.setSize(mainWindow.getWidthInPixel() + 1, mainWindow.getHeightInPixel() + 1, SizeUnit.PIXEL);
      }
    });
    mainWindow.addChild(tabPanel);

    UiWidgetVerticalPanel verticalPanel3 = factory.create(UiWidgetVerticalPanel.class);
    UiWidgetDateField dateField = factory.create(UiWidgetDateField.class);
    verticalPanel3.addChild(dateField);
    UiWidgetTextField textBoxField = factory.create(UiWidgetTextField.class);
    verticalPanel3.addChild(textBoxField);
    UiWidgetRichTextArea richTextArea = factory.create(UiWidgetRichTextArea.class);
    verticalPanel3.addChild(richTextArea);
    // mainWindow.addChild(verticalPanel3);

    UiWidgetImage image = factory.create(UiWidgetImage.class);
    image.setUrl("http://m-m-m.sourceforge.net/maven/images/logo.png");
    final UiWidgetButton button = factory.create(UiWidgetButton.class);

    final UiWidgetTextField textField = factory.create(UiWidgetTextField.class);
    textField.setKeyboardFilter(CharFilter.LATIN_DIGIT_FILTER);

    button.setLabel("Send");
    // button.setImage(image);
    button.setTooltip("Send to server");

    // nameField.setText("GWT User");
    final Label errorLabel = new Label();

    UiWidgetHorizontalPanel horizontalPanel = factory.create(UiWidgetHorizontalPanel.class);
    horizontalPanel.addChild(textField);
    horizontalPanel.addChild(button);
    mainWindow.addChild(horizontalPanel);

    String message = new NlsNullPointerException("test").getMessage();
    // NlsMessage message = NlsAccess.getFactory().create("Hello World {arg}!", "arg", "foo");
    textField.setValue(message);

    // Add the nameField and sendButton to the RootPanel
    // Use RootPanel.get() to get the entire body element
    // RootPanel.get("nameFieldContainer").add(nameField);
    // RootPanel.get("sendButtonContainer").add(sendButton);
    // RootPanel.get("errorLabelContainer").add(errorLabel);

    // final Audio audio = Audio.createIfSupported();
    // audio.setControls(true);
    // RootLayoutPanel.get().add(audio);
    // audio.setSrc("http://allen-sauer.com/com.allen_sauer.gwt.voices.demo.VoicesDemo/wikipedia/Rondo_Alla_Turka.ogg");
    // audio.load();
    // audio.play();
    // final Label info = new Label("info: ");
    // RootLayoutPanel.get().add(info);
    // Callable<Boolean> task = new Callable<Boolean>() {
    //
    // @Override
    // public Boolean call() {
    //
    // info.setText("Duration: " + audio.getDuration() + " seconds, position: " + audio.getCurrentTime());
    // return Boolean.TRUE;
    // }
    // };
    // factory.getDispatcher().invokeTimer(task, 1000);

    // Focus the cursor on the name field when the app loads
    textField.setFocused();
    // support for selectAll in TextualField?
    // textField.selectAll();

    // Create the popup dialog box
    final DialogBox dialogBox = new DialogBox();
    dialogBox.setText("Remote Procedure Call");
    dialogBox.setAnimationEnabled(true);
    final Button closeButton = new Button("Close");
    // We can set the id of a widget by accessing its Element
    closeButton.getElement().setId("closeButton");
    final Label textToServerLabel = new Label();
    final HTML serverResponseLabel = new HTML();
    VerticalPanel dialogVPanel = new VerticalPanel();
    dialogVPanel.addStyleName("dialogVPanel");
    dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
    dialogVPanel.add(textToServerLabel);
    dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
    dialogVPanel.add(serverResponseLabel);
    dialogVPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
    dialogVPanel.add(closeButton);
    dialogBox.setWidget(dialogVPanel);

    // Add a handler to close the DialogBox
    closeButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {

        dialogBox.hide();
        button.setEnabled(true);
        // TODO: Support Focus for button...
        // button.setFocus(true);
      }
    });

    // Create a handler for the sendButton and nameField
    class MyHandler extends UiHandlerEventClick implements KeyUpHandler {

      /**
       * {@inheritDoc}
       */
      @Override
      public void onClick(UiFeatureClick source, boolean programmatic) {

        sendNameToServer();
      }

      /**
       * Fired when the user types in the nameField.
       */
      @Override
      public void onKeyUp(KeyUpEvent event) {

        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
          sendNameToServer();
        }
      }

      /**
       * Send the name from the nameField to the server and wait for a response.
       */
      private void sendNameToServer() {

        // First, we validate the input.
        errorLabel.setText("");
        String textToServer = textField.getValue();

        // Then, we send the input to the server.
        button.setEnabled(false);
        textToServerLabel.setText(textToServer);
        serverResponseLabel.setText("");
        Consumer<String> successCallback = new Consumer<String>() {

          @Override
          public void accept(String result) {

            dialogBox.setText("Remote Procedure Call");
            serverResponseLabel.removeStyleName("serverResponseLabelError");
            serverResponseLabel.setHTML(result);
            dialogBox.center();
            closeButton.setFocus(true);
          }
        };
        Consumer<Throwable> failureCallback = new Consumer<Throwable>() {

          @Override
          public void accept(Throwable failure) {

            // Show the RPC error message to the user
            dialogBox.setText("Remote Procedure Call - Failure");
            serverResponseLabel.addStyleName("serverResponseLabelError");
            serverResponseLabel.setHTML(SERVER_ERROR);
            dialogBox.center();
            closeButton.setFocus(true);
          }
        };
        RemoteInvocationServiceCaller serviceCaller;
        serviceCaller = GWT.create(RemoteInvocationServiceCaller.class);
        // serviceCaller = getGinjector().getServiceCaller();
        RemoteInvocationServiceQueue queue = serviceCaller.newQueue();
        queue.getServiceClient(GreetingService.class, String.class, successCallback, failureCallback).greeting(
            textToServer);
        queue.commit();
      }
    }

    // Add a handler to send the name to the server
    MyHandler handler = new MyHandler();
    button.addClickHandler(handler);
    // TODO textField.addSubmitHandler();
    // textField.setKeyboardFilter(handler);
  }

  /**
   * Stupid adapter required due to lack of closures in java.
   */
  private static class SaveAdapter<O> implements UiHandlerObjectSave<O> {

    /** @see #onSave(Object, Object) */
    private UiHandlerObjectSave<O> delegate;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSave(O object, Object variant) {

      if (this.delegate != null) {
        this.delegate.onSave(object, variant);
      }
    }
  }
}
