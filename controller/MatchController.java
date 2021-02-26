package controller;

import model.*;
import view.Table;

public class MatchController
{

	protected Deck deck;
	protected Table cardTable;

	public MatchController(Deck deck, Table cardTable)
	 	{

			this.deck = deck;
			this.cardTable = cardTable;

		}

	public void startNewGame()
	 	{
			cardTable.prepare(deck);
		}

}
