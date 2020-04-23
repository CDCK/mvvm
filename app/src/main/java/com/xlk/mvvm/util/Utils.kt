package com.xlk.mvvm.util

import android.content.Context
import android.widget.Toast

/**
 * Created by xlk on 2020/4/23.
 *
 */

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

/**
 * dp->px:  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, context.getResources().getDisplayMetrics());
 * in->px:  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_IN, 20, context.getResources().getDisplayMetrics());
 * mm->px:  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, 20, context.getResources().getDisplayMetrics());
 * pt->px:  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, 20, context.getResources().getDisplayMetrics());
 * sp->px:  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, context.getResources().getDisplayMetrics());
 */
fun Context.px2sp(px: Float): Float {
    return px / resources.displayMetrics.scaledDensity + 0.5f
//    return TypedValue.applyDimension(
//        TypedValue.COMPLEX_UNIT_PX,
//        px,
//        getResources().getDisplayMetrics()
//    )
}

fun Context.sp2px(sp: Float): Float {
    return sp * resources.displayMetrics.scaledDensity + 0.5f
}
