package it.cosenonjaviste.ui.author;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.ParcelClass;

import it.cosenonjaviste.R;
import it.cosenonjaviste.core.author.AuthorListModel;
import it.cosenonjaviste.core.author.AuthorListPresenter;
import it.cosenonjaviste.core.author.AuthorListView;
import it.cosenonjaviste.core.model.Author;
import it.cosenonjaviste.core.post.PostListModel;
import it.cosenonjaviste.databinding.AuthorCellBinding;
import it.cosenonjaviste.ui.CoseNonJavisteApp;
import it.cosenonjaviste.ui.post.PostListFragment;
import it.cosenonjaviste.ui.utils.RecyclerViewRxMvpFragment;
import it.cosenonjaviste.ui.utils.SingleFragmentActivity;

@ParcelClass(AuthorListModel.class)
public class AuthorListFragment extends RecyclerViewRxMvpFragment<AuthorListPresenter, Author> implements AuthorListView {

    @Override protected AuthorListPresenter createPresenter() {
        return CoseNonJavisteApp.getComponent(this).getAuthorListPresenter();
    }

    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        binding.setPresenter(presenter);
        binding.swipeRefresh.setOnRefreshListener(presenter::loadDataPullToRefresh);
        presenter.setListChangeListener(adapter::reloadData);
        return view;
    }


    @Override @NonNull protected AuthorViewHolder createViewHolder(LayoutInflater inflater, ViewGroup v) {
        return new AuthorViewHolder(AuthorCellBinding.bind(inflater.inflate(R.layout.author_cell, v, false)), presenter);
    }

    @Override protected void retry() {
        presenter.reloadData();
    }

    @Override public void openPostList(PostListModel model) {
        SingleFragmentActivity.open(getActivity(), PostListFragment.class, model);
    }
}