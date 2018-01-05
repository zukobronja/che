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
package org.eclipse.che.plugin.sdk.server;

import com.google.inject.Inject;
import javax.inject.Singleton;
import org.eclipse.che.api.project.server.type.TransientMixin;
import org.eclipse.che.plugin.sdk.shared.Constants;

@Singleton
public class ChePluginProjectType extends TransientMixin {

  @Inject
  public ChePluginProjectType() {
    super(Constants.CHE_PLUGIN_ID, Constants.CHE_PLUGIN_NAME);
  }
}
