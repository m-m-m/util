/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.eclipse.templatevariables;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.internal.corext.template.java.CodeTemplateContext;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateVariableResolver;
import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.m2e.core.project.IMavenProjectFacade;
import org.eclipse.m2e.core.project.IMavenProjectRegistry;

/**
 * This is the abstract base class for a {@link TemplateVariableResolver} that reads a property from a maven
 * project.
 * 
 * @author Andreas Hoehmann (hoehmann)
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("restriction")
public abstract class AbstractMavenTemplateVariableResolver extends TemplateVariableResolver {

  /**
   * The constructor.
   * 
   * @param type is the type
   * @param description
   */
  public AbstractMavenTemplateVariableResolver(String type, String description) {

    super(type, description);
  }

  /**
   * Get the current maven project version.
   * 
   * @param project is the {@link IProject}.
   * @return the maven project version excluding a potential "-SNAPSHOT" suffix.
   */
  private String getMavenProperty(IProject project) {

    if (project == null) {
      throw new IllegalArgumentException("Missing project");
    }
    String result = "";
    try {
      // IMavenConstants.NATURE_ID
      if (project.hasNature("org.eclipse.m2e.core.maven2Nature")) {
        final IMavenProjectRegistry projectRegistry = MavenPlugin.getMavenProjectRegistry();
        final IMavenProjectFacade projectFacade = projectRegistry.create(project, new NullProgressMonitor());
        if (projectFacade != null) {
          result = getMavenProperty(projectFacade);
        }
      }
    } catch (CoreException ex) {
      throw new IllegalStateException(ex);
    }
    return result;
  }

  /**
   * @param projectFacade is the current maven project.
   * @return the resolved variable value.
   */
  protected abstract String getMavenProperty(IMavenProjectFacade projectFacade);

  /**
   * {@inheritDoc}
   */
  @Override
  protected String resolve(TemplateContext context) {

    return getMavenProperty(((CodeTemplateContext) context).getJavaProject().getProject());
  }

}
