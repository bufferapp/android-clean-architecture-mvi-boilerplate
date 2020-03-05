package org.buffer.android.boilerplate.ui.browse

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bumptech.glide.Glide
import dagger.android.AndroidInjection
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_browse.*
import org.buffer.android.boilerplate.presentation.base.BaseView
import org.buffer.android.boilerplate.presentation.browse.BrowseBufferoosViewModel
import org.buffer.android.boilerplate.presentation.browse.BrowseIntent
import org.buffer.android.boilerplate.presentation.browse.BrowseUiModel
import org.buffer.android.boilerplate.presentation.browse.model.ArticleView
import org.buffer.android.boilerplate.ui.R
import org.buffer.android.boilerplate.ui.mapper.ArticleMapper
import org.buffer.android.boilerplate.ui.widget.empty.EmptyListener
import org.buffer.android.boilerplate.ui.widget.error.ErrorListener
import javax.inject.Inject

class BrowseActivity : AppCompatActivity(),
        BaseView<BrowseIntent, BrowseUiModel> {

    private val loadConversationsIntentPublisher =
            BehaviorSubject.create<BrowseIntent.LoadBufferoosIntent>()
    private val refreshConversationsIntentPublisher =
            BehaviorSubject.create<BrowseIntent.RefreshBufferoosIntent>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var browseAdapter: BrowseAdapter
    @Inject
    lateinit var mapper: ArticleMapper
    private lateinit var browseBufferoosViewModel: BrowseBufferoosViewModel
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)
        AndroidInjection.inject(this)

        browseBufferoosViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(BrowseBufferoosViewModel::class.java)

        setupBrowseRecycler()
        setupViewListeners()

        compositeDisposable.add(browseBufferoosViewModel.states().subscribe({ render(it) }))
        browseBufferoosViewModel.processIntents(intents())
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    override fun intents(): Observable<BrowseIntent> {
        return Observable.merge(initialIntent(), loadConversationsIntentPublisher,
                refreshConversationsIntentPublisher)
    }

    private fun initialIntent(): Observable<BrowseIntent.InitialIntent> {
        return Observable.just(BrowseIntent.InitialIntent)
    }

    override fun render(state: BrowseUiModel) {
        when {
            state.inProgress -> {
                setupScreenForLoadingState()
            }
            state is BrowseUiModel.Failed -> {
                setupScreenForError()
            }
            state is BrowseUiModel.Success -> {
                setupScreenForSuccess(state.bufferoos)
            }
        }
    }

    private fun setupBrowseRecycler() {
        browseAdapter = BrowseAdapter(Glide.with(this))
        recycler_browse.layoutManager = LinearLayoutManager(this)
        recycler_browse.adapter = browseAdapter
    }

    private fun setupScreenForLoadingState() {
        progress.visibility = View.VISIBLE
        recycler_browse.visibility = View.GONE
        view_empty.visibility = View.GONE
        view_error.visibility = View.GONE
    }

    private fun setupScreenForSuccess(data: List<ArticleView>?) {
        view_error.visibility = View.GONE
        progress.visibility = View.GONE
        if (data != null && data.isNotEmpty()) {
            updateListView(data)
            recycler_browse.visibility = View.VISIBLE
        } else {
            view_empty.visibility = View.VISIBLE
        }
    }

    private fun updateListView(articles: List<ArticleView>) {
        browseAdapter.articles = articles.map { mapper.mapToViewModel(it) }
        browseAdapter.notifyDataSetChanged()
    }

    private fun setupScreenForError() {
        progress.visibility = View.GONE
        recycler_browse.visibility = View.GONE
        view_empty.visibility = View.GONE
        view_error.visibility = View.VISIBLE
    }

    private fun setupViewListeners() {
        view_empty.emptyListener = emptyListener
        view_error.errorListener = errorListener
    }

    private val emptyListener = object : EmptyListener {
        override fun onCheckAgainClicked() {
            refreshConversationsIntentPublisher.onNext(BrowseIntent.RefreshBufferoosIntent)
        }
    }

    private val errorListener = object : ErrorListener {
        override fun onTryAgainClicked() {
            refreshConversationsIntentPublisher.onNext(BrowseIntent.RefreshBufferoosIntent)
        }
    }

}