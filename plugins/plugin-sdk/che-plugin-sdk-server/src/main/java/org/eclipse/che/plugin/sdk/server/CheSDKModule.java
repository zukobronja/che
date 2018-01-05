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

import static com.google.inject.multibindings.Multibinder.newSetBinder;

import com.google.inject.AbstractModule;
import org.eclipse.che.api.project.server.type.ProjectTypeDef;
import org.eclipse.che.inject.DynaModule;
import org.eclipse.che.plugin.maven.server.projecttype.handler.GeneratorStrategy;

/** Guice module for 'Che SDK' plugin. */
@DynaModule
public class CheSDKModule extends AbstractModule {

  @Override
  protected void configure() {
    newSetBinder(binder(), ProjectTypeDef.class).addBinding().to(ChePluginProjectType.class);
    newSetBinder(binder(), GeneratorStrategy.class)
        .addBinding()
        .to(ChePluginGeneratorStrategy.class);
  }
}
