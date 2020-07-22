package com.droidknights.app2020.ui.schedule

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.*
import com.droidknights.app2020.base.BaseViewModel
import com.droidknights.app2020.base.DispatcherProvider
import com.droidknights.app2020.common.Event
import com.droidknights.app2020.data.Session
import com.droidknights.app2020.db.SessionRepository
import com.droidknights.app2020.ui.model.UiSessionModel
import com.droidknights.app2020.ui.model.asUiModel
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.io.Serializable
import javax.inject.Inject

/**
 * Created by jiyoung on 04/12/2019
 */
class ScheduleViewModel @Inject constructor(
    private val dispatchers: DispatcherProvider,
    private val repo: SessionRepository
) : BaseViewModel() {

    private val _refreshEvent = MutableLiveData(Unit)
    val sessionList: LiveData<List<UiSessionModel>> = _refreshEvent.switchMap {
        liveData<List<UiSessionModel>> {
            emitSource(
                repo.get()
                    .map {
                        it.map { session -> session.asUiModel() }
                    }
                    .flowOn(dispatchers.default())
                    .asLiveData()
            )
        }
    }

    val isRefreshing: LiveData<Boolean> = sessionList.map { false }

    var selectedTags: List<String> = emptyList()
        set(value) {
            val equal = field.containsAll(value) && value.containsAll(field)
            if (!equal) {
                //both lists are of the same elements
                field = value
                refresh()
            }
        }

    var allTags: List<String> = emptyList()

    private val _itemEvent = MutableLiveData<Event<String>>()
    val itemEvent: LiveData<Event<String>> get() = _itemEvent

    private val _fabEvent = MutableLiveData<Event<String>>()
    val fabEvent: LiveData<Event<String>> get() = _fabEvent

    private val _submitEvent = MutableLiveData<Event<String>>()
    val submitEvent: LiveData<Event<String>> get() = _submitEvent

    init {
        refresh()
    }

    fun refresh() {
        _refreshEvent.value = Unit
    }

    fun onClickItem(sessionId: String) {
        _itemEvent.value = Event(sessionId)
    }

    fun onClickFilter() {
        _fabEvent.value = Event("")
    }

    fun onClickSubmit() {
        _submitEvent.value = Event("")
    }
}