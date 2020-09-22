package adp_db.gui;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import adp_db.ADPElement;
import drafter.Draft;

public class TeamOptionsDisplay extends JFrame {
	/**
	 * generated by eclipse
	 */
	private static final long serialVersionUID = -2864524618864772793L;

	private final JComponent panel = new JPanel();
	private final Draft draft;
	public final int teamIndex;
	private final TeamRoundDisplay[] displays;

	public TeamOptionsDisplay(final int teamIndex, final Draft draft, final int numDisplays,
			final TierColoring tierColoring) {
		this.displays = new TeamRoundDisplay[numDisplays];
		this.draft = draft;
		this.teamIndex = teamIndex - 1;

		this.setContentPane(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		for (int i = 0; i < numDisplays; ++i) {
			displays[i] = new TeamRoundDisplay(draft, this.teamIndex, i + 1, tierColoring);
			panel.add(displays[i]);
		}

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		updateView();

		this.setVisible(true);
		this.pack();
	}

	public void updateView() {
		int roundPick = draft.getTeamNthSelection(teamIndex, draft.getRound());
		final int currentPick = draft.currentPick();
		final int nextRound = draft.getRound() + (roundPick >= currentPick ? 0 : 1);

		for (int i = 0; i < displays.length; ++i) {
			displays[i].setRound(nextRound + i);
		}

		panel.revalidate();
		panel.repaint();
		this.revalidate();
		this.repaint();
		this.pack();
	}
}
