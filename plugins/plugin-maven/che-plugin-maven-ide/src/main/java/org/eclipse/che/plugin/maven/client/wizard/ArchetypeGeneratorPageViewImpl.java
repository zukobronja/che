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

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.che.ide.ui.listbox.CustomListBox;
import org.eclipse.che.plugin.maven.client.MavenArchetype;
import org.eclipse.che.plugin.maven.client.MavenResources;

@Singleton
public class ArchetypeGeneratorPageViewImpl implements ArchetypeGeneratorPageView {

  private ActionDelegate delegate;

  private List<MavenArchetype> archetypes;

  private FlowPanel mainPanel;
  private CustomListBox archetypeField;

  @Inject
  public ArchetypeGeneratorPageViewImpl(MavenResources resources) {
    archetypes = new ArrayList<>();

    Label archetypeLabel = new Label("Archetype:");
    archetypeLabel.addStyleName(resources.css().label());
    archetypeLabel.setWidth("138px");

    archetypeField = new CustomListBox();
    archetypeField.addStyleName(resources.css().field());
    archetypeField.setWidth("511px");
    archetypeField.setHeight("29px");
    archetypeField.ensureDebugId("mavenPageView-archetypeField");

    mainPanel = new FlowPanel();
    mainPanel.getElement().getStyle().setMargin(15, Unit.PX);
    mainPanel.add(archetypeLabel);
    mainPanel.add(archetypeField);

    archetypeField.addChangeHandler(event -> delegate.onArchetypeChanged(getArchetype()));
  }

  @Override
  public Widget asWidget() {
    return mainPanel;
  }

  @Override
  public void setDelegate(ActionDelegate delegate) {
    this.delegate = delegate;
  }

  @Override
  public void setArchetypes(List<MavenArchetype> archetypes) {
    this.archetypes.clear();
    this.archetypes.addAll(archetypes);
    archetypeField.clear();

    for (MavenArchetype archetype : archetypes) {
      archetypeField.addItem(archetype.toString(), archetype.toString());
    }

    if (!archetypes.isEmpty()) {
      archetypeField.setItemSelected(0, true);
      delegate.onArchetypeChanged(getArchetype());
    }
  }

  @Override
  public MavenArchetype getArchetype() {
    final String coordinates = archetypeField.getValue(archetypeField.getSelectedIndex());

    for (MavenArchetype archetype : archetypes) {
      if (coordinates.equals(archetype.toString())) {
        return archetype;
      }
    }

    return null;
  }
}
