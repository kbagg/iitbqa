package com.example.iitbqa.di.component;

import com.example.iitbqa.di.modules.PostQuestionModule;
import com.example.iitbqa.di.scope.PostQuestionScope;
import com.example.iitbqa.presentation.home.post_question.PostQuestionFragment;

import dagger.Subcomponent;

@PostQuestionScope
@Subcomponent(modules = {PostQuestionModule.class})
public interface PostQuestionComponent {
    void inject(PostQuestionFragment fragment);
}
