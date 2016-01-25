package hm.twentydown.player;

import hm.twentydown.Deck;
import hm.twentydown.card.Card;
import hm.twentydown.trick.Trick;

public class Player {
    private String name;
    private PlayerCards cards;
    private int score;

    public Player(String name) {
        this.name = name;
        score = 20;
        cards = new PlayerCards();
    }

    public String getName() {
        return name;
    }

    public boolean equals(Object other) {
        return other instanceof Player && equalsPlayer((Player) other);
    }

    private boolean equalsPlayer(Player other) {
        return name.equals(other.name);
    }

    public PlayerCards getCards() {
        return cards;
    }

    public void drawFrom(Deck deck) {
        cards.add(PlayerCard.makeNotPlayable(deck.take()));
    }

    public void drop(Card card) {
        cards.remove(card);
    }

    public void updateCards(Trick trick) {
        cards = cards.update(trick);
    }

    public int getScore() {
        return score;
    }

    public void updateScore(Iterable<Trick> tricks) {
        int wins = countWins(tricks);
        score = wins > 0 ? score - wins : score + 5;
    }

    private int countWins(Iterable<Trick> tricks) {
        int wins = 0;
        for (Trick trick : tricks)
            if (trick.isWinner(this))
                wins++;
        return wins;
    }
}
