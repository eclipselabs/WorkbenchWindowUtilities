/*******************************************************************************
 * Copyright (c) 2014, 2015 The Eclipse Foundation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Wayne Beaton - initial API and implementation
 *******************************************************************************/
package org.eclipselabs.windowutils.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * The WindowSizeHandler resizes the active workbench window (if any) to
 * 1024x768. Why? Why not?
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class WindowSizer1024x768Handler extends AbstractHandler {

	public WindowSizer1024x768Handler() {
	}

	/**
	 * Get the current workbench window and set its size to 1024x768.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		if (window == null)
			return null;
		final Shell shell = window.getShell();
		final Display display = shell.getDisplay();

		display.syncExec(new Runnable() {

			@Override
			public void run() {
				window.getShell().setSize(1024, 768);
			}
		});
		return null;
	}

}
