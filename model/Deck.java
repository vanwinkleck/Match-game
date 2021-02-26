package model;


import java.util.*;
import java.util.Random;

public class Deck implements Iterable<Card>
{
	public ArrayList<Card> cards;
	Random randomizer;
	public int rows;
	public int cols;

	public Iterator<Card> iterator()
	{
		return cards.iterator();
	}

	private void generateCards(int diff)
	{
		cards = new ArrayList<Card>();
		int value;
		for (int index = 0; index < diff; index++)
		{
			value = ((index) % (diff / 2)) + 1;
			cards.add(new Card(index+1,value));
		}
		this.shuffle();
	}

	public Deck()
	 	{
			randomizer = new Random();
		}

	public void shuffle()
	{
		for (int i = 0; i < cards.size(); i++)
		{
			int swap_with = randomizer.nextInt(cards.size());
			Card a = cards.get(i);
			cards.set(i, cards.get(swap_with));
			cards.set(swap_with, a);
		}
	}

	/*
	 * public void easyDeck() {
	 *
	 * }
	 *
	 * public void mediumDeck() { generateCards(12); this.rows = 3; this.cols = 4; }
	 *
	 * public void hardDeck() { generateCards(16); this.rows = 4; this.cols = 4; }
	 */

	public void start(int numberOfCards)
	 {
		this.rows = numberOfCards/4;
		this.cols = 4;
		generateCards(numberOfCards);

	}
}
