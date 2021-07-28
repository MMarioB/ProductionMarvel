
package com.example.personajesmarvel.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("count")
    private String mCount;
    @SerializedName("limit")
    private Long mLimit;
    @SerializedName("offset")
    private Long mOffset;
    @SerializedName("results")
    private List<Result> mResults;
    @SerializedName("total")
    private String mTotal;

    public String getCount() {
        return mCount;
    }

    public void setCount(String count) {
        mCount = count;
    }

    public Long getLimit() {
        return mLimit;
    }

    public void setLimit(Long limit) {
        mLimit = limit;
    }

    public Long getOffset() {
        return mOffset;
    }

    public void setOffset(Long offset) {
        mOffset = offset;
    }

    public List<Result> getResults() {
        return mResults;
    }

    public void setResults(List<Result> results) {
        mResults = results;
    }

    public String getTotal() {
        return mTotal;
    }

    public void setTotal(String total) {
        mTotal = total;
    }

    @Override
    public String toString() {
        return "Data{" +
                "mCount='" + mCount + '\'' +
                ", mLimit=" + mLimit +
                ", mOffset=" + mOffset +
                ", mResults=" + mResults +
                ", mTotal='" + mTotal + '\'' +
                '}';
    }
}
