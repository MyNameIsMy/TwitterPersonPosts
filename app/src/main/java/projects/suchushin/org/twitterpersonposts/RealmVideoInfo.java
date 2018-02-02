package projects.suchushin.org.twitterpersonposts;

import io.realm.RealmList;
import io.realm.RealmObject;

public class RealmVideoInfo extends RealmObject{

    public RealmList<Integer> aspectRatio;

    public long durationMillis;

    public RealmList<RealmVariant> variants;

    public RealmVideoInfo(){

    }

    RealmVideoInfo(RealmList<Integer> aspectRatio, long durationMillis, RealmList<RealmVariant> variants) {
        this.durationMillis = durationMillis;
        this.variants = variants;
        this.aspectRatio = aspectRatio;
    }

}
