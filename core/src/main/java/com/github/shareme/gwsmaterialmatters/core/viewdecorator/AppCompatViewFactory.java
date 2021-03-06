/*
 * Copyright(C) 2016 Nemi
 * Modifications Copyright (C) 2016 Fred Grott(aka shareme GrottWorkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under License.
 */
package com.github.shareme.gwsmaterialmatters.core.viewdecorator;

import android.content.Context;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by fgrott on 6/15/2016.
 */

public class AppCompatViewFactory implements ViewFactory {
  private AppCompatDelegate appCompatDelegate;
  private Context context;

  public AppCompatViewFactory(AppCompatDelegate appCompatDelegate, Context context) {
    this.appCompatDelegate = appCompatDelegate;
    this.context = context;
  }

  @Override
  public View createView(View parent, String name, AttributeSet attrs) {
    return appCompatDelegate.createView(parent, name, context, attrs);
  }
}
