package com.coding.manewsapp.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import android.view.*
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.coding.manewsapp.databinding.FragmentSettingBinding
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private lateinit var settingPreferences: SettingPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingPreferences = SettingPreferences.getInstance(requireContext())

        lifecycleScope.launch {
            settingPreferences.getThemeSetting().collect { isNightMode ->
                binding.switchTheme.isChecked = isNightMode
                setNightMode(isNightMode)
            }

            settingPreferences.getNotificationSetting().collect { isNewsNotificationEnabled ->
                binding.switchReminder.isChecked = isNewsNotificationEnabled
                if (isNewsNotificationEnabled) {
                    schedulePeriodicWork()
                } else {
                    cancelPeriodicWork()
                }
            }
        }

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch {
                settingPreferences.saveThemeSetting(isChecked)
                setNightMode(isChecked)
            }
        }

        binding.switchReminder.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch {
                settingPreferences.saveNotificationSetting(isChecked)
                if (isChecked) {
                    schedulePeriodicWork()
                } else {
                    cancelPeriodicWork()
                }
            }
        }
    }

    private fun setNightMode(isEnabled: Boolean) {
        if (isEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun schedulePeriodicWork() {
        val periodicWorkRequest = PeriodicWorkRequest.Builder(
            NewsNotificationWorker::class.java,
            2, TimeUnit.HOURS
        ).build()

        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            "news_notification_work",
            ExistingPeriodicWorkPolicy.UPDATE,
            periodicWorkRequest
        )
    }

    private fun cancelPeriodicWork() {
        WorkManager.getInstance(requireContext()).cancelUniqueWork("news_notification_work")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
