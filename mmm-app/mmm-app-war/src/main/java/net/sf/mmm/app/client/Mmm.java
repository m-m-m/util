/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.app.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import net.sf.mmm.app.client.dialog.DialogControllerFactoryImpl;
import net.sf.mmm.app.shared.GreetingService;
import net.sf.mmm.client.base.gwt.dialog.DialogManagerImplGwt;
import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.common.Length;
import net.sf.mmm.client.ui.api.common.LengthUnit;
import net.sf.mmm.client.ui.api.common.SelectionMode;
import net.sf.mmm.client.ui.api.common.Size;
import net.sf.mmm.client.ui.api.common.UiMode;
import net.sf.mmm.client.ui.api.dialog.DialogConstants;
import net.sf.mmm.client.ui.api.event.UiEvent;
import net.sf.mmm.client.ui.api.event.UiEventClick;
import net.sf.mmm.client.ui.api.event.UiEventClose;
import net.sf.mmm.client.ui.api.event.UiEventOpen;
import net.sf.mmm.client.ui.api.event.UiEventValueChange;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventClick;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventOpenClose;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventValueChange;
import net.sf.mmm.client.ui.api.handler.object.UiHandlerObjectSave;
import net.sf.mmm.client.ui.api.widget.UiWidgetFactory;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractTree.UiTreeNodeRenderer;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetListTable;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetTableColumn;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetTree;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetCollapsableSection;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetImage;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetLabel;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetToggleButton;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetCheckboxField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetDateField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetIntegerSliderField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetRichTextField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetTextField;
import net.sf.mmm.client.ui.api.widget.menu.UiWidgetMenu;
import net.sf.mmm.client.ui.api.widget.menu.UiWidgetMenuBar;
import net.sf.mmm.client.ui.api.widget.menu.UiWidgetMenuItemClickable;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetCollapsableBorderPanel;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetHorizontalPanel;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetVerticalPanel;
import net.sf.mmm.client.ui.api.widget.window.UiWidgetMainWindow;
import net.sf.mmm.client.ui.api.widget.window.UiWidgetPopup;
import net.sf.mmm.client.ui.base.UiConfigurationBean;
import net.sf.mmm.client.ui.base.dialog.DialogControllerFactory;
import net.sf.mmm.client.ui.impl.gwt.UiContextGwt;
import net.sf.mmm.service.api.rpc.client.RemoteInvocationServiceCaller;
import net.sf.mmm.service.api.rpc.client.RemoteInvocationServiceQueue;
import net.sf.mmm.util.filter.api.CharFilter;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.base.NlsMessageLookupProxy;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorBuilderFactoryLimited;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Random;
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
    UiConfigurationBean configuration = new UiConfigurationBean();
    configuration.setLabelLookup(new NlsMessageLookupProxy(NlsBundleLabelsRoot.class));
    context.setConfiguration(configuration);
    context.initialize();

    DialogManagerImplGwt dialogManager = new DialogManagerImplGwt();
    DialogControllerFactory dialogControllerFactory = new DialogControllerFactoryImpl();
    dialogManager.setDialogControllerFactory(dialogControllerFactory);
    dialogManager.setContext(context);
    dialogManager.initialize(DialogConstants.PLACE_HOME);

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

    // UiWidgetTabPanel tabPanel = factory.create(UiWidgetTabPanel.class);

    UiWidgetVerticalPanel verticalPanel1 = factory.create(UiWidgetVerticalPanel.class);
    UiWidgetLabel label1 = factory.create(UiWidgetLabel.class);
    label1.setLabel("label1");
    verticalPanel1.addChild(label1);
    // final UiWidgetTab tab1 =
    // tabPanel.addChild(verticalPanel1, "Tab1");

    // UiWidgetGridPanel gridPanel = factory.create(UiWidgetGridPanel.class);
    // final UiWidgetTextField textField1 = factory.create(UiWidgetTextField.class);
    // textField1.setFieldLabel("Label1");
    // textField1.setValidationFailure("Totally nonse. Please provide corrent data.");
    // final UiWidgetComboBox<Boolean> comboBox = factory.create(UiWidgetComboBox.class);
    // comboBox.setFieldLabel("Label2");
    // comboBox.setOptions(Arrays.asList(Boolean.TRUE, Boolean.FALSE));
    // gridPanel.addChildren(textField1, comboBox);
    UiWidgetCollapsableBorderPanel borderPanel = factory.create(UiWidgetCollapsableBorderPanel.class);
    borderPanel.setLabel("Hello World");

    // borderPanel.setChild(gridPanel);
    SaveAdapter<ContactBean> handlerObjectSave = new SaveAdapter<ContactBean>();
    final ContactEditor contactEditor = new ContactEditor(context, handlerObjectSave);
    handlerObjectSave.delegate = new UiHandlerObjectSave<ContactBean>() {

      @Override
      public void onSave(ContactBean object, UiEvent event) {

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

    final UiWidgetListTable<ContactBean> contactTable = factory.create(UiWidgetListTable.class);
    Comparator<String> sortComparator = null;
    final UiWidgetTableColumn<ContactBean, ?> columnFirstName = contactTable.createColumn(Contact.PROPERTY_FIRST_NAME,
        null, sortComparator);
    final UiWidgetTableColumn<ContactBean, ?> columnLastName = contactTable.createColumn(Contact.PROPERTY_LAST_NAME,
        null, sortComparator);
    final UiWidgetTableColumn<ContactBean, ?> columnIncome = contactTable.createColumn(Contact.PROPERTY_INCOME, null,
        null);
    // final UiWidgetTableColumn<ContactBean, ?> columnBirthday =
    // contactTable.createColumn(Contact.PROPERTY_BIRTHDAY, null, null);
    Size size = columnFirstName.getSize();
    size.setMinimumWidth(Length.valueOfPixel(20));
    size.setWidth(Length.valueOfPixel(100));
    size.setMaximumWidth(Length.valueOfPixel(200));
    size = columnLastName.getSize();
    size.setMinimumWidth(Length.valueOfPixel(50));
    size.setWidth(Length.valueOfPixel(400));
    columnIncome.getSize().setWidthInPixel(100);
    contactTable.setColumns(columnFirstName, columnLastName, columnIncome);
    contactTable.setSelectionMode(SelectionMode.MULTIPLE_SELECTION);

    final List<ContactBean> contactBeanList = new ArrayList<ContactBean>();
    createContacts(contactBeanList);

    verticalPanel1.addChild(contactTable);
    UiWidgetButton buttonFillTable = factory.create(UiWidgetButton.class);
    buttonFillTable.setLabel("Fill Table");
    verticalPanel1.addChild(buttonFillTable);
    buttonFillTable.addClickHandler(new UiHandlerEventClick() {

      @Override
      public void onClick(UiEventClick event) {

        contactTable.setValue(contactBeanList);
      }
    });
    UiWidgetButton buttonUpdateList = factory.create(UiWidgetButton.class);
    buttonUpdateList.setLabel("Update List");
    verticalPanel1.addChild(buttonUpdateList);
    buttonUpdateList.addClickHandler(new UiHandlerEventClick() {

      @Override
      public void onClick(UiEventClick event) {

        contactBeanList.clear();
        createContacts(contactBeanList);
        columnFirstName.sort();
      }
    });

    verticalPanel1.setMode(UiMode.EDIT);

    UiWidgetVerticalPanel verticalPanel2 = factory.create(UiWidgetVerticalPanel.class);
    final UiWidgetLabel label2 = factory.create(UiWidgetLabel.class);
    // final UiWidgetTab tab2 =
    // tabPanel.addChild(verticalPanel2, "Tab2");
    label2.setLabel("label2");
    verticalPanel2.addChild(label2);
    UiWidgetButton button1 = factory.create(UiWidgetButton.class);
    button1.setLabel("Button1");
    verticalPanel2.addChild(button1);
    UiWidgetToggleButton buttonToggle = factory.create(UiWidgetToggleButton.class);
    buttonToggle.setLabel("Test");
    verticalPanel2.addChild(buttonToggle);
    UiHandlerEventClick clickHandler = new UiHandlerEventClick() {

      @Override
      public void onClick(UiEventClick event) {

        final UiWidgetPopup popup = factory.create(UiWidgetPopup.class);
        popup.setTitle("Popup Test");
        // popup.setSize(600, 400, SizeUnit.PIXEL);
        UiWidgetVerticalPanel verticalPanel3 = factory.create(UiWidgetVerticalPanel.class);
        UiWidgetLabel label3 = factory.create(UiWidgetLabel.class);
        label3.setLabel("Hello World!");
        UiWidgetLabel label4 = factory.create(UiWidgetLabel.class);
        label4
            .setLabel("This is some extra long text to test scrollbar facitlities and resizing. We spent various extra "
                + "effort for these features that are unfortunately not offered by GWT itself out of the box.");
        final UiWidgetCheckboxField checkboxResizsable = factory.create(UiWidgetCheckboxField.class);
        checkboxResizsable.setTitle("resizable");
        checkboxResizsable.setValue(Boolean.TRUE);
        UiHandlerEventValueChange<Boolean> changeHandler = new UiHandlerEventValueChange<Boolean>() {

          /**
           * {@inheritDoc}
           */
          @Override
          public void onValueChange(UiEventValueChange<Boolean> changeEvent) {

            popup.setResizable(checkboxResizsable.getValue().booleanValue());
          }
        };
        popup.addOpenCloseHandler(new UiHandlerEventOpenClose() {

          @Override
          public void onOpen(UiEventOpen event) {

            Log.debug("open");
          }

          @Override
          public void onClose(UiEventClose event) {

            Log.debug("close");
          }
        });
        checkboxResizsable.addChangeHandler(changeHandler);
        final UiWidgetCheckboxField checkboxMovable = factory.create(UiWidgetCheckboxField.class);
        checkboxMovable.setTitle("movable");
        checkboxMovable.setValue(Boolean.TRUE);
        UiHandlerEventValueChange<Boolean> changeHandlerMove = new UiHandlerEventValueChange<Boolean>() {

          /**
           * {@inheritDoc}
           */
          @Override
          public void onValueChange(UiEventValueChange<Boolean> changeEvent) {

            popup.setMovable(checkboxMovable.getValue().booleanValue());
          }
        };
        checkboxMovable.addChangeHandler(changeHandlerMove);
        final UiWidgetCheckboxField checkboxClosable = factory.create(UiWidgetCheckboxField.class);
        checkboxClosable.setTitle("closable");
        checkboxClosable.setValue(Boolean.TRUE);
        UiHandlerEventValueChange<Boolean> changeHandlerClose = new UiHandlerEventValueChange<Boolean>() {

          /**
           * {@inheritDoc}
           */
          @Override
          public void onValueChange(UiEventValueChange<Boolean> changeEvent) {

            popup.setClosable(checkboxClosable.getValue().booleanValue());
          }
        };
        checkboxClosable.addChangeHandler(changeHandlerClose);
        UiWidgetButton button = factory.create(UiWidgetButton.class);
        button.setLabel("Close2");
        UiHandlerEventClick handler = new UiHandlerEventClick() {

          @Override
          public void onClick(UiEventClick evt) {

            popup.setVisible(false);
          }
        };
        button.addClickHandler(handler);
        verticalPanel3.addChild(label3);
        verticalPanel3.addChild(label4);
        verticalPanel3.addChild(checkboxResizsable);
        verticalPanel3.addChild(checkboxMovable);
        verticalPanel3.addChild(checkboxClosable);
        popup.addChild(verticalPanel3);
        popup.createAndAddCloseButton();
        popup.getButtonPanel().addChild(button);
        popup.centerWindow();
        popup.setVisible(true);
      }
    };
    button1.addClickHandler(clickHandler);
    // clickHandler.onClick(null);
    UiWidgetButton button2 = factory.create(UiWidgetButton.class);
    button2.setLabel("button2:" + Locale.getDefault());
    verticalPanel2.addChild(button2);
    button2.addClickHandler(new UiHandlerEventClick() {

      @Override
      public void onClick(UiEventClick event) {

        Size windowSize = mainWindow.getSize();
        label2.setLabel(windowSize.getWidthInPixel() + "x" + windowSize.getHeightInPixel());
        mainWindow.setPosition(mainWindow.getPositionX() - 5, mainWindow.getPositionY() - 5);
        windowSize.setSize(windowSize.getWidthInPixel() + 1, windowSize.getHeightInPixel() + 1, LengthUnit.PIXEL);
      }
    });

    UiWidgetTree<String> tree = factory.create(UiWidgetTree.class);
    tree.setTitle("Tree");
    tree.getSize().setSize(300, 300, LengthUnit.PIXEL);
    tree.setSelectionMode(SelectionMode.MULTIPLE_SELECTION);
    UiTreeModelDummy model = new UiTreeModelDummy();
    tree.setTreeModel(model);
    // setRichTextRenderer(tree);
    tree.setValue(model.getRootNode());
    verticalPanel2.addChild(tree);

    mainWindow.addChild(verticalPanel1);
    mainWindow.addChild(verticalPanel2);

    UiWidgetDateField dateField = factory.create(UiWidgetDateField.class);
    verticalPanel2.addChild(dateField);
    UiWidgetTextField textBoxField = factory.create(UiWidgetTextField.class);
    verticalPanel2.addChild(textBoxField);
    UiWidgetRichTextField richTextArea = factory.create(UiWidgetRichTextField.class);
    verticalPanel2.addChild(richTextArea);
    UiWidgetIntegerSliderField rangeField = factory.create(UiWidgetIntegerSliderField.class);
    rangeField.setMaximumValue(Integer.valueOf(100));
    verticalPanel2.addChild(rangeField);

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
        // button.setFocus(true);
      }
    });

    // Create a handler for the sendButton and nameField
    class MyHandler extends UiHandlerEventClick implements KeyUpHandler {

      /**
       * {@inheritDoc}
       */
      @Override
      public void onClick(UiEventClick event) {

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

  private void createContacts(final List<ContactBean> contactBeanList) {

    ContactBean contact = new ContactBean();
    contact.setFirstName("James");
    contact.setLastName("Bond");
    contactBeanList.add(contact);
    int rnd = Random.nextInt() % 1000;
    if (rnd < 0) {
      rnd = -rnd;
    }
    for (int i = 1; i <= rnd; i++) {
      contact = new ContactBean();
      String suffix = i + " of " + rnd;
      contact.setFirstName("First " + suffix);
      contact.setLastName("Last " + suffix);
      contact.setIncome(i + 0.001D);
      contactBeanList.add(contact);
    }
  }

  private void setRichTextRenderer(UiWidgetTree<String> tree) {

    UiTreeNodeRenderer<String, UiWidgetRichTextField> renderer = new UiTreeNodeRenderer<String, UiWidgetRichTextField>() {

      /**
       * {@inheritDoc}
       */
      @Override
      public UiWidgetRichTextField create(UiContext context) {

        return context.getWidgetFactory().create(UiWidgetRichTextField.class);
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void assignNodeToWidget(String node, UiWidgetRichTextField widget) {

        widget.setValue(node);
      }
    };
    tree.setTreeNodeRenderer(renderer);
  }

  /**
   * Stupid adapter required due to lack of closures in java.
   */
  private static class SaveAdapter<O> implements UiHandlerObjectSave<O> {

    /** @see #onSave(Object, UiEvent) */
    private UiHandlerObjectSave<O> delegate;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSave(O object, UiEvent event) {

      if (this.delegate != null) {
        this.delegate.onSave(object, event);
      }
    }
  }
}
