/**
 * AndTinder v0.1 for Android
 *
 * @Author: Enrique López Mañas <eenriquelopez@gmail.com>
 * http://www.lopez-manas.com
 *
 * TAndTinder is a native library for Android that provide a
 * Tinder card like effect. A card can be constructed using an
 * image and displayed with animation effects, dismiss-to-like
 * and dismiss-to-unlike, and use different sorting mechanisms.
 *
 * AndTinder is compatible with API Level 13 and upwards
 *
 * @copyright: Enrique López Mañas
 * @license: Apache License 2.0
 */

package com.example.carolinamarin.swipecards.model;

import android.graphics.drawable.Drawable;

//public class CardModel {
//
//	private String   title;
//	private String   description;
//	private Drawable cardImageDrawable;
//	private Drawable cardLikeImageDrawable;
//	private Drawable cardDislikeImageDrawable;
//
//	private OnCardDismissedListener mOnCardDismissedListener = null;
//
//	private OnClickListener mOnClickListener = null;
//
//	public interface OnCardDismissedListener {
//		void onLike();
//		void onDislike();
//	}
//
//	public interface OnClickListener {
//		void OnClickListener();
//	}
//
//	public CardModel() {
//		this(null, null, (Drawable)null);
//	}
//
//	public CardModel(String title, String description, Drawable cardImage) {
//		this.title = title;
//		this.description = description;
//		this.cardImageDrawable = cardImage;
//	}
//
//	public CardModel(String title, String description, Bitmap cardImage) {
//		this.title = title;
//		this.description = description;
//		this.cardImageDrawable = new BitmapDrawable(null, cardImage);
//	}
//
//	public String getTitle() {
//		return title;
//	}
//
//	public void setTitle(String title) {
//		this.title = title;
//	}
//
//	public String getDescription() {
//		return description;
//	}
//
//	public void setDescription(String description) {
//		this.description = description;
//	}
//
//	public Drawable getCardImageDrawable() {
//		return cardImageDrawable;
//	}
//
//	public void setCardImageDrawable(Drawable cardImageDrawable) {
//		this.cardImageDrawable = cardImageDrawable;
//	}
//
//	public Drawable getCardLikeImageDrawable() {
//		return cardLikeImageDrawable;
//	}
//
//	public void setCardLikeImageDrawable(Drawable cardLikeImageDrawable) {
//		this.cardLikeImageDrawable = cardLikeImageDrawable;
//	}
//
//	public Drawable getCardDislikeImageDrawable() {
//		return cardDislikeImageDrawable;
//	}
//
//	public void setCardDislikeImageDrawable(Drawable cardDislikeImageDrawable) {
//		this.cardDislikeImageDrawable = cardDislikeImageDrawable;
//	}
//
//	public void setOnCardDismissedListener( OnCardDismissedListener listener ) {
//		this.mOnCardDismissedListener = listener;
//	}
//
//	public OnCardDismissedListener getOnCardDismissedListener() {
//		return this.mOnCardDismissedListener;
//	}
//
//
//	public void setOnClickListener( OnClickListener listener ) {
//		this.mOnClickListener = listener;
//	}
//
//	public OnClickListener getOnClickListener() {
//		return this.mOnClickListener;
//	}
//}
///**
// * AndTinder v0.1 for Android
// *
// * @Author: Enrique López Mañas <eenriquelopez@gmail.com>
// * http://www.lopez-manas.com
// *
// * TAndTinder is a native library for Android that provide a
// * Tinder card like effect. A card can be constructed using an
// * image and displayed with animation effects, dismiss-to-like
// * and dismiss-to-unlike, and use different sorting mechanisms.
// *
// * AndTinder is compatible with API Level 13 and upwards
// *
// * @copyright: Enrique López Mañas
// * @license: Apache License 2.0
// */
//
//package com.example.carolinamarin.swipecards.model;
//
//import android.graphics.drawable.Drawable;
//
public class CardModel {

	private String   title;
	private String   description;

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	private String brand;

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	private Double price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name;
	private String cardImageDrawable;
	private Drawable cardLikeImageDrawable;
	private Drawable cardDislikeImageDrawable;
	private boolean isLike = false;

    private OnCardDismissedListener mOnCardDismissedListener = null;

    private OnClickListener mOnClickListener = null;

    public interface OnCardDismissedListener {
        void onLike();
        void onDislike();
    }

	public boolean isLike() {
		return isLike;
	}

	public void setLike(boolean isLike) {
		this.isLike = isLike;
	}
    public interface OnClickListener {
        void OnClickListener();
    }

	public CardModel() {
		this(null, null, null,null,null);
	}

//	public CardModel(String title, String description, Drawable cardImage) {
//		this.title = title;
//		this.description = description;
//		this.cardImageDrawable = cardImage;
//	}
//
//	public CardModel(String title, String description, Bitmap cardImage) {
//		this.title = title;
//		this.description = description;
//		this.cardImageDrawable = new BitmapDrawable(null, cardImage);
//	}

	public CardModel(String title, String description, String cardImage,String brand,Double price) {
		this.title = title;
		this.description = description;
		this.brand=brand;
		this.price=price;
		this.cardImageDrawable = cardImage;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCardImageDrawable() {
		return cardImageDrawable;
	}

	public void setCardImageDrawable(String cardImageDrawable) {
		this.cardImageDrawable = cardImageDrawable;
	}

	public Drawable getCardLikeImageDrawable() {
		return cardLikeImageDrawable;
	}

	public void setCardLikeImageDrawable(Drawable cardLikeImageDrawable) {
		this.cardLikeImageDrawable = cardLikeImageDrawable;
	}

	public Drawable getCardDislikeImageDrawable() {
		return cardDislikeImageDrawable;
	}

	public void setCardDislikeImageDrawable(Drawable cardDislikeImageDrawable) {
		this.cardDislikeImageDrawable = cardDislikeImageDrawable;
	}

    public void setOnCardDismissedListener( OnCardDismissedListener listener ) {
        this.mOnCardDismissedListener = listener;
    }

    public OnCardDismissedListener getOnCardDismissedListener() {
       return this.mOnCardDismissedListener;
    }


    public void setOnClickListener( OnClickListener listener ) {
        this.mOnClickListener = listener;
    }

    public OnClickListener getOnClickListener() {
        return this.mOnClickListener;
    }
}
