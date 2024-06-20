package com.resqfood.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.resqfood.data.pref.UserPreference
import com.resqfood.data.pref.dataStore
import com.resqfood.repository.Repository
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ExpiredPostCleanupWorker (private val context: Context,
                                workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val userPreference = UserPreference.getInstance(context.dataStore)
        val repository = Repository.getInstance(userPreference)

        return try {
            val currentDate = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("dd/mm/yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(currentDate)

            val session = userPreference.getSession().first()
            val token = session.token

            val posts = repository.getSale(token)
            for (post in posts.sells) {
                if (post.expire < formattedDate) {
                    repository.deleteSell(post.sellId, token)
                }
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}