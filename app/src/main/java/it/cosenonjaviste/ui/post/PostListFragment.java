package it.cosenonjaviste.ui.post;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;
import javax.inject.Provider;

import it.codingjam.lifecyclebinder.LifeCycleBinder;
import it.codingjam.lifecyclebinder.RetainedObjectProvider;
import it.cosenonjaviste.core.post.PostListViewModel;
import it.cosenonjaviste.databinding.PostRowBinding;
import it.cosenonjaviste.ui.CoseNonJavisteApp;
import it.cosenonjaviste.ui.utils.RecyclerBindingBuilder;

public class PostListFragment extends Fragment {

    @RetainedObjectProvider("viewModel") @Inject Provider<PostListViewModel> provider;

    PostListViewModel viewModel;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        CoseNonJavisteApp.getComponent(this).inject(this);
        LifeCycleBinder.bind(this);
    }

    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new RecyclerBindingBuilder<>(inflater, container, viewModel)
                .viewHolder(viewGroup -> new PostViewHolder(PostRowBinding.inflate(inflater, viewGroup, false), viewModel))
                .loadMoreListener(viewModel::loadNextPage)
                .showToolbar((AppCompatActivity) getActivity(), viewModel.isToolbarVisible(), viewModel.getToolbarTitle())
                .getRoot();
    }
}
