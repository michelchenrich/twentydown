package hm.twentydown.card;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Cards<T extends Card> extends ArrayList<T> {
    public boolean hasSuit(Suit suit) {
        return !filterBySuit(suit).isEmpty();
    }

    public Cards<T> filterBySuit(Suit suit) {
        return stream().filter((play -> play.hasSuit(suit))).collect(Collectors.toCollection(Cards::new));
    }

    public T getFirst() {
        return get(0);
    }
}
