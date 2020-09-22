package adp_db.gui;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import drafter.Draft;
import adp_db.ADPElement;

public class PlayerSelectChooserFrame extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8794714394833526226L;

	public static final int MAX_RES = 25;

	private final JComponent panel = new JPanel();

	private final Draft draft;
	private final JRadioButton[] radioSelect = new JRadioButton[MAX_RES];
	private final JTextField searchField = new JTextField("search");
	private final JRadioButton nobody = new JRadioButton("nobody");

	private final ButtonGroup buttonGroup = new ButtonGroup();

	private final ArrayList<ADPElement> list = new ArrayList<ADPElement>(
			MAX_RES);

	private final TeamOptionsDisplay[] displays;

	public PlayerSelectChooserFrame(final Draft draft,
			TeamOptionsDisplay... displays) {
		this.displays = displays;
		this.draft = draft;

		nobody.setActionCommand("-1");
		nobody.addActionListener(this);

		searchField.setActionCommand("search");
		searchField.addActionListener(this);

		this.setContentPane(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		initRadioButtons();
	}

	private void clearButtonGroup() {
		final int size = list.size() < MAX_RES ? list.size() : MAX_RES;

		buttonGroup.remove(nobody);

		for (int i = 0; i < size; ++i)
			buttonGroup.remove(radioSelect[i]);
	}

	public synchronized void display() {
		// clean up old state
		clearButtonGroup();
		panel.removeAll();

		// undrafted.findElements(query, list);
		final int size = draft.querySize() < MAX_RES ? draft.querySize()
				: MAX_RES;

		// System.err.println(draft.promptDraft());
		final JLabel roundLabel = new JLabel(draft.promptDraft());
		panel.add(roundLabel);
		panel.add(searchField);
		// panel.add(searchButton);
		panel.add(nobody);

		buttonGroup.add(nobody);

		for (int i = 0; i < size; ++i) {
			final JRadioButton button = radioSelect[i];

			button.setText(draft.getQuery(i).name);

			// set returned command to index from query list
			button.setActionCommand("" + i);
			buttonGroup.add(button);

			// add radio button to panel
			panel.add(button);
		}

		buttonGroup.clearSelection();

		// display frame
		this.setVisible(true);
		panel.revalidate();
		panel.repaint();
		this.revalidate();
		this.repaint();
		// this.setVisible(true);
		this.pack();
	}

	private void initRadioButtons() {
		for (int i = 0; i < radioSelect.length; ++i) {
			final JRadioButton button = radioSelect[i] = new JRadioButton();
			button.addActionListener(this);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final String com = e.getActionCommand();
		switch (com) {
		case "search":
			updateSearch();
			break;
		default:
			performQuery(com);
		}
	}

	private void updateSearch() {
		draft.queryDraft(searchField.getText());

		this.display();
	}

	private void performQuery(final String com) {
		draft.draft(com);

		for (final TeamOptionsDisplay display : displays) {
			display.updateView();
		}

		updateSearch();
	}
}
