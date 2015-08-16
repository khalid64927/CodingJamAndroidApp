package it.cosenonjaviste.ui.utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.cosenonjaviste.R;
import it.cosenonjaviste.databinding.RecyclerBinding;
import it.cosenonjaviste.lib.mvp.MvpFragment;
import it.cosenonjaviste.lib.mvp.RxMvpListPresenterAdapter;

public abstract class RecyclerViewRxMvpFragment<P extends RxMvpListPresenterAdapter<T, ?, ?>, T> extends MvpFragment<P> {
    protected BindableAdapter<T> adapter;
    protected RecyclerBinding binding;

    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = RecyclerBinding.bind(inflater.inflate(R.layout.recycler, null, false));

        adapter = new BindableAdapter<>(v -> createViewHolder(inflater, v));
        binding.list.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = createLayoutManager();
        binding.list.setLayoutManager(layoutManager);

        binding.swipeRefresh.setColorSchemeResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);

        binding.setPresenter(presenter);
        presenter.setListChangeListener(adapter::reloadData);

        return binding.getRoot();
    }

    protected void initLoadMore(Runnable listener) {
        binding.list.addOnScrollListener(new EndlessRecyclerOnScrollListener((LinearLayoutManager) binding.list.getLayoutManager()) {
            @Override
            public void onLoadMore() {
                listener.run();
            }
        });
    }

    @NonNull protected RecyclerView.LayoutManager createLayoutManager() {
        return new GridLayoutManager(getActivity(), 2);
    }

    @NonNull protected abstract BindableViewHolder<T> createViewHolder(LayoutInflater inflater, ViewGroup v);
}
