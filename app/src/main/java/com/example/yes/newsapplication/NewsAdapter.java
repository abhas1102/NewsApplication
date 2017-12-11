package com.example.yes.newsapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yes on 12/4/2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, List<News> news) {

        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.newslist_item, parent, false);
        }

        News newsCurrent = getItem(position);

        // Find the TextView with view ID newsTitle
        TextView titleView = (TextView) listItemView.findViewById(R.id.newsHeadline);
        String headline = newsCurrent.getHeadline();

        titleView.setText(headline);

        TextView authorView = (TextView) listItemView.findViewById(R.id.newsContent);
        String content = newsCurrent.getContent();
        authorView.setText(content);

        TextView dateView = (TextView) listItemView.findViewById(R.id.newsPublishDate);
        String dop = newsCurrent.getPublishDate();
        dateView.setText(dop);

        TextView contriView = (TextView) listItemView.findViewById(R.id.newsContributor);
        String contri = newsCurrent.getContributor();
        contriView.setText(contri);

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.newsImage);
        int image = Integer.parseInt(newsCurrent.getImage());
        imageView.setImageResource(image);

        return listItemView;
    }}
