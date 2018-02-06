package projects.suchushin.org.twitterpersonposts;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.CompactTweetView;
import com.twitter.sdk.android.tweetui.Timeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

import java.util.List;

public class TweetAdapter extends TweetTimelineListAdapter{
    private List<Tweet> tweets;

    public TweetAdapter(Context context, Timeline<Tweet> timeline) {
        super(context, timeline);
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet tweet = getItem(position);
        return getView(tweet, context);
    }

    static View getView(Tweet tweet, Context context){
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        CompactTweetView view = new CompactTweetView(linearLayout.getContext(), tweet);
        linearLayout.addView(view);

        LinearLayout ln = new LinearLayout(linearLayout.getContext());
        ln.setOrientation(LinearLayout.HORIZONTAL);

        Resources resources = context.getResources();
        Drawable retweetDrawable = resources.getDrawable(R.drawable.tw__ic_retweet_light);
        Drawable favoriteDrawable = resources.getDrawable(R.drawable.ic_favorite_black_16dp);

        ImageView retweet = new ImageView(ln.getContext());
        retweet.setImageDrawable(retweetDrawable);

        TextView retweetCount = new TextView(ln.getContext());
        retweetCount.setText(String.valueOf(tweet.retweetCount));

        ImageView favorite = new ImageView(ln.getContext());
        favorite.setImageDrawable(favoriteDrawable);

        TextView favoriteCount = new TextView(ln.getContext());
        favoriteCount.setText(String.valueOf(tweet.favoriteCount));

        ln.setGravity(Gravity.CENTER_VERTICAL);

        ln.addView(retweet);
        ln.addView(retweetCount);
        ln.addView(favorite);
        ln.addView(favoriteCount);

        linearLayout.addView(ln);

        return linearLayout;
    }
}
