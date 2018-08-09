package com.hit.effective.chapter1.init;

/**
 * author:Charies Gavin
 * date:2018/5/24,8:30
 * https:github.com/guobinhit
 * description:重叠构造器模式
 */
public class NutritionFacts {
    // 必填项
    private final int servingSize;
    // 必填项
    private final int servings;
    // 选填项
    private final int calories;
    // 选填项
    private final int fat;
    // 选填项
    private final int sodium;
    // 选填项
    private final int carbohydrate;

    public NutritionFacts(int servingSize, int servings) {
        this(servingSize, servings, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories) {
        this(servingSize, servings, calories, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories, int fat) {
        this(servingSize, servings, calories, fat, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium) {
        this(servingSize, servings, calories, fat, sodium, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium, int carbohydrate) {
        this.servingSize = servingSize;
        this.servings = servings;
        this.calories = calories;
        this.fat = fat;
        this.sodium = sodium;
        this.carbohydrate = carbohydrate;
    }
}
