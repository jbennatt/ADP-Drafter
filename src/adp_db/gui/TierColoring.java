package adp_db.gui;

import java.awt.Color;

import adp_db.ADPElement;
import adp_db.ADPTiers;

public class TierColoring {
	public static final Color QB_COLOR = Color.decode("#F2A29D");
	public static final Color RB_COLOR = Color.decode("#B8CAEA");
	public static final Color WR_COLOR = Color.decode("#9DF2A7");
	public static final Color TE_COLOR = Color.decode("#F2E09D");
	public static final Color K_COLOR = Color.decode("#E99DF2");
	public static final Color DEF_COLOR = Color.decode("#CDBE70");

	/**
	 * Uses the fact that ADPElement class defines the positions in the
	 * following way:
	 * 
	 * 
	 * QB = 0, WR = 1, RB = 2, TE = 3, DEF = 4, K = 5;
	 */
	private static final Color[] POS_COLOR = { QB_COLOR, WR_COLOR, RB_COLOR, TE_COLOR, DEF_COLOR, K_COLOR };

	private final ADPTiers tiers;

	public TierColoring() {
		tiers = null;
	}

	public TierColoring(final ADPTiers tiers) {
		this.tiers = tiers;
	}

	public Color getColor(final ADPElement player) {
		final int pos = player.pos;

		if (tiers == null) {
			return POS_COLOR[pos];
		}

		final int tier = tiers.getTier(player);

		Color color = Color.WHITE;

		if (tier != -1) {
			color = POS_COLOR[pos];
			final float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), new float[3]);
			// increase saturation
			hsb[1] -= 0.15f * (float) (tier - 1);
			final int rgbInt = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);

			return new Color(rgbInt);
		}

		return color;
	}
}
