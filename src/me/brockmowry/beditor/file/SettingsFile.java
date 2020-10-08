package me.brockmowry.beditor.file;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import me.brockmowry.beditor.BEditor;
import me.brockmowry.beditor.window.Window;

public class SettingsFile {

	private final Window window;

	private final File directory;
	private final File settingsFile;
	private final Gson settingsGson;

	public SettingsFile(final Window window) {
		this.window = window;

		directory = new File(System.getProperty("user.home"), "/BEditor/");
		if (!directory.exists()) {
			directory.mkdirs();
		}

		settingsFile = new File(directory, "settings.json");
		settingsGson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
	}

	public void loadSettings() throws Exception {
		if (!settingsFile.exists()) {
			return;
		}
		
		final FileReader fileReader = new FileReader(settingsFile);
		final BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		final JsonElement jsonElement = settingsGson.fromJson(bufferedReader, JsonElement.class);
		if (jsonElement instanceof JsonNull) 
			return;
		final JsonObject jsonObject = (JsonObject) jsonElement;
		
		if (jsonObject.has("file")) {
			final String fileName = jsonObject.get("file").getAsString();
			final File file = new File(fileName);
			window.getFileManager().loadFile(file);
			window.setTitle(BEditor.WINDOW_TITLE + " | " + file.getName());
		}
		
		if (jsonObject.has("font")) {
			final JsonElement fontElement = jsonObject.get("font");
			if (fontElement instanceof JsonNull)
				return;
			final JsonObject fontObject = (JsonObject) fontElement;
			
			final String fontName = fontObject.get("name").getAsString();
			final int style = fontObject.get("style").getAsInt();
			final int fontSize = fontObject.get("size").getAsInt();
			window.getEditor().getTextArea().setFont(new Font(fontName, style, fontSize));
		}
	}

	public void saveSettings() throws Exception {
		if (!settingsFile.exists()) {
			settingsFile.createNewFile();
		}

		final FileWriter fileWriter = new FileWriter(settingsFile);
		final BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

		final JsonObject settingsObject = new JsonObject();
		if (window.getFileManager().getCurrentFile() != null) {
			settingsObject.addProperty("file", window.getFileManager().getCurrentFile().getAbsolutePath());
		}

		final JsonObject fontObject = new JsonObject();
		fontObject.addProperty("name", window.getEditor().getTextArea().getFont().getName());
		fontObject.addProperty("style", window.getEditor().getTextArea().getFont().getStyle());
		fontObject.addProperty("size", window.getEditor().getTextArea().getFont().getSize());
		settingsObject.add("font", fontObject);

		bufferedWriter.write(settingsGson.toJson(settingsObject));
		bufferedWriter.flush();
		bufferedWriter.close();
		fileWriter.close();
	}

}
