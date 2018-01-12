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

import java.util.List;
import org.eclipse.che.ide.api.mvp.View;

public interface SimpleGeneratorPageView extends View<SimpleGeneratorPageView.ActionDelegate> {

  void setPackagings(List<String> packagings);

  String getPackaging();

  void setPackaging(String packaging);

  interface ActionDelegate {
    void onPackagingChanged(String packaging);
  }
}
