package com.hit.effective.chapter1.init;

/**
 * author:Charies Gavin
 * date:2018/5/24,9:00
 * https:github.com/guobinhit
 * description:Builder模式
 */
public class NutritionFacts3 {
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

    public static class Builder {
        // 必填项
        private final int servingSize;
        // 必填项
        private final int servings;
        // 选填项，初始化为默认值
        private int calories = 0;
        private int fat = 0;
        private int sodium = 0;
        private int carbohydrate = 0;

        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
        }

        public Builder calories(int calories) {
            this.calories = calories;
            return this;
        }

        public Builder fat(int fat) {
            this.fat = fat;
            return this;
        }

        public Builder sodium(int sodium) {
            this.sodium = sodium;
            return this;
        }

        public Builder carbohydrate(int carbohydrate) {
            this.carbohydrate = carbohydrate;
            return this;
        }

        public NutritionFacts3 build() {
            return new NutritionFacts3(this);
        }
    }

    /**
     * 私有化构造器，通过类的静态内部类来构造对象
     *
     * @param builder
     */
    private NutritionFacts3(Builder builder) {
        servingSize = builder.servingSize;
        servings = builder.servings;
        calories = builder.calories;
        fat = builder.fat;
        sodium = builder.sodium;
        carbohydrate = builder.carbohydrate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("servingSize: " + this.servingSize + "\n").
                append("servings: " + this.servings + "\n").
                append("calories: " + this.calories + "\n").
                append("fat: " + this.fat + "\n").
                append("sodium: " + this.sodium + "\n").
                append("carbohydrate: " + this.carbohydrate);
        return sb.toString();
    }

    public static void main(String[] args) {
        NutritionFacts3 nutritionFacts =
                new Builder(100, 99).calories(10).carbohydrate(5).build();
        System.out.println(nutritionFacts);
    }
}
