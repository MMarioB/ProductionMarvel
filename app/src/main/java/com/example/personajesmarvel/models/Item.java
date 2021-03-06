
package com.example.personajesmarvel.models;

import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("name")
    private String mName;
    @SerializedName("resourceURI")
    private String mResourceURI;
    @SerializedName("type")
    private String mType;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getResourceURI() {
        return mResourceURI;
    }

    public void setResourceURI(String resourceURI) {
        mResourceURI = resourceURI;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    @Override
    public String toString() {
        return "Item{" +
                "mName='" + mName + '\'' +
                ", mResourceURI='" + mResourceURI + '\'' +
                ", mType='" + mType + '\'' +
                '}';
    }
}
