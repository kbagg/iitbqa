package com.example.iitbqa.di.component;

import com.example.iitbqa.di.modules.FeedModule;
import com.example.iitbqa.di.modules.UserModule;
import com.example.iitbqa.di.scope.FeedScope;
import com.example.iitbqa.di.scope.UserScope;
import com.example.iitbqa.presentation.home.feed.FeedFragment;

import dagger.Subcomponent;

@FeedScope
@Subcomponent(modules = {FeedModule.class})
public interface FeedComponent {
    void inject(FeedFragment fragment);
}
