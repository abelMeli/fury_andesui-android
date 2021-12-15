package com.mercadolibre.android.andesui.demoapp.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mercadolibre.android.andesui.demoapp.home.datasource.HomeDataSource.getCarouselData
import com.mercadolibre.android.andesui.demoapp.home.datasource.HomeDataSource.getComponentsData
import com.mercadolibre.android.andesui.demoapp.home.datasource.HomeDataSource.getFoundationsData
import com.mercadolibre.android.andesui.demoapp.home.datasource.HomeDataSource.getPatternsData
import com.mercadolibre.android.andesui.demoapp.home.model.MainAction
import com.mercadolibre.android.andesui.demoapp.home.model.Section

class MainViewModel : ViewModel() {

    private val mainActions by lazy { MutableLiveData(getCarouselData()) }
    private val patterns by lazy { MutableLiveData(getPatternsData()) }
    private val components by lazy { MutableLiveData(getComponentsData()) }
    private val foundations by lazy { MutableLiveData(getFoundationsData()) }

    fun getMainActions(): LiveData<List<MainAction>> {
        return mainActions
    }

    fun getPatterns(): LiveData<List<Section>> {
        return patterns
    }

    fun getComponents(): LiveData<List<Section>> {
        return components
    }

    fun getFoundations(): LiveData<List<Section>> {
        return foundations
    }
}
