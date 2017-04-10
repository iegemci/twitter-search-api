package vngrs.enesgemci.tweetsearch.dagger.component;

import javax.inject.Singleton;

import dagger.Component;
import vngrs.enesgemci.tweetsearch.dagger.module.AppModule;
import vngrs.enesgemci.tweetsearch.dagger.module.DataModule;
import vngrs.enesgemci.tweetsearch.data.repository.online.OnlineRepository;
import vngrs.enesgemci.tweetsearch.ui.MainActivity;
import vngrs.enesgemci.tweetsearch.ui.detail.FragmentDetail;
import vngrs.enesgemci.tweetsearch.ui.master.FragmentSearch;

/**
 * Created by enesgemci on 08/04/2017.
 */

@Singleton
@Component(modules = {AppModule.class, DataModule.class})
public interface AppComponent {

    void inject(MainActivity activity);

    void inject(OnlineRepository appRemoteDataStore);

    void inject(FragmentSearch fragmentSearch);

    void inject(FragmentDetail fragmentDetail);
}