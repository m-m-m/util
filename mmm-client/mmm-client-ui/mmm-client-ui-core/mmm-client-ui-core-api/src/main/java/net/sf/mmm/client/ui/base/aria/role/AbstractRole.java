/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.datatype.AriaChangeNotifications;
import net.sf.mmm.client.ui.api.aria.datatype.AriaDropEffect;
import net.sf.mmm.client.ui.api.aria.datatype.AriaIdList;
import net.sf.mmm.client.ui.api.aria.datatype.AriaInvalid;
import net.sf.mmm.client.ui.api.aria.datatype.AriaLiveLevel;
import net.sf.mmm.client.ui.api.aria.role.Role;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteOnlyAttribute;
import net.sf.mmm.util.lang.api.Datatype;
import net.sf.mmm.util.lang.api.StringUtil;

/**
 * This is the implementation of {@link Role}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractRole implements Role {

  /** The empty {@link AriaIdList}. */
  private static final AriaIdList EMPTY_ID_LIST = new AriaIdList("");

  /** @see #setAttribute(String, String) */
  private AttributeWriteOnlyAttribute delegate;

  /** @see #isAtomic() */
  private boolean atomic;

  /** @see #isBusy() */
  private boolean busy;

  /** @see #getControls() */
  private AriaIdList controls;

  /** @see #getDescribedBy() */
  private AriaIdList describedBy;

  /** @see #isDisabled() */
  private boolean disabled;

  /** @see #getDropEffect() */
  private AriaDropEffect dropEffect;

  /** @see #getFlowTo() */
  private AriaIdList flowTo;

  /** @see #getGrabbed() */
  private Boolean grabbed;

  /** @see #hasPopup() */
  private boolean hasPopup;

  /** @see #isHidden() */
  private boolean hidden;

  /** @see #getInvalid() */
  private AriaInvalid invalid;

  /** @see #getLabel() */
  private String label;

  /** @see #getLabelledBy() */
  private AriaIdList labelledBy;

  /** @see #getLive() */
  private AriaLiveLevel live;

  /** @see #getOwns() */
  private AriaIdList owns;

  /** @see #getRelevant() */
  private AriaChangeNotifications relevant;

  /**
   * The constructor.
   */
  public AbstractRole() {

    super();
  }

  /**
   * @return the {@link Role}-Interface corresponding to this instance.
   */
  public abstract Class<? extends Role> getRoleInterface();

  /**
   * @param delegate is the delegate where {@link AttributeWriteOnlyAttribute#setAttribute(String, String)
   *        attributes are set}.
   */
  public final void setDelegate(AttributeWriteOnlyAttribute delegate) {

    this.delegate = delegate;
    this.delegate.setAttribute(HTML_ATTRIBUTE_ARIA_ROLE, getName());
    updateDelegate();
  }

  /**
   * This method is called from {@link #setDelegate(AttributeWriteOnlyAttribute)} in order to update all
   * attributes on the new delegate.
   */
  protected void updateDelegate() {

    if (this.atomic) {
      setAtomic(true);
    }
    if (this.busy) {
      setBusy(true);
    }
    if (this.disabled) {
      setDisabled(true);
    }
    if (this.hasPopup) {
      setHasPopup(true);
    }
    if (this.hidden) {
      setHidden(true);
    }
    if (this.grabbed != null) {
      setGrabbed(this.grabbed);
    }
    if (this.controls != null) {
      setControls(this.controls);
    }
    if (this.describedBy != null) {
      setDescribedBy(this.describedBy);
    }
    if (this.dropEffect != null) {
      setDropEffect(this.dropEffect);
    }
    if (this.flowTo != null) {
      setFlowTo(this.flowTo);
    }
    if (this.invalid != null) {
      setInvalid(this.invalid);
    }
    if (this.label != null) {
      setLabel(this.label);
    }
    if (this.labelledBy != null) {
      setLabelledBy(this.labelledBy);
    }
    if (this.live != null) {
      setLive(this.live);
    }
    if (this.owns != null) {
      setOwns(this.owns);
    }
    if (this.relevant != null) {
      setRelevant(this.relevant);
    }
  }

  /**
   * @see AttributeWriteOnlyAttribute#setAttribute(String, String)
   * 
   * @param name is the name of the attribute.
   * @param datatype is the value to set as a {@link Datatype}.
   */
  protected final void setAttribute(String name, Datatype datatype) {

    if (this.delegate != null) {
      String value = null;
      if (datatype != null) {
        value = datatype.toString();
      }
      this.delegate.setAttribute(name, value);
    }
  }

  /**
   * @see AttributeWriteOnlyAttribute#setAttribute(String, String)
   * 
   * @param name is the name of the attribute.
   * @param number is the value to set as a {@link Number}.
   */
  protected final void setAttribute(String name, Number number) {

    if (this.delegate != null) {
      String value = null;
      if (number != null) {
        value = number.toString();
      }
      this.delegate.setAttribute(name, value);
    }
  }

  /**
   * @see AttributeWriteOnlyAttribute#setAttribute(String, String)
   * 
   * @param name is the name of the attribute.
   * @param flag is the value to set as a {@link Boolean}.
   */
  protected final void setAttribute(String name, Boolean flag) {

    if (this.delegate != null) {
      String value = null;
      if (flag != null) {
        value = flag.toString();
      }
      this.delegate.setAttribute(name, value);
    }
  }

  /**
   * @see AttributeWriteOnlyAttribute#setAttribute(String, String)
   * 
   * @param name is the name of the attribute.
   * @param flag is the value to set as <code>boolean</code>.
   */
  protected final void setAttribute(String name, boolean flag) {

    if (this.delegate != null) {
      if (flag) {
        this.delegate.setAttribute(name, StringUtil.TRUE);
      } else {
        // all true/false attributes in ARIA are specified such that false is the default...
        this.delegate.setAttribute(name, null);
      }
    }
  }

  /**
   * @see AttributeWriteOnlyAttribute#setAttribute(String, String)
   * 
   * @param name is the name of the attribute.
   * @param value is the value to set. May be <code>null</code> to unset.
   */
  protected final void setAttribute(String name, String value) {

    if (this.delegate != null) {
      this.delegate.setAttribute(name, value);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAtomic(boolean atomic) {

    this.atomic = atomic;
    setAttribute(HTML_ATTRIBUTE_ARIA_ATOMIC, atomic);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isAtomic() {

    return this.atomic;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setBusy(boolean busy) {

    this.busy = busy;
    setAttribute(HTML_ATTRIBUTE_ARIA_BUSY, busy);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isBusy() {

    return this.busy;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setControls(AriaIdList controls) {

    this.controls = controls;
    setAttribute(HTML_ATTRIBUTE_ARIA_CONTROLS, controls);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setControls(String controls) {

    if ((controls == null) || (controls.isEmpty())) {
      setControls((AriaIdList) null);
    } else {
      setControls(new AriaIdList(controls));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AriaIdList getControls() {

    if (this.controls == null) {
      return EMPTY_ID_LIST;
    }
    return this.controls;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setDescribedBy(AriaIdList describedBy) {

    this.describedBy = describedBy;
    setAttribute(HTML_ATTRIBUTE_ARIA_DESCRIBED_BY, describedBy);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setDescribedBy(String describedBy) {

    if ((describedBy == null) || (describedBy.isEmpty())) {
      setDescribedBy((AriaIdList) null);
    } else {
      setDescribedBy(new AriaIdList(describedBy));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AriaIdList getDescribedBy() {

    if (this.describedBy == null) {
      return EMPTY_ID_LIST;
    }
    return this.describedBy;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setDisabled(boolean disabled) {

    this.disabled = disabled;
    setAttribute(HTML_ATTRIBUTE_ARIA_DISABLED, disabled);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDisabled() {

    return this.disabled;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setDropEffect(AriaDropEffect dropEffect) {

    this.dropEffect = dropEffect;
    setAttribute(HTML_ATTRIBUTE_ARIA_DROP_EFFECT, dropEffect);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AriaDropEffect getDropEffect() {

    if (this.dropEffect == null) {
      return AriaDropEffect.NONE;
    }
    return this.dropEffect;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFlowTo(AriaIdList flowTo) {

    this.flowTo = flowTo;
    setAttribute(HTML_ATTRIBUTE_ARIA_FLOW_TO, flowTo);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFlowTo(String flowTo) {

    if ((flowTo == null) || (flowTo.isEmpty())) {
      setFlowTo((AriaIdList) null);
    } else {
      setFlowTo(new AriaIdList(flowTo));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AriaIdList getFlowTo() {

    if (this.flowTo == null) {
      return EMPTY_ID_LIST;
    }
    return this.flowTo;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setGrabbed(Boolean grabbed) {

    this.grabbed = grabbed;
    setAttribute(HTML_ATTRIBUTE_ARIA_GRABBED, grabbed);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Boolean getGrabbed() {

    return this.grabbed;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHasPopup(boolean hasPopup) {

    this.hasPopup = hasPopup;
    setAttribute(HTML_ATTRIBUTE_ARIA_HAS_POPUP, hasPopup);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasPopup() {

    return this.hasPopup;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHidden(boolean hidden) {

    this.hidden = hidden;
    setAttribute(HTML_ATTRIBUTE_ARIA_HIDDEN, hidden);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isHidden() {

    return this.hidden;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setInvalid(AriaInvalid invalid) {

    this.invalid = invalid;
    setAttribute(HTML_ATTRIBUTE_ARIA_INVALID, invalid);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AriaInvalid getInvalid() {

    if (this.invalid == null) {
      return AriaInvalid.FALSE;
    }
    return this.invalid;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLabel(String label) {

    this.label = label;
    setAttribute(HTML_ATTRIBUTE_ARIA_LABEL, label);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLabel() {

    return this.label;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLabelledBy(AriaIdList labelledBy) {

    this.labelledBy = labelledBy;
    setAttribute(HTML_ATTRIBUTE_ARIA_LABELLED_BY, labelledBy);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLabelledBy(String labelledBy) {

    if ((labelledBy == null) || (labelledBy.isEmpty())) {
      setLabelledBy((AriaIdList) null);
    } else {
      setLabelledBy(new AriaIdList(labelledBy));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AriaIdList getLabelledBy() {

    if (this.labelledBy == null) {
      return EMPTY_ID_LIST;
    }
    return this.labelledBy;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLive(AriaLiveLevel live) {

    this.live = live;
    setAttribute(HTML_ATTRIBUTE_ARIA_LIVE, live);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AriaLiveLevel getLive() {

    if (this.live == null) {
      return AriaLiveLevel.OFF;
    }
    return this.live;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setOwns(AriaIdList owns) {

    this.owns = owns;
    setAttribute(HTML_ATTRIBUTE_ARIA_OWNS, owns);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setOwns(String owns) {

    if ((owns == null) || (owns.isEmpty())) {
      setOwns((AriaIdList) null);
    } else {
      setOwns(new AriaIdList(owns));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AriaIdList getOwns() {

    if (this.owns != null) {
      return EMPTY_ID_LIST;
    }
    return this.owns;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setRelevant(AriaChangeNotifications relevant) {

    this.relevant = relevant;
    setAttribute(HTML_ATTRIBUTE_ARIA_RELEVANT, relevant);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AriaChangeNotifications getRelevant() {

    if (this.relevant == null) {
      // define AriaChangeNotifications.NONE ?
    }
    return this.relevant;
  }

}
