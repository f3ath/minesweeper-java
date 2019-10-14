package f3ath.minesweeper.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.crnk.core.resource.annotations.JsonApiId;
import io.crnk.core.resource.annotations.JsonApiResource;

@JsonApiResource(type = "games")
public class Game {
    @JsonApiId
    private final String id = "123-456";

    @JsonProperty
    private final String name = "foo";

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
