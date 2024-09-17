package com.ethpalser.data;

public class TestData {

    private String name;
    private int height;
    private double weight;
    private Employment employment;
    private boolean isAvailable;

    public TestData() {
        this.name = "";
        this.height = 0;
        this.weight = 0.0;
        this.employment = Employment.UNKNOWN;
        this.isAvailable = false;
    }

    private TestData(TestDataBuilder builder) {
        this.name = builder.name;
        this.height = builder.height;
        this.weight = builder.weight;
        this.employment = builder.employment;
        this.isAvailable = builder.isAvailable;
    }

    public String getName() {
        return this.name;
    }

    public int getHeight() {
        return this.height;
    }

    public double getWeight() {
        return weight;
    }

    public Employment getEmployment() {
        return employment;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public enum Employment {
        UNKNOWN,
        UNEMPLOYED,
        FREELANCE,
        CONTRACT,
        PART_TIME,
        FULL_TIME
    }

    public static class TestDataBuilder {

        // Required
        private String name;

        // Optional
        private int height;
        private double weight;
        private Employment employment;
        private boolean isAvailable;

        public TestDataBuilder(String name) {
            this.name = name;
        }

        public TestData build() {
            return new TestData(this);
        }

        public TestDataBuilder height(int height) {
            this.height = height;
            return this;
        }

        public TestDataBuilder weight(double weight) {
            this.weight = weight;
            return this;
        }

        public TestDataBuilder employment(Employment employment) {
            this.employment = employment;
            return this;
        }

        public TestDataBuilder isAvailable(boolean isAvailable) {
            this.isAvailable = isAvailable;
            return this;
        }

    }

}
