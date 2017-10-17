package com.cy.cylnxuexijia.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2017/10/11 0011.
 */

public class TermsBean {
    /**
     * id : 1
     * term : 上学期
     * points : [{"params":"[{\"product_id\":\"11\",\"content_id\":\"63\",\"point_id\":\"89\",\"point_name\":\"偏旁、部首、笔画、笔顺\"},{\"product_id\":\"11\",\"content_id\":\"63\",\"point_id\":\"91\",\"point_name\":\"字的音、形、义\"},{\"product_id\":\"11\",\"content_id\":\"63\",\"point_id\":\"103\",\"point_name\":\"名著名篇导读\"},{\"product_id\":\"11\",\"content_id\":\"63\",\"point_id\":\"109\",\"point_name\":\"情景交际\"}]","point_id":"87","point_name":"拼音","videos":[{"is_free":"0","courseware_count":"12","video_id":"257","video_name":"声母、韵母的读音、形及写法"},{"is_free":"1","courseware_count":"5","video_id":"259","video_name":"整体音节的读音、形及写法"},{"is_free":"1","courseware_count":"12","video_id":"265","video_name":"声调和标调、拼音方法及规则"}]},{"params":"[{\"product_id\":\"11\",\"content_id\":\"63\",\"point_id\":\"109\",\"point_name\":\"情景交际\"},{\"product_id\":\"11\",\"content_id\":\"63\",\"point_id\":\"107\",\"point_name\":\"续说故事\"},{\"product_id\":\"11\",\"content_id\":\"63\",\"point_id\":\"105\",\"point_name\":\"看图说话\"},{\"product_id\":\"11\",\"content_id\":\"63\",\"point_id\":\"103\",\"point_name\":\"名著名篇导读\"},{\"product_id\":\"11\",\"content_id\":\"63\",\"point_id\":\"101\",\"point_name\":\"古诗词\"}]","point_id":"89","point_name":"偏旁、部首、笔画、笔顺","videos":[{"is_free":"0","courseware_count":"10","video_id":"289","video_name":"常见偏旁、部首的写法及意义"},{"is_free":"1","courseware_count":"17","video_id":"293","video_name":"汉字的笔画、笔顺"}]},{"params":"[{\"product_id\":\"11\",\"content_id\":\"63\",\"point_id\":\"109\",\"point_name\":\"情景交际\"},{\"product_id\":\"11\",\"content_id\":\"63\",\"point_id\":\"107\",\"point_name\":\"续说故事\"},{\"product_id\":\"11\",\"content_id\":\"63\",\"point_id\":\"105\",\"point_name\":\"看图说话\"},{\"product_id\":\"11\",\"content_id\":\"63\",\"point_id\":\"103\",\"point_name\":\"名著名篇导读\"},{\"product_id\":\"11\",\"content_id\":\"63\",\"point_id\":\"101\",\"point_name\":\"古诗词\"}]","point_id":"91","point_name":"字的音、形、义","videos":[{"is_free":"1","courseware_count":"22","video_id":"297","video_name":"汉字字形的演变和字义的变化"}]},{"params":"[{\"product_id\":\"11\",\"content_id\":\"63\",\"point_id\":\"109\",\"point_name\":\"情景交际\"},{\"product_id\":\"11\",\"content_id\":\"63\",\"point_id\":\"107\",\"point_name\":\"续说故事\"},{\"product_id\":\"11\",\"content_id\":\"63\",\"point_id\":\"105\",\"point_name\":\"看图说话\"},{\"product_id\":\"11\",\"content_id\":\"63\",\"point_id\":\"103\",\"point_name\":\"名著名篇导读\"},{\"product_id\":\"11\",\"content_id\":\"63\",\"point_id\":\"101\",\"point_name\":\"古诗词\"}]","point_id":"93","point_name":"常见词语","videos":[{"is_free":"1","courseware_count":"19","video_id":"301","video_name":"分类学词语"}]},{"params":"[{\"product_id\":\"11\",\"content_id\":\"63\",\"point_id\":\"109\",\"point_name\":\"情景交际\"},{\"product_id\":\"11\",\"content_id\":\"63\",\"point_id\":\"107\",\"point_name\":\"续说故事\"},{\"product_id\":\"11\",\"content_id\":\"63\",\"point_id\":\"105\",\"point_name\":\"看图说话\"},{\"product_id\":\"11\",\"content_id\":\"63\",\"point_id\":\"103\",\"point_name\":\"名著名篇导读\"},{\"product_id\":\"11\",\"content_id\":\"63\",\"point_id\":\"101\",\"point_name\":\"古诗词\"}]","point_id":"95","point_name":"叠词","videos":[{"is_free":"1","courseware_count":"18","video_id":"305","video_name":"叠词的组成和运用"}]},{"params":"[{\"product_id\":\"11\",\"content_id\":\"63\",\"point_id\":\"109\",\"point_name\":\"情景交际\"},{\"product_id\":\"11\",\"content_id\":\"63\",\"point_id\":\"107\",\"point_name\":\"续说故事\"},{\"product_id\":\"11\",\"content_id\":\"63\",\"point_id\":\"105\",\"point_name\":\"看图说话\"},{\"product_id\":\"11\",\"content_id\":\"63\",\"point_id\":\"103\",\"point_name\":\"名著名篇导读\"},{\"product_id\":\"11\",\"content_id\":\"63\",\"point_id\":\"101\",\"point_name\":\"古诗词\"}]","point_id":"97","point_name":"连词成句","videos":[{"is_free":"1","courseware_count":"16","video_id":"309","video_name":"连词成句题的解答"}]}]
     */

    private int id;
    private String term;
    private List<PointsBean> points;

    public static TermsBean objectFromData(String str) {

        return new Gson().fromJson(str, TermsBean.class);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public List<PointsBean> getPoints() {
        return points;
    }

    public void setPoints(List<PointsBean> points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "TermsBean{" +
                "id=" + id +
                ", term='" + term + '\'' +
                ", points=" + points +
                '}';
    }
}
