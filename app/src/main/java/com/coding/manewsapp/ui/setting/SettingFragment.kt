package com.coding.manewsapp.ui.setting

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import android.view.*
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.coding.manewsapp.databinding.FragmentSettingBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true) // Enable options menu
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        // Set initial states based on preferences
        val isNightMode = sharedPref.getBoolean("night_mode", false)
        val isNewsNotificationEnabled = sharedPref.getBoolean("news_notification", true)

        binding.switchTheme.isChecked = isNightMode
        binding.switchReminder.isChecked = isNewsNotificationEnabled

        // Night Mode Switch Listener
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            sharedPref.edit().putBoolean("night_mode", isChecked).apply()
            setNightMode(isChecked)
        }

        // News Notification Switch Listener
        binding.switchReminder.setOnCheckedChangeListener { _, isChecked ->
            sharedPref.edit().putBoolean("news_notification", isChecked).apply()
            setNewsNotification(isChecked)
        }
    }

    private fun setNightMode(isEnabled: Boolean) {
        if (isEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun setNewsNotification(isEnabled: Boolean) {
        if (isEnabled) {
            // Code to enable notifications (if you use Firebase or WorkManager)
        } else {
            // Code to disable notifications
        }
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
