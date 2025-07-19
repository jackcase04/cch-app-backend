package com.cch.cch_app.responses;

import com.cch.cch_app.model.Chore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChoreResponse {
    private List<Chore> chores;

    public ChoreResponse(List<Chore> chores) {
        this.chores = chores;
    }
}
