package me.brockmowry.beditor.window;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import me.brockmowry.beditor.BEditor;
import me.brockmowry.beditor.file.FileManager;
import me.brockmowry.beditor.file.SettingsFile;
import me.brockmowry.beditor.window.panels.Editor;

public class Window extends JFrame {
	
	private final JFileChooser fileChooser;
	
	private final FileManager fileManager;
	private final SettingsFile settingsFile;
	
	private final MenuBar menuBar;
	private final Editor editor;
	
	public Window() {
		super(BEditor.WINDOW_TITLE);
		setLayout(new BorderLayout());
		
		fileChooser = new JFileChooser();
		
		fileManager = new FileManager(this);
		settingsFile = new SettingsFile(this);
		
		menuBar = new MenuBar(this);
		setJMenuBar(menuBar);
		
		editor = new Editor();
		add(editor, BorderLayout.CENTER);
		
		try {
			settingsFile.loadSettings();
		} catch (final Exception exception) {
			exception.printStackTrace();
		}
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent event) {
				closeWindow();
			}
		});
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(1280, 720);
		setResizable(true);
		setLocationRelativeTo(null);
	}
	
	public void closeWindow() {
		final int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit? You may have made unsaved changes.", 
				"Confirm exit", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
		if (option == 0) {
			try {
				settingsFile.saveSettings();
			} catch (final Exception exception) {
				exception.printStackTrace();
			}
			
			System.exit(0);
		}
	}
	
	public JFileChooser getFileChooser() {
		return fileChooser;
	}
	
	public FileManager getFileManager() {
		return fileManager;
	}
	
	public Editor getEditor() {
		return editor;
	}

}
