
package com.ryanhuen.customviewcareer.model;

/**
 * Created by ryanhuenwork on 16-10-25.
 */

public class PieModel {
    // data user care
    private String name; // 标题
    private float value; // 数值
    private float percentage; // 百分比

    // data user don't care
    private int color; // 颜色
    private float angle; // 角度

    public PieModel(String name, float value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
