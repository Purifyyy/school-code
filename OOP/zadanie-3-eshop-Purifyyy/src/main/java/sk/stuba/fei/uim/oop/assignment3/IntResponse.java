package sk.stuba.fei.uim.oop.assignment3;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntResponse {

    private int amount;

    public IntResponse(int amount) {
        this.amount = amount;
    }
}
