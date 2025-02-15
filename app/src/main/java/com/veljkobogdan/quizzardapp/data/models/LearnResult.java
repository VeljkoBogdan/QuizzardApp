package com.veljkobogdan.quizzardapp.data.models;

import java.io.Serializable;

public class LearnResult implements Serializable {
    public int correctAmount;
    public int totalAmount;
    public float percentage;

    public LearnResult(int correctAmount, int totalAmount, float percentage) {
        this.correctAmount = correctAmount;
        this.totalAmount = totalAmount;
        this.percentage = percentage;
    }
}
