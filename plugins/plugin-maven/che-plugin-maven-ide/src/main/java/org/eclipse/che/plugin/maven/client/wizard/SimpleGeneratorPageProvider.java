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

import com.google.inject.Inject;
import com.google.inject.Provider;

public class SimpleGeneratorPageProvider implements Provider<SimpleGeneratorPage> {

  private final SimpleGeneratorPage page;

  @Inject
  public SimpleGeneratorPageProvider(SimpleGeneratorPage page) {
    this.page = page;
  }

  @Override
  public SimpleGeneratorPage get() {
    return page;
  }
}
