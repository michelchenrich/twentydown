package hm.twentydown.trick;

import hm.twentydown.card.Card;
import hm.twentydown.card.Suit;
import hm.twentydown.player.Player;

import java.util.List;

public class Trick {
    private int number;
    private List<Player> players;
    private Suit trumpSuit;
    private TrickCards cards;

    public Trick(List<Player> players, Suit trumpSuit) {
        this(1, players, trumpSuit);
    }

    private Trick(int number, List<Player> players, Suit trumpSuit) {
        this.number = number;
        this.players = players;
        this.trumpSuit = trumpSuit;
        cards = new TrickCards();
    }

    public boolean isWinner(Player player) {
        return cards.isWinner(player, trumpSuit);
    }

    public boolean isFinished() {
        return cards.size() == players.size();
    }

    public int getNumber() {
        return number;
    }

    public Trick makeNext() {
        return new Trick(number + 1, players, trumpSuit);
    }

    public void play(Player player, Card card) {
        cards.add(new TrickCard(player, card));
        updatePlayers();
    }

    private void updatePlayers() {
        for (Player player : players)
            player.visit(this);
    }

    public boolean isLast() {
        return number == 5;
    }

    public Suit getTrumpSuit() {
        return trumpSuit;
    }

    public Suit getFollowSuit() {
        return cards.getFollowSuit();
    }

    public boolean hasFollowSuit() {
        return !cards.isEmpty();
    }
}
