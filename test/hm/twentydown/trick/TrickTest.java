package hm.twentydown.trick;

import hm.twentydown.card.Card;
import static hm.twentydown.card.Kind.*;
import hm.twentydown.card.Suit;
import static hm.twentydown.card.Suit.*;
import hm.twentydown.player.Player;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TrickTest {
    private List<Player> players;
    private PlayerSpy spyA;
    private PlayerSpy spyB;
    private Suit trumpSuit;
    private Suit followSuit;
    private Suit otherSuit;
    private Trick trick;

    @Before
    public void setUp() throws Exception {
        spyA = new PlayerSpy("A");
        spyB = new PlayerSpy("B");
        players = Arrays.asList(spyA, spyB);
        trumpSuit = SPADES;
        followSuit = CLUBS;
        otherSuit = DIAMONDS;
        trick = new Trick(players, trumpSuit);
    }

    @Test
    public void newTrickIsNotFinished() {
        assertFalse(trick.isFinished());
    }

    @Test
    public void newTrickIsNumberOne() {
        assertEquals(1, trick.getNumber());
    }

    @Test
    public void nextTrickIsNumberTwo() {
        assertEquals(2, trick.makeNext().getNumber());
    }

    @Test
    public void nextOfNextTrickIsNumberThree() {
        assertEquals(3, trick.makeNext().makeNext().getNumber());
    }

    @Test
    public void trickOneIsNotLast() {
        assertFalse(trick.isLast());
    }

    @Test
    public void trickFourIsNotLast() {
        assertFalse(trick.makeNext().makeNext().makeNext().isLast());
    }

    @Test
    public void trickFiveIsLast() {
        assertTrue(trick.makeNext().makeNext().makeNext().makeNext().isLast());
    }

    @Test
    public void trickIsFinishedAfterAllPlayersPlayed() {
        for (Player player : players)
            trick.play(player, new Card(trumpSuit, ACE));
        assertTrue(trick.isFinished());
    }

    @Test
    public void trickIsNotFinishedBeforeAllPlayersPlayed() {
        trick.play(players.get(0), new Card(trumpSuit, ACE));
        assertFalse(trick.isFinished());
    }

    @Test
    public void onlyTrumpSuitCardWins() {
        trick.play(spyA, new Card(followSuit, ACE));
        trick.play(spyB, new Card(trumpSuit, SEVEN));
        assertFalse(trick.isWinner(spyA));
        assertTrue(trick.isWinner(spyB));
    }

    @Test
    public void givenNoTrumpSuitCard_onlyFollowSuitCardWins() {
        trick.play(spyA, new Card(followSuit, SEVEN));
        trick.play(spyB, new Card(otherSuit, ACE));
        assertTrue(trick.isWinner(spyA));
        assertFalse(trick.isWinner(spyB));
    }

    @Test
    public void givenTwoTrumpSuitCards_winnerIsTheHighestKind() {
        trick.play(spyA, new Card(trumpSuit, KING));
        trick.play(spyB, new Card(trumpSuit, ACE));
        assertFalse(trick.isWinner(spyA));
        assertTrue(trick.isWinner(spyB));
    }

    @Test
    public void givenTwoFollowSuitCards_winnerIsTheHighestKind() {
        trick.play(spyA, new Card(followSuit, ACE));
        trick.play(spyB, new Card(followSuit, KING));
        assertTrue(trick.isWinner(spyA));
        assertFalse(trick.isWinner(spyB));
    }

    @Test
    public void afterEachPlay_eachPlayerObservesTheNewState() {
        trick.play(spyA, new Card(followSuit, ACE));
        assertEquals(trick, spyA.visitedTrick);
        assertEquals(trick, spyB.visitedTrick);
    }

    @Test
    public void trickReturnsTrumpSuit() {
        assertEquals(trumpSuit, trick.getTrumpSuit());
    }

    @Test
    public void hasNoFollowSuitBeforeFirstPlay() {
        assertFalse(trick.hasFollowSuit());
    }

    @Test
    public void hasFollowSuitAfterFirstPlay() {
        trick.play(spyA, new Card(followSuit, ACE));
        assertTrue(trick.hasFollowSuit());
    }

    @Test
    public void followSuitIsTheSuitOfTheFirstCardPlayed() {
        trick.play(spyA, new Card(SPADES, ACE));
        trick.play(spyB, new Card(CLUBS, KING));
        assertEquals(SPADES, trick.getFollowSuit());
    }
}
