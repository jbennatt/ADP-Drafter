package adp_db;

import adp_tiers.Tier;
import adp_tiers.TierData;

public class ADPTiers {
	private final ADPPOSTiers[] tiers = new ADPPOSTiers[ADPElement.NUM_POS];

	public ADPTiers(final TierData tierData, final ADPDatabase db) {
		// create the tiers for the different positions
		for (int i = 0; i < tiers.length; ++i)
			tiers[i] = new ADPPOSTiers(i);

		// add RBs to tiers
		for (final Tier tier : tierData.RBs)
			tiers[ADPElement.RB]
					.add(new ADPElementTier(ADPElement.RB, tier, db));

		// add WRs to tiers
		for (final Tier tier : tierData.WRs)
			tiers[ADPElement.WR]
					.add(new ADPElementTier(ADPElement.WR, tier, db));

		// add QBs to tiers
		for (final Tier tier : tierData.QBs)
			tiers[ADPElement.QB]
					.add(new ADPElementTier(ADPElement.QB, tier, db));

		// add TEs to tiers
		for (final Tier tier : tierData.TEs)
			tiers[ADPElement.TE]
					.add(new ADPElementTier(ADPElement.TE, tier, db));

		// add Ks to tiers
		for (final Tier tier : tierData.Ks)
			tiers[ADPElement.K].add(new ADPElementTier(ADPElement.K, tier, db));

		// add DEFs to tiers
		for (final Tier tier : tierData.DSTs)
			tiers[ADPElement.DEF].add(new ADPElementTier(ADPElement.DEF, tier,
					db));
		
		// try to place any stragglers in the closestest tier
		for(final ADPElement player : db){
			if(this.getTier(player) == -1){
				// this player wasn't place in a tier...try to find the closest tier.
				tiers[player.pos].placePlayer(player);
			}
		}
	}

	public int getTier(final ADPElement player) {
		for (final ADPElementTier tier : tiers[player.pos]) {
			if (tier.contains(player)) {
				return tier.tierIndex;
			}
		}

		// player wasn't found in any of the tiers
		return -1;
	}

	public void printTiers() {
		for (final ADPPOSTiers posTiers : tiers) {
			System.out.println(ADPElement.getPOS(posTiers.pos) + " Tiers:");

			for (final ADPElementTier tier : posTiers) {
				System.out.println("Tier " + tier.tierIndex);

				for (final ADPElement player : tier) {
					System.out.println(player);
				}
			}
		}
	}
}
