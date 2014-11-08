package it.cosenonjaviste.mvp.post;

import org.junit.Test;

import dagger.Module;
import it.cosenonjaviste.MvpTestModule;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.mvp.base.optional.OptionalList;
import it.cosenonjaviste.stubs.JsonStubs;

import static org.assertj.core.api.Assertions.assertThat;

public class PostListPresenterTest extends PostListPresenterBaseTest {

    @Override protected Object getTestModule() {
        return new TestModule();
    }

    @Test
    public void testLoad() throws InterruptedException {
        presenter.reloadData();
        OptionalList<Post> model = presenter.getModel();
        assertThat(model.size()).isEqualTo(1);
        String lastUrl = server.getLastUrl();
        System.out.println(lastUrl);
//        int requestCount = server.getRequestCount();
//        System.out.println(recordedRequest);
    }

    @Test
    public void testLoadMore() {
        server.initDispatcher(JsonStubs.getPostList(20));
        presenter.reloadData();
        server.initDispatcher(JsonStubs.getPostList(5));
        presenter.loadNextPage();
        OptionalList<Post> model = presenter.getModel();
        assertThat(model.size()).isEqualTo(25);
    }

    @Test
    public void testGoToDetails() {
        presenter.reloadData();
        OptionalList<Post> model = presenter.getModel();
        Post firstPost = model.get(0);

        presenter.goToDetail(firstPost);

        PostDetailModel detailModel = getLastModel();
        Post detailPost = detailModel.getPost();

        assertThat(detailPost).isNotNull();
        assertThat(detailPost.getId()).isEqualTo(firstPost.getId());
    }

    @Module(injects = {PostListPresenterTest.class}, addsTo = MvpTestModule.class)
    public static class TestModule {
    }

}