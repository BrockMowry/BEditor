package me.brockmowry.beditor.window.panels;

import java.awt.BorderLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Editor extends JPanel {
	
	private final JTextArea textArea;
	
	public Editor() {
		super(new BorderLayout());
		
		textArea = new JTextArea("", 0, 0);
		textArea.setWrapStyleWord(true);
		textArea.setMargin(new Insets(5, 5, 5, 5));
		add(new JScrollPane(textArea), BorderLayout.CENTER);
		
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	}
	
	public JTextArea getTextArea() {
		return textArea;
	}
	
}
