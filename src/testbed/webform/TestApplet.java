package testbed.webform;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JApplet;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;

public class TestApplet extends JApplet {
	private static final long serialVersionUID = -6161533859246681403L;

	/**
	 * Create the applet.
	 */
	public TestApplet() {
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		final JProgressBar progressBar = new JProgressBar();
		final JTextArea detailsText = new JTextArea();
		final JScrollPane detailsScrollPane = new JScrollPane();
		final JLabel progressLabel = new JLabel("SAP-NP Progress");
		final JCheckBox detailsCheckbox = new JCheckBox("View Progress Details");
		
		detailsCheckbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JCheckBox source = (JCheckBox) event.getSource();
				boolean selected = source.isSelected();
				detailsScrollPane.setVisible(selected);
				revalidate();
				repaint();
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(detailsCheckbox)
						.addComponent(detailsScrollPane, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(progressLabel, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(progressLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
					.addGap(18)
					.addComponent(detailsCheckbox)
					.addPreferredGap(ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
					.addComponent(detailsScrollPane, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)
					.addGap(10))
		);
		
		detailsScrollPane.setViewportView(detailsText);
		detailsScrollPane.setVisible(false);
		getContentPane().setLayout(groupLayout);
	}
}
