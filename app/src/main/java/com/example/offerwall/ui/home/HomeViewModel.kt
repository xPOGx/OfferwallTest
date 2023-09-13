package com.example.offerwall.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.offerwall.data.EntityRepository
import com.example.offerwall.network.Entity
import com.example.offerwall.network.Ids
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val entityRepository: EntityRepository
) : ViewModel() {
    var homeUiState = MutableStateFlow(HomeUiState())
        private set

    init {
        getIdsAndEntity()
    }

    private fun getIdsAndEntity() {
        viewModelScope.launch {
            val ids = entityRepository.getIds()
            if (ids.data.isEmpty()) {
                updateStatus(status = Status.Error)
                return@launch
            }
            val entityId = ids.data[0].id
            val entity = entityRepository.getEntity(entityId)
            homeUiState.update {
                it.copy(
                    ids = ids,
                    entity = entity,
                    status = Status.Done
                )
            }

        }
    }

    fun getNextEntity() {
        updateStatus(Status.Loading)
        viewModelScope.launch {
            val uiState = homeUiState.value
            val id = uiState.currentIdList.inc()
            val nextId = if (uiState.ids.data.isEmpty() || id > uiState.ids.data.size - 1) {
                checkNewIds()
                if (uiState.ids.data.isEmpty()) {
                    updateStatus(Status.Error)
                    return@launch
                }
                0
            } else id
            val entityId = uiState.ids.data[nextId].id
            val entity = entityRepository.getEntity(entityId)
            homeUiState.update {
                it.copy(
                    currentIdList = if (entity.type == "error") -1 else nextId,
                    entity = entity,
                )
            }
            updateStatus(Status.Done)
        }
    }

    private fun updateStatus(status: Status) {
        homeUiState.update {
            it.copy(
                status = status,
                currentIdList = if (status == Status.Error) 0 else it.currentIdList
            )
        }
    }

    private suspend fun checkNewIds() {
        val ids = entityRepository.getIds()
        if (ids != homeUiState.value.ids && ids.data.isNotEmpty()) {
            homeUiState.update {
                it.copy(ids = ids)
            }
        }
    }
}

data class HomeUiState(
    val status: Status = Status.Loading,
    val currentIdList: Int = 0,
    val ids: Ids = Ids(),
    val entity: Entity = Entity()
)

enum class Status {
    Loading,
    Done,
    Error
}