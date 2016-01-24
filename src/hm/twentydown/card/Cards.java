package hm.twentydown.card;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cards<T extends Card> extends ArrayList<T> {
    public boolean hasSuit(Suit suit) {
        return !filterPlays(suit).isEmpty();
    }

    protected List<T> filterPlays(Suit suit) {
        return stream().filter((play -> play.hasSuit(suit))).collect(Collectors.toList());
    }
}
