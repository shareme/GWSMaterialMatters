/*
 * Copyright 2015 florent37, Inc.
 * Modifications Copyright(C) 2016 Fred Grott(GrottWorkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.github.shareme.gwsmaterialmatters.core.mviewpager;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * Created by fgrott on 6/28/2016.
 */

public class MaterialViewPagerImageHelper {

  private static MaterialViewPager.OnImageLoadListener imageLoadListener;

  /**
   * change the image with a fade
   *
   * @param urlImage
   * @param fadeDuration TODO : remove Picasso
   */
  public static void setImageUrl(final ImageView imageView, final String urlImage, final int fadeDuration) {
    final float alpha = ViewCompat.getAlpha(imageView);
    final ImageView viewToAnimate = imageView;

    //fade to alpha=0
    fadeOut(viewToAnimate, fadeDuration, new ViewPropertyAnimatorListenerAdapter() {
      @Override
      public void onAnimationEnd(View view) {
        super.onAnimationEnd(view);

        //change the image when alpha=0
        Glide.with(imageView.getContext()).load(urlImage)
                .centerCrop()
                .listener(new RequestListener<String, GlideDrawable>() {
                  @Override
                  public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                  }

                  @Override
                  public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    //then fade to alpha=1
                    fadeIn(viewToAnimate, alpha, fadeDuration, new ViewPropertyAnimatorListenerAdapter());
                    if (imageLoadListener != null) {
                      imageLoadListener.OnImageLoad(imageView, ((BitmapDrawable) imageView.getDrawable()).getBitmap());
                    }
                    return false;
                  }
                })
                .into(viewToAnimate);
      }
    });
  }

  public static void fadeOut(View view, int fadeDuration, ViewPropertyAnimatorListenerAdapter listener) {
    //fade to alpha=0
    ViewCompat.animate(view)
            .alpha(0)
            .setDuration(fadeDuration)
            .withLayer()
            .setInterpolator(new DecelerateInterpolator())
            .setListener(listener);
  }

  public static void fadeIn(View view, float alpha, int fadeDuration, ViewPropertyAnimatorListenerAdapter listener) {
    //fade to alpha=0
    ViewCompat.animate(view)
            .alpha(alpha)
            .setDuration(fadeDuration)
            .withLayer()
            .setInterpolator(new AccelerateInterpolator())
            .setListener(listener);
  }

  /**
   * change the image with a fade
   *
   * @param drawable
   * @param fadeDuration
   */
  public static void setImageDrawable(final ImageView imageView, final Drawable drawable, final int fadeDuration) {
    final float alpha = ViewCompat.getAlpha(imageView);
    final ImageView viewToAnimate = imageView;

    fadeOut(viewToAnimate, fadeDuration, new ViewPropertyAnimatorListenerAdapter() {
      @Override
      public void onAnimationEnd(View view) {
        super.onAnimationEnd(view);
        //change the image when alpha=0

        imageView.setImageDrawable(drawable);

        //then fade to alpha=1
        fadeIn(viewToAnimate, alpha, fadeDuration, new ViewPropertyAnimatorListenerAdapter());
      }
    });
  }

  public static void setImageLoadListener(MaterialViewPager.OnImageLoadListener imageLoadListener) {
    MaterialViewPagerImageHelper.imageLoadListener = imageLoadListener;
  }
}

