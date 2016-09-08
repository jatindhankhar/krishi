package in.jatindhankhar.krishi;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jatin on 8/9/16.
 */
public class ColdStoreModel {
    @SerializedName("store_name")
    public String store_name;
    @SerializedName("address")
    public String address;
    @SerializedName("district")
    public String district;
    @SerializedName("name")
    public String name;
    @SerializedName("number")
    public String number;
    @SerializedName("state")
    public String state;

    public  ColdStoreModel()
    {

    }
}
