package projects.suchushin.org.twitterpersonposts;

import io.realm.RealmObject;

public class RealmMediaEntity extends RealmObject{
    public long id;

    public String idStr;

    public String mediaUrl;

    public String mediaUrlHttps;

    public long sourceStatusId;

    public String sourceStatusIdStr;

    public String type;

    public RealmVideoInfo videoInfo;

    public String altText;

    public RealmMediaEntity(){

    }

    RealmMediaEntity(long id,
                     String idStr,
                     String mediaUrl,
                     String mediaUrlHttps,
                     long sourceStatusId,
                     String sourceStatusIdStr,
                     String type,
                     RealmVideoInfo videoInfo,
                     String altText){
        this.id = id;
        this.idStr = idStr;
        this.mediaUrl = mediaUrl;
        this.mediaUrlHttps = mediaUrlHttps;
        this.sourceStatusId = sourceStatusId;
        this.sourceStatusIdStr = sourceStatusIdStr;
        this.type = type;
        this.videoInfo = videoInfo;
        this.altText = altText;
    }
}
