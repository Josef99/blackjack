import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class BlackJack{
    private class Card{
        String value;
        String type;

        Card(String value, String type){
            this.value = value;
            this.type = type;
        }

        public String toString() {
            return value + "-" + type;
        }
        public int getValue() {
            if ("AJQK".contains(value)){
                if(value.equals("A")){
                    return 11;
                }
                return 10;
            }
            return Integer.parseInt(value); //for number 2 to 10
        }

        public boolean isAce(){
            return value.equals("A");
        }

        public String getImagePath() {
            return "/cards/" + toString() + ".png";
        }
    }


    ArrayList<Card> deck;
    Random random = new Random(); //for the shuffling

    //dealer
    Card hiddenCard;
    ArrayList<Card> dealerHand;
    int dealerSum;
    int dealerAces;
    int playerWins = 0;
    int dealerWins = 0;
    //player
    ArrayList<Card> playerHand;
    int playerSum;
    int playerAces;


    //The Board
    int boardWidth = 600;
    int boardHeight = boardWidth;
    int cardWidth = 110;
    int cardHeight = 152;

    JFrame frame = new JFrame("Josefs Black Jack");
    JPanel gamePanel = new JPanel() {
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);

            try {
                //draw hidden card
                Image hiddenCardImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/cards/BACK.png"))).getImage();
                if(!stayButton.isEnabled()){
                    hiddenCardImg = new ImageIcon(Objects.requireNonNull(getClass().getResource(hiddenCard.getImagePath()))).getImage();
                }
                g.drawImage(hiddenCardImg, 20, 20, cardWidth, cardHeight, null);

                //drawing dealer's hand
                for(int i = 0; i < dealerHand.size(); i++){
                    Card card = dealerHand.get(i);
                    Image cardImg = new ImageIcon(Objects.requireNonNull(getClass().getResource(card.getImagePath()))).getImage();
                    g.drawImage(cardImg, cardWidth + 25 + (cardWidth - 70)*i, 20, cardWidth, cardHeight, null);
                }
                //drawing player's hand
                for(int i = 0; i < playerHand.size(); i++){
                    Card card = playerHand.get(i);
                    Image cardImg = new ImageIcon(Objects.requireNonNull(getClass().getResource(card.getImagePath()))).getImage();
                    g.drawImage(cardImg,  20 + (cardWidth - 55)*i, 320, cardWidth, cardHeight, null);
                }

                if(!stayButton.isEnabled()){
                    dealerSum = reduceDealerAce();
                    playerSum = reducePlayerAce();
                    System.out.println("STAY: ");
                    System.out.print(dealerSum);
                    System.out.println(playerSum);

                    String message;
                    if(playerSum > 21){
                        message = "You Lose!";
                        dealerWins++;
                    }else if(dealerSum > 21) {
                        message = "You Win!";
                        playerWins++;
                    } else if(playerSum == dealerSum){
                        message = "Tie!";
                    } else if(playerSum > dealerSum){
                        message = "You Win!";
                        playerWins++;
                    } else {
                        message = "You Lose!";
                        dealerWins++;
                    }
                    g.setFont(new Font("Arial", Font.PLAIN, 30));
                    g.setColor(Color.black);
                    g.drawString(message, 220, 250);
                }
                    //Players/Dealers wins
                    g.setFont(new Font("Arial", Font.BOLD, 10));
                g.setColor(Color.black);
                g.drawString("Player Wins: " + playerWins, boardWidth - 150, 30);
                g.drawString("Dealer Wins: " + dealerWins, boardWidth - 150, 45);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    };
    JPanel buttonPanel = new JPanel();
    JButton hitButton = new JButton("Hit");
    JButton stayButton = new JButton("Stay");
    JButton newGame = new JButton("New Game");


    BlackJack() {
        deck = new ArrayList<>();
        startGame();

        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(50,90,60));
        frame.add(gamePanel);

        hitButton.setFocusable(false);
        buttonPanel.add(hitButton);
        stayButton.setFocusable(false);
        buttonPanel.add(stayButton);
        newGame.setFocusable(false);
        buttonPanel.add(newGame);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        hitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                Card card = deck.remove(deck.size() - 1);
                playerSum += card.getValue();
                playerAces += card.isAce() ? 1 : 0;
                playerHand.add(card);

                if (reducePlayerAce() > 21){
                    hitButton.setEnabled(false);
                }
                gamePanel.repaint();
            }
        });

        stayButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);

                while(dealerSum < 17){
                    Card card = deck.remove(deck.size()-1);
                    dealerSum += card.getValue();
                    dealerAces += card.isAce() ? 1 : 0;
                    dealerHand.add(card);
                }
                newGame.setEnabled(true);
                gamePanel.repaint();
            }
        });


        gamePanel.repaint();

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hitButton.setEnabled(true);
                stayButton.setEnabled(true);
                newGame.setEnabled(false);
                playerHand.clear();
                dealerHand.clear();
                startGame();
                gamePanel.repaint();
            }
        });

    }



    public void startGame(){
        //deck
        buildDeck();
        shuffleDeck();
        //dealer
        dealerHand = new ArrayList<Card>();
        dealerSum = 0;
        dealerAces = 0;

        hiddenCard = deck.remove(deck.size()-1); //removes the last card
        dealerSum = hiddenCard.getValue();
        dealerAces += hiddenCard.isAce() ? 1 : 0; //give 1, otherwise 0;

        Card card = deck.remove(deck.size()-1);
        dealerHand.add(card);
        dealerSum += card.getValue();
        dealerAces += card.isAce() ? 1 : 0;

        System.out.println("DEALER: ");
        System.out.println(hiddenCard);
        System.out.println(dealerHand);
        System.out.println(dealerSum);
        System.out.println(dealerAces);

        //player
        playerHand = new ArrayList<Card>();
        playerSum = 0;
        playerAces = 0;

        for(int i = 0; i < 2; i++){
            card = deck.remove(deck.size()-1);
            playerSum += card.getValue();
            playerAces += card.isAce() ? 1 : 0;
            playerHand.add(card);

            System.out.println("PLAYER: ");
            System.out.println(playerHand);
            System.out.println(playerSum);
            System.out.println(playerAces);
        }
    }
    public void buildDeck(){
        new ArrayList<Card>();
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] types = {"C", "D", "H", "S"};
        for (String type : types) {
            for (String value : values) {
                Card card = new Card(value, type);
                deck.add(card);
            }
        }

    }

    public void shuffleDeck(){
        for(int i=0; i<deck.size(); i++){
            int j = random.nextInt(deck.size());
            Card currentCard = deck.get(i);
            Card randomCard = deck.get(j);
            deck.set(i, randomCard);
            deck.set(j, currentCard);
        }

    }

    public int reducePlayerAce(){
        while(playerSum > 21 && playerAces > 0){
            playerSum -= 10;
            playerAces -= 1;
        }
        return playerSum;
    }

    public int reduceDealerAce(){
        while(dealerSum > 21 && dealerAces > 0){
            dealerSum -= 10;
            dealerAces -=1;
        }
        return dealerSum;
    }
}