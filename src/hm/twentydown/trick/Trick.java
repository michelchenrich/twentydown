package hm.twentydown.trick;

import hm.twentydown.card.Card;
import hm.twentydown.card.Suit;
import hm.twentydown.player.Player;
import hm.twentydown.player.Players;

public class Trick {
    private int number;
    private Players players;
    private Suit trumpSuit;
    private TrickCards cards;

    public Trick(Players players, Suit trumpSuit) {
        this(1, players, trumpSuit);
    }

    private Trick(int number, Players players, Suit trumpSuit) {
        this.number = number;
        this.players = players;
        this.trumpSuit = trumpSuit;
        cards = new TrickCards(trumpSuit);
    }

    public int getNumber() {
        return number;
    }

    public Player getCurrentPlayer() {
        return players.getFirst();
    }

    public Suit getTrumpSuit() {
        return trumpSuit;
    }

    public Suit getFollowSuit() {
        return cards.getFollowSuit();
    }

    public void play(Card card) {
        Player player = getCurrentPlayer();
        player.drop(card);
        cards.add(new TrickCard(player, card));
        players = players.rotate();
        players.updateCards(this);
    }

    public boolean isFinished() {
        return cards.size() == players.size();
    }

    public boolean isLast() {
        return number == 5;
    }

    private Player getWinner() {
        return cards.getWinner();
    }

    public boolean isWinner(Player player) {
        return cards.isWinner(player);
    }

    public boolean hasFollowSuit() {
        return !cards.isEmpty();
    }

    public Trick makeNext() {
        return new Trick(number + 1, players.rotateUntil(getWinner()), trumpSuit);
    }
}
