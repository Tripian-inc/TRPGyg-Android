package com.tripian.gyg.ui.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.textfield.TextInputEditText
import com.tripian.gyg.R
import com.tripian.gyg.base.BaseFragment
import com.tripian.gyg.databinding.FrActivityDetailsBinding
import com.tripian.gyg.databinding.ItemDetailBinding
import com.tripian.gyg.repository.model.BookingParameters
import com.tripian.gyg.ui.book.model.Language
import com.tripian.gyg.util.datalistener.injectVM
import java.util.Locale


/**
 * Created by semihozkoroglu on 28.01.2022.
 */
class FRActivityDetails : BaseFragment<FrActivityDetailsBinding>() {

    override val binding: (LayoutInflater) -> FrActivityDetailsBinding = FrActivityDetailsBinding::inflate

    private val viewModel: FRActivityDetailsVM by injectVM()

    private var languageBinding: ItemDetailBinding? = null
    private var hotelBinding: ItemDetailBinding? = null

    private var selectedLang: Language? = null

    companion object {
        fun newInstance(): FRActivityDetails {
            return FRActivityDetails()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        BookingPageData.data.parameters = arrayListOf<BookingParameters>().apply {
//            add(BookingParameters().apply {
//                name = "language"
//                description = "Please provide your name."
//                mandatory = true
//            })
//
//            add(BookingParameters().apply {
//                name = "supplier_requested_question"
//                description = "Please provide your name."
//            })
//
//            add(BookingParameters().apply {
//                name = "supplier_requested_question"
//                description = "Please provide your name.- 2"
//                mandatory = true
//            })
//
//            add(BookingParameters().apply {
//                name = "hotel"
//                description = "Please provide hotel."
//            })
//        }

        vi.container.removeAllViews()

        checkLanguages()
        checkHotel()
        checkParameters()
    }

    override fun onResume() {
        super.onResume()

//        (requireActivity() as ACBook).setPageTitle(R.string.page_title_activity_details)
    }

    override fun setListeners() {

        vi.btnNext.setOnClickListener {
            var isOk = true

            for (i in 0 until vi.container.childCount) {
                val child = vi.container.getChildAt(i)

                if (child.tag != null) {
                    val parameter = (child.tag as BookingParameters)

                    if (parameter.name == "language" && parameter.mandatory == true && selectedLang == null) {
                        isOk = false

                        languageBinding?.etInput?.error = "Please select language"
                    } else if (parameter.name == "hotel" && parameter.mandatory == true && hotelBinding?.etInput?.text.toString().isEmpty()) {
                        isOk = false

                        languageBinding?.etInput?.error = "Please enter hotel"
                    } else if (parameter.name == "supplier_requested_question" && parameter.mandatory == true) {
                        val editText = child?.findViewById<TextInputEditText>(R.id.etInput)

                        if (editText != null && editText.text.isNullOrEmpty()) {
                            isOk = false

                            editText.error = "Please enter requested question"
                        }
                    }
                }
            }

            if (isOk) {
                BookingPageData.data.bookable?.parameters = arrayListOf()

                for (i in 0 until vi.container.childCount) {
                    val child = vi.container.getChildAt(i)

                    if (child.tag != null) {
                        val parameter = (child.tag as BookingParameters)

                        if (parameter.name == "language" && selectedLang != null) {
                            BookingPageData.data.bookable?.parameters.apply {
                                this?.add(BookingParameters().apply {
                                    name = "language"
                                    value1 = selectedLang!!.value1
                                    value2 = selectedLang!!.code
                                    description = parameter.description
                                    mandatory = parameter.mandatory
                                })
                            }
                        } else if (parameter.name == "hotel" && !hotelBinding?.etInput?.text.toString().isEmpty()) {
                            BookingPageData.data.bookable?.parameters.apply {
                                this?.add(BookingParameters().apply {
                                    name = "hotel"
                                    value1 = hotelBinding?.etInput?.text.toString()
                                    description = parameter.description
                                    mandatory = parameter.mandatory
                                })
                            }
                        } else if (parameter.name == "supplier_requested_question") {
                            val editText = child?.findViewById<TextInputEditText>(R.id.etInput)

                            if (editText != null && !editText.text.isNullOrEmpty()) {
                                BookingPageData.data.bookable?.parameters.apply {
                                    this?.add(BookingParameters().apply {
                                        name = "supplier_requested_question"
                                        value1 = editText.text.toString()
                                        description = parameter.description
                                        mandatory = parameter.mandatory
                                    })
                                }
                            }
                        }
                    }
                }

                viewModel.onClickedOk()
            }
        }
    }

    override fun setReceivers() {
        setFragmentResultListener("SelectedLanguage") { requestKey, result ->
            (result.getSerializable("language") as Language).let { lang ->
                languageBinding?.etInput?.setText(lang.display)

                selectedLang = lang
            }
        }
    }

    private fun checkLanguages() {
        val languages = arrayListOf<Language>()

        if (!BookingPageData.data.language?.audio.isNullOrEmpty()) {
            languages.addAll(BookingPageData.data.language!!.audio!!.map {
                Language().apply {
                    code = it
                    display = "${Locale(it).displayName} (Audio)"
                    value1 = "language_audio"
                }
            })
        }

        if (!BookingPageData.data.language?.booklet.isNullOrEmpty()) {
            languages.addAll(BookingPageData.data.language!!.booklet!!.map {
                Language().apply {
                    code = it
                    display = "${Locale(it).displayName} (Booklet)"
                    value1 = "language_booklet"
                }
            })
        }

        if (!BookingPageData.data.language?.live.isNullOrEmpty()) {
            languages.addAll(BookingPageData.data.language!!.live!!.map {
                Language().apply {
                    code = it
                    display = "${Locale(it).displayName} (Live)"
                    value1 = "language_live"
                }
            })
        }

        if (languages.size > 0) {
            val language = BookingPageData.data.parameters?.find { it.name == "language" }

            languageBinding = ItemDetailBinding.inflate(layoutInflater, vi.container, false)
            languageBinding!!.root.tag = language

            languageBinding!!.tvTitle.text = "Language"
            languageBinding!!.etInput.hint = "Select language"
            languageBinding!!.etInput.isFocusable = false
            languageBinding!!.etInput.isFocusableInTouchMode = false

            languageBinding!!.etInput.setOnClickListener { viewModel.onClickedLanguage(languages) }
            languageBinding!!.root.setOnClickListener { viewModel.onClickedLanguage(languages) }
            vi.container.addView(languageBinding!!.root)
        }
    }

    private fun checkHotel() {
        val hotel = BookingPageData.data.parameters?.find { it.name == "hotel" }

        if (hotel != null) {
            hotelBinding = ItemDetailBinding.inflate(layoutInflater, vi.container, false)
            hotelBinding!!.root.tag = hotel

            hotelBinding!!.tvTitle.text = "Pickup address"
            hotelBinding!!.etInput.hint = BookingPageData.data.pickUp ?: "Hotel name, address, etc"

            vi.container.addView(hotelBinding!!.root)
        }
    }

    private fun checkParameters() {
        if (!BookingPageData.data.parameters.isNullOrEmpty()) {
            for (param in BookingPageData.data.parameters!!) {
                if (param.name == "supplier_requested_question") {
                    val view = ItemDetailBinding.inflate(layoutInflater, vi.container, false)
                    view.root.tag = param

                    view.tvTitle.text = "Required information"
                    view.tvDescription.text = param.description
                    view.etInput.hint = "Enter your information"

                    vi.container.addView(view.root)
                }
            }
        }
    }
}