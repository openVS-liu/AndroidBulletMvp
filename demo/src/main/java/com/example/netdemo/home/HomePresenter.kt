package com.example.netdemo.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import com.bullet.mvp.Presenter;
import com.bullet.request.OnSuccessListener
import com.example.netdemo.request.StructRequestClient
import java.util.HashMap


class HomePresenter : Presenter<HomeActivity>() {

    override fun onCreate(@NonNull owner: LifecycleOwner) {
        super.onCreate(owner)
        requestCityList()
    }

    fun requestCityList() = StructRequestClient.with(this)
            .setUrl("/apis/CityCode/")
            .addParameter("code", "13")
            .setTargetObjectClass(City::class.java)
            .setOnSuccessListener { targetObject, _ ->
                view.displayCity(targetObject as List<City>)
            }
            .sendRequest()
}
class City(){
    var name: String?=null
    var code: String?=null
}
