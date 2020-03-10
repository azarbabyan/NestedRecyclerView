package com.artzarbo.nestedRecyclerView.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Artur Zarbabyan on 3/10/2020.
 */
class DividerItemDecoration(val context: Context, val divider: Drawable?, orientation: Int, val withHeader:Boolean) : RecyclerView.ItemDecoration() {
    private val attrs = intArrayOf(android.R.attr.listDivider)

    companion object {
        const val HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL
        const val VERTICAL_LIST = LinearLayoutManager.VERTICAL
    }

    private var mOrientation: Int = VERTICAL_LIST
    private var mDivider: Drawable? = null

    init {
        val a = context.obtainStyledAttributes(attrs)
        mDivider = a.getDrawable(0)
        a.recycle()
        setOrientation(orientation)
    }

    private fun setOrientation(orientation: Int) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw  IllegalArgumentException("invalid orientation")
        }
        mOrientation = orientation
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (mOrientation == VERTICAL_LIST) {
            if (divider != null) {
                drawVertical(c, parent, divider)
            } else {
                drawVertical(c, parent, mDivider)
            }
        } else {
            if (divider != null) {
                drawHorizontal(c, parent, divider)
            } else {
                drawHorizontal(c, parent, mDivider)
            }
        }
    }

    private fun drawVertical(c: Canvas, parent: RecyclerView, div: Drawable?) {
        if (div == null) {
            return
        }
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child: View = parent.getChildAt(i)
            val params = child
                .layoutParams as RecyclerView.LayoutParams
            val top: Int = child.bottom + params.bottomMargin
            val bottom: Int = top + div.intrinsicHeight
            div.setBounds(left, top, right, bottom)
            div.draw(c)
        }
    }

    private fun drawHorizontal(c: Canvas, parent: RecyclerView, div: Drawable?) {
        if (div == null) {
            return
        }
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child
                .layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin
            val right: Int = left + div.intrinsicHeight
            div.setBounds(left, top, right, bottom)
            div.draw(c)
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.getChildAdapterPosition(view) == 0 || (withHeader && parent.getChildAdapterPosition(
                view
            ) == 1)
        ) {
            return
        }
        if (mOrientation == VERTICAL_LIST) {
            if (divider != null) {
                outRect.top = divider.intrinsicHeight
            } else if (mDivider != null) {
                outRect.top = mDivider!!.intrinsicHeight
            }
        } else {
            if (divider != null) {
                outRect.left = divider.intrinsicWidth
            } else if (mDivider != null) {
                outRect.left = mDivider!!.intrinsicWidth
            }
        }
    }
}