package com.example.carolinamarin.stylestumble.data.provider;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by carolinamarin on 4/3/16.
 */
@ContentProvider(authority = ProductProvider.AUTHORITY, database = ProductDatabase.class)
public class ProductProvider {
    public static final String AUTHORITY = "com.example.carolinamarin.stylestumble.data.provider.ProductProvider";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private static Uri buildUri(String ... paths){
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths){
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = ProductDatabase.PRODUCTS)
    public static class Products {

        @ContentUri(
                path = "products",
                type = "vnd.android.cursor.dir/products")
        public static final Uri PRODUCTS = buildUri("products");

        @InexactContentUri(
                name = "PRODUCT_ID",
                path =  "products/#",
                type = "vnd.android.cursor.item/products",
                whereColumn = ProductColumns._ID,
                pathSegment = 1)
        public static Uri withId(long id){

            return buildUri("products", String.valueOf(id));
        }


    }

}
