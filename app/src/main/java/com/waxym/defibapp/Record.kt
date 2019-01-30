package com.waxym.defibapp

import java.io.Serializable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Record : Serializable//, Comparable<Record>
{

    @SerializedName("datasetid")
    @Expose
    var datasetid: String? = null
    @SerializedName("recordid")
    @Expose
    var recordid: String? = null
    @SerializedName("fields")
    @Expose
    var fields: Fields? = null
    @SerializedName("geometry")
    @Expose
    var geometry: Geometry? = null
    @SerializedName("record_timestamp")
    @Expose
    var recordTimestamp: String? = null

    companion object {
        private const val serialVersionUID = -5642240646859572265L
    }

    /*@Override
    public int compareTo(Record o) {
        int result = Integer.compare(getFields().getDistance(), o.getFields().getDistance());
        return result;
    }*/
}
