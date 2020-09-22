package adp_db;

import java.util.InputMismatchException;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.special.Gamma;

public class ADPElement {
	private static final StringBuilder sb = new StringBuilder();
	public static final String LINE_SEP = System.lineSeparator();
	public static final double STD_MULT = 2;

	public static final int QB = 0;
	public static final int WR = 1;
	public static final int RB = 2;
	public static final int TE = 3;
	public static final int DEF = 4;
	public static final int K = 5;

	public static final int NUM_POS = 6;

	public final String name;
	public final int pos;
	int rank;
	double avgRank;
	public final double std;
	public final int roundHigh, roundSelectHigh;
	public final int roundLow, roundSelectLow;

	// probability for current selection
	double p;

	public ADPElement(final String name, final int pos, final int rank, final double avgRank, final double std) {
		this.name = name;
		this.pos = pos;
		this.rank = rank;
		this.avgRank = avgRank;
		this.std = std;

		// not used currently
		this.roundHigh = this.roundLow = this.roundSelectHigh = this.roundSelectLow = 0;
	}

	public ADPElement(final String line) {

		final String[] elements = line.split("\\,");

		int index = 0;
		name = elements[index++].trim();
		pos = getPOS(elements[index++].trim());
		rank = Integer.parseInt(elements[index++].trim());
		avgRank = Double.parseDouble(elements[index++].trim());
		std = Double.parseDouble(elements[index++].trim());

		final String[] high = elements[index++].trim().split("\\.");
		final String[] low = elements[index++].trim().split("\\.");

		roundHigh = Integer.parseInt(high[0].trim());
		roundSelectHigh = Integer.parseInt(high[1].trim());

		roundLow = Integer.parseInt(low[0].trim());
		roundSelectLow = Integer.parseInt(low[1].trim());
	}

	public int getRank() {
		return rank;
	}

	public static String getPOS(final int pos) {
		switch (pos) {
		case QB:
			return "QB";
		case WR:
			return "WR";
		case RB:
			return "RB";
		case TE:
			return "TE";
		case K:
			return "K";
		case DEF:
			return "DEF";
		}

		throw new InputMismatchException("index = " + pos + " is not a valid position index");
	}

	public static int getPOS(String pos) {
		pos = pos.toUpperCase();

		switch (pos) {
		case "QB":
			return QB;
		case "RB":
			return RB;
		case "WR":
			return WR;
		case "TE":
			return TE;
		case "PK":
			return K;
		case "DEF":
			return DEF;
		}

		throw new InputMismatchException('"' + pos + "\" isn't a valid position");
	}

	public double getNormalP(final double selection) {
		final NormalDistribution pFun = new NormalDistribution(avgRank, std);
		// p = pFun.cumulativeProbability(selection + std)
		// - pFun.cumulativeProbability(selection - std);
		final double pom = STD_MULT * std;

		return p = pFun.cumulativeProbability(selection + pom) - pFun.cumulativeProbability(selection - pom);
	}

	/**
	 * Return the probability that this element has NOT been selected by this
	 * pick. This is based on a Regularized Gamma Function, where we are given
	 * the mean (avg rank) and standard deviation.
	 * 
	 * From http://mathworld.wolfram.com/GammaDistribution.html
	 * 
	 * D(x=select, \alpha, \theta) = P(\alpha, z = \frac{x}{\theta})
	 * 
	 * where P(\alpha, z) = regularized gamma function
	 * 
	 * mean = \alpha * \theta
	 * 
	 * var = std^2 = \alpha * \theta^2
	 * 
	 * -->
	 * 
	 * \theta = var / mean
	 * 
	 * \alpha = 0.5 * (\frac{mean}P{\theta} + \frac{var}{\theta^2}) = 0.5 *
	 * \frac{(mean * \theta + var}{\theta^2}
	 * 
	 * z = \frac{selection}{\theta}
	 * 
	 * From
	 * 
	 * <pre>
	 * 	 http://en.wikipedia.org/wiki/Incomplete_gamma_function#Regularized_Gamma_functions_and_Poisson_random_variables
	 * </pre>
	 * 
	 * \
	 * 
	 * $P(s, x)$ is the cumulative distribution function for Gamma random
	 * variables with shape parameter $s$ and scale parameter $1$ is the
	 * cumulative distribution function for Poisson random variables: If X is a
	 * {\rm Poi}(\lambda) random variable then.
	 * 
	 * When $s > 0$ is an integer, $Q(s, \lambda)$
	 * 
	 * Regularized_Gamma_functions_and_Poisson_random_variables. P(s, x) is the
	 * cumulative distribution function for Gamma random variables with shape
	 * parameter s and scale parameter 1.
	 * 
	 * Pr(X < s) = Q(s, \lambda = z) = 1 - P(x, \lambda = z)
	 * 
	 * P(X >= s) = P(x, \lambda = z)
	 * 
	 * @param selection
	 * @return
	 */
	public double getPoissonP(final double selection) {
		return p = Gamma.regularizedGammaP(selection, avgRank);
	}

	public double getP() {
		return p;
	}

	@Override
	public String toString() {
		synchronized (sb) {
			sb.delete(0, sb.length());

			sb.append(name + "\t\t");
			sb.append(ADPElement.getPOS(pos) + "\t\t");
			sb.append(rank + "\t\t");
			sb.append(avgRank + "\t\t");
			sb.append(std + "\t\t");
			sb.append(roundHigh + "." + roundSelectHigh + "\t\t");
			sb.append(roundLow + "." + roundSelectLow);

			return sb.toString();
		}
	}

	public double getAVGRank() {
		return avgRank;
	}
}
