/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.eclipse.templatevariables;

import org.eclipse.jface.text.templates.TemplateVariableResolver;
import org.eclipse.m2e.core.embedder.ArtifactKey;
import org.eclipse.m2e.core.project.IMavenProjectFacade;

/**
 * This is the {@link TemplateVariableResolver} for the maven artifactId.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class MavenArtifactIdResolver extends AbstractMavenTemplateVariableResolver {

  /**
   * The constructor.
   */
  public MavenArtifactIdResolver() {

    super("project_artifactId", "artifactId of the current maven project.");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getMavenProperty(IMavenProjectFacade projectFacade) {

    String result = "";
    final ArtifactKey mavenProject = projectFacade.getArtifactKey();
    if (mavenProject != null) {
      result = mavenProject.getArtifactId();
    }
    return result;
  }

}
