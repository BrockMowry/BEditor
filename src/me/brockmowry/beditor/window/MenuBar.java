package me.brockmowry.beditor.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import me.brockmowry.beditor.BEditor;
import say.swing.JFontChooser;

public class MenuBar extends JMenuBar {

	private final JMenu file;
	private final JMenuItem open, save, saveAs, exit;

	private final JMenu edit;
	private final JMenuItem cut, copy, paste, selectAll, clear, find;

	private final JMenu view, showView, preferences;
	private final JCheckBoxMenuItem editor;
	private final JMenuItem font;

	public MenuBar(final Window window) {
		file = new JMenu("File");
		file.setMnemonic('F');

		open = new JMenuItem("Open");
		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent event) {
				final int option = window.getFileChooser().showOpenDialog(null);
				if (option == JFileChooser.APPROVE_OPTION) {
					final File file = window.getFileChooser().getSelectedFile();
					if (file != null) {
						try {
							window.getFileManager().loadFile(file);
						} catch (final IOException exception) {
							exception.printStackTrace();
						}
					}
				}

				window.setTitle(BEditor.WINDOW_TITLE + " | " + window.getFileManager().getCurrentFile().getName());
			}
		});
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		save = new JMenuItem("Save");
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent event) {
				if (window.getFileManager().getCurrentFile() != null) {
					try {
						window.getFileManager().saveFile(window.getFileManager().getCurrentFile());
					} catch (final IOException exception) {
						exception.printStackTrace();
					}

					window.setTitle(BEditor.WINDOW_TITLE + " | " + window.getFileManager().getCurrentFile().getName());

					return;
				}

				final int option = window.getFileChooser().showSaveDialog(null);
				if (option == JFileChooser.APPROVE_OPTION) {
					final File file = window.getFileChooser().getSelectedFile();
					if (file != null) {
						try {
							window.getFileManager().saveFile(file);
						} catch (final IOException exception) {
							exception.printStackTrace();
						}
					}
				}

				window.setTitle(BEditor.WINDOW_TITLE + " | " + window.getFileManager().getCurrentFile().getName());

			}
		});
		save.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK));
		saveAs = new JMenuItem("Save As");
		saveAs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent event) {
				final int option = window.getFileChooser().showSaveDialog(null);
				if (option == JFileChooser.APPROVE_OPTION) {
					final File file = window.getFileChooser().getSelectedFile();
					if (file != null) {
						try {
							window.getFileManager().saveFile(file);
						} catch (final IOException exception) {
							exception.printStackTrace();
						}
					}
				}

				window.setTitle(BEditor.WINDOW_TITLE + " | " + window.getFileManager().getCurrentFile().getName());
			}
		});
		saveAs.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));

		addToMenu(file, open, save, saveAs);

		exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent event) {
				window.closeWindow();
			}
		});
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, InputEvent.CTRL_MASK));
		file.addSeparator();
		file.add(exit);

		add(file);

		edit = new JMenu("Edit");
		edit.setMnemonic('E');

		cut = new JMenuItem("Cut");
		cut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent event) {
				window.getEditor().getTextArea().cut();
			}
		});
		cut.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK));
		copy = new JMenuItem("Copy");
		copy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent event) {
				window.getEditor().getTextArea().copy();
			}
		});
		copy.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK));
		paste = new JMenuItem("Paste");
		paste.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent event) {
				window.getEditor().getTextArea().paste();
			}
		});
		paste.setAccelerator(KeyStroke.getKeyStroke('V', InputEvent.CTRL_MASK));
		selectAll = new JMenuItem("Select All");
		selectAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent event) {
				window.getEditor().getTextArea().selectAll();
			}
		});
		selectAll.setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.CTRL_MASK));
		addToMenu(edit, cut, copy, paste, selectAll);

		clear = new JMenuItem("Clear");
		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent event) {
				window.getEditor().getTextArea().setText(" ");
			}
		});
		edit.addSeparator();
		edit.add(clear);
		find = new JMenuItem("Find");
		find.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent event) {
				
			}
		});
		find.setAccelerator(KeyStroke.getKeyStroke('F', InputEvent.CTRL_MASK));
		edit.addSeparator();
		edit.add(find);

		add(edit);

		view = new JMenu("View");
		view.setMnemonic('V');

		showView = new JMenu("Show View");
		editor = new JCheckBoxMenuItem("Editor", true);
		editor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent event) {
				window.getEditor().setVisible(editor.getState());
			}
		});
		showView.add(editor);
		view.add(showView);

		preferences = new JMenu("Preferences");
		font = new JMenuItem("Font");
		font.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent event) {
				final JFontChooser fontChooser = new JFontChooser();
				final int option = fontChooser.showDialog(null);
				if (option == JFontChooser.OK_OPTION) {
					window.getEditor().getTextArea().setFont(fontChooser.getSelectedFont());
				}
			}
		});
		preferences.add(font);
		view.add(preferences);

		add(view);
	}

	private void addToMenu(final JMenu menu, final JMenuItem... items) {
		for (final JMenuItem item : items) {
			menu.add(item);
		}
	}

}
