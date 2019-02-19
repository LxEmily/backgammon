package game_engine;

import constants.GameConstants;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * This class represents the point number labels of the Backgammon game.
 * 
 * NOTE: To simulate a more realistic board, add rotation,
 * and rotate the label by 180 when its at the top.
 * This will show the numbers based on the player's perspective.
 * 
 * @teamname TeaCup
 * @author Bryan Sng, 17205050
 * @author @LxEmily, 17200573
 *
 */
public class PipNumberLabel extends Label {
	public PipNumberLabel(int pipNum) {
		super(Integer.toString(pipNum));
		setAlignment(Pos.CENTER);
		setTextFill(Color.WHITE);
		setMinSize(GameConstants.getPipSize().getWidth(), GameConstants.getPipNumberLabelHeight());
	}
}
