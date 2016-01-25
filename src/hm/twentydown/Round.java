package hm.twentydown;

import hm.twentydown.card.Card;
import hm.twentydown.card.Suit;
import hm.twentydown.player.Player;
import hm.twentydown.player.Players;
import hm.twentydown.trick.Trick;

import java.util.ArrayList;
import java.util.List;

public class Round {
    private int number;
    private Deck deck;
    private Players players;
    private int currentPlayerIndex;
    private List<Trick> tricks;
    private Trick trick;
    private Suit trumpSuit;

    public Round(Players players, Deck deck) {
        this(1, players, deck);
    }

    private Round(int number, Players players, Deck deck) {
        this.players = players;
        this.number = number;
        this.deck = deck;
        currentPlayerIndex = 0;
        tricks = new ArrayList<>();
        trick = new Trick(players, Suit.SPADES);
        tricks.add(trick);
        players.drawFrom(deck);
        players.drawFrom(deck);
    }

    public boolean isFinished() {
        return trick.isLast() && trick.isFinished();
    }

    public int getNumber() {
        return number;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void play(Card card) {
        trick.play(card);
        currentPlayerIndex++;
        if (currentPlayerIndex == players.size()) {
            currentPlayerIndex = 0;
            if (!trick.isLast()) {
                trick = trick.makeNext();
                tricks.add(trick);
            }
        }
    }

    public Round makeNext() {
        return new Round(number + 1, players.rotate(), deck);
    }

    public void setTrumpSuit(Suit trumpSuit) {
        this.trumpSuit = trumpSuit;
        players.drawFrom(deck);
        players.drawFrom(deck);
        players.drawFrom(deck);
    }

    public Suit getTrumpSuit() {
        return trumpSuit;
    }
}
