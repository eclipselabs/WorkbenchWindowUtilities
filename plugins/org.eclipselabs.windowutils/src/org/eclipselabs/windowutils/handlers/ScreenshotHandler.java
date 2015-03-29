/*******************************************************************************
 * Copyright (c) 2015 The Eclipse Foundation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Wayne Beaton - initial API and implementation
 *******************************************************************************/
package org.eclipselabs.windowutils.handlers;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class ScreenshotHandler extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		if (window == null)
			return null;

		new Timer("Screenshot").schedule(new TimerTask() {

			@Override
			public void run() {
				takeScreenShot(window);
			}

		}, 5000);

		return null;
	}

	private void takeScreenShot(final IWorkbenchWindow window) {
		final Shell shell = window.getShell();
		final Display display = shell.getDisplay();
		
		// All SWT work must be done in the UI thread
		display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				
				// Snapshot the area of the display occupied by the window
				GC gc = new GC(display);
				final Image image = new Image(display, shell.getBounds());
				gc.copyArea(image, shell.getLocation().x, shell.getLocation().y);
				gc.dispose();

				// Open the file dialog to find a home for the image.
				FileDialog dialog = new FileDialog(shell, SWT.SAVE);
				dialog.setFilterExtensions(new String[] { "*.png" });
				String fileName = dialog.open();

				// If the user did not click cancel, save the image.
				if (fileName != null) {
					ImageLoader loader = new ImageLoader();
					loader.data = new ImageData[] { image.getImageData() };
					loader.save(fileName, SWT.IMAGE_PNG);
				}
			}
		});
	}
}
