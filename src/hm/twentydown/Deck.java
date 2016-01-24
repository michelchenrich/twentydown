package hm.twentydown;

import hm.twentydown.card.Card;

public interface Deck {
    Card take();
    Deck makeNew();
}
