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

import static java.util.Collections.singletonList;
import static org.eclipse.che.ide.ext.java.shared.Constants.SOURCE_FOLDER;
import static org.eclipse.che.plugin.maven.shared.MavenAttributes.DEFAULT_SOURCE_FOLDER;
import static org.eclipse.che.plugin.maven.shared.MavenAttributes.DEFAULT_TEST_SOURCE_FOLDER;
import static org.eclipse.che.plugin.maven.shared.MavenAttributes.PACKAGING;
import static org.eclipse.che.plugin.maven.shared.MavenAttributes.SIMPLE_GENERATION_STRATEGY;
import static org.eclipse.che.plugin.maven.shared.MavenAttributes.TEST_SOURCE_FOLDER;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.eclipse.che.ide.api.project.MutableProjectConfig;

/** Allows to choose a packaging for generating Maven project. */
@Singleton
public class SimpleGeneratorPage extends AbstractGeneratorPage
    implements SimpleGeneratorPageView.ActionDelegate {

  private final SimpleGeneratorPageView view;

  @Inject
  public SimpleGeneratorPage(SimpleGeneratorPageView view) {
    this.view = view;

    view.setDelegate(this);
  }

  @Override
  public String getGeneratorId() {
    return SIMPLE_GENERATION_STRATEGY;
  }

  @Override
  public void init(MutableProjectConfig dataObject) {
    super.init(dataObject);

    view.setPackagings(Arrays.asList("jar", "war", "pom"));
  }

  @Override
  public void go(AcceptsOneWidget container) {
    container.setWidget(view);
  }

  @Override
  public void onPackagingChanged(String packaging) {
    Map<String, List<String>> attributes = dataObject.getAttributes();
    attributes.put(PACKAGING, singletonList(packaging));

    if ("pom".equals(packaging)) {
      attributes.remove(SOURCE_FOLDER);
      attributes.remove(TEST_SOURCE_FOLDER);
    } else {
      attributes.put(SOURCE_FOLDER, singletonList(DEFAULT_SOURCE_FOLDER));
      attributes.put(TEST_SOURCE_FOLDER, singletonList(DEFAULT_TEST_SOURCE_FOLDER));
    }

    updateDelegate.updateControls();
  }
}
