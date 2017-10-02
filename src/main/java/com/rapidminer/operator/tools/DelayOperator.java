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
package com.rapidminer.operator.tools;

import com.rapidminer.operator.Operator;
import com.rapidminer.operator.OperatorDescription;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.operator.ports.DummyPortPairExtender;
import com.rapidminer.operator.ports.PortPairExtender;
import com.rapidminer.parameter.ParameterHandler;
import com.rapidminer.parameter.ParameterType;
import com.rapidminer.parameter.ParameterTypeCategory;
import com.rapidminer.parameter.ParameterTypeInt;
import com.rapidminer.parameter.UndefinedParameterError;
import com.rapidminer.parameter.conditions.EqualTypeCondition;
import com.rapidminer.tools.RandomGenerator;

import java.util.LinkedList;
import java.util.List;


/**
 * Delays the process execution (in non-parallel processing).
 *
 * @author Tobias Malbrecht
 */
public class DelayOperator extends Operator {

    /**
     * The type Delay provider.
     */
    public static class DelayProvider {

        /**
         * The constant PARAMETER_DELAY.
         */
        public static final String PARAMETER_DELAY = "delay";

        /**
         * The constant DELAY_MODES.
         */
        public static final String[] DELAY_MODES = { "none", "fixed", "random" };

        /**
         * The constant DELAY_NONE.
         */
        public static final int DELAY_NONE = 0;

        /**
         * The constant DELAY_FIXED.
         */
        public static final int DELAY_FIXED = 1;

        /**
         * The constant DELAY_RANDOM.
         */
        public static final int DELAY_RANDOM = 2;

        /**
         * The constant PARAMETER_DELAY_AMOUNT.
         */
        public static final String PARAMETER_DELAY_AMOUNT = "delay_amount";

        /**
         * The constant PARAMETER_DELAY_MIN_AMOUNT.
         */
        public static final String PARAMETER_DELAY_MIN_AMOUNT = "min_delay_amount";

        /**
         * The constant PARAMETER_DELAY_MAX_AMOUNT.
         */
        public static final String PARAMETER_DELAY_MAX_AMOUNT = "max_delay_amount";

		private int minAmount = 0;

		private int maxAmount = 1000;

		private RandomGenerator randomGenerator = null;

		private DelayProvider(int mode, int amount, int minAmount, int maxAmount) {
			switch (mode) {
				case DELAY_NONE:
					this.minAmount = 0;
					this.maxAmount = 0;
					break;
				case DELAY_FIXED:
					this.minAmount = amount;
					this.maxAmount = amount;
					break;
				case DELAY_RANDOM:
					this.minAmount = minAmount;
					this.maxAmount = maxAmount;
					this.randomGenerator = RandomGenerator.getGlobalRandomGenerator();
					break;
			}
		}

        /**
         * Delay.
         */
        public void delay() {
			try {
				if (minAmount == maxAmount) {
					Thread.sleep(maxAmount);
				} else {
					Thread.sleep(randomGenerator.nextIntInRange(minAmount, maxAmount));
				}
			} catch (InterruptedException e) {
			}
		}

        /**
         * Create delay provider delay provider.
         *
         * @param handler the handler
         * @return the delay provider
         * @throws UndefinedParameterError the undefined parameter error
         */
        public static DelayProvider createDelayProvider(ParameterHandler handler) throws UndefinedParameterError {
			return new DelayProvider(handler.getParameterAsInt(PARAMETER_DELAY),
					handler.getParameterAsInt(PARAMETER_DELAY_AMOUNT),
					handler.getParameterAsInt(PARAMETER_DELAY_MIN_AMOUNT),
					handler.getParameterAsInt(PARAMETER_DELAY_MAX_AMOUNT));
		}

        /**
         * Gets parameter types.
         *
         * @param handler the handler
         * @return the parameter types
         */
        public static List<ParameterType> getParameterTypes(ParameterHandler handler) {
			List<ParameterType> types = new LinkedList<ParameterType>();
			types.add(new ParameterTypeCategory(PARAMETER_DELAY,
					"Specifies whether execution should not be delayed, delayed by a fixed or random amount of time.",
					DELAY_MODES, DELAY_FIXED, false));
			ParameterType type = new ParameterTypeInt(PARAMETER_DELAY_AMOUNT, "The delay amount in ms.", 0,
					Integer.MAX_VALUE, 1000, false);
			type.registerDependencyCondition(new EqualTypeCondition(handler, PARAMETER_DELAY, DELAY_MODES, true, DELAY_FIXED));
			types.add(type);
			type = new ParameterTypeInt(PARAMETER_DELAY_MIN_AMOUNT, "The minimum delay amount in ms.", 0, Integer.MAX_VALUE,
					0, false);
			type.registerDependencyCondition(new EqualTypeCondition(handler, PARAMETER_DELAY, DELAY_MODES, true,
					DELAY_RANDOM));
			types.add(type);
			type = new ParameterTypeInt(PARAMETER_DELAY_MAX_AMOUNT, "The maximum delay amount in ms.", 0, Integer.MAX_VALUE,
					1000, false);
			type.registerDependencyCondition(new EqualTypeCondition(handler, PARAMETER_DELAY, DELAY_MODES, true,
					DELAY_RANDOM));
			types.add(type);
			return types;
		}
	}

	private PortPairExtender dummyPorts = new DummyPortPairExtender("through", getInputPorts(), getOutputPorts());

    /**
     * Instantiates a new Delay operator.
     *
     * @param description the description
     */
    public DelayOperator(OperatorDescription description) {
		super(description);
		dummyPorts.start();
		getTransformer().addRule(dummyPorts.makePassThroughRule());
	}

	@Override
	public void doWork() throws OperatorException {
		DelayProvider.createDelayProvider(this).delay();
		dummyPorts.passDataThrough();
	}

	@Override
	public List<ParameterType> getParameterTypes() {
		return DelayProvider.getParameterTypes(this);
	}
}
