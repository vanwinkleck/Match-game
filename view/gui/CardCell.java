package view.gui;

import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import model.Card;

public class CardCell extends JButton
{

	protected Card card;
	private int value;
	private URL defaultImage;

	public CardCell(Card c)
	{
		this.card = c;
		this.value = c.getValue();
		this.defaultImage = getClass().getResource("/view/images/question.png");
		this.setIcon(new ImageIcon(this.defaultImage));
	}

	public void setOnHit(ActionListener l)
	 {
		this.addActionListener(l);
	}

	public Card getCard()
	{
		return this.card;
	}

	public URL getDefaultImage()
	{
		return defaultImage;
	}

	public void setDefaultImage(URL defaultImage)
	{
		this.defaultImage = defaultImage;
	}

	public int getValue()
	 {
		return value;
	}

}
