package hm.twentydown;

import static hm.twentydown.GameTest.State.*;
import hm.twentydown.card.Card;
import hm.twentydown.card.Kind;
import hm.twentydown.card.Suit;
import hm.twentydown.player.Player;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GameTest {
    private FixedOrderDeck deck;
    private PresenterSpy presenterSpy;
    private Player playerA;
    private Player playerB;

    @Before
    public void setUp() {
        presenterSpy = new PresenterSpy();
        deck = new FixedOrderDeck();
        playerA = new Player("A");
        playerB = new Player("B");
    }

    @Test
    public void acceptance() {
        Game game = new Game(deck, presenterSpy);
        assertEquals(CHOOSE_PLAYERS, presenterSpy.state);

        game.setPlayers("A", "B");
        assertEquals(CHOOSE_TRUMP_SUIT, presenterSpy.state);
        assertEquals(playerA, presenterSpy.currentPlayer);
        assertEquals(playerA, presenterSpy.playersForScores.get(0));
        assertEquals(playerB, presenterSpy.playersForScores.get(1));

        game.setTrumpSuit(Suit.SPADES);
        assertEquals(CHOOSE_CARD, presenterSpy.state);
        assertEquals(Suit.SPADES, presenterSpy.trumpSuit);

        game.play(new Card(Suit.SPADES, Kind.ACE));
    }

    private static class PresenterSpy implements Presenter {
        public Player currentPlayer;
        public State state;
        public List<Player> playersForScores = new ArrayList<>();
        public Suit trumpSuit;

        public void askForPlayers() {
            state = CHOOSE_PLAYERS;
        }

        public void askForTrumpSuit(Player currentPlayer) {
            state = CHOOSE_TRUMP_SUIT;
            this.currentPlayer = currentPlayer;
        }

        public void presentScores(Iterable<Player> players) {
            playersForScores = new ArrayList<>();
            for (Player player : players)
                playersForScores.add(player);
        }

        public void presentTrumpSuit(Suit trumpSuit) {
            this.trumpSuit = trumpSuit;
        }

        public void askForCard(Player currentPlayer) {
            this.state = CHOOSE_CARD;
        }
    }

    public enum State {
        CHOOSE_PLAYERS,
        CHOOSE_TRUMP_SUIT,
        CHOOSE_CARD
    }
}
