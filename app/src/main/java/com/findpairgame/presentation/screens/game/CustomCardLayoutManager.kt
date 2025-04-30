package com.findpairgame.presentation.screens.game

import android.content.Context
import android.util.DisplayMetrics
import android.util.Size
import androidx.recyclerview.widget.RecyclerView

class CustomCardLayoutManager(
    context: Context,
    private val cardSize: Size
) : RecyclerView.LayoutManager() {

    private val displayMetrics = context.resources.displayMetrics
    private val verticalSpacing = dpToPx(8, displayMetrics)
    private val horizontalSpacing = dpToPx(4, displayMetrics)
    private var totalHeight = 0
    private var canScroll = false

    private val pattern10 = listOf(3, 2, 3, 2)
    private val pattern20 = listOf(5, 5, 5, 5)
    private val pattern32 = listOf(5, 4, 5, 4, 5, 4, 5)

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            cardSize.width + horizontalSpacing,
            cardSize.height + verticalSpacing
        )
    }

    override fun canScrollVertically(): Boolean = canScroll

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)
        if (itemCount == 0) return

        scrollVerticallyOffset = scrollVerticallyOffset.coerceAtMost(totalHeight - height).coerceAtLeast(0)

        val pattern = when (itemCount) {
            10 -> pattern10
            20 -> pattern20
            32 -> pattern32
            else -> return
        }

        var currentPos = 0
        var offsetY = 0

        for (itemsInRow in pattern) {
            if (currentPos >= itemCount) break

            val rowWidth = itemsInRow * (cardSize.width + horizontalSpacing)
            val parentWidth = width - paddingStart - paddingEnd
            val offsetX = (parentWidth - rowWidth) / 2

            var currentX = offsetX
            val endPos = minOf(currentPos + itemsInRow, itemCount)

            for (i in currentPos until endPos) {
                val view = recycler.getViewForPosition(i)
                addView(view)

                measureChildWithMargins(view, 0, 0)
                layoutDecorated(
                    view,
                    currentX,
                    offsetY - scrollVerticallyOffset,
                    currentX + cardSize.width,
                    offsetY + cardSize.height - scrollVerticallyOffset
                )

                currentX += cardSize.width + horizontalSpacing
            }

            currentPos = endPos
            offsetY += cardSize.height + verticalSpacing
        }

        totalHeight = offsetY
        canScroll = totalHeight > height
    }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int {
        if (!canScroll || totalHeight <= height) return 0

        val maxScroll = totalHeight - height
        val delta = when {
            dy < 0 -> maxOf(dy, -scrollVerticallyOffset)
            else -> minOf(dy, maxScroll - scrollVerticallyOffset)
        }

        scrollVerticallyOffset += delta
        offsetChildrenVertical(-delta)
        return delta
    }

    private var scrollVerticallyOffset = 0

    private fun dpToPx(dp: Int, displayMetrics: DisplayMetrics): Int {
        return (dp * displayMetrics.density).toInt()
    }
}