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

import org.eclipse.che.ide.api.project.MutableProjectConfig;
import org.eclipse.che.ide.api.wizard.WizardPage;

/**
 * Interface for project wizard pages for configuring Maven project generators.
 *
 * @see AbstractGeneratorPage
 */
public interface MavenGeneratorPage extends WizardPage<MutableProjectConfig> {

  /** Returns ID of the generator. */
  String getGeneratorId();
}