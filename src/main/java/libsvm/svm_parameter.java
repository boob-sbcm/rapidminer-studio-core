/**
 * Copyright (c) 2000-2005 Chih-Chung Chang and Chih-Jen Lin All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * 3. Neither name of copyright holders nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF
 * THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package libsvm;

/**
 * The type Svm parameter.
 */
public class svm_parameter implements Cloneable, java.io.Serializable {

	private static final long serialVersionUID = -2733609912517132812L;

    /**
     * The constant C_SVC.
     */
/* svm_type */
	public static final int C_SVC = 0;
    /**
     * The constant NU_SVC.
     */
    public static final int NU_SVC = 1;
    /**
     * The constant ONE_CLASS.
     */
    public static final int ONE_CLASS = 2;
    /**
     * The constant EPSILON_SVR.
     */
    public static final int EPSILON_SVR = 3;
    /**
     * The constant NU_SVR.
     */
    public static final int NU_SVR = 4;

    /**
     * The constant LINEAR.
     */
/* kernel_type */
	public static final int LINEAR = 0;
    /**
     * The constant POLY.
     */
    public static final int POLY = 1;
    /**
     * The constant RBF.
     */
    public static final int RBF = 2;
    /**
     * The constant SIGMOID.
     */
    public static final int SIGMOID = 3;
    /**
     * The constant PRECOMPUTED.
     */
    public static final int PRECOMPUTED = 4;

    /**
     * The Svm type.
     */
    public int svm_type;
    /**
     * The Kernel type.
     */
    public int kernel_type;
    /**
     * The Degree.
     */
    public int degree;	// for poly
    /**
     * The Gamma.
     */
    public double gamma;	// for poly/rbf/sigmoid
    /**
     * The Coef 0.
     */
    public double coef0;	// for poly/sigmoid

    /**
     * The Cache size.
     */
// these are for training only
	public double cache_size; // in MB
    /**
     * The Eps.
     */
    public double eps;	// stopping criteria
    /**
     * The C.
     */
    public double C;	// for C_SVC, EPSILON_SVR and NU_SVR
    /**
     * The Nr weight.
     */
    public int nr_weight;		// for C_SVC
    /**
     * The Weight label.
     */
    public int[] weight_label;	// for C_SVC
    /**
     * The Weight.
     */
    public double[] weight;		// for C_SVC
    /**
     * The Nu.
     */
    public double nu;	// for NU_SVC, ONE_CLASS, and NU_SVR
    /**
     * The P.
     */
    public double p;	// for EPSILON_SVR
    /**
     * The Shrinking.
     */
    public int shrinking;	// use the shrinking heuristics
    /**
     * The Probability.
     */
    public int probability; // do probability estimates

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
