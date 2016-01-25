package hm.twentydown.trick;

import hm.twentydown.FixedOrderDeck;
import hm.twentydown.card.Card;
import static hm.twentydown.card.Kind.*;
import hm.twentydown.card.Suit;
import static hm.twentydown.card.Suit.*;
import hm.twentydown.doubles.PlayerSpy;
import hm.twentydown.player.Player;
import hm.twentydown.player.Players;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TrickTest {
    private Players players;
    private PlayerSpy playerASpy;
    private PlayerSpy playerBSpy;
    private Suit trumpSuit;
    private Suit followSuit;
    private Suit otherSuit;
    private Trick trick;

    private Trick nextTrick(Trick original) {
        players.drawFrom(new FixedOrderDeck());
        for (Player player : players)
            original.play(player.getCards().getFirst());
        return original.makeNext();
    }

    @Before
    public void setUp() throws Exception {
        playerASpy = new PlayerSpy("A");
        playerBSpy = new PlayerSpy("B");
        players = new Players(playerASpy, playerBSpy);
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
        assertEquals(2, nextTrick(trick).getNumber());
    }

    @Test
    public void nextOfNextTrickIsNumberThree() {
        assertEquals(3, nextTrick(nextTrick(trick)).getNumber());
    }

    @Test
    public void trickOneIsNotLast() {
        assertFalse(trick.isLast());
    }

    @Test
    public void trickFourIsNotLast() {
        assertFalse(nextTrick(nextTrick(nextTrick(trick))).isLast());
    }

    @Test
    public void trickFiveIsLast() {
        assertTrue(nextTrick(nextTrick(nextTrick(nextTrick(trick)))).isLast());
    }

    @Test
    public void trickIsFinishedAfterAllPlayersPlayed() {
        for (Player ignored : players)
            trick.play(new Card(trumpSuit, ACE));
        assertTrue(trick.isFinished());
    }

    @Test
    public void trickIsNotFinishedBeforeAllPlayersPlayed() {
        trick.play(new Card(trumpSuit, ACE));
        assertFalse(trick.isFinished());
    }

    @Test
    public void firstPlayerIsCurrentInNewTrick() {
        assertEquals(playerASpy, trick.getCurrentPlayer());
    }

    @Test
    public void afterAPlayer_theNextPlayerPlays() {
        trick.play(new Card(followSuit, ACE));
        assertEquals(playerBSpy, trick.getCurrentPlayer());
    }

    @Test
    public void trickPlayRemovesTheCardFromTheCurrentPlayersHand() {
        playerASpy.drawFrom(new FixedOrderDeck());

        trick.play(playerASpy.getCards().getFirst());

        assertEquals(0, playerASpy.getCards().size());
    }

    @Test
    public void givenAIsWinnerOfATrick_AStartsPlayingInTheNext() {
        trick.play(new Card(trumpSuit, ACE));
        trick.play(new Card(otherSuit, SEVEN));
        assertEquals(playerASpy, trick.makeNext().getCurrentPlayer());
    }

    @Test
    public void givenBIsWinnerOfATrick_BStartsPlayingInTheNext() {
        trick.play(new Card(followSuit, ACE));
        trick.play(new Card(trumpSuit, ACE));
        assertEquals(playerBSpy, trick.makeNext().getCurrentPlayer());
    }

    @Test
    public void onlyTrumpSuitCardWins() {
        trick.play(new Card(followSuit, ACE));
        trick.play(new Card(trumpSuit, SEVEN));
        assertFalse(trick.isWinner(playerASpy));
        assertTrue(trick.isWinner(playerBSpy));
    }

    @Test
    public void givenNoTrumpSuitCard_onlyFollowSuitCardWins() {
        trick.play(new Card(followSuit, SEVEN));
        trick.play(new Card(otherSuit, ACE));
        assertTrue(trick.isWinner(playerASpy));
        assertFalse(trick.isWinner(playerBSpy));
    }

    @Test
    public void givenTwoTrumpSuitCards_winnerIsTheHighestKind() {
        trick.play(new Card(trumpSuit, KING));
        trick.play(new Card(trumpSuit, ACE));
        assertFalse(trick.isWinner(playerASpy));
        assertTrue(trick.isWinner(playerBSpy));
    }

    @Test
    public void givenTwoFollowSuitCards_winnerIsTheHighestKind() {
        trick.play(new Card(followSuit, ACE));
        trick.play(new Card(followSuit, KING));
        assertTrue(trick.isWinner(playerASpy));
        assertFalse(trick.isWinner(playerBSpy));
    }

    @Test
    public void afterEachPlay_eachPlayerObservesTheNewState() {
        trick.play(new Card(followSuit, ACE));
        assertEquals(trick, playerASpy.visitedTrick);
        assertEquals(trick, playerBSpy.visitedTrick);
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
        trick.play(new Card(followSuit, ACE));
        assertTrue(trick.hasFollowSuit());
    }

    @Test
    public void followSuitIsTheSuitOfTheFirstCardPlayed() {
        trick.play(new Card(SPADES, ACE));
        trick.play(new Card(CLUBS, KING));
        assertEquals(SPADES, trick.getFollowSuit());
    }
}
