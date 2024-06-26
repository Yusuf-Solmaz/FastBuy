package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.settings

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import com.yusuf.yusuf_mucahit_solmaz_final.data.remoteconfig.RemoteConfigManager
import com.yusuf.yusuf_mucahit_solmaz_final.data.remoteconfig.RemoteConfigManager.loadBackgroundColor
import com.yusuf.yusuf_mucahit_solmaz_final.data.remoteconfig.RemoteConfigManager.saveBackgroundColor
import com.yusuf.yusuf_mucahit_solmaz_final.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnColorRed.setOnClickListener { updateBackgroundColor("#FF5733", view) }
        binding.btnColorGreen.setOnClickListener { updateBackgroundColor("#4CAF50", view) }
        binding.btnColorBlue.setOnClickListener { updateBackgroundColor("#2196F3", view) }
        binding.btnColorYellow.setOnClickListener { updateBackgroundColor("#FFEB3B", view) }

        binding.btnLanguageTurkish.setOnClickListener { updateLocale("tr") }
        binding.btnLanguageEnglish.setOnClickListener { updateLocale("en") }
    }

    private fun updateBackgroundColor(color: String, view: View) {
        RemoteConfigManager.setBackgroundColor(color) { success ->
            if (success) {
                saveBackgroundColor(color, requireContext())
                loadBackgroundColor(requireContext()) { color ->
                    view.setBackgroundColor(Color.parseColor(color))
                }
                Toast.makeText(context, "Background color updated successfully.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Background color update failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadBackgroundColor(requireContext()) { color ->
            view?.setBackgroundColor(Color.parseColor(color))
        }
    }

    private fun updateLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)


        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.create(Locale.forLanguageTag(languageCode))
        )

        val sharedPref = requireActivity().getSharedPreferences("com.yusuf.yusuf_mucahit_solmaz_final", Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString("SELECTED_LANGUAGE", languageCode)
            apply()
        }

        requireActivity().recreate()
    }
}
