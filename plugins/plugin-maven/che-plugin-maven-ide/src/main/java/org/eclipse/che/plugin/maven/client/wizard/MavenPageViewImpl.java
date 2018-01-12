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

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import java.util.List;
import org.eclipse.che.ide.Resources;
import org.eclipse.che.ide.ui.listbox.CustomListBox;
import org.eclipse.che.plugin.maven.client.MavenLocalizationConstant;

/** @author Evgen Vidolob */
public class MavenPageViewImpl implements MavenPageView {

  private static MavenPageViewImplUiBinder ourUiBinder =
      GWT.create(MavenPageViewImplUiBinder.class);

  private final DockLayoutPanel rootElement;
  private final Resources coreRes;

  @UiField Style style;
  @UiField TextBox versionField;
  @UiField TextBox groupId;
  @UiField TextBox artifactId;
  @UiField Button artifactIdTooltipButton;
  @UiField Button groupIdTooltipButton;
  @UiField Label generatorsLabel;
  @UiField CustomListBox generatorsList;

  private ActionDelegate delegate;

  @Inject
  public MavenPageViewImpl(MavenLocalizationConstant localizedConstant, Resources coreRes) {
    this.coreRes = coreRes;

    rootElement = ourUiBinder.createAndBindUi(this);

    artifactId.setFocus(true);

    final Element artifactIdTooltip = DOM.createSpan();
    artifactIdTooltip.setInnerText(localizedConstant.mavenPageArtifactIdTooltip());

    artifactIdTooltipButton.addMouseOverHandler(
        event -> {
          final Element link = event.getRelativeElement();
          if (!link.isOrHasChild(artifactIdTooltip)) {
            link.appendChild(artifactIdTooltip);
          }
        });
    artifactIdTooltipButton.addStyleName(style.tooltip());

    final Element groupIdTooltip = DOM.createSpan();
    groupIdTooltip.setInnerText(localizedConstant.mavenPageGroupIdTooltip());

    groupIdTooltipButton.addMouseOverHandler(
        event -> {
          final Element link = event.getRelativeElement();
          if (!link.isOrHasChild(groupIdTooltip)) {
            link.appendChild(groupIdTooltip);
          }
        });
    groupIdTooltipButton.addStyleName(style.tooltip());

    generatorsList.addChangeHandler(
        event -> delegate.onGeneratorChanged(generatorsList.getValue()));
  }

  @Override
  public void setDelegate(ActionDelegate delegate) {
    this.delegate = delegate;
  }

  @Override
  public Widget asWidget() {
    return rootElement;
  }

  @Override
  public String getArtifactId() {
    return artifactId.getText();
  }

  @Override
  public void setArtifactId(String artifactId) {
    this.artifactId.setText(artifactId);
  }

  @Override
  public String getVersion() {
    return versionField.getText();
  }

  @Override
  public void setVersion(String value) {
    versionField.setText(value);
  }

  @Override
  public String getGroupId() {
    return groupId.getText();
  }

  @Override
  public void setGroupId(String group) {
    groupId.setText(group);
  }

  @UiHandler({"versionField", "groupId", "artifactId"})
  void onKeyUp(KeyUpEvent event) {
    delegate.onCoordinatesChanged();
  }

  @Override
  public void showArtifactIdMissingIndicator(boolean doShow) {
    if (doShow) {
      artifactId.addStyleName(style.inputError());
    } else {
      artifactId.removeStyleName(style.inputError());
    }
  }

  @Override
  public void showGroupIdMissingIndicator(boolean doShow) {
    if (doShow) {
      groupId.addStyleName(style.inputError());
    } else {
      groupId.removeStyleName(style.inputError());
    }
  }

  @Override
  public void showVersionMissingIndicator(boolean doShow) {
    if (doShow) {
      versionField.addStyleName(style.inputError());
    } else {
      versionField.removeStyleName(style.inputError());
    }
  }

  @Override
  public void setGenerators(List<String> generators) {
    generatorsList.clear();

    for (String generator : generators) {
      generatorsList.addItem(generator, generator);
    }

    if (!generators.isEmpty()) {
      generatorsList.setItemSelected(0, true);
      delegate.onGeneratorChanged(generatorsList.getValue());
    }
  }

  @Override
  public void setGeneratorsListVisibility(boolean visible) {
    generatorsLabel.setVisible(visible);
    generatorsList.setVisible(visible);
  }

  interface MavenPageViewImplUiBinder extends UiBinder<DockLayoutPanel, MavenPageViewImpl> {}

  interface Style extends CssResource {
    String inputError();

    String tooltip();
  }
}
