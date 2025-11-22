package com.tripian.gyg.ui.book

import android.os.Bundle
import android.view.LayoutInflater
import com.tripian.gyg.base.BaseBottomDialogFragment
import com.tripian.gyg.databinding.FrCountrySelectBinding
import com.tripian.gyg.ui.book.model.Language

/**
 * Created by semihozkoroglu on 28.01.2022.
 */
class FRLangSelect : BaseBottomDialogFragment<FrCountrySelectBinding>() {

    override val binding: (LayoutInflater) -> FrCountrySelectBinding = FrCountrySelectBinding::inflate

    companion object {
        fun newInstance(languages: ArrayList<Language>): FRLangSelect {
            return FRLangSelect().apply {
                arguments = Bundle().apply {
                    putSerializable("languages", languages)
                }
            }
        }
    }

    override fun setListeners() {
        val items = arguments?.getSerializable("languages") as List<Language>

        vi.rvCountries.adapter = object : AdapterLang(requireContext(), items) {
            override fun onSelectedLang(lang: Language) {
                setFragmentResult("SelectedLanguage", Bundle().apply { putSerializable("language", lang) })
                dismiss()
            }
        }
    }

    override fun setReceivers() {
    }
}