/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.propertyanimation

import android.animation.*
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView


class MainActivity : AppCompatActivity() {

    lateinit var star: ImageView
    lateinit var rotateButton: Button
    lateinit var translateButton: Button
    lateinit var scaleButton: Button
    lateinit var fadeButton: Button
    lateinit var colorizeButton: Button
    lateinit var showerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        star = findViewById(R.id.star)
        rotateButton = findViewById<Button>(R.id.rotateButton)
        translateButton = findViewById<Button>(R.id.translateButton)
        scaleButton = findViewById<Button>(R.id.scaleButton)
        fadeButton = findViewById<Button>(R.id.fadeButton)
        colorizeButton = findViewById<Button>(R.id.colorizeButton)
        showerButton = findViewById<Button>(R.id.showerButton)

        rotateButton.setOnClickListener {
            rotater()
        }

        translateButton.setOnClickListener {
            translater()
        }

        scaleButton.setOnClickListener {
            scaler()
        }

        fadeButton.setOnClickListener {
            fader()
        }

        colorizeButton.setOnClickListener {
            colorizer()
        }

        showerButton.setOnClickListener {
            shower()
        }
    }

    // Create an animation that rotates the ImageView containing the star from a value of -360 to 0.
    // This means that the view, and thus the star inside it,
    // will rotate in a full circle (360 degrees) around its center.
    private fun rotater() {
        // Note: The reason that the animation starts at -360 is that that allows the star to
        // complete a full circle (360 degrees) and end at 0, which is the default rotation value
        // for a non-rotated view, so it’s a good value to have at the end of the animation
        // (in case any other action occurs on that view later, expecting the default value).

        // This line of code creates an ObjectAnimator that acts on the target “star”
        // (the ImageView instance that holds our star graphic). It runs an animation on the
        // ROTATION property of star. Changes to that property will cause the star to rotate around
        // its center. There are two other rotation properties (ROTATION_X and ROTATION_Y)
        // that rotate around the other axes (in 3D), but these are not typically used
        // in UI animations, since UIs are typically 2D.

        // Note: A property, to the animation system, is a field that is exposed via setters and
        // getters, either implicitly (as properties are in Kotlin) or explicitly
        // (via the setter/getter pattern in the Java programming language).
        // There are also a special case of properties exposed via the class android.util.Property
        // which is used by the View class, which allows a type-safe approach for animations,
        // as we’ll see later. The Animator system in Android was specifically written to
        // animate properties, meaning that it can animate anything (not just UI elements)
        // that has a setter (and, in some cases, a getter)

        // Note: View properties are a set of functionality added to the base View class that
        // allows all views to be transformed in specific ways that are useful in UI animations.
        // There are properties for position (called “translation”), rotation, scale,
        // and transparency (called “alpha”).

        // There are actually two different ways to access the properties, by regular
        // setter/getter pairs, like setTranslateX()/getTranslateX(),
        // and by static android.util.Property objects, like View.TRANSLATE_X
        // (an object that has both a get() and a set() method). This lesson primarily uses
        // android.util.Propertyobjects, because it has less overhead internally along with
        // better type-safety, but you can also use the setters and getters of any object,
        // as you will see toward the end of the lab.

        // Note: Property animation is, simply, the changing of property values over time.
        // ObjectAnimator was created to provide a simple set-and-forget mechanism for
        // animating properties. For example, you can define an animator that changes
        // the “alpha” property of a View, which will alter the transparency of that view on the screen.

        // The animation runs from a start value of -360 degrees to an end value of 0 degrees,
        // which will spin the star in a single rotation about its center. Note that the start
        // starts at 0 degrees, before the animation begins, and then jumps immediately to
        // -360 degrees. But since -360 is visually the same as 0 degrees,
        // there is no noticeable change when the animation begins.
        val animator = ObjectAnimator.ofFloat(star, View.ROTATION, -360f, 0f)

//        animator.start()
        // Now if you run the application again and click on ROTATE, you will see that
        // the star does, indeed, spin around its center. But it does so really quickly.
        // In fact, it does it in 300 milliseconds, which is the default duration of all animations
        // on the platform. 300 milliseconds is a decent default for most animations,
        // but in this case, we’d like more time to enjoy the animation.

        animator.duration = 1000

        // Note: This code uses an adapter class (which provides default implementations of all of
        // the listener methods) instead of implementing the raw AnimatorListener interface.
        // The code only needs to know about a couple of the callbacks; it’s not worth implementing
        // the rest of the listener methods, so we let the adapter stub them out for us.
        animator.disableViewDuringAnimation(rotateButton)
        animator.start()
    }

    private fun translater() {
        val animator = ObjectAnimator.ofFloat(star, View.TRANSLATION_X, 200f)
        // Note: Repetition is a way of telling animations to do the same task again and again.
        // You can specify how many times to repeat (or just tell it to run infinitely).
        // You can also specify the repetition behavior, either REVERSE
        // (for reversing the direction every time it repeats) or
        // RESTART (for animating from the original start value to the original end value,
        // thus repeating in the same direction every time).
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(translateButton)
        animator.start()
    }

    private fun scaler() {
        // Scaling to a value of 4f means the star will scale to 4 times its default size.

        // Note: These PropertyValuesHolder objects look similar to the ObjectAnimators you created
        // previously, but they only hold the property and value information for the animation,
        // not the target. So even if you wanted to run these as an animation, you could not;
        // there’s not enough information because you haven’t told the system which target object(s) to animate.
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 4f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 4f)

        // This is similar to the animators you created previously, but instead of defining
        // a property and a set of values, it uses multiple PropertyValuesHolders which contain all
        // of that information already. Using several PropertyValuesHolder objects in
        // a single animator will cause them all to be animated in parallel.
        val animator = ObjectAnimator.ofPropertyValuesHolder(star, scaleX, scaleY)

        // As with the translater() function, you want to make this a repeating/reversing animation
        // to leave the star’s SCALE_X and SCALE_Y properties at their default values (1.0) when
        // the animation is done. Do this by setting the appropriate repeatCount and repeatMode values on the animator:
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(scaleButton)
        animator.start()
    }

    private fun fader() {
        val animator = ObjectAnimator.ofFloat(star, View.ALPHA, 0f)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(fadeButton)
        animator.start()
    }

    private fun colorizer() {
        // Note: The ofArgb() method is the reason that this app builds against minSdk 21; the rest
        // of the functionality of the app can be run on earlier SDKs, but ofArgb() was introduced
        // in the Lollipop release. It is also possible to animate color values on earlier releases,
        // involving TypeEvaluators, and the use of ArgbEvaluator specifically. We used ofArgb()
        // in this lesson instead for simplicity.
        //
        // The other thing to notice about this construction of the ObjectAnimator is
        // the property: instead of specifying one of the View properties, like ALPHA,
        // you are simply passing in the string “backgroundColor”. When you do this,
        // the system searches for setters and getters with that exact spelling using reflection.
        // It caches references to those methods and calls them during the animation,
        // instead of calling the Property set/get functions as the previous animations did.

        // Animate colors, not integers
        var animator = ObjectAnimator.ofArgb(star.parent,
            "backgroundColor", Color.BLACK, Color.RED)
        animator.setDuration(500)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(colorizeButton)
        animator.start()
    }

    private fun shower() {
        // a reference to the star field ViewGroup (which is just the parent of the current star view).
        val container = star.parent as ViewGroup
        // the width and height of that container (which you will use to calculate the end translation values for our falling stars).
        val containerW = container.width
        val containerH = container.height
        // the default width and height of our star (which you will later alter with a scale factor to get different-sized stars).
        var starW: Float = star.width.toFloat()
        var starH: Float = star.height.toFloat()

        // Create a new View to hold the star graphic. Because the star is a VectorDrawable asset,
        // use an AppCompatImageView, which has the ability to host that kind of resource.
        // Create the star and add it to the background container.
        val newStar = AppCompatImageView(this)
        newStar.setImageResource(R.drawable.ic_star)
        newStar.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT)
        container.addView(newStar)

        // You have now cached the star’s pixel H/W stored in starW and starH:
        newStar.scaleX = Math.random().toFloat() * 1.5f + .1f
        newStar.scaleY = newStar.scaleX
        starW *= newStar.scaleX
        starH *= newStar.scaleY

        // Now position the new star. Horizontally, it should appear randomly somewhere from the left edge
        // to the right edge. This code uses the width of the star to position it
        // from half-way off the screen on the left (-starW / 2) to half-way off the screen on the right
        // (with the star positioned at (containerW - starW / 2).
        // The vertical positioning of the star will be handled later in the actual animation code.
        newStar.translationX = Math.random().toFloat() *
                containerW - starW / 2

        val mover = ObjectAnimator.ofFloat(newStar, View.TRANSLATION_Y,
            -starH, containerH + starH)
        mover.interpolator = AccelerateInterpolator(1f)
        val rotator = ObjectAnimator.ofFloat(newStar, View.ROTATION,
            (Math.random() * 1080).toFloat())
        rotator.interpolator = LinearInterpolator()

        val set = AnimatorSet()
        set.playTogether(mover, rotator)
        // set the duration to a random number between 500 and 2000 milliseconds
        set.duration = (Math.random() * 1500 + 500).toLong()

        // Remember: It should seem obvious that when a view is not needed anymore, it should be removed.
        // But complex animations can make us forget about the simple things.
        // Use animation listeners to handle important tasks for bringing views onto or off of the screen,
        // like we’re doing here, to remove a view when the animation to move it off the screen is complete.
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                container.removeView(newStar)
            }
        })
        set.start()
    }

    private fun ObjectAnimator.disableViewDuringAnimation(view: View) {
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?, isReverse: Boolean) {
                view.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                view.isEnabled = true
            }
        })
    }
}
