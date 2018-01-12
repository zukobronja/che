/*
 * Copyright (c) 2012-2017 Red Hat, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package org.eclipse.che.plugin.maven.client.wizard;

import static org.eclipse.che.plugin.maven.shared.MavenAttributes.ARCHETYPE_ARTIFACT_ID_OPTION;
import static org.eclipse.che.plugin.maven.shared.MavenAttributes.ARCHETYPE_GENERATION_STRATEGY;
import static org.eclipse.che.plugin.maven.shared.MavenAttributes.ARCHETYPE_GROUP_ID_OPTION;
import static org.eclipse.che.plugin.maven.shared.MavenAttributes.ARCHETYPE_REPOSITORY_OPTION;
import static org.eclipse.che.plugin.maven.shared.MavenAttributes.ARCHETYPE_VERSION_OPTION;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.Arrays;
import java.util.List;
import org.eclipse.che.ide.api.project.MutableProjectConfig;
import org.eclipse.che.plugin.maven.client.MavenArchetype;

/** Allows to choose an archetype for generating Maven project. */
@Singleton
public class ArchetypeGeneratorPage extends AbstractGeneratorPage
    implements ArchetypeGeneratorPageView.ActionDelegate {

  private final ArchetypeGeneratorPageView view;

  @Inject
  public ArchetypeGeneratorPage(ArchetypeGeneratorPageView view) {
    this.view = view;

    view.setDelegate(this);
  }

  @Override
  public String getGeneratorId() {
    return ARCHETYPE_GENERATION_STRATEGY;
  }

  @Override
  public void init(MutableProjectConfig dataObject) {
    super.init(dataObject);

    List<MavenArchetype> archetypes =
        Arrays.asList(
            new MavenArchetype(
                "org.apache.maven.archetypes", "maven-archetype-quickstart", "RELEASE", null),
            new MavenArchetype(
                "org.apache.maven.archetypes", "maven-archetype-webapp", "RELEASE", null),
            new MavenArchetype(
                "org.apache.openejb.maven", "tomee-webapp-archetype", "1.7.1", null));

    view.setArchetypes(archetypes);
  }

  @Override
  public void go(AcceptsOneWidget container) {
    container.setWidget(view);
  }

  @Override
  public void onArchetypeChanged(MavenArchetype archetype) {
    dataObject.getOptions().put(ARCHETYPE_GROUP_ID_OPTION, archetype.getGroupId());
    dataObject.getOptions().put(ARCHETYPE_ARTIFACT_ID_OPTION, archetype.getArtifactId());
    dataObject.getOptions().put(ARCHETYPE_VERSION_OPTION, archetype.getVersion());
    dataObject.getOptions().put(ARCHETYPE_REPOSITORY_OPTION, archetype.getRepository());

    updateDelegate.updateControls();
  }
}
