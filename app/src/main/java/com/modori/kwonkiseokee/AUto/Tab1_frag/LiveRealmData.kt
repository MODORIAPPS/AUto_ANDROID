package com.modori.kwonkiseokee.AUto.Tab1_frag

import androidx.lifecycle.LiveData
import io.realm.RealmChangeListener
import io.realm.RealmModel
import io.realm.RealmResults

class LiveRealmData<T:RealmModel>(val realmResults: RealmResults<T>):LiveData<RealmResults<T>>(){
    private val listener = RealmChangeListener<RealmResults<T>> {value = it}
    override fun onActive() = realmResults.addChangeListener(listener)
    override fun onInactive() = realmResults.removeChangeListener(listener)

}