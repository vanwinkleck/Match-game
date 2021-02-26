package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import controller.GameUserInterface;
import model.Card;
import model.Deck;
import model.GameLevel;
import view.gui.CardCell;

public class Table implements GameUserInterface, ActionListener
{
	private JFrame mainFrame;
	private JPanel cardTable;
	private JLabel timerLabel;
	private JButton start;
	private JPanel container;
	private JPanel controlPanel;
	private JPanel resultPanel;
	private JButton startPlay;
	private JComboBox<GameLevel> levels;
	int count;
	private CardCell cardsPicked = null;
	private Deck deck;
	private int numberOfPickedCards = 0;
	private JLabel resultLabel;

	public Table()
	{

	}

	public void prepare(Deck deck)
	 {
		this.deck = deck;

		container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		// container.setBackground(Color.BLUE);
		// .setIconImage();
		controlPanel = new JPanel();
		controlPanel.setPreferredSize(new Dimension(800, 100));
		//controlPanel.setBackground(Color.BLUE);
		initializeLevelsComboBox();
		initilizeStartPlayButton();

		container.add(controlPanel);
		initializeMainFram();

	}

	private void initializeMainFram()
	{
		mainFrame = new JFrame("Match");
	//	mainFrame.setContentPane(new JLabel(new ImageIcon(getClass().getResource("/images/bengals.png"))));
		mainFrame.setPreferredSize(new Dimension(800, 800));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setBackground(Color.WHITE);
		mainFrame.add(container);
		mainFrame.pack();
		mainFrame.setVisible(true);

	}

	private void initiaizeCardTable(int numberOfCards)
	{

		cardTable = new JPanel();
		cardTable.setOpaque(false);
		cardTable.setBackground(Color.GREEN);
		cardTable.setLayout(new GridLayout(numberOfCards / 4, 4));
		cardTable.setVisible(true);
		container.add(cardTable);
	}

	private void initializeLevelsComboBox()
	 {
		levels = new JComboBox<GameLevel>(getOptions());
		levels.setPreferredSize(new Dimension(100, 30));
		controlPanel.add(levels);
	}

	private void initilizeStartPlayButton()
	 {
		startPlay = new JButton("Start");
		startPlay.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(resultLabel != null)
				{
					resultLabel.setText("");
				}
				numberOfPickedCards= 0;
				timerLabel = new JLabel("Timer");
				controlPanel.add(timerLabel);
				startPlay.setVisible(false);
				levels.setVisible(false);
				int numberOfCards = getNumberOfCards();

				deck.start(numberOfCards);
				initiaizeCardTable(numberOfCards);
				showCard(deck);
				beforeStartGame();
				startCutDownTimer();

			}

		});
		controlPanel.add(startPlay);
	}


	public int getNumberOfCards()
	{
		Object selectedItem = levels.getSelectedItem();
		GameLevel gameLevel = (GameLevel) selectedItem;
		return gameLevel.getNumberOfCards();
	}

	public GameLevel getSelectedLevel()
	 {
		GameLevel gameLevel = (GameLevel) levels.getSelectedItem();
		return gameLevel;
	}

	private GameLevel[] getOptions()
	{
		GameLevel[] arr = new GameLevel[3];
		arr[0] = GameLevel.EASY;
		arr[1] = GameLevel.MEDIUM;
		arr[2] = GameLevel.HARD;
		return arr;
	}

	public void setOnStart(ActionListener l)
	 {
		start.addActionListener(l);
	}


	@Override
	public void showCard(Deck deck)
	{
		Iterator<Card> handIter = deck.iterator();

		while (handIter.hasNext())
		{
			Card c = handIter.next();
			CardCell cell = new CardCell(c);
			cell.addActionListener(this);
			cell.setPreferredSize(new Dimension(30, 120));
			cardTable.add(cell);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	 {
		java.util.Timer timer = null;

		CardCell cell = (CardCell) e.getSource();



		if (cardsPicked == null)
		 {
			cell.setIcon(new ImageIcon(getClass().getResource(cell.getCard().getImage())));
			int cardValue = cell.getCard().getValue();
			System.out.println(cardValue);
			cardsPicked = cell;
		}
		else
		{
			if(cell.getCard().getId() == cardsPicked.getCard().getId())
			 {
				return;
				}

			cell.setIcon(new ImageIcon(getClass().getResource(cell.getCard().getImage())));
			int cardValue = cell.getCard().getValue();
			System.out.println(cardValue);

			if (cardsPicked.getCard().getValue() == cardValue)
			{ // if matched
				cell.setVisible(false); // current card
				cardsPicked.setVisible(false); // previous card selected
				cardsPicked = null;
				numberOfPickedCards += 2;
			} else { // if not matched

				timer = new java.util.Timer();
				timer.schedule(new TimerTask()
				 {

					@Override
					public void run()
					{
						cell.setIcon(new ImageIcon(cell.getDefaultImage())); // current card
						cardsPicked.setIcon(new ImageIcon((cardsPicked.getDefaultImage())));// previous card selected
						cardsPicked = null;
					}

				}, 500);

			}

		}

		if (numberOfPickedCards == getNumberOfCards())
		{
			sucessMessage();
		}
	}


	private void showAll()
	{
		Component[] componentsArray = cardTable.getComponents();
		for(Component component: componentsArray )
		{
			CardCell cardCell = (CardCell)component;
			cardCell.setIcon(new ImageIcon(getClass().getResource(cardCell.getCard().getImage())));
		}
	}

	private void hideAll()
	{
		Component[] componentsArray = cardTable.getComponents();
		for(Component component: componentsArray )
		 {
			CardCell cardCell = (CardCell)component;
			cardCell.setIcon(new ImageIcon(cardCell.getDefaultImage()));
		}
	}

	private void failedPlayer()
	 {
		cardTable.setVisible(false);
		levels.setVisible(true);
		startPlay.setVisible(true);
		timerLabel.setVisible(false);

		resultLabel = new JLabel("You have failed! Timed out");
		resultPanel = new JPanel();
		resultPanel.removeAll();
		resultPanel.add(resultLabel);
		container.add(resultPanel);

	}

	private void sucessMessage()
	 {
		cardTable.setVisible(false);
		levels.setVisible(true);
		startPlay.setVisible(true);
		timerLabel.setVisible(false);
		int score = getSelectedLevel().getLevel() * 60 - count;
		resultLabel = new JLabel("You finished successfully in " + score + "seconds!");
		resultPanel = new JPanel();
		resultPanel.removeAll();
		resultPanel.add(resultLabel);
		container.add(resultPanel);

	}

	private void startCutDownTimer()
	 {
		count = getSelectedLevel().getLevel() * 60;
		Timer countDownTimer = new Timer(1000, new ActionListener()
		 {
			@Override
			public void actionPerformed(ActionEvent e)
			 {
				count--;
				if (count >= 0) {
					timerLabel.setText(count + "");
				} else {
					((Timer) (e.getSource())).stop();
					failedPlayer();
				}
			}

		});
		countDownTimer.setInitialDelay(0);
		countDownTimer.start();
	}
	/**
	 * <p>before start game we should show all cards with their location  within 4 seconds</p>
	 *
	 * */
	private void beforeStartGame()
	{
		showAll();

		Component[] componentsArray = cardTable.getComponents();
		for(Component component: componentsArray )
		 {
			CardCell cardCell = (CardCell)component;
			cardCell.setEnabled(false);
		}


		java.util.Timer timer = new java.util.Timer();
		timer.schedule(new TimerTask()
		 {

			@Override
			public void run()
			 {
				hideAll();

				Component[] componentsArray = cardTable.getComponents();
				for(Component component: componentsArray )
				 {
					CardCell cardCell = (CardCell)component;
					cardCell.setEnabled(true);
				}
			}

		}, 4000);
	}

}
