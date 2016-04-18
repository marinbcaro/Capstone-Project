package com.example.carolinamarin.stylestumble.data;

import android.support.annotation.Nullable;

/**
 * Created by carolinamarin on 4/15/16.
 */
public class ProductDetail {

    private final String id;
    @Nullable
    private final String description;
    private final String name;
    private final String clickUrl;
    public final String brandedName;
    private final Double price;
    public final Image image;

    public  class Image {
        public Sizes sizes;
    }

    public class Sizes{
        public IPhone IPhone;

    }

    public class IPhone{
        public   String url;
    }

    public ProductDetail(@Nullable String mid,@Nullable String mdescription,String mname,String murl,String mbrand,Double mprice,Image mimage) {
        //   mId = UUID.randomUUID().toString();
        id=mid;
        description = mdescription;
        name=mname;
        clickUrl=murl;
        brandedName=mbrand;
        price=mprice;
        image=mimage;

    }

    public String getId() {
        return id;
    }
    //
    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public String getClickUrl() {
        return clickUrl;
    }
    @Nullable
    public String getBrandedName() {
        return brandedName;
    }
    @Nullable
    public String getDescription() {
        return description;
    }
    @Nullable
    public Image getImage() {
        return image;
    }
    @Nullable
    public double getPrice() {
        return price;
    }


}
