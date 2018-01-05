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
package org.eclipse.che.ide.macro;

import com.google.common.annotations.Beta;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.eclipse.che.api.promises.client.Promise;
import org.eclipse.che.api.promises.client.PromiseProvider;
import org.eclipse.che.ide.CoreLocalizationConstant;
import org.eclipse.che.ide.api.macro.Macro;

/**
 * Provider which is responsible for retrieving the current window location protocol (e.g. http or
 * https).
 *
 * <p>Macro provided: <code>${window.location.protocol}</code>
 *
 * @author Vitalii Parfonov
 * @see Macro
 * @since 6.0.0-M5
 */
@Beta
@Singleton
public class WindowLocationProtocolMacro implements Macro {

  public static final String KEY = "${window.location.protocol}";

  private final PromiseProvider promises;
  private final CoreLocalizationConstant localizationConstants;

  @Inject
  public WindowLocationProtocolMacro(
      PromiseProvider promises, CoreLocalizationConstant localizationConstants) {
    this.promises = promises;
    this.localizationConstants = localizationConstants;
  }

  /** {@inheritDoc} */
  @Override
  public String getName() {
    return KEY;
  }

  @Override
  public String getDescription() {
    return localizationConstants.macroWindowLocationProtocolDescription();
  }

  /** {@inheritDoc} */
  @Override
  public Promise<String> expand() {
    return promises.resolve(Window.Location.getProtocol());
  }
}
