package com.example.iitbqa.di.component;

import com.example.iitbqa.di.modules.UserModule;
import com.example.iitbqa.di.scope.UserScope;
import com.example.iitbqa.presentation.home.profile.ProfileFragment;

import dagger.Subcomponent;

@UserScope
@Subcomponent(modules = {UserModule.class})
public interface UserComponent {
    void inject(ProfileFragment profileFragment);
}
