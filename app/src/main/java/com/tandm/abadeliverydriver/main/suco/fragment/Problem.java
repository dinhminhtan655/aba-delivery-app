package com.tandm.abadeliverydriver.main.suco.fragment;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Problem {

    @SerializedName("problems")
    public List<ProblemChild> problems = null;

    public List<ProblemChild> getProblems() {
        return problems;
    }

    public void setProblems(List<ProblemChild> problems) {
        this.problems = problems;
    }
}
