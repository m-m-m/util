/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import net.sf.mmm.client.ui.NlsBundleClientUiRoot;
import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.UiPopupHelper;
import net.sf.mmm.client.ui.api.common.IconConstants;
import net.sf.mmm.client.ui.api.common.MessageSeverity;
import net.sf.mmm.client.ui.api.feature.UiFeatureClick;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventClick;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlain;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlainApprove;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlainCancel;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlainConfirm;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlainDelete;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlainDown;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlainNext;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlainOpen;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlainPrevious;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlainRemove;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlainSave;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlainStartEdit;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlainStopEdit;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlainSubmit;
import net.sf.mmm.client.ui.api.handler.plain.UiHandlerPlainUp;
import net.sf.mmm.client.ui.api.widget.UiWidgetFactoryAdvanced;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetImage;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetLabel;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetComboBox;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetIntegerField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetLongField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetTextField;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetHorizontalSplitPanel;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetSplitPanel;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetVerticalSplitPanel;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.lang.api.Callback;
import net.sf.mmm.util.lang.api.EnumDefinition;
import net.sf.mmm.util.lang.api.EnumProvider;
import net.sf.mmm.util.lang.api.Orientation;
import net.sf.mmm.util.lang.base.SimpleEnumProvider;
import net.sf.mmm.util.nls.api.IllegalCaseException;
import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This is the implementation of {@link UiWidgetFactoryAdvanced}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Named
public class UiWidgetFactoryAdvancedImpl extends AbstractLoggableComponent implements UiWidgetFactoryAdvanced {

  /** @see #getContext() */
  private UiContext context;

  /** @see #getEnumProvider() */
  private EnumProvider enumProvider;

  /** @see #getClientUiBundle() */
  private NlsBundleClientUiRoot clientUiBundle;

  /**
   * The constructor.
   */
  public UiWidgetFactoryAdvancedImpl() {

    super();
  }

