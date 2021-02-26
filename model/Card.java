package model;

public class Card
{
	private int id;
	private int value;
	private String image;

	public Card(int value)
	{
		super();
		this.value = value;
		this.image = Utils.map.get(value);
	}

	public Card(int id, int value)
	{
		this.id = id;
		this.value = value;
		this.image = Utils.map.get(value);
	}

	public int getValue()
	 {
		return value;
	}

	public void setValue(int value)
	 {
		this.value = value;
	}

	public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}




}
