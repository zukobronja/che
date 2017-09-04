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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import org.eclipse.che.api.promises.client.Operation;

/**
 * GWT binding to term.js script
 *
 * @author Evgen Vidolob
 * @author Alexander Andrienko
 */
class TerminalJso extends JavaScriptObject {
  protected TerminalJso() {}

  public static native TerminalJso create(
      JavaScriptObject termJSO, TerminalOptionsJso options) /*-{
        return new termJSO(options);
    }-*/;

  public final native void open(Element element) /*-{
        this.open(element);
    }-*/;

  public final native void attachCustomKeyDownHandler(JavaScriptObject customKeyDownHandler) /*-{
        this.attachCustomKeydownHandler(customKeyDownHandler);
    }-*/;

  public final native Element getElement() /*-{
        return this.element;
    }-*/;

  public final native Element getRowContainer() /*-{
    return this.rowContainer;
  }-*/;

  public final native TerminalGeometryJso proposeGeometry() /*-{
        return this.proposeGeometry();
    }-*/;

  public final native void on(String event, Operation<String> operation) /*-{
        this.on(event, $entry(function (data) {
            operation.@org.eclipse.che.api.promises.client.Operation::apply(*)(data);
        }));
    }-*/;

  public final native void resize(int x, int y) /*-{
        this.resize(x, y);
    }-*/;

  public final native void write(String data) /*-{
        this.write(data);
    }-*/;

  public final native void focus() /*-{
    console.log(this.document.activeElement);
    console.log(this.textarea);
    console.log(this.document.activeElement === this.textarea);

      var selection = this.document.getSelection(),
          collapsed = selection.isCollapsed,
          isRange = typeof collapsed === 'boolean' ? !collapsed : selection.type === 'Range';
      var selectionLength = this.rowContainer.selectionEnd - this.rowContainer.selectionStart;
      console.log(selectionLength);

//     console.log(isRange);
     console.log("focus");
    // Don't call focus if terminal had already focused to prevent losing selection in the terminal

        if (!isRange && this.document.activeElement !== this.textarea) {
            this.focus();
        }
    }-*/;

  public final native void blur() /*-{
        this.blur();
    }-*/;
}
