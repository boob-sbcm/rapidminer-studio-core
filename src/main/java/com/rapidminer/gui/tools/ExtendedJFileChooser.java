/**
 * Copyright (C) 2001-2017 by RapidMiner and the contributors
 * 
 * Complete list of developers available at our web site:
 * 
 * http://rapidminer.com
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Affero General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see http://www.gnu.org/licenses/.
*/
package com.rapidminer.gui.tools;

import com.rapidminer.gui.look.fc.FileChooserUI;
import com.rapidminer.gui.tools.dialogs.ButtonDialog;

import javax.accessibility.AccessibleContext;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.Dialog.ModalityType;
import java.io.File;
import java.util.Collection;
import java.util.LinkedList;


/**
 * The type Extended j file chooser.
 *
 * @author Tobias Malbrecht
 */
public class ExtendedJFileChooser extends JFileChooser {

	private static final long serialVersionUID = -3457903206380227482L;

	private class FileChooserButtonDialog extends ButtonDialog {

		private static final long serialVersionUID = -553876079090407051L;

        /**
         * Instantiates a new File chooser button dialog.
         *
         * @param owner     the owner
         * @param key       the key
         * @param modal     the modal
         * @param chooser   the chooser
         * @param arguments the arguments
         */
        public FileChooserButtonDialog(Window owner, String key, boolean modal, ExtendedJFileChooser chooser,
				Object... arguments) {
			super(owner, key, modal ? ModalityType.APPLICATION_MODAL : ModalityType.MODELESS, arguments);
			chooser.setControlButtonsAreShown(false);
			layoutDefault(chooser, NORMAL, getButtons());
		}
	}

	private final String i18nKey;

	private final Object[] i18nArgs;

    /**
     * Instantiates a new Extended j file chooser.
     *
     * @param directory the directory
     */
    public ExtendedJFileChooser(File directory) {
		super(directory);
		this.i18nKey = null;
		this.i18nArgs = null;
	}

    /**
     * Instantiates a new Extended j file chooser.
     *
     * @param i18nKey   the 18 n key
     * @param directory the directory
     * @param i18nArgs  the 18 n args
     */
    public ExtendedJFileChooser(String i18nKey, File directory, Object... i18nArgs) {
		super(directory);
		this.i18nKey = i18nKey;
		this.i18nArgs = i18nArgs;
	}

	@Override
	protected JDialog createDialog(Component parent) throws HeadlessException {
		JDialog dialog;
		if (i18nKey != null) {
			Window window = getWindowForComponent(parent);
			dialog = new FileChooserButtonDialog(window, i18nKey, true, this, i18nArgs);
		} else {
			String title = getUI().getDialogTitle(this);
			putClientProperty(AccessibleContext.ACCESSIBLE_DESCRIPTION_PROPERTY, title);

			Window window = getWindowForComponent(parent);
			dialog = new JDialog(window, title, ModalityType.APPLICATION_MODAL);
			dialog.setComponentOrientation(this.getComponentOrientation());

			Container contentPane = dialog.getContentPane();
			contentPane.setLayout(new BorderLayout());
			this.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
			contentPane.add(this, BorderLayout.CENTER);

			if (JDialog.isDefaultLookAndFeelDecorated()) {
				boolean supportsWindowDecorations = UIManager.getLookAndFeel().getSupportsWindowDecorations();
				if (supportsWindowDecorations) {
					dialog.getRootPane().setWindowDecorationStyle(JRootPane.FILE_CHOOSER_DIALOG);
				}
			}
			dialog.pack();
			dialog.setLocationRelativeTo(parent);
		}

		return dialog;
	}

	private static Window getWindowForComponent(Component parentComponent) throws HeadlessException {
		if (parentComponent == null) {
			return JOptionPane.getRootFrame();
		}
		if (parentComponent instanceof Frame || parentComponent instanceof Dialog) {
			return (Window) parentComponent;
		}
		return getWindowForComponent(parentComponent.getParent());
	}

	private Collection<AbstractButton> getButtons() {
		Collection<AbstractButton> buttons = new LinkedList<AbstractButton>();
		if (getUI() instanceof FileChooserUI) {
			buttons.add(((FileChooserUI) getUI()).getApproveButton());
			buttons.add(((FileChooserUI) getUI()).getCancelButton());
		}
		return buttons;
	}

    /**
     * Add change listener.
     *
     * @param l the l
     */
    public void addChangeListener(ChangeListener l) {
		if (getUI() instanceof FileChooserUI) {
			((FileChooserUI) getUI()).addChangeListener(l);
		}
	}

    /**
     * Remove change listener.
     *
     * @param l the l
     */
    public void removeChangeListener(ChangeListener l) {
		if (getUI() instanceof FileChooserUI) {
			((FileChooserUI) getUI()).removeChangeListener(l);
		}
	}

    /**
     * Is file selected boolean.
     *
     * @return the boolean
     */
    public boolean isFileSelected() {
		if (getUI() instanceof FileChooserUI) {
			return ((FileChooserUI) getUI()).isFileSelected();
		}
		return true;
	}
}
