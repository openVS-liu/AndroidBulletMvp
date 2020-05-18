package com.example.netdemo.home;


import android.content.Intent
import com.bullet.mvp.MvpActivity;
import com.bullet.mvp.ViewInit;
import android.os.Bundle;
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netdemo.DetailActivity
import com.example.netdemo.R;
import kotlinx.android.synthetic.main.activity_home.recyclerView

@ViewInit(layoutName = "activity_home", title = "城市列表")
class HomeActivity : MvpActivity<HomePresenter?>() {
    var adapter = CityAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    fun displayCity(cities: List<City>){
        adapter.list.addAll(cities)
        adapter.notifyDataSetChanged()
    }



    inner class CityAdapter : RecyclerView.Adapter<CityAdapter.ViewHolder>() {
        var list = mutableListOf<City>()

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            lateinit var textView: TextView

            init {
                textView = itemView as TextView
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(TextView(this@HomeActivity))
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.text = list[position].name
            holder.textView.setOnClickListener {
                startActivity(Intent(this@HomeActivity, DetailActivity::class.java))
            }
        }
    }




}
