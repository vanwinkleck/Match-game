package model;

public enum GameLevel {

	EASY(8, "Easy", 3), MEDIUM(12, "Mid", 2), HARD(16, "Hard", 1);

	private int numberOfCards;
	private String title;
	private int level;

	private GameLevel(int numberOfCards, String title, int level)
	{
		this.numberOfCards = numberOfCards;
		this.title = title;
		this.level = level;
	}

	public int getNumberOfCards()
	{
		return numberOfCards;
	}

	public String getTitle()
	{
		return title;
	}

	public int getLevel()
	{
		return level;
	}

}
