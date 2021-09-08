package com.example.application.views.minesweeper;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.server.connect.Endpoint;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.*;

@Endpoint
@AnonymousAllowed
@Service
public class MineSweeperHighScores {
    private final HashMap<Pair<Integer, Integer>, Integer[]> highScoreStore = new HashMap<>();

    public Integer[] getHighScores(int x, int y, Integer[] mine){
        System.out.println("getHighScores -- caller has those high scores for "+x+"x"+y+" playfield:");
        Arrays.stream(mine).sorted(Collections.reverseOrder()).forEach(System.out::println);

        Pair<Integer, Integer> dimensions = Pair.of(x, y);
        ArrayList<Integer> merger = new ArrayList<>(); // TODO: this algorithm is broken and allows the highest score to take all places over time. Names / initials of score holders are missing in the API.
        merger.addAll(Arrays.asList(retrieveHighScoresForPlayfieldWithDimensions(dimensions)));
        merger.addAll(Arrays.asList(mine));
        merger.sort(Comparator.reverseOrder());
        Integer[] calculatedNewHighscores = merger.subList(0, 10).toArray(new Integer[10]);
        highScoreStore.put(dimensions, calculatedNewHighscores);

        System.out.println("updated high scores to be returned for playfield "+x+"x"+y+" are now:");
        Arrays.stream(calculatedNewHighscores).sorted(Collections.reverseOrder()).forEach(System.out::println);
        return calculatedNewHighscores;
    }

    private Integer[] retrieveHighScoresForPlayfieldWithDimensions(final Pair<Integer, Integer> dimensions){
        Integer[] value = highScoreStore.get(dimensions);
        if (value == null){
            value = new Integer[10];
            Arrays.fill(value, 200); // TODO: they should come from storage
            highScoreStore.put(dimensions, value);
        }
        return value;
    }
}
