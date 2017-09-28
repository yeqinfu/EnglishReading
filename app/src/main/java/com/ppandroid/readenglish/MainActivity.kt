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
import com.google.gson.Gson
import com.ppandroid.readenglish.base.AC_Base
import com.ppandroid.readenglish.bean.home.BN_Pic
import com.ppandroid.readenglish.http.OkHttpUtils
import com.ppandroid.readenglish.http.callback.StringCallback
import com.ppandroid.readenglish.utils.glide.GlideUtils
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Call
import org.jetbrains.anko.find


class MainActivity : AC_Base() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setEnablePullToBack(false)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        rv_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        loadContent()

    }

    private fun loadContent() {
        var url = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1"

        OkHttpUtils.get().url(url).build().execute(object : StringCallback() {
            override fun onError(call: Call, e: Exception, id: Int) {
                e.printStackTrace()
            }

            override fun onResponse(response: String, id: Int) {
                var gson= Gson()
                var response=gson.fromJson(response,BN_Pic::class.java)
                response?.let {
                    if (it.isError) {
                        toast("请求错误")
                    } else {
                        it.results?.let {
                            var adapter=AD_List(it,this@MainActivity)
                            rv_list.adapter=adapter
                        }
                    }
                }
            }
        })
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_img:ImageView?=null
        init {
            iv_img=itemView.find(R.id.iv_img)
        }
    }

    class AD_List(results: List<BN_Pic.ResultsBean>, mContext: Context) : RecyclerView.Adapter<ViewHolder>() {
        var results = results
        var mContext = mContext
        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            GlideUtils.displayImage(mContext, results[position].url,holder?.iv_img)
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            var mInflater=LayoutInflater.from(mContext)
            return ViewHolder(mInflater.inflate(R.layout.item_pic, parent, false))
        }
        override fun getItemCount(): Int=results.size
    }


}
