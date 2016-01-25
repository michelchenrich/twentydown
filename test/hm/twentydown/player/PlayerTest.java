package hm.twentydown.player;

import hm.twentydown.Deck;
import hm.twentydown.FixedOrderDeck;
import hm.twentydown.card.Card;
import static hm.twentydown.card.Kind.ACE;
import static hm.twentydown.card.Kind.KING;
import hm.twentydown.card.Suit;
import static hm.twentydown.card.Suit.*;
import hm.twentydown.doubles.TrickStub;
import hm.twentydown.trick.Trick;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerTest {
    private Player player;
    private Deck deck;

    private Trick stubEmptyTrick(Suit trumpSuit) {
        TrickStub trickStub = new TrickStub();
        trickStub.setTrumpSuit(trumpSuit);
        return trickStub;
    }

    private Trick stubTrick(Suit trumpSuit, Suit followSuit) {
        TrickStub trickStub = new TrickStub();
        trickStub.setTrumpSuit(trumpSuit);
        trickStub.setFollowSuit(followSuit);
        return trickStub;
    }

    private Trick stubLostTrick() {
        TrickStub trickStub = new TrickStub();
        trickStub.setWinner(new Player("Other player"));
        return trickStub;
    }

    private TrickStub stubWonTrick() {
        TrickStub trickStub = new TrickStub();
        trickStub.setWinner(player);
        return trickStub;
    }

    @Before
    public void setUp() throws Exception {
        player = new Player("Player");
        deck = new FixedOrderDeck();
    }

    @Test
    public void hasName() {
        assertEquals("A", new Player("A").getName());
        assertEquals("B", new Player("B").getName());
    }

    @Test
    public void equalsOtherPlayerWithSameName() {
        assertEquals(new Player("A"), new Player("A"));
    }

    @Test
    public void differsFromOtherPlayerWithDifferentName() {
        assertNotEquals(new Player("A"), new Player("B"));
    }

    @Test
    public void startsHoldingZeroCards() {
        assertEquals(0, player.getCards().size());
    }

    @Test
    public void givenADeck_playerCanDrawFromIt_andHoldMoreCards() {
        player.drawFrom(deck);
        player.drawFrom(deck);
        player.drawFrom(deck);
        assertEquals(3, player.getCards().size());
    }

    @Test
    public void drawnCardsAreTheOnesFromTheDeck() {
        player.drawFrom(deck);
        player.drawFrom(deck);
        assertEquals(new Card(SPADES, ACE), player.getCards().get(0));
        assertEquals(new Card(SPADES, KING), player.getCards().get(1));
    }

    @Test
    public void beforeVisitingTrick_noCardsArePlayable() {
        player.drawFrom(deck);
        player.drawFrom(deck);
        assertFalse(player.getCards().get(0).isPlayable());
        assertFalse(player.getCards().get(1).isPlayable());
    }

    @Test
    public void afterVisitingAnEmptyTrick_allCardsArePlayable() {
        player.drawFrom(deck);
        player.drawFrom(deck);
        player.drawFrom(deck);
        player.drawFrom(deck);
        player.drawFrom(deck);
        player.updateCards(stubEmptyTrick(SPADES));
        assertTrue(player.getCards().get(0).isPlayable());
        assertTrue(player.getCards().get(1).isPlayable());
        assertTrue(player.getCards().get(2).isPlayable());
        assertTrue(player.getCards().get(3).isPlayable());
        assertTrue(player.getCards().get(4).isPlayable());
    }

    @Test
    public void ifThePlayerHasIt_canOnlyPlayFollowSuit() {
        Suit followSuit = CLUBS;
        deck = new FixedOrderDeck(Arrays.asList(
                new Card(SPADES, ACE),
                new Card(followSuit, ACE),
                new Card(DIAMONDS, ACE),
                new Card(HEARTS, ACE)));
        player.drawFrom(deck);
        player.drawFrom(deck);
        player.drawFrom(deck);
        player.drawFrom(deck);
        player.updateCards(stubTrick(SPADES, followSuit));
        assertFalse(player.getCards().get(0).isPlayable());
        assertTrue(player.getCards().get(1).isPlayable());
        assertFalse(player.getCards().get(2).isPlayable());
        assertFalse(player.getCards().get(3).isPlayable());
    }

    @Test
    public void givenNoFollowSuit_allOtherSuitsArePlayable() {
        Suit followSuit = CLUBS;
        deck = new FixedOrderDeck(Arrays.asList(
                new Card(SPADES, ACE),
                new Card(SPADES, KING),
                new Card(DIAMONDS, ACE),
                new Card(HEARTS, ACE)));
        player.drawFrom(deck);
        player.drawFrom(deck);
        player.drawFrom(deck);
        player.drawFrom(deck);
        player.updateCards(stubTrick(SPADES, followSuit));
        assertTrue(player.getCards().get(0).isPlayable());
        assertTrue(player.getCards().get(1).isPlayable());
        assertTrue(player.getCards().get(2).isPlayable());
        assertTrue(player.getCards().get(3).isPlayable());
    }

    @Test
    public void initialScoreIsTwenty() {
        assertEquals(20, player.getScore());
    }

    @Test
    public void winningATrickReducesTheScoreByOne() {
        player.updateScore(stubTricksWithWins(1));
        assertEquals(19, player.getScore());
    }

    @Test
    public void eachWonTrickReducesScoreByOnOne() {
        player.updateScore(stubTricksWithWins(3));
        assertEquals(17, player.getScore());
    }

    @Test
    public void withZeroWonTricks_scoreIsIncreasedByFive() {
        player.updateScore(stubTricksWithWins(0));
        assertEquals(25, player.getScore());
    }

    private List<Trick> stubTricksWithWins(int wins) {
        List<Trick> tricks = new ArrayList<>();
        for (int i = 0; i < wins; i++)
            tricks.add(stubWonTrick());
        for (int i = 0; i < (5 - wins); i++)
            tricks.add(stubLostTrick());
        return tricks;
    }
}
