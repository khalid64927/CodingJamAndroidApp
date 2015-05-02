package it.cosenonjaviste.androidtest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;

import it.cosenonjaviste.TestData;
import it.cosenonjaviste.androidtest.base.FragmentRule;
import it.cosenonjaviste.androidtest.dagger.DaggerUtils;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.model.WordPressService;
import it.cosenonjaviste.post.PostListFragment;
import it.cosenonjaviste.post.PostListModel;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class PostListTest {

    @Inject WordPressService wordPressService;

    @Rule public FragmentRule fragmentRule = new FragmentRule(PostListFragment.class);

    @Before public void setUp() {
        DaggerUtils.createTestComponent().inject(this);

        when(wordPressService.listPosts(eq(1)))
                .thenReturn(TestData.postResponse(0, 10));
        when(wordPressService.listPosts(eq(2)))
                .thenReturn(TestData.postResponse(10, 10));
    }

    @Test public void testPostList() throws InterruptedException {
        fragmentRule.launchFragment(new PostListModel());

        onView(withText("post title 1")).check(matches(isDisplayed()));
    }

    @Test public void testGoToPostDetail() {
        fragmentRule.launchFragment(new PostListModel());

        onData(is(instanceOf(Post.class))).inAdapterView(withId(android.R.id.list))
                .atPosition(3).perform(click());
    }

    @Test public void testLoadMore() {
        fragmentRule.launchFragment(new PostListModel());

        onData(is(instanceOf(Post.class))).inAdapterView(withId(android.R.id.list))
                .atPosition(9).check(matches(isDisplayed()));
        onView(withText("post title 10")).check(matches(isDisplayed()));
    }
}
