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
package com.rapidminer.gui.tools.dialogs;

import com.rapidminer.gui.tools.ResourceAction;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Map;


/**
 * The type Multi page dialog.
 *
 * @author Tobias Malbrecht
 */
public abstract class MultiPageDialog extends ButtonDialog implements ChangeListener {

	private static final long serialVersionUID = 1L;

	private int currentStep;

	private JPanel cardPanel;

	private final JButton previous = new JButton(new ResourceAction("previous") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(java.awt.event.ActionEvent e) {
			previous();
		}
	});
	private final JButton next = new JButton(new ResourceAction("next") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(java.awt.event.ActionEvent e) {
			next();
		}
	});
	private final JButton finish = new JButton(new ResourceAction("finish") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(java.awt.event.ActionEvent e) {
			finish();
		}
	});

    /**
     * Instantiates a new Multi page dialog.
     *
     * @param owner     the owner
     * @param key       the key
     * @param modal     the modal
     * @param arguments the arguments
     */
    public MultiPageDialog(Dialog owner, String key, boolean modal, Object... arguments) {
		super(owner, key, modal ? ModalityType.APPLICATION_MODAL : ModalityType.MODELESS, arguments);
	}

    /**
     * Instantiates a new Multi page dialog.
     *
     * @param owner     the owner
     * @param key       the key
     * @param arguments the arguments
     */
    public MultiPageDialog(Dialog owner, String key, Object... arguments) {
		super(owner, key, ModalityType.MODELESS, arguments);
	}

    /**
     * Instantiates a new Multi page dialog.
     *
     * @param owner     the owner
     * @param key       the key
     * @param modal     the modal
     * @param arguments the arguments
     */
    public MultiPageDialog(Frame owner, String key, boolean modal, Object... arguments) {
		super(owner, key, modal ? ModalityType.APPLICATION_MODAL : ModalityType.MODELESS, arguments);
	}

    /**
     * Instantiates a new Multi page dialog.
     *
     * @param owner     the owner
     * @param key       the key
     * @param arguments the arguments
     */
    public MultiPageDialog(Frame owner, String key, Object... arguments) {
		super(owner, key, ModalityType.MODELESS, arguments);
	}

    /**
     * Is complete boolean.
     *
     * @return the boolean
     */
    protected abstract boolean isComplete();

    /**
     * Is last step boolean.
     *
     * @param step the step
     * @return the boolean
     */
    protected abstract boolean isLastStep(int step);

    /**
     * Gets name for step.
     *
     * @param step the step
     * @return the name for step
     */
    protected abstract String getNameForStep(int step);

    /**
     * Can proceed boolean.
     *
     * @param step the step
     * @return the boolean
     */
    protected boolean canProceed(int step) {
		return true;
	}

    /**
     * Can go back boolean.
     *
     * @param step the step
     * @return the boolean
     */
    protected boolean canGoBack(int step) {
		return true;
	}

    /**
     * Layout default.
     *
     * @param cards the cards
     */
    protected void layoutDefault(Map<String, Component> cards) {
		layoutDefault(cards, DEFAULT_SIZE);
	}

    /**
     * Layout default.
     *
     * @param cards Maps arbitrary names to GUI cards. The name for the current component is looked up            by using {@link #getNameForStep(int)} as a key into this map. This is useful, if            we do not have a linear order of cards.
     * @param size  the size
     */
    protected void layoutDefault(Map<String, Component> cards, int size) {
		cardPanel = new JPanel(new CardLayout());
		for (Map.Entry<String, Component> entry : cards.entrySet()) {
			cardPanel.add(entry.getValue(), entry.getKey());
		}
		showCurrent();
		super.layoutDefault(cardPanel, size, previous, next, finish, makeCancelButton());
	}

    /**
     * Gets current step.
     *
     * @return the current step
     */
    protected int getCurrentStep() {
		return currentStep;
	}

	private void showCurrent() {
		updateButtons();

		if (isLastStep(currentStep)) {
			getRootPane().setDefaultButton(finish);
		} else {
			getRootPane().setDefaultButton(next);
		}

		String key = getNameForStep(getCurrentStep());
		CardLayout cl = (CardLayout) cardPanel.getLayout();
		cl.show(cardPanel, key);
	}

    /**
     * Finish.
     */
    protected void finish() {
		wasConfirmed = true;
		dispose();
	}

    /**
     * Previous.
     */
    protected void previous() {
		currentStep--;
		showCurrent();
	}

    /**
     * Next.
     */
    protected void next() {
		currentStep++;
		showCurrent();
	}

	private void updateButtons() {
		previous.setEnabled(currentStep > 0 && canGoBack(currentStep));
		next.setEnabled(!isLastStep(currentStep) && canProceed(currentStep));
		finish.setEnabled(isComplete() && canProceed(currentStep));
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		updateButtons();
	}

    /**
     * Gets finish button.
     *
     * @return the finish button
     */
    protected JButton getFinishButton() {
		return finish;
	}
}
