package network;

import com.google.gson.annotations.SerializedName;

public class ClassResponse {
    private Integer id;
    private String className;
    @SerializedName("average")
    private Double average;

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public Integer getId()      { return id; }
    public String getClassName(){ return className; }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setClassName(String className) {
        this.className = className;
    }
}
