package com.example.hueverianieto.data.services

import com.example.hueverianieto.data.models.remote.ElectricityWaterGasResourcesData
import com.example.hueverianieto.data.models.remote.HensResourcesData
import com.example.hueverianieto.utils.MaterialUtils
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetEWGService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun getEWG() : List<ElectricityWaterGasResourcesData?>? = kotlin.runCatching {
        firebaseClient.db
            .collection("material_electricity_water_gas")
            .whereEqualTo("deleted", false)
            .get()
            .await()
    }.toEWGResourcesData()

    private fun Result<QuerySnapshot>.toEWGResourcesData() = when(val result = getOrNull()) {
        null -> null
        else -> {
            val list = mutableListOf<ElectricityWaterGasResourcesData>()
            if (!result.isEmpty && result.documents.size > 0) {
                for (document in result) {
                    val data = document.data
                    val ewgResourcesData = MaterialUtils.ewgMapToParcelable(data, document.id)
                    list.add(ewgResourcesData)
                }
            }
            list
        }
    }

}
