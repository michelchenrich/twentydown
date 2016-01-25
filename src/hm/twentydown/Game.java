package hm.twentydown;

import hm.twentydown.card.Card;
import hm.twentydown.card.Suit;
import hm.twentydown.player.Player;
import hm.twentydown.player.Players;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Game {
    private Deck deck;
    private Presenter presenter;
    private Round round;

    public Game(Deck deck, Presenter presenter) {
        this.deck = deck;
        this.presenter = presenter;
        presenter.askForPlayers();
    }

    public void setPlayers(String... names) {
        Players players = makePlayers(names);
        round = new Round(players, deck);
        presenter.askForTrumpSuit(round.getCurrentPlayer());
        presenter.presentScores(players);
    }

    private Players makePlayers(String[] names) {
        return Stream.of(names).map(Player::new).collect(Collectors.toCollection(Players::new));
    }

    public void setTrumpSuit(Suit trumpSuit) {
        presenter.presentTrumpSuit(trumpSuit);
        presenter.askForCard(round.getCurrentPlayer());
    }

    public void play(Card card) {

    }
}
