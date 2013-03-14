/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.eclipse.templatevariables;

import org.eclipse.core.variables.IValueVariable;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateVariableResolver;

/**
 * This is the {@link TemplateVariableResolver} for a {@link IValueVariable} from the "String Substitution"
 * preferences. It makes the variable available to all templates.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class StringValueTemplateVariableResolver extends TemplateVariableResolver {

  /** @see #resolve(TemplateContext) */
  private final IValueVariable variable;

  /**
   * The constructor.
   * 
   * @param variable is the {@link IValueVariable}.
   */
  public StringValueTemplateVariableResolver(IValueVariable variable) {

    super(variable.getName(), variable.getDescription());
    this.variable = variable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String resolve(TemplateContext context) {

    return this.variable.getValue();
  }

}
