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

import com.google.inject.ImplementedBy;
import java.util.List;
import org.eclipse.che.ide.api.mvp.View;

/** @author Evgen Vidolob */
@ImplementedBy(MavenPageViewImpl.class)
public interface MavenPageView extends View<MavenPageView.ActionDelegate> {

  String getGroupId();

  void setGroupId(String group);

  String getArtifactId();

  void setArtifactId(String artifact);

  String getVersion();

  void setVersion(String value);

  void showArtifactIdMissingIndicator(boolean doShow);

  void showGroupIdMissingIndicator(boolean doShow);

  void showVersionMissingIndicator(boolean doShow);

  void setGenerators(List<String> generators);

  void setGeneratorsListVisibility(boolean visible);

  interface ActionDelegate {
    void onCoordinatesChanged();

    void onGeneratorChanged(String generatorId);
  }
}
