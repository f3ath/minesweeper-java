package f3ath.minesweeper.web;

import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryBase;
import io.crnk.core.resource.list.ResourceList;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class GameRepository extends ResourceRepositoryBase<Game, String> {


    public GameRepository() {
        super(Game.class);
    }

    @Override
    public ResourceList<Game> findAll(QuerySpec querySpec) {
        final var games = new ArrayList<Game>();
        games.add(new Game());

        return querySpec.apply(games);
    }
}
