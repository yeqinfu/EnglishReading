package com.ppandroid.readenglish.bean.home;

import java.util.List;

/**
 * Created by yeqinfu on 2017/9/28.
 */

public class BN_News {

    /**
     * status : ok
     * source : abc-news-au
     * sortBy : top
     * articles : [{"author":"http://www.abc.net.au/news/julia-holman/4760348","title":"Macklemore singing at grand final like 'seeping sewage into debutante ball'","description":"North Queensland MP Bob Katter accuses the NRL of insulting and offending fans by choosing vocal American same-sex-marriage supporter Macklemore as its half-time act.","url":"http://www.abc.net.au/news/2017-09-28/macklemore-singing-at-gf-like-seeping-sewage-into-debutante-ball/8998442","urlToImage":"http://www.abc.net.au/news/image/8372596-1x1-700x700.jpg","publishedAt":"2017-09-28T08:51:35Z"},{"author":null,"title":"Hugh Hefner dies aged 91","description":"The pipe-smoking, silk-pyjama-wearing World War II veteran and journalist, whose Playboy brand defined the sexual culture of the second half of the 20th century, dies in his home surrounded by family.","url":"http://www.abc.net.au/news/2017-09-28/hugh-hefner-playboy-founder-dies-aged-91/8997178","urlToImage":"http://www.abc.net.au/news/image/8998314-1x1-700x700.jpg","publishedAt":"2017-09-28T07:53:20Z"},{"author":null,"title":"Hugh Hefner in his own words","description":"Playboy founder Hugh Hefner was a colourful character and nothing proved that more than his women and his life quotes.","url":"http://www.abc.net.au/news/2017-09-28/hugh-hefner-in-his-own-words/8997380","urlToImage":"http://www.abc.net.au/news/image/8997316-1x1-700x700.jpg","publishedAt":"2017-09-28T06:57:07Z"},{"author":"http://www.abc.net.au/news/jackson-gothe-snape/8998150","title":"The 'incremental' change that could prove crucial for Peter Dutton","description":"Queenslanders will get a fresh set of electoral boundaries on Friday that could help Peter Dutton's election prospects.","url":"http://www.abc.net.au/news/2017-09-28/incremental--electoral-boundary-change-crucial-for-peter-dutton/8997078","urlToImage":"http://www.abc.net.au/news/image/8997740-1x1-700x700.jpg","publishedAt":"2017-09-28T07:08:03Z"},{"author":"http://www.abc.net.au/news/stephen-letts/5639296","title":"Gas prices: Deal done but the days of cheap gas are long gone","description":"The big gas producers have promised to fill any domestic supply shortfall, but that doesn't mean prices are coming down.","url":"http://www.abc.net.au/news/2017-09-28/gas-prices-deal-done-but-the-days-of-cheap-gas-are-long-gone/8998570","urlToImage":"http://www.abc.net.au/news/image/8794760-1x1-700x700.jpg","publishedAt":"2017-09-28T08:45:28Z"},{"author":null,"title":"Things are still really bad in Puerto Rico after Hurricane Maria","description":"It's been just over than a week since Hurricane Maria smashed Puerto Rico and the recovery effort is struggling to keep up.","url":"http://www.abc.net.au/news/2017-09-28/things-are-still-really-bad-in-puerto-rico-after-hurricane-maria/8996542","urlToImage":"http://www.abc.net.au/news/image/8966470-1x1-700x700.jpg","publishedAt":"2017-09-28T07:54:50Z"},{"author":"http://www.abc.net.au/news/5511636","title":"'Citizenship Seven' back Commonwealth's ignorance defence","description":"Federal politicians embroiled in the dual citizenship saga are seemingly agreeing with the Commonwealth's argument that ignorance of their foreign citizenship is grounds to remain in Parliament.","url":"http://www.abc.net.au/news/2017-09-28/dual-citizenship-saga-politicians-back-ignorance-legal-argument/8998202","urlToImage":"http://www.abc.net.au/news/image/8891010-1x1-700x700.jpg","publishedAt":"2017-09-28T08:48:14Z"},{"author":"Sarah Whyte","title":"How a 'troll' enraged the yes campaign then vanished","description":"A Facebook account provoked thousands of comments before ghosting.","url":"http://www.abc.net.au/triplej/programs/hack/same-sex-marriage-troll-enrages-yes-campaign-then-vanishes/8998282","urlToImage":"http://www.abc.net.au/cm/rimage/8998430-1x1-large.jpg?v=2","publishedAt":"2017-09-28T08:45:28Z"},{"author":null,"title":"Video shows England vice-captain Stokes throwing punches outside nightclub","description":"Footage emerges showing England Test cricket vice-captain Ben Stokes throwing about a dozen punches in a late-night brawl outside a Bristol nightclub.","url":"http://www.abc.net.au/news/2017-09-28/english-test-cricket-ben-stokes-in-bristol-brawl-video/8997180","urlToImage":"http://www.abc.net.au/news/image/8997388-1x1-700x700.jpg","publishedAt":"2017-09-28T06:04:35Z"},{"author":"http://www.abc.net.au/news/elizabeth-byrne/3603426","title":"Why the High Court threw out the SSM survey challenges","description":"Two challenges brought against the same-sex marriage postal survey were insubstantial, and misunderstood the limits of the fund used to pay for the poll, the High Court finds.","url":"http://www.abc.net.au/news/2017-09-28/high-court-ssm-survey-challenge-reasons-published/8996824","urlToImage":"http://www.abc.net.au/news/image/8883206-1x1-700x700.jpg","publishedAt":"2017-09-28T03:59:54Z"}]
     */

    private String status;
    private String source;
    private String sortBy;
    private List<ArticlesBean> articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public List<ArticlesBean> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticlesBean> articles) {
        this.articles = articles;
    }

    public static class ArticlesBean {
        /**
         * author : http://www.abc.net.au/news/julia-holman/4760348
         * title : Macklemore singing at grand final like 'seeping sewage into debutante ball'
         * description : North Queensland MP Bob Katter accuses the NRL of insulting and offending fans by choosing vocal American same-sex-marriage supporter Macklemore as its half-time act.
         * url : http://www.abc.net.au/news/2017-09-28/macklemore-singing-at-gf-like-seeping-sewage-into-debutante-ball/8998442
         * urlToImage : http://www.abc.net.au/news/image/8372596-1x1-700x700.jpg
         * publishedAt : 2017-09-28T08:51:35Z
         */

        private String author;
        private String title;
        private String description;
        private String url;
        private String urlToImage;
        private String publishedAt;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrlToImage() {
            return urlToImage;
        }

        public void setUrlToImage(String urlToImage) {
            this.urlToImage = urlToImage;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }
    }
}
