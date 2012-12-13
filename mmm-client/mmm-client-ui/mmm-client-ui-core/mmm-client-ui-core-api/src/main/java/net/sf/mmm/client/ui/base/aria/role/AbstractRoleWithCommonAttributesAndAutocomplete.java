/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.attribute.AttributeWriteAriaAutocomplete;
import net.sf.mmm.client.ui.api.aria.datatype.AriaAutocomplete;

/**
 * This class extends {@link AbstractRoleWithCommonAttributes} with {@link AttributeWriteAriaAutocomplete}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractRoleWithCommonAttributesAndAutocomplete extends AbstractRoleWithCommonAttributes
    implements AttributeWriteAriaAutocomplete {

  /** @see #getAutocomplete() */
  private AriaAutocomplete autocomplete;

  /**
   * The constructor.
   */
  public AbstractRoleWithCommonAttributesAndAutocomplete() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AriaAutocomplete getAutocomplete() {

    if (this.autocomplete == null) {
      return AriaAutocomplete.NONE;
    }
    return this.autocomplete;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAutocomplete(AriaAutocomplete autocomplete) {

    this.autocomplete = autocomplete;
    setAttribute(HTML_ATTRIBUTE_ARIA_AUTOCOMPLETE, autocomplete);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void updateDelegate() {

    super.updateDelegate();
    if (this.autocomplete != null) {
      setAutocomplete(this.autocomplete);
    }
  }

}
