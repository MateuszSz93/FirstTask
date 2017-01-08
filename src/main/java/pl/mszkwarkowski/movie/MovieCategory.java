package pl.mszkwarkowski.movie;

import java.math.BigDecimal;

/**
 * Movie categories. Enum's value is a price of the movie.
 */
public enum MovieCategory {
    NEW(new BigDecimal("14.95")), HIT(new BigDecimal("9.95")), OTHER(new BigDecimal("4.95"));
    private BigDecimal value;

    MovieCategory(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal value(){
        return value;
    }
}
