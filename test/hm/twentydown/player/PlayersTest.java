package hm.twentydown.player;

import hm.twentydown.FixedOrderDeck;
import hm.twentydown.doubles.PlayerSpy;
import hm.twentydown.doubles.TrickStub;
import hm.twentydown.trick.Trick;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

public class PlayersTest {
    public static final int FIRST = 0;
    public static final int MIDDLE = 1;
    public static final int LAST = 2;
    private PlayerSpy playerASpy;
    private PlayerSpy playerBSpy;
    private PlayerSpy playerCSpy;
    private Players players;
    private TrickStub trickStub;

    private void assertPositionsOfABC(int positionOfA, int positionOfB, int positionOfC) {
        assertEquals(playerASpy, players.get(positionOfA));
        assertEquals(playerBSpy, players.get(positionOfB));
        assertEquals(playerCSpy, players.get(positionOfC));
    }

    @Before
    public void setUp() throws Exception {
        playerASpy = new PlayerSpy("A");
        playerBSpy = new PlayerSpy("B");
        playerCSpy = new PlayerSpy("C");
        players = new Players();
        trickStub = new TrickStub();
    }

    @Test
    public void rotateReturnsNewObject() {
        assertNotSame(players, players.rotate());
    }

    @Test
    public void rotateDoesNotChangeOriginalObject() {
        players.add(playerASpy);
        players.add(playerBSpy);
        players.add(playerCSpy);
        players.rotate();
        assertPositionsOfABC(FIRST, MIDDLE, LAST);
    }

    @Test
    public void rotatePutsFirstPlayerAtTheEndOfList() {
        players.add(playerASpy);
        players.add(playerBSpy);
        players.add(playerCSpy);
        players = players.rotate();
        assertPositionsOfABC(LAST, FIRST, MIDDLE);
    }

    @Test
    public void rotateUntilC_mustReturnAListWithCAB() {
        players.add(playerASpy);
        players.add(playerBSpy);
        players.add(playerCSpy);
        players = players.rotateUntil(playerCSpy);
        assertPositionsOfABC(MIDDLE, LAST, FIRST);
    }

    @Test
    public void givenTheFirstPlayerAsTargetToRotateUntil_mustReturnTheSameObject() {
        players.add(playerASpy);
        players.add(playerBSpy);
        players.add(playerCSpy);
        assertSame(players, players.rotateUntil(playerASpy));
    }

    @Test
    public void drawPropagatesToEachPlayer() {
        players.add(playerASpy);
        players.add(playerBSpy);
        players.add(playerCSpy);
        players.drawFrom(new FixedOrderDeck());
        players.drawFrom(new FixedOrderDeck());
        assertEquals(2, playerASpy.getCards().size());
        assertEquals(2, playerBSpy.getCards().size());
        assertEquals(2, playerCSpy.getCards().size());
    }

    @Test
    public void updateCardsPropagatesToEachPlayer() {
        players.add(playerASpy);
        players.add(playerBSpy);
        players.add(playerCSpy);
        players.updateCards(trickStub);
        assertEquals(trickStub, playerASpy.trickForCardsUpdate);
        assertEquals(trickStub, playerBSpy.trickForCardsUpdate);
        assertEquals(trickStub, playerCSpy.trickForCardsUpdate);
    }

    @Test
    public void updateScorePropagatesToEachPlayer() {
        players.add(playerASpy);
        players.add(playerBSpy);
        players.add(playerCSpy);
        Iterable<Trick> tricks = Collections.singletonList(trickStub);
        players.updateScore(tricks);
        assertEquals(tricks, playerASpy.tricksForScoreUpdate);
        assertEquals(tricks, playerBSpy.tricksForScoreUpdate);
        assertEquals(tricks, playerCSpy.tricksForScoreUpdate);
    }
}
