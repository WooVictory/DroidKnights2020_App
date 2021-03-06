package com.droidknights.app2020.ui.sponsor

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.droidknights.app2020.R
import com.droidknights.app2020.common.Event
import com.droidknights.app2020.data.Sponsor
import com.droidknights.app2020.ui.model.asUiModel

/**
 * Created by jiyoung on 04/12/2019
 */
class SponsorViewModel @ViewModelInject constructor() : ViewModel() {

    val sponsorList = listOf(
        Sponsor("toss", "https://toss.im/", R.drawable.ic_sponsor_toss),
        Sponsor("헤이딜러", "https://dealer.heydealer.com/", R.drawable.ic_sponsor_heydealer),
        Sponsor("카카오페이", "https://www.kakaopay.com/", R.drawable.ic_sponsor_kakaopay),
        Sponsor("vcnc", "https://tadacareer.vcnc.co.kr/", R.drawable.ic_sponsor_vcnc)
    ).map { sponsor -> sponsor.asUiModel() }

    private val _onClickSponsorEvent = MutableLiveData<Event<String>>()
    val onClickSponsorEvent : LiveData<Event<String>> get() = _onClickSponsorEvent

    fun onClickSponsor (link: String) {
        _onClickSponsorEvent.value = Event(link)
    }
}