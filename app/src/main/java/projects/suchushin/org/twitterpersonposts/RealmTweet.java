package projects.suchushin.org.twitterpersonposts;


import io.realm.RealmList;
import io.realm.RealmObject;

public class RealmTweet extends RealmObject {
    String createdAt;
    Integer favoriteCount;
    boolean favorited;
    String filterLevel;
    long id;
    String idStr;
    String inReplyToScreenName;
    long inReplyToStatusId;
    String inReplyToStatusIdStr;
    long inReplyToUserId;
    String inReplyToUserIdStr;
    String lang;
    boolean possiblySensitive;
    long quotedStatusId;
    String quotedStatusIdStr;
    int retweetCount;
    boolean retweeted;
    String source;
    String text;
    boolean truncated;
    boolean withheldCopyright;
    String withheldScope;
    String userName;
    String profileImageUrl;
    String profileImageUrlHttps;
    String screenName;
    RealmList<RealmMediaEntity> entities;

    public RealmTweet(){

    }

    RealmTweet(String createdAt,
               Integer favoriteCount,
               boolean favorited,
               String filterLevel,
               long id,
               String idStr,
               String inReplyToScreenName,
               long inReplyToStatusId,
               String inReplyToStatusIdStr,
               long inReplyToUserId,
               String inReplyToUserIdStr,
               String lang,
               boolean possiblySensitive,
               long quotedStatusId,
               String quotedStatusIdStr,
               int retweetCount,
               boolean retweeted,
               String source,
               String text,
               boolean truncated,
               boolean withheldCopyright,
               String withheldScope,
               String userName,
               String profileImageUrl,
               String profileImageUrlHttps,
               String screenName,
               RealmList<RealmMediaEntity> entities){
        this.createdAt = createdAt;
        this.favoriteCount = favoriteCount;
        this.id = id;
        this.idStr = idStr;
        this.inReplyToScreenName = inReplyToScreenName;
        this.retweetCount = retweetCount;
        this.text = text;
        this.favorited = favorited;
        this.filterLevel = filterLevel;
        this.inReplyToStatusId = inReplyToStatusId;
        this.inReplyToStatusIdStr = inReplyToStatusIdStr;
        this.inReplyToUserId = inReplyToUserId;
        this.inReplyToUserIdStr = inReplyToUserIdStr;
        this.lang = lang;
        this.possiblySensitive = possiblySensitive;
        this.quotedStatusId = quotedStatusId;
        this.quotedStatusIdStr = quotedStatusIdStr;
        this.retweeted = retweeted;
        this.source = source;
        this.truncated = truncated;
        this.withheldCopyright = withheldCopyright;
        this.withheldScope = withheldScope;
        this.userName = userName;
        this.profileImageUrl = profileImageUrl;
        this.profileImageUrlHttps = profileImageUrlHttps;
        this.screenName = screenName;
        this.entities = entities;
    }


}
