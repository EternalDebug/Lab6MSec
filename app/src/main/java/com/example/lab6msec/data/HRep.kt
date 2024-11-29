package com.example.lab6msec.data

import android.content.Context
import android.util.Log
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import androidx.health.connect.client.request.AggregateRequest
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.health.connect.client.units.Energy
import java.time.Instant

class HRep(context: Context) {
    private val healthConnectClient = HealthConnectClient.getOrCreate(context)

    suspend fun insertCalories(
        count: String,
        instant0: Instant,
        instant1: Instant
    ) {
        try {
            val calRecord = TotalCaloriesBurnedRecord(
                energy = Energy.kilocalories(count.toDouble()),
                startTime = instant0,
                endTime = instant1,
                startZoneOffset = null,
                endZoneOffset = null,
            )
            healthConnectClient.insertRecords(listOf(calRecord))
        } catch (e: Exception) {
            Log.v("HRep","insertException " + e.message)
        }
    }

    suspend fun readCaloriesByTimeRange(
        start: Long,
        final: Long
    ) : List<TotalCaloriesBurnedRecord> {
        try {
            return healthConnectClient.readRecords(
                ReadRecordsRequest(
                    TotalCaloriesBurnedRecord::class,
                    timeRangeFilter = TimeRangeFilter.between(
                        Instant.ofEpochSecond(start),
                        Instant.ofEpochSecond(final + 1)
                    )
                )
            ).records
        } catch (e: Exception) {
            Log.v("HealthRepo","readException " + e.message)
            return listOf<TotalCaloriesBurnedRecord>()
        }
    }

    suspend fun aggregateCalories(
        start: Long,
        final: Long
    ): Energy {
        try {
            return healthConnectClient.aggregate(
                AggregateRequest(
                    metrics = setOf(TotalCaloriesBurnedRecord.ENERGY_TOTAL),
                    timeRangeFilter = TimeRangeFilter.between(
                        Instant.ofEpochSecond(start),
                        Instant.ofEpochSecond(final + 1)
                    )
                )
            )[TotalCaloriesBurnedRecord.ENERGY_TOTAL]!!
        } catch (e: Exception) {
            Log.v("HealthRepo","aggregateException " + e.message)
            return Energy.kilocalories(0.0)
        }
    }

    suspend fun aggregateCalories2(
        start: Long,
        final: Long
    ): Energy {
        var lst = readCaloriesByTimeRange(start,final)
        var subres = 0.0

        for (elem in lst){
            subres += elem.energy.inKilocalories
        }
        val res = Energy.kilocalories(subres)
        return res
    }


    suspend fun deleteCalories(record: TotalCaloriesBurnedRecord) {
        try {
            healthConnectClient.deleteRecords(
                TotalCaloriesBurnedRecord::class,
                TimeRangeFilter.between(
                    record.startTime.minusSeconds(2),
                    record.endTime.plusSeconds(2)
                )
            )
        } catch (e: Exception) {
            Log.v("HRep","deleteException " + e.message)
        }
    }

}