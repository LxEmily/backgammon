package ui;

import constants.GameConstants;
import constants.MessageType;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * A TextArea that displays game information.
 * 
 * @teamname TeaCup
 * @author Bryan Sng, 17205050
 * @author @LxEmily, 17200573
 * 
 */
public class InfoPanel extends ScrollPane {
	public int textPadding;
	private TextFlow textContainer;
	
	public InfoPanel() {
		super();
		textContainer = new TextFlow();
		style();
		initLayout();
		welcome();
	}
	
	private void style() {
		double height = GameConstants.getHalfBoardSize().getHeight();
		double width = GameConstants.getMiddlePartWidth() / 3.0;
		setMinHeight(height);
		setMaxHeight(height);
		setMinWidth(width);
		setMaxWidth(width);
		setFitToWidth(true);									// text fits into width.
		setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);		// no horizontal scroll bar.
		vvalueProperty().bind(textContainer.heightProperty());	// auto scroll down with texts.
		setFocusTraversable(false);
		
		textPadding = 3;
		textContainer.setPadding(new Insets(textPadding));
		textContainer.setLineSpacing(textPadding / 2.0);
	}
	
	private void initLayout() {
		setContent(textContainer);
	}

	// text padding at top and bottom.
	// 2 line spacing in a single line, top and bottom.
	//
	// First we get a rough possible lines,
	// multiply that with the line spacings,
	// divide by font size, and we get the number of lines
	// that should not be new lines.
	//
	// subtract that by the possible lines and we get our actual lines.
	public void clear() {
		TextFlow textContainer = getTextContainer();
		int possibleLines = (int) (getMinHeight() - textPadding * 2) / GameConstants.FONT_SIZE;
		int numberOfLines = (int) (possibleLines - (possibleLines * textContainer.getLineSpacing() * 2) / GameConstants.FONT_SIZE);
		printNewlines(numberOfLines);
	}
	
	/**
	 * Outputs welcome message and prompts player to start game
	 */
	public void welcome() {
		print("Welcome to Backgammon!");
		print("Enter \"/start\" below to start a new game.");
		print("Or enter \"/help\" for a list of possible commands.");
	}
	
	/**
	 * Prints the given text to the information panel.
	 * @param msg - string to be printed
	 * @param mtype - message type, (i.e., error or system message) 
	 */
	public void print(String msg, MessageType mtype) {
		Text text = new Text();
		String prefix = ">";
		String type = "";
		switch (mtype) {
			case ANNOUNCEMENT:
				prefix = "\n" + prefix;
			case SYSTEM:
				type = "[System]";
				text.setFill(Color.GREEN);
				break;
			case ERROR:
				type = "[Error]";
				text.setFill(Color.FIREBRICK);
				break;
			case DEBUG:
				type = "[Debug]";
				text.setFill(Color.DIMGRAY);
				break;
			case WARNING:
				type = "[Warning]";
				text.setFill(Color.YELLOW);
			case CHAT:
				break;
		}
		text.setText(prefix + " " + type + " " + msg + "\n");
		
		// same as
		// (debugMode || (!debugMode && mtype != MessageType.DEBUG))
		// (GameConstants.DEBUG_MODE || mtype != MessageType.DEBUG)
		if (GameConstants.DEBUG_MODE)
			appendText(text);
		else if (mtype != MessageType.DEBUG)
			appendText(text);
	}
	public void print(String msg) {
		print(msg, MessageType.SYSTEM);
	}
	
	// text flow version of textarea's appendText().
	private void appendText(Text text) {
		textContainer.getChildren().add(text);
	}

	/**
	 * Print empty row to information panel.
	 * @param times number of times to print the new line.
	 */
	public void printNewlines(int times) {
		for (int i = 0; i < times; i++) {
			appendText(new Text("\n"));
		}
	}
	
	/**
	 * Saves everything on the information panel to a text file.
	 */
	public void saveToFile() {
		StringBuilder sb = new StringBuilder();
		for (Node node : this.getChildren()) {
			if (node instanceof Text) {
				sb.append(((Text) node).getText());
			}
		}
		
		try {
			BufferedWriter buffer = new BufferedWriter(new FileWriter(new File("backgammon.txt")));
			buffer.append(sb.toString());
			buffer.newLine();
			buffer.flush();
			buffer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		print("Game log saved to backgammon.txt");	
	}
	
	public TextFlow getTextContainer() {
		return textContainer;
	}
}
