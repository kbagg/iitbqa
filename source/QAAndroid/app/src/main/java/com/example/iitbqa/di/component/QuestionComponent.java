package com.example.iitbqa.di.component;


import com.example.iitbqa.di.modules.QuestionModule;
import com.example.iitbqa.di.scope.QuestionScope;
import com.example.iitbqa.presentation.question.QuestionActivity;

import dagger.Subcomponent;

@QuestionScope
@Subcomponent(modules = {QuestionModule.class})
public interface QuestionComponent {
    void inject(QuestionActivity activity);
}
