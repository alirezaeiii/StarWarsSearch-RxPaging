package com.android.sample.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.sample.common.util.DisposableManager
import com.android.sample.common.util.Resource
import com.android.sample.common.util.schedulers.BaseSchedulerProvider
import io.reactivex.Single
import timber.log.Timber

/**
 * BaseViewModel designed to store and manage UI-related data in a lifecycle conscious way. This
 * allows data to survive configuration changes such as screen rotations. In addition, background
 * work such as fetching network results can continue through configuration changes and deliver
 * results after the new Fragment or Activity is available.
 */
open class BaseViewModel<T>(
    private val schedulerProvider: BaseSchedulerProvider,
    private val singleRequest: Single<T>,
) : ViewModel() {

    private val _liveData = MutableLiveData<Resource<T>>()
    val liveData: LiveData<Resource<T>>
        get() = _liveData

    fun sendRequest() {
        sendRequest(singleRequest)
    }

    protected fun sendRequest(singleRequest: Single<T>) {
        _liveData.value = Resource.Loading
        singleRequest.subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({
                _liveData.postValue(Resource.Success(it))
            }) {
                _liveData.postValue(Resource.Error(it.localizedMessage))
                Timber.e(it)
            }.also { DisposableManager.add(it) }
    }

    /**
     * Called when the ViewModel is dismantled.
     * At this point, we want to cancel all disposables;
     * otherwise we end up with processes that have nowhere to return to
     * using memory and resources.
     */
    override fun onCleared() {
        super.onCleared()
        DisposableManager.clear()
    }
}