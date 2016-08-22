package in.jatindhankhar.krishi;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jatin on 21/8/16.
 */
public class DataModel {
    @SerializedName("state")
    public String mstate;
    @SerializedName("district")
    public String mdistrict;
    @SerializedName("market")
    public String mmarket;
    @SerializedName("commodity")
    public String mcommodity;
    @SerializedName("variety")
    public String mvariety;
    @SerializedName("arrival_date")
    public String marrival_date;
    @SerializedName("min_price")
   public String mmin_price;
    @SerializedName("max_price")
    public String mmax_price;
    @SerializedName("modal_price")
    public String mmodal_price;
    String name;
    String version;


    public DataModel(String name,String version )
    {
        this.name = name;
        this.version = version;
    }

    public String getName()
    {
        return name;
    }

    public String getVersion()
    {
        return version;
    }
}
