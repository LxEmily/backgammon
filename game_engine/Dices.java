package game_engine;

import constants.DieInstance;
import constants.GameConstants;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * This class represents a HBox of dices.
 * 
 * @teamname TeaCup
 * @author Bryan Sng, 17205050
 * @author @LxEmily, 17200573
 *
 */
public class Dices extends HBox implements ColorParser {
	private Dice[] dices;
	private Color color;
	
	/**
	 * Default Constructor
	 * 		- Initialize the dices array with two dices.
	 * @param color of dices
	 */
	public Dices(Color color) {
		this(color, 2);
	}
	
	/**
	 * Overloaded Constructor
	 * 		- Initialize the dices array with any number of dices.
	 * 		- Set this node's alignment.
	 * @param color of dices.
	 * @param numberOfDices number of dices. 
	 */
	public Dices(Color color, int numberOfDices) {
		super();
		this.color = color;
		dices = new Dice[numberOfDices*2];
		setAlignment(Pos.CENTER);
		setSpacing(GameConstants.getDiceSize().getWidth() / 4.0);
		initDices();
		drawDices(DieInstance.DOUBLE);
	}
	
	/**
	 * Initialize the individual dices and assign them into the dices array.
	 */
	private void initDices() {
		for (int i = 0; i < dices.length; i++) {
			dices[i] = new Dice(color);
		}
	}
	
	/**
	 * Draw the die to the board, provided this HBox is drawn as well.
	 * @param instance instance where the dices are single, double or default.
	 */
	private void drawDices(DieInstance instance) {
		getChildren().clear();
		int numDices = getNumDices(instance);
		for (int i = 0; i < numDices; i++) {
			getChildren().add(dices[i]);
		}
	}
	
	/**
	 * Returns an array of integers, containing the result of each dice roll.
	 * @return result of each dice roll in terms of an array of integers.
	 */
	public int[] getTotalRoll(DieInstance instance) {
		int numDices = getNumDices(instance);
		int[] res = new int[numDices];
		for (int i = 0; i < numDices; i++) {
			res[i] = dices[i].roll();
			dices[i].draw();
		}
		drawDices(instance);
		
		if (isDouble(res)) {
			res = addDoubleDie(res);
		}
		return res;
	}
	
	// checks if result of die roll is a double instance.
	private boolean isDouble(int[] res) {
		boolean isDouble = true;
		
		// can't be double if only 1 dice.
		if (res.length > 1) {
			for (int i = 0; i < res.length-1; i++) {
				if (res[i] != res[i+1]) {
					isDouble = false;
					break;
				}
			}
		} else {
			isDouble = false;
		}
		
		return isDouble;
	}
	
	// upon calling, this method should double current cube objects in dices.
	private int[] addDoubleDie(int[] res) {
		int[] newRes = new int[res.length*2];
		for (int i = 0; i < newRes.length; i++) {
			newRes[i] = res[0];
			dices[i].draw(newRes[i]);
		}
		drawDices(DieInstance.DOUBLE);
		return newRes;
	}
	
	/**
	 * Returns the number of dices based on the die instance.
	 * @param instance instance where the dices are single, double or default.
	 * @return number of dices.
	 */
	private int getNumDices(DieInstance instance) {
		int numDices = 0;
		switch (instance) {
			case SINGLE:
				numDices = 1;
				break;
			case DOUBLE:
				numDices = dices.length;
				break;
			case DEFAULT:
				numDices = dices.length/2;
				break;
		}
		return numDices;
	}
}
