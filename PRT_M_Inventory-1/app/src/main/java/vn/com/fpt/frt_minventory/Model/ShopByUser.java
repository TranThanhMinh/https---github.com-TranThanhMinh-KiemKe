package vn.com.fpt.frt_minventory.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by minhtran on 12/28/17.
 */

public class ShopByUser {

    @SerializedName("ShopByUser")
    @Expose
    private List<Shop> shopByUser = null;

    public List<Shop> getShopByUser() {
        return shopByUser;
    }

    public void setShopByUser(List<Shop> shopByUser) {
        this.shopByUser = shopByUser;
    }
}
