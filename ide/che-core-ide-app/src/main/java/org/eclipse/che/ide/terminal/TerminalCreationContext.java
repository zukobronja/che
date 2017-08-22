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
package org.eclipse.che.ide.terminal;

/**
 * Describing terminal creation context. This context it's a way how we create new instance of the terminal.
 * @author Alexander Andrienko
 */
public enum TerminalCreationContext {
    /** Terminal created from action */
    ACTION,
    /** Terminal created from click handler*/
    CLICK_HANDLER,
    /** Terminal created on start workspace*/
    START_WORKSPACE
}
