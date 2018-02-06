package projects.suchushin.org.twitterpersonposts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.view.ViewManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.models.MediaEntity;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.TweetEntities;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.models.VideoInfo;
import com.twitter.sdk.android.tweetui.UserTimeline;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class ScrollingActivity extends AppCompatActivity {
    EditText editText;
    TweetAdapter adapter;
    LinearLayout linearLayout;
    ScrollView scrollView;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        ButterKnife.bind(this);

        Realm.init(this);

        Twitter.initialize(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();

        realm = Realm.getInstance(config);

        linearLayout = findViewById(R.id.linear_layout);

        editText = findViewById(R.id.edit_text);

        setListViewFromRealm();
    }

    @OnClick(R.id.button)
    public void createTimeline(){
        if (scrollView != null){
            ((ViewManager)scrollView.getParent()).removeView(scrollView);
            scrollView = null;
        }
        ListView listItem = new ListView(linearLayout.getContext());
        Editable e = editText.getText();
        String userNick = String.valueOf(e);

        UserTimeline timeline = new UserTimeline
                .Builder()
                .screenName(userNick)
                .build();

        adapter = new TweetAdapter(this, timeline);

        listItem.setAdapter(adapter);

        linearLayout.addView(listItem);
    }

    @OnClick(R.id.delete_data)
    public void deleteData(){
        if (scrollView != null){
            ((ViewManager)scrollView.getParent()).removeView(scrollView);
            scrollView = null;
        }
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    @OnClick(R.id.safe_data)
    public void safeData(){
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();

        if (adapter != null){
            for (int i = 0; i < adapter.getCount(); i++){
                Tweet tweet = adapter.getItem(i);

                RealmList<RealmMediaEntity> entities = new RealmList<>();
                List<MediaEntity> media = tweet.extendedEntities.media;

                for (MediaEntity entity : media){
                    VideoInfo videoInfo = entity.videoInfo;

                    RealmList<Integer> aspectRatio = new RealmList<>();
                    RealmList<RealmVariant> variants = new RealmList<>();

                    RealmVideoInfo realmVideoInfo = null;

                    if (videoInfo != null){
                        long durationMillis = videoInfo.durationMillis;

                        for (Integer integer : videoInfo.aspectRatio){
                            aspectRatio.add(integer);
                        }

                        for (VideoInfo.Variant variant : videoInfo.variants){
                            RealmVariant realmVariant = new RealmVariant(variant.bitrate, variant.contentType, variant.url);
                            variants.add(realmVariant);
                        }

                        realmVideoInfo = new RealmVideoInfo(aspectRatio, durationMillis, variants);
                    }

                    RealmMediaEntity realmEntity = new RealmMediaEntity(entity.id, entity.idStr, entity.mediaUrl, entity.mediaUrlHttps, entity.sourceStatusId, entity.sourceStatusIdStr, entity.type, realmVideoInfo, entity.altText);
                    entities.add(realmEntity);
                }

                RealmTweet realmTweet = new RealmTweet(tweet.createdAt, tweet.favoriteCount, tweet.favorited, tweet.filterLevel, tweet.id,
                        tweet.idStr, tweet.inReplyToScreenName, tweet.inReplyToStatusId, tweet.inReplyToStatusIdStr, tweet.inReplyToUserId,
                        tweet.inReplyToUserIdStr, tweet.lang, tweet.possiblySensitive, tweet.quotedStatusId, tweet.quotedStatusIdStr, tweet.retweetCount,
                        tweet.retweeted, tweet.source, tweet.text, tweet.truncated, tweet.withheldCopyright, tweet.withheldScope, tweet.user.name,
                        tweet.user.profileImageUrl, tweet.user.profileImageUrlHttps, tweet.user.screenName, entities);

                realm.beginTransaction();
                realm.copyToRealm(realmTweet);
                realm.commitTransaction();
            }
        }
    }

    private void setListViewFromRealm(){
        RealmResults<RealmTweet> results = realm.where(RealmTweet.class).findAll();
        if (results.size() > 0){
            scrollView = new ScrollView(linearLayout.getContext());
            LinearLayout linLay = new LinearLayout(scrollView.getContext());
            linLay.setOrientation(LinearLayout.VERTICAL);

            for (RealmTweet realmTweet : results){
                User user = new User(true, null, true, true, null, null, null,
                        0, true, 0, 0, false, 0, null, true, null, 0,
                        null, realmTweet.userName, null, null, null, true,
                        null, realmTweet.profileImageUrl, realmTweet.profileImageUrlHttps, null, null, null, null,
                        true, true, realmTweet.screenName, true, null, 0, null, null, 0,
                        true, null, null);

                List<MediaEntity> entities = new ArrayList<>();
                RealmList<RealmMediaEntity> realmEntities = realmTweet.entities;

                for (RealmMediaEntity entity : realmEntities){
                    RealmVideoInfo realmVideoInfo = entity.videoInfo;
                    List<VideoInfo.Variant> variants = new ArrayList<>();

                    VideoInfo videoInfo = null;

                    if (realmVideoInfo != null){
                        RealmList<RealmVariant> realmVariants = realmVideoInfo.variants;

                        for (RealmVariant realmVariant : realmVariants){
                            VideoInfo.Variant variant = new VideoInfo.Variant(realmVariant.bitrate, realmVariant.contentType, realmVariant.url);
                            variants.add(variant);
                        }

                        videoInfo = new VideoInfo(realmVideoInfo.aspectRatio, realmVideoInfo.durationMillis, variants);
                    }

                    MediaEntity mediaEntity = new MediaEntity(null, null, null, 0, 0, entity.id, entity.idStr, entity.mediaUrl,
                            entity.mediaUrlHttps, null, entity.sourceStatusId, entity.sourceStatusIdStr, entity.type, videoInfo, entity.altText);

                    entities.add(mediaEntity);
                }

                TweetEntities tweetEntities = new TweetEntities(null, null, entities, null, null);

                Tweet tweet = new Tweet(null, realmTweet.createdAt, null, null, tweetEntities, realmTweet.favoriteCount,
                        realmTweet.favorited, realmTweet.filterLevel, realmTweet.id, realmTweet.idStr, realmTweet.inReplyToScreenName, realmTweet.inReplyToStatusId,
                        realmTweet.inReplyToStatusIdStr, realmTweet.inReplyToUserId, realmTweet.inReplyToUserIdStr, realmTweet.lang, null, realmTweet.possiblySensitive,
                        null, realmTweet.quotedStatusId, realmTweet.quotedStatusIdStr, null,  realmTweet.retweetCount, realmTweet.retweeted, null,
                        realmTweet.source, realmTweet.text, null, realmTweet.truncated, user, realmTweet.withheldCopyright, null, realmTweet.withheldScope ,
                        null);

                View tweetView = TweetAdapter.getView(tweet, linLay.getContext());
                linLay.addView(tweetView);
            }

            scrollView.addView(linLay);
            linearLayout.addView(scrollView);
        }
    }
}
