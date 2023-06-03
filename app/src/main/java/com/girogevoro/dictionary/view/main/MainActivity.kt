package com.girogevoro.dictionary.view.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.girogevoro.dictionary.R
import com.girogevoro.dictionary.databinding.ActivityMainBinding
import com.girogevoro.data.AppState
import com.girogevoro.data.DataModel
import com.girogevoro.dictionary.utils.viewById
import com.girogevoro.dictionary.view.base.BaseActivity
import com.girogevoro.dictionary.view.history.HistoryFragment
import com.girogevoro.dictionary.view.history.SeacrhInHistoryDialogFragment
import com.girogevoro.dictionary.view.main.adapter.MainAdapter
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class MainActivity : BaseActivity<AppState>() {



    private val observer = Observer<AppState> { renderData(it) }

    private lateinit var binding: ActivityMainBinding

    private val mainActivityRecyclerView by viewById<RecyclerView>(R.id.main_activity_recyclerview)

    private var adapter: MainAdapter? = null
    private val scopeActivity by lazy {
        getKoin().createScope("mainActivityId", named<MainActivity>())
    }

    override  val model: MainViewModel by scopeActivity.inject()


    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                Toast.makeText(
                    this@MainActivity, data.text,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model.subscribe().observe(this@MainActivity) {
            renderData(it)
        }
        model.subscribe().observe(this, observer)

        binding.searchFab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object :
                SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    model.getData(searchWord, true)
                }
            })
            searchDialogFragment.show(
                supportFragmentManager,
                BOTTOM_SHEET_FRAGMENT_DIALOG_TAG
            )
        }

        if (binding.mainActivityRecyclerview.adapter != null) {
            throw IllegalStateException("ViewModel not initialised")
        }

    }

    override fun onDestroy() {
        scopeActivity.close()
        super.onDestroy()
    }

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val dataModel = appState.data
                if (dataModel == null || dataModel.isEmpty()) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    if (adapter == null) {
                        mainActivityRecyclerView.layoutManager =
                            LinearLayoutManager(applicationContext)
                        mainActivityRecyclerView.adapter =
                            MainAdapter(onListItemClickListener, dataModel)
                    } else {
                        adapter!!.setData(dataModel)
                    }
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
                    binding.progressBarHorizontal.visibility = VISIBLE
                    binding.progressBarRound.visibility = GONE
                    binding.progressBarHorizontal.progress = appState.progress!!
                } else {
                    binding.progressBarHorizontal.visibility = GONE
                    binding.progressBarRound.visibility = VISIBLE
                }
            }
            is AppState.Error -> {
                showErrorScreen(appState.error.message)
            }
        }
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        binding.errorTextview.text = error ?: getString(R.string.undefined_error)
        binding.reloadButton.setOnClickListener {
            model.getData("hi", true)
        }
    }

    private fun showViewSuccess() {
        binding.successLinearLayout.visibility = VISIBLE
        binding.loadingFrameLayout.visibility = GONE
        binding.errorLinearLayout.visibility = GONE
    }

    private fun showViewLoading() {
        binding.successLinearLayout.visibility = GONE
        binding.loadingFrameLayout.visibility = VISIBLE
        binding.errorLinearLayout.visibility = GONE
    }

    private fun showViewError() {
        binding.successLinearLayout.visibility = GONE
        binding.loadingFrameLayout.visibility = GONE
        binding.errorLinearLayout.visibility = VISIBLE
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.searched_words -> {
                startHistoryFragment("")
                return true
            }
            else -> {
                val searchInHistoryDialogFragment = SeacrhInHistoryDialogFragment.newInstance()
                searchInHistoryDialogFragment.setOnSearchInHistoryClickListener(
                    onSearchInHistoryClickListener
                )
                searchInHistoryDialogFragment.show(
                    supportFragmentManager,
                    BOTTOM_SHEET_FRAGMENT_DIALOG_TAG
                )
                return true
            }
        }
    }

    private val onSearchInHistoryClickListener: SeacrhInHistoryDialogFragment.OnSearchInHistoryClickListener =
        object : SeacrhInHistoryDialogFragment.OnSearchInHistoryClickListener {
            override fun onClick(searchWord: String) {
                startHistoryFragment(searchWord)
            }
        }


    private fun startHistoryFragment(word: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, HistoryFragment(word))
            .addToBackStack(null)
            .commit()
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
    }
}