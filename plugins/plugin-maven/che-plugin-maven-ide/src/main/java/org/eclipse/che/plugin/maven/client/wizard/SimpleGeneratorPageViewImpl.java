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
import java.util.List;
import org.eclipse.che.ide.ui.listbox.CustomListBox;
import org.eclipse.che.plugin.maven.client.MavenResources;

@Singleton
public class SimpleGeneratorPageViewImpl implements SimpleGeneratorPageView {

  private ActionDelegate delegate;

  private FlowPanel mainPanel;
  private CustomListBox packagingField;

  @Inject
  public SimpleGeneratorPageViewImpl(MavenResources resources) {
    Label packagingLabel = new Label("Packaging:");
    packagingLabel.addStyleName(resources.css().label());
    packagingLabel.setWidth("138px");

    packagingField = new CustomListBox();
    packagingField.addStyleName(resources.css().field());
    packagingField.setWidth("511px");
    packagingField.setHeight("29px");
    packagingField.ensureDebugId("mavenPageView-packagingField");

    mainPanel = new FlowPanel();
    mainPanel.getElement().getStyle().setMargin(15, Unit.PX);
    mainPanel.add(packagingLabel);
    mainPanel.add(packagingField);

    packagingField.addChangeHandler(event -> delegate.onPackagingChanged(getPackaging()));
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
  public void setPackagings(List<String> packagings) {
    packagingField.clear();

    for (String packaging : packagings) {
      packagingField.addItem(packaging, packaging);
    }

    if (!packagings.isEmpty()) {
      packagingField.setItemSelected(0, true);
      delegate.onPackagingChanged(packagingField.getValue());
    }
  }

  @Override
  public String getPackaging() {
    return packagingField.getValue(packagingField.getSelectedIndex());
  }

  @Override
  public void setPackaging(String packaging) {
    for (int i = 0; i < packagingField.getItemCount(); i++) {
      if (packaging.equals(packagingField.getValue(i))) {
        packagingField.setSelectedIndex(i);
        break;
      }
    }
  }
}
