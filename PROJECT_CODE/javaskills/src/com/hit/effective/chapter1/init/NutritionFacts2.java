package com.hit.effective.chapter1.init;

/**
 * author:Charies Gavin
 * date:2018/5/24,8:45
 * https:github.com/guobinhit
 * description:JavaBeans模式
 */
public class NutritionFacts2 {
    // 必填项
    private int servingSize = -1;
    // 必填项
    private int servings = -1;
    // 选填项
    private int calories = 0;
    // 选填项
    private int fat = 0;
    // 选填项
    private int sodium = 0;
    // 选填项
    private int carbohydrate = 0;

    public NutritionFacts2() {
    }

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate = carbohydrate;
    }
}
