package com.ppandroid.readenglish

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.ppandroid.readenglish.base.AC_Base
import com.ppandroid.readenglish.bean.home.BN_News
import com.ppandroid.readenglish.bean.home.BN_Pic
import com.ppandroid.readenglish.http.OkHttpUtils
import com.ppandroid.readenglish.http.callback.StringCallback
import com.ppandroid.readenglish.utils.DebugLog
import com.ppandroid.readenglish.utils.glide.GlideUtils
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Call
import org.jetbrains.anko.find


class MainActivity : AC_Base() {
    private var bbckey="9393536dc2a348d99efb96890171c6c2"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setEnablePullToBack(false)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
      //  rv_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_news.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
      //  loadContent()
        loadNews()

    }

    private fun loadNews() {
        var url="https://newsapi.org/v1/articles?source=abc-news-au&sortBy=top&apiKey="+bbckey
        DebugLog.d("http",url)
        OkHttpUtils.get().url(url).build().execute(object : StringCallback() {
            override fun onError(call: Call, e: Exception, id: Int) {
                e.printStackTrace()
            }

            override fun onResponse(response: String, id: Int) {
                DebugLog.d("http",response)
                var gson= Gson()
                var response=gson.fromJson(response,BN_News::class.java)
                response?.let {
                    var adapter=AD_ListNews(it.articles,this@MainActivity)
                    rv_news.adapter=adapter
                }
            }
        })

    }

    private fun loadContent() {
        var url = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/30/1"
        DebugLog.d("http",url)
        OkHttpUtils.get().url(url).build().execute(object : StringCallback() {
            override fun onError(call: Call, e: Exception, id: Int) {
                e.printStackTrace()
            }

            override fun onResponse(response: String, id: Int) {
                DebugLog.d("http",response)
                var gson= Gson()
                var response=gson.fromJson(response,BN_Pic::class.java)
                response?.let {
                    if (it.isError) {
                        toast("请求错误")
                    } else {
                        it.results?.let {
                            var adapter=AD_List(it,this@MainActivity)
                          //  rv_list.adapter=adapter
                        }
                    }
                }
            }
        })
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_img:ImageView?=null
        var tv_title:TextView?=null
        var tv_author:TextView?=null
        var tv_publish_at:TextView?=null
        var tv_description:TextView?=null
        init {
            iv_img=itemView.find(R.id.iv_img)
            tv_title=itemView.find(R.id.tv_title)
            tv_author=itemView.find(R.id.tv_author)
            tv_publish_at=itemView.find(R.id.tv_publish_at)
            tv_description=itemView.find(R.id.tv_description)
        }
    }

    class AD_List(results: List<BN_Pic.ResultsBean>, mContext: Context) : RecyclerView.Adapter<ViewHolder>() {
        var results = results
        var mContext = mContext
        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            holder?.let {
                var item=results[position]
                GlideUtils.displayImage(mContext, item.url,it.iv_img)
            }


        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            var mInflater=LayoutInflater.from(mContext)
            return ViewHolder(mInflater.inflate(R.layout.item_pic, parent, false))
        }
        override fun getItemCount(): Int=results.size
    }
    class AD_ListNews(results: List<BN_News.ArticlesBean>, mContext: Context) : RecyclerView.Adapter<ViewHolder>() {
        var results = results
        var mContext = mContext
        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            holder?.let {
                var item=results[position]
                GlideUtils.displayImage(mContext,item.urlToImage,it.iv_img)
                it.tv_title?.text=item.title
                it.tv_author?.text="author:"+item.author
                it.tv_publish_at?.text=item.publishedAt
                it.tv_description?.text=item.description
                it.iv_img?.setOnClickListener {
                    var it=MyH5Activity.createIntent(mContext,item.url)
                    mContext.startActivity(it)
                }
                it.tv_author?.setOnClickListener {
                    var it=MyH5Activity.createIntent(mContext,item.author)
                    mContext.startActivity(it)
                }


            }
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            var mInflater=LayoutInflater.from(mContext)
            return ViewHolder(mInflater.inflate(R.layout.item_pic, parent, false))
        }
        override fun getItemCount(): Int=results.size
    }


}