  /**
   * @return the {@link UiContext} instance.
   */
  protected UiContext getContext() {

    return this.context;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.enumProvider == null) {
      SimpleEnumProvider impl = new SimpleEnumProvider();
      impl.initialize();
      this.enumProvider = impl;
    }
    this.clientUiBundle = NlsAccess.getBundleFactory().createBundle(NlsBundleClientUiRoot.class);
  }

  /**
   * @param context is the {@link UiContext} to {@link Inject}.
   */
  @Inject
  public void setContext(UiContext context) {

    getInitializationState().requireNotInitilized();
    this.context = context;
  }

  /**
   * @return the enumProvider
   */
  protected EnumProvider getEnumProvider() {

    return this.enumProvider;
  }

  /**
   * @param enumProvider is the enumProvider to set
   */
  @Inject
  public void setEnumProvider(EnumProvider enumProvider) {

    getInitializationState().requireNotInitilized();
    this.enumProvider = enumProvider;
  }

  /**
   * @return the instance of {@link NlsBundleClientUiRoot}.
   */
  protected NlsBundleClientUiRoot getClientUiBundle() {

    return this.clientUiBundle;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetButton createButton(String label, UiHandlerEventClick clickHandler) {

    UiWidgetButton widget = this.context.getWidgetFactory().create(UiWidgetButton.class);
    widget.setLabel(label);
    widget.addClickHandler(clickHandler);
    return widget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetButton createButton(UiHandlerPlain handler) {

    return createButton(handler, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetButton createButton(UiHandlerPlain handler, final boolean preventConfirmationPopup) {

    if (handler instanceof UiHandlerPlainSave) {
      return createButtonSave((UiHandlerPlainSave) handler);
    } else if (handler instanceof UiHandlerPlainCancel) {
      return createButtonCancel((UiHandlerPlainCancel) handler);
    } else if (handler instanceof UiHandlerPlainApprove) {
      return createButtonApprove((UiHandlerPlainApprove) handler);
    } else if (handler instanceof UiHandlerPlainConfirm) {
      return createButtonConfirm((UiHandlerPlainConfirm) handler);
    } else if (handler instanceof UiHandlerPlainSubmit) {
      return createButtonSubmit((UiHandlerPlainSubmit) handler);
    } else if (handler instanceof UiHandlerPlainOpen) {
      return createButtonOpen((UiHandlerPlainOpen) handler);
    } else if (handler instanceof UiHandlerPlainStartEdit) {
      return createButtonStartEdit((UiHandlerPlainStartEdit) handler);
    } else if (handler instanceof UiHandlerPlainStopEdit) {
      return createButtonStopEdit((UiHandlerPlainStopEdit) handler);
    } else if (handler instanceof UiHandlerPlainNext) {
      return createButtonNext((UiHandlerPlainNext) handler);
    } else if (handler instanceof UiHandlerPlainPrevious) {
      return createButtonPrevious((UiHandlerPlainPrevious) handler);
    } else if (handler instanceof UiHandlerPlainUp) {
      return createButtonUp((UiHandlerPlainUp) handler);
    } else if (handler instanceof UiHandlerPlainDown) {
      return createButtonDown((UiHandlerPlainDown) handler);
    } else if (handler instanceof UiHandlerPlainRemove) {
      return createButtonRemove((UiHandlerPlainRemove) handler, preventConfirmationPopup);
    } else if (handler instanceof UiHandlerPlainDelete) {
      return createButtonDelete((UiHandlerPlainDelete) handler, preventConfirmationPopup);
    } else {
      throw new IllegalCaseException(handler.getClass().getName());
    }
  }

  /**
   * @param handler is the {@link UiHandlerPlainSave}.
   * @return the new widget.
   */
  public UiWidgetButton createButtonSave(final UiHandlerPlainSave handler) {

    UiHandlerEventClick clickHandler = new UiHandlerEventClick() {

      @Override
      public void onClick(UiFeatureClick source, boolean programmatic) {

        handler.onSave();
      }
    };
    return createButton(this.clientUiBundle.labelSave(), clickHandler, null, null);
  }

  /**
   * @param handler is the {@link UiHandlerPlainCancel}.
   * @return the new widget.
   */
  public UiWidgetButton createButtonCancel(final UiHandlerPlainCancel handler) {

    UiHandlerEventClick clickHandler = new UiHandlerEventClick() {

      @Override
      public void onClick(UiFeatureClick source, boolean programmatic) {

        handler.onCancel();
      }
    };
    return createButton(this.clientUiBundle.labelCancel(), clickHandler, null, null);
  }

  /**
   * @param handler is the {@link UiHandlerPlainApprove}.
   * @return the new widget.
   */
  public UiWidgetButton createButtonApprove(final UiHandlerPlainApprove handler) {

    UiHandlerEventClick clickHandler = new UiHandlerEventClick() {

      @Override
      public void onClick(UiFeatureClick source, boolean programmatic) {

        handler.onApprove();
      }
    };
    return createButton(this.clientUiBundle.labelApprove(), clickHandler, null, null);
  }

  /**
   * @param handler is the {@link UiHandlerPlainConfirm}.
   * @return the new widget.
   */
  public UiWidgetButton createButtonConfirm(final UiHandlerPlainConfirm handler) {

    UiHandlerEventClick clickHandler = new UiHandlerEventClick() {

      @Override
      public void onClick(UiFeatureClick source, boolean programmatic) {

        handler.onConfirm();
      }
    };
    return createButton(this.clientUiBundle.labelConfirm(), clickHandler, null, null);
  }

  /**
   * @param handler is the {@link UiHandlerPlainSubmit}.
   * @return the new widget.
   */
  public UiWidgetButton createButtonSubmit(final UiHandlerPlainSubmit handler) {

    UiHandlerEventClick clickHandler = new UiHandlerEventClick() {

      @Override
      public void onClick(UiFeatureClick source, boolean programmatic) {

        handler.onSubmit();
      }
    };
    return createButton(this.clientUiBundle.labelSubmit(), clickHandler, null, null);
  }

  /**
   * @param handler is the {@link UiHandlerPlainOpen}.
   * @return the new widget.
   */
  public UiWidgetButton createButtonOpen(final UiHandlerPlainOpen handler) {

    UiHandlerEventClick clickHandler = new UiHandlerEventClick() {

      @Override
      public void onClick(UiFeatureClick source, boolean programmatic) {

        handler.onOpen();
      }
    };
    return createButton(this.clientUiBundle.labelOpen(), clickHandler, null, null);
  }

  /**
   * @param handler is the {@link UiHandlerPlainStartEdit}.
   * @return the new widget.
   */
  public UiWidgetButton createButtonStartEdit(final UiHandlerPlainStartEdit handler) {

    UiHandlerEventClick clickHandler = new UiHandlerEventClick() {

      @Override
      public void onClick(UiFeatureClick source, boolean programmatic) {

        handler.onStartEditMode();
      }
    };
    return createButton(this.clientUiBundle.labelStartEdit(), clickHandler, null, null);
  }

  /**
   * @param handler is the {@link UiHandlerPlainStopEdit}.
   * @return the new widget.
   */
  public UiWidgetButton createButtonStopEdit(final UiHandlerPlainStopEdit handler) {

    UiHandlerEventClick clickHandler = new UiHandlerEventClick() {

      @Override
      public void onClick(UiFeatureClick source, boolean programmatic) {

        handler.onStopEditMode();
      }
    };
    return createButton(this.clientUiBundle.labelStopEdit(), clickHandler, null, null);
  }

  /**
   * @param handler is the {@link UiHandlerPlainRemove}.
   * @param preventConfirmationPopup - see {@link #createButton(UiHandlerPlain, boolean)}.
   * @return the new widget.
   */
  public UiWidgetButton createButtonRemove(final UiHandlerPlainRemove handler, final boolean preventConfirmationPopup) {

    NlsMessage labelRemove = this.clientUiBundle.labelRemove();
    final String labelRemoveText = labelRemove.getLocalizedMessage();
    UiHandlerEventClick clickHandler = new UiHandlerEventClick() {

      @Override
      public void onClick(UiFeatureClick source, boolean programmatic) {

        if (preventConfirmationPopup) {
          handler.onRemove();
        } else {
          UiPopupHelper popupHelper = UiWidgetFactoryAdvancedImpl.this.context.getPopupHelper();
          Callback<String> callback = new Callback<String>() {

            @Override
            public Void apply(String argument) {

              if (UiPopupHelper.BUTTON_ID_OK.equals(argument)) {
                handler.onRemove();
              }
              return null;
            }
          };
          String message = UiWidgetFactoryAdvancedImpl.this.clientUiBundle.messageConfirmRemove().getLocalizedMessage();
          String title = UiWidgetFactoryAdvancedImpl.this.clientUiBundle.titleRemovePopup().getLocalizedMessage();
          String cancel = UiWidgetFactoryAdvancedImpl.this.clientUiBundle.labelCancel().getLocalizedMessage();
          popupHelper.showPopup(message, MessageSeverity.QUESTION, title, callback, labelRemoveText, cancel);
        }
      }
    };
    return createButton(labelRemove, clickHandler, null, null);
  }

  /**
   * @param handler is the {@link UiHandlerPlainDelete}.
   * @param preventConfirmationPopup - see {@link #createButton(UiHandlerPlain, boolean)}.
   * @return the new widget.
   */
  public UiWidgetButton createButtonDelete(final UiHandlerPlainDelete handler, final boolean preventConfirmationPopup) {

    NlsMessage labelDelete = this.clientUiBundle.labelDelete();
    final String labelDeleteText = labelDelete.getLocalizedMessage();
    UiHandlerEventClick clickHandler = new UiHandlerEventClick() {

      @Override
      public void onClick(UiFeatureClick source, boolean programmatic) {

        if (preventConfirmationPopup) {
          handler.onDelete();
        } else {
          UiPopupHelper popupHelper = UiWidgetFactoryAdvancedImpl.this.context.getPopupHelper();
          Callback<String> callback = new Callback<String>() {

            @Override
            public Void apply(String argument) {

              if (UiPopupHelper.BUTTON_ID_OK.equals(argument)) {
                handler.onDelete();
              }
              return null;
            }
          };
          String message = UiWidgetFactoryAdvancedImpl.this.clientUiBundle.messageConfirmDelete().getLocalizedMessage();
          String title = UiWidgetFactoryAdvancedImpl.this.clientUiBundle.titleDeletePopup().getLocalizedMessage();
          String cancel = UiWidgetFactoryAdvancedImpl.this.clientUiBundle.labelCancel().getLocalizedMessage();
          popupHelper.showPopup(message, MessageSeverity.QUESTION, title, callback, labelDeleteText, cancel);
        }
      }
    };
    return createButton(labelDelete, clickHandler, null, null);
  }

  /**
   * @param handler is the {@link UiHandlerPlainNext}.
   * @return the new widget.
   */
  public UiWidgetButton createButtonNext(final UiHandlerPlainNext handler) {

    UiHandlerEventClick clickHandler = new UiHandlerEventClick() {

      @Override
      public void onClick(UiFeatureClick source, boolean programmatic) {

        handler.onNext();
      }
    };
    UiWidgetImage icon = createImage(IconConstants.ICON_BUTTON_NEXT, this.clientUiBundle.tooltipNext()
        .getLocalizedMessage());
    return createButton(null, clickHandler, this.clientUiBundle.tooltipNext(), icon);
  }

  /**
   * @param handler is the {@link UiHandlerPlainPrevious}.
   * @return the new widget.
   */
  public UiWidgetButton createButtonPrevious(final UiHandlerPlainPrevious handler) {

    UiHandlerEventClick clickHandler = new UiHandlerEventClick() {

      @Override
      public void onClick(UiFeatureClick source, boolean programmatic) {

        handler.onPrevious();
      }
    };
    NlsMessage tooltipPrevious = this.clientUiBundle.tooltipPrevious();
    UiWidgetImage icon = createImage(IconConstants.ICON_BUTTON_PREVIOUS, tooltipPrevious.getLocalizedMessage());
    return createButton(null, clickHandler, tooltipPrevious, icon);
  }

  /**
   * @param handler is the {@link UiHandlerPlainUp}.
   * @return the new widget.
   */
  public UiWidgetButton createButtonUp(final UiHandlerPlainUp handler) {

    UiHandlerEventClick clickHandler = new UiHandlerEventClick() {

      @Override
      public void onClick(UiFeatureClick source, boolean programmatic) {

        handler.onUp();
      }
    };
    NlsMessage labelUp = this.clientUiBundle.labelUp();
    UiWidgetImage icon = createImage(IconConstants.ICON_BUTTON_UP, labelUp.getLocalizedMessage());
    return createButton(labelUp, clickHandler, null, icon);
  }

  /**
   * @param handler is the {@link UiHandlerPlainDown}.
   * @return the new widget.
   */
  public UiWidgetButton createButtonDown(final UiHandlerPlainDown handler) {

    UiHandlerEventClick clickHandler = new UiHandlerEventClick() {

      @Override
      public void onClick(UiFeatureClick source, boolean programmatic) {

        handler.onDown();
      }
    };
    NlsMessage labelDown = this.clientUiBundle.labelDown();
    UiWidgetImage icon = createImage(IconConstants.ICON_BUTTON_DOWN, labelDown.getLocalizedMessage());
    return createButton(labelDown, clickHandler, null, icon);
  }

  /**
   * Creates a new {@link UiWidgetButton}.
   * 
   * @param label is the optional label.
   * @param handler is the required click handler.
   * @param tooltip is the optional tooltip.
   * @param icon is the optional icon.
   * @return the new widget.
   */
  private UiWidgetButton createButton(NlsMessage label, UiHandlerEventClick handler, NlsMessage tooltip,
      UiWidgetImage icon) {

    String labelText = "";
    if (label != null) {
      labelText = label.getLocalizedMessage();
    }
    UiWidgetButton button = createButton(labelText, handler);
    if (tooltip != null) {
      button.setTooltip(tooltip.getLocalizedMessage());
    }
    if (icon != null) {
      button.setImage(icon);
    }
    return button;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetImage createImage(String url, String altText) {

    UiWidgetImage widget = this.context.getWidgetFactory().create(UiWidgetImage.class);
    widget.setUrl(url);
    widget.setAltText(altText);
    return widget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetLabel createLabel(String label) {

    UiWidgetLabel widget = this.context.getWidgetFactory().create(UiWidgetLabel.class);
    widget.setLabel(label);
    return widget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetTextField createTextField(String label) {

    UiWidgetTextField widget = this.context.getWidgetFactory().create(UiWidgetTextField.class);
    widget.setFieldLabel(label);
    return widget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetIntegerField createIntegerField(String label) {

    UiWidgetIntegerField widget = this.context.getWidgetFactory().create(UiWidgetIntegerField.class);
    widget.setFieldLabel(label);
    return widget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetLongField createLongField(String label) {

    UiWidgetLongField widget = this.context.getWidgetFactory().create(UiWidgetLongField.class);
    widget.setFieldLabel(label);
    return widget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <VALUE> UiWidgetComboBox<VALUE> createComboBox(String label, final EnumDefinition<VALUE, ?> enumDefinition) {

    final UiWidgetComboBox<VALUE> widget = this.context.getWidgetFactory().create(UiWidgetComboBox.class);
    widget.setFieldLabel(label);
    widget.setFormatter(enumDefinition.getFormatter());
    Runnable callback = new Runnable() {

      @Override
      public void run() {

        List<VALUE> enumValues = UiWidgetFactoryAdvancedImpl.this.enumProvider.getEnumValues(enumDefinition);
        widget.setOptions(enumValues);
      }
    };
    this.enumProvider.require(callback, enumDefinition);
    return widget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetSplitPanel createSplitPanel(Orientation orientation, UiWidgetRegular... children) {

    if (children == null) {
      throw new NlsNullPointerException("children");
    }
    if (children.length < 2) {
      throw new NlsIllegalArgumentException(Integer.valueOf(children.length), "children.length");
    }
    UiWidgetSplitPanel splitPanel;
    switch (orientation) {
      case HORIZONTAL:
        splitPanel = this.context.getWidgetFactory().create(UiWidgetHorizontalSplitPanel.class);
        break;
      case VERTICAL:
        splitPanel = this.context.getWidgetFactory().create(UiWidgetVerticalSplitPanel.class);
        break;
      default :
        throw new IllegalCaseException(Orientation.class, orientation);
    }
    for (UiWidgetRegular child : children) {
      splitPanel.addChild(child);
    }
    return splitPanel;
  }

}
