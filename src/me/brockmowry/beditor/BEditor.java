package me.brockmowry.beditor;

import javax.swing.SwingUtilities;

import me.brockmowry.beditor.window.Window;

public class BEditor {
	
	public static final double VERSION = 0.1;
	public static final String WINDOW_TITLE = "BEditor " + VERSION;
	
	public static void main(final String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Window().setVisible(true);
		});
	}

}
