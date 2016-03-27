package com.example.carolinamarin.stylestumble.data;

import android.support.annotation.Nullable;

import java.util.UUID;

/**
 * Created by carolinamarin on 2/23/16.
 */
public final class Category {
    private final String mId;

    @Nullable
    private final String mDescription;


    public final String name;


//    public Category(@Nullable String description) {
//        this(description, null);
//    }

    public Category(@Nullable String id,@Nullable String description,String mname) {
        mId = UUID.randomUUID().toString();

        mDescription = description;
        name=mname;

    }


    public String getId() {
        return mId;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }


}
