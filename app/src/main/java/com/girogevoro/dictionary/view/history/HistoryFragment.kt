package com.girogevoro.dictionary.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.girogevoro.data.AppState
import com.girogevoro.dictionary.R
import com.girogevoro.dictionary.databinding.FragmentHistoryBinding
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class HistoryFragment(private var searchedWord: String) : Fragment() {

    private val scopeHistory by lazy {
        getKoin().getOrCreateScope("historyId", named<HistoryFragment>())
    }
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var model: HistoryViewModel
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentHistoryBinding.inflate(inflater)
        return binding.historyFragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initViews()
    }

    override fun onDestroy() {
        scopeHistory.close()
        super.onDestroy()
    }

    private fun initViews() {
        binding.historyFragmentRecyclerview.adapter = adapter
        model.getData(searchedWord, false)
    }

    private fun initViewModel() {
        if (binding.historyFragmentRecyclerview.adapter != null) {
            throw IllegalStateException("ViewModel not initialised")
        }
        val viewModel: HistoryViewModel by scopeHistory.inject()
        model = viewModel
        model.subscribe().observe(viewLifecycleOwner) {
            renderData(it)
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val dataModel = appState.data
                appState.data?.let {
                    if (it.isEmpty()) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.empty_data_header),
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        if (searchedWord == "") {
                            dataModel?.let { list ->
                                adapter.setData(list)
                            }
                        } else {
                            var wordFound = false
                            dataModel?.let {
                                for (i in dataModel.indices) {
                                    if (dataModel[i].text.equals(searchedWord)) {
                                        wordFound = true
                                        val dialog = TranslationDialogFragment(
                                            dataModel[i].text.toString(),
                                            dataModel[i].meanings?.firstOrNull()?.translation?.translation
                                                ?: "",
                                            dataModel[i].meanings?.firstOrNull()?.imageUrl ?: ""
                                        )
                                        dialog.show(
                                            childFragmentManager,
                                            "translation with image dialog"
                                        )
                                    }
                                }
                                if (!wordFound) {
                                    Toast.makeText(
                                        context,
                                        "Слово не найдено в истории запросов",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                }
            }
            is AppState.Loading -> {}
            is AppState.Error -> {}
        }
    }
}