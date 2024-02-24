
package com.example.kissanseva;


public class Crop {
    private String cropName;
    private int cropQuantity;
    private String cropDate;
    private double cropPrice;

    public Crop() {
        // Default constructor required for Firebase
    }

    public Crop(String cropName, int cropQuantity, String cropDate, double cropPrice) {
        this.cropName = cropName;
        this.cropQuantity = cropQuantity;
        this.cropDate = cropDate;
        this.cropPrice = cropPrice;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public int getCropQuantity() {
        return cropQuantity;
    }

    public void setCropQuantity(int cropQuantity) {
        this.cropQuantity = cropQuantity;
    }

    public String getCropDate() {
        return cropDate;
    }

    public void setCropDate(String cropDate) {
        this.cropDate = cropDate;
    }

    public double getCropPrice() {
        return cropPrice;
    }

    public void setCropPrice(double cropPrice) {
        this.cropPrice = cropPrice;
    }
}

