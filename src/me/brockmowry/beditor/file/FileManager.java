package me.brockmowry.beditor.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import me.brockmowry.beditor.window.Window;

public class FileManager {
	
	private final Window window;
	private File currentFile = null;
	
	public FileManager(final Window window) {
		this.window = window;
	}
	
	public void loadFile(final File file) throws IOException {
		final FileReader fileReader = new FileReader(file);
		final BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = "", text = "";
		while ((line = bufferedReader.readLine()) != null) {
			text = text + line + "\n";
		}
		bufferedReader.close();
		
		window.getEditor().getTextArea().setText(text);
		
		currentFile = file;
	}
	
	public void saveFile(final File file) throws IOException {
		final FileWriter fileWriter = new FileWriter(file);
		final BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		bufferedWriter.write(window.getEditor().getTextArea().getText());
		bufferedWriter.flush();
		bufferedWriter.close();
		
		currentFile = file;
	}
	
	public void setCurrentFile(final File currentFile) {
		this.currentFile = currentFile;
	}
	
	public File getCurrentFile() {
		return currentFile;
	}

}
