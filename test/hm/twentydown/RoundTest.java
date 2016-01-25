package hm.twentydown;

import hm.twentydown.card.Card;
import static hm.twentydown.card.Kind.ACE;
import static hm.twentydown.card.Kind.KING;
import static hm.twentydown.card.Suit.CLUBS;
import static hm.twentydown.card.Suit.SPADES;
import hm.twentydown.player.Player;
import hm.twentydown.player.Players;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class RoundTest {
    private Player playerA;
    private Player playerB;
    private Player playerC;
    private Round round;
    private Deck deck;

    @Before
    public void setUp() {
        playerA = new Player("A");
        playerB = new Player("B");
        playerC = new Player("C");
        deck = new FixedOrderDeck();
        round = new Round(new Players(playerA, playerB, playerC), deck);
    }

    @Test
    public void newRoundStartsByHavingEachPlayerDrawTwoCards() {
        assertEquals(2, playerA.getCards().size());
        assertEquals(2, playerB.getCards().size());
        assertEquals(2, playerC.getCards().size());
    }

    @Test
    public void newRoundHasNoTrumpSuit() {
        assertNull(round.getTrumpSuit());
    }

    @Test
    public void afterTrumpSuitSelection_eachPlayerMustDrawMoreThreeCards() {
        round.setTrumpSuit(SPADES);
        assertEquals(5, playerA.getCards().size());
        assertEquals(5, playerB.getCards().size());
        assertEquals(5, playerC.getCards().size());
    }

    @Test
    public void afterTrumpSuitSelection_roundCanReturnIt() {
        round.setTrumpSuit(SPADES);
        assertEquals(SPADES, round.getTrumpSuit());
    }

    @Test
    public void nextRoundHasNoTrumpSuit() {
        round.setTrumpSuit(SPADES);
        assertNull(round.makeNext().getTrumpSuit());
    }

    @Test
    public void nextRound_trumpSuitSelection() {
        round.setTrumpSuit(SPADES);
        round = round.makeNext();
        round.setTrumpSuit(CLUBS);
        assertEquals(CLUBS, round.getTrumpSuit());
    }

    @Test
    public void newRoundIsNotFinished() {
        assertFalse(round.isFinished());
    }

    @Test
    public void newRoundIsNumberOne() {
        assertEquals(1, round.getNumber());
    }

    @Test
    public void newRoundStartsWithFirstPlayer() {
        assertEquals(playerA, round.getCurrentPlayer());
    }

    @Test
    public void afterOnePlay_secondPlayerIsCurrent() {
        round.play(new Card(SPADES, ACE));
        assertEquals(playerB, round.getCurrentPlayer());
    }

    @Test
    public void afterTwoPlays_thirdPlayerIsCurrent() {
        round.play(new Card(SPADES, ACE));
        round.play(new Card(SPADES, KING));
        assertEquals(playerC, round.getCurrentPlayer());
    }

    @Test
    public void nextRoundIsNumberTwo() {
        assertEquals(2, round.makeNext().getNumber());
    }

    @Test
    public void nextRoundStartsWithSecondPlayer() {
        assertEquals(playerB, round.makeNext().getCurrentPlayer());
    }

    @Test
    public void nextOfNextRoundIsNumberThree() {
        assertEquals(3, round.makeNext().makeNext().getNumber());
    }

    @Test
    public void nextOfNextRoundStartsWithThirdPlayer() {
        assertEquals(playerC, round.makeNext().makeNext().getCurrentPlayer());
    }

    @Test
    public void beforeFiveTricksAreFinished_theRoundIsNotFinished() {
        for (int trickPlay = 0; trickPlay < 3; trickPlay++)
            for (int trickNumber = 0; trickNumber < 5; trickNumber++)
                if (trickNumber == 4 && trickPlay == 2)
                    break;
                else
                    round.play(deck.take());
        assertFalse(round.isFinished());
    }

    @Test
    public void afterFiveTricksAreFinished_theRoundIsFinished() {
        for (int trickPlay = 0; trickPlay < 3; trickPlay++)
            for (int trickNumber = 0; trickNumber < 5; trickNumber++)
                round.play(deck.take());
        assertTrue(round.isFinished());
    }
}
