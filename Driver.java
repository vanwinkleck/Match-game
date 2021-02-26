import controller.MatchController;
import model.Deck;
import view.Table;

public class Driver
 {
	public static void main(String[] args)
	{

		Deck deck = new Deck();
		Table cardTable = new Table();
		MatchController gameController = new MatchController(deck, cardTable);
		gameController.startNewGame();

	}
}
