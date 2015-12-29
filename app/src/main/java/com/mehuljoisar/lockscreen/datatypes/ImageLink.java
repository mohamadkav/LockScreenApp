package com.mehuljoisar.lockscreen.datatypes;

/**
 * Created by Mohammad on 12/29/2015.
 */
public class ImageLink {
    private Integer id;
    private String image;
    private String updated_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
