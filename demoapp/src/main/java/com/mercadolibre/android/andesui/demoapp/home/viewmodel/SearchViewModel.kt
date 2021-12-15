package com.mercadolibre.android.andesui.demoapp.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercadolibre.android.andesui.demoapp.home.datasource.HomeDataSource.getComponentsData
import com.mercadolibre.android.andesui.demoapp.home.datasource.HomeDataSource.getFoundationsData
import com.mercadolibre.android.andesui.demoapp.home.datasource.HomeDataSource.getPatternsData
import com.mercadolibre.android.andesui.demoapp.home.model.Section
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val patterns by lazy { MutableLiveData(getPatternsData()) }
    private val components by lazy { MutableLiveData(getComponentsData()) }
    private val foundations by lazy { MutableLiveData(getFoundationsData()) }

    fun getPatterns(): LiveData<List<Section>> {
        return patterns
    }

    fun getComponents(): LiveData<List<Section>> {
        return components
    }

    fun getFoundations(): LiveData<List<Section>> {
        return foundations
    }

    fun filterSectionsData(text: String) {
        viewModelScope.launch(Dispatchers.Default) {
            filterList(text, getFoundationsData(), foundations)
            filterList(text, getPatternsData(), patterns)
            filterList(text, getComponentsData(), components)
        }
    }

    private fun filterList(text: String, list: List<Section>, liveData: MutableLiveData<List<Section>>) {
        list.filter {
            it.name.startsWith(text, true)
        }.also { filteredList ->
            liveData.postValue(filteredList)
        }
    }
}
