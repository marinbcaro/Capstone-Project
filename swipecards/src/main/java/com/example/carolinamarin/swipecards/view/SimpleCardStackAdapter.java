package com.example.carolinamarin.swipecards.view;


//public final class SimpleCardStackAdapter extends CardStackAdapter {
//
//	public SimpleCardStackAdapter(Context mContext) {
//		super(mContext);
//	}
//
//	@Override
//	public View getCardView(int position, CardModel model, View convertView, ViewGroup parent) {
//		if(convertView == null) {
//			LayoutInflater inflater = LayoutInflater.from(getContext());
//			convertView = inflater.inflate(R.layout.std_card_inner, parent, false);
//			assert convertView != null;
//		}
//
//		((ImageView) convertView.findViewById(R.id.image)).setImageDrawable(model.getCardImageDrawable());
//		((TextView) convertView.findViewById(R.id.title)).setText(model.getTitle());
//		((TextView) convertView.findViewById(R.id.description)).setText(model.getDescription());
//
//		return convertView;
//	}
//}


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.carolinamarin.swipecards.R;
import com.example.carolinamarin.swipecards.model.CardModel;

public final class SimpleCardStackAdapter extends CardStackAdapter {

	public SimpleCardStackAdapter(Context mContext) {
		super(mContext);
	}

	@Override
	public View getCardView(int position, CardModel model, View convertView, ViewGroup parent) {
		if(convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.std_card_inner, parent, false);
			assert convertView != null;
		}

		//((ImageView) convertView.findViewById(R.id.image)).setImageDrawable(model.getCardImageDrawable());
		ImageView imageView = ((ImageView) convertView.findViewById(R.id.image));
		//Picasso.with(getContext()).load(model.getCardImageDrawable()).into(imageView);
		//Glide.with(getContext()).load(model.getCardImageDrawable())
		Glide.with(getContext()).load(model.getCardImageDrawable())
				.placeholder(R.drawable.ic_launcher)
				.thumbnail(0.5f)
				.centerCrop()
				.crossFade()
				.diskCacheStrategy(DiskCacheStrategy.ALL)
						//	.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				.into(imageView);



		((TextView) convertView.findViewById(R.id.title)).setText(model.getTitle());
		((TextView) convertView.findViewById(R.id.description)).setText(model.getDescription());

		final TextView like_dislike_text = ((TextView) convertView.findViewById(R.id.like_dislike_text));
//		if(model.isLike())
//			like_dislike_text.setText("Liked");
//		else
//			like_dislike_text.setText("DisLiked");

		return convertView;
	}
}
