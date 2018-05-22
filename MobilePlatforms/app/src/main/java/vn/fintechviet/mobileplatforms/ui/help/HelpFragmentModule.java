/*
 *  Copyright (C) 2018 - 2019 FINTECHVIET
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package vn.fintechviet.mobileplatforms.ui.help;

import dagger.Module;
import dagger.Provides;
import vn.fintechviet.mobileplatforms.data.DataManager;
import vn.fintechviet.mobileplatforms.utils.rx.SchedulerProvider;

/**
 * Created by long_tran on 14/09/17.
 */
@Module
public class HelpFragmentModule {

    @Provides
    HelpViewModel provideHelpViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new HelpViewModel(dataManager, schedulerProvider);
    }
}
