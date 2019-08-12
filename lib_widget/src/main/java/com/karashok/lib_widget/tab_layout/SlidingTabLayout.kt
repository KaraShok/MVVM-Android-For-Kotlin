package com.karashok.lib_widget.tab_layout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.SparseArray
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

import com.karashok.lib_widget.R
import com.karashok.lib_widget.tab_layout.listener.OnTabSelectListener
import com.karashok.lib_widget.tab_layout.utils.UnreadMsgUtils
import com.karashok.lib_widget.tab_layout.widget.MsgView

import java.util.ArrayList
import java.util.Collections

/**
 * 滑动TabLayout,对于ViewPager的依赖性强
 **/
class SlidingTabLayout @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : HorizontalScrollView(mContext, attrs, defStyleAttr), ViewPager.OnPageChangeListener {
    private var mViewPager: ViewPager? = null
    private var mTitles: ArrayList<String>? = null
    private val mTabsContainer: LinearLayout
    private var mCurrentTab: Int = 0
    private var mCurrentPositionOffset: Float = 0.toFloat()
    var tabCount: Int = 0
        private set
    /** 用于绘制显示器  */
    private val mIndicatorRect = Rect()
    /** 用于实现滚动居中  */
    private val mTabRect = Rect()
    private val mIndicatorDrawable = GradientDrawable()

    private val mRectPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mDividerPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mTrianglePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mTrianglePath = Path()
    private var mIndicatorStyle = STYLE_NORMAL

    private var mTabPadding: Float = 0.toFloat()
    private var mTabSpaceEqual: Boolean = false
    private var mTabWidth: Float = 0.toFloat()

    /** indicator  */
    private var mIndicatorColor: Int = 0
    private var mIndicatorHeight: Float = 0.toFloat()
    private var mIndicatorWidth: Float = 0.toFloat()
    private var mIndicatorCornerRadius: Float = 0.toFloat()
    var indicatorMarginLeft: Float = 0.toFloat()
        private set
    var indicatorMarginTop: Float = 0.toFloat()
        private set
    var indicatorMarginRight: Float = 0.toFloat()
        private set
    var indicatorMarginBottom: Float = 0.toFloat()
        private set
    private var mIndicatorGravity: Int = 0
    private var mIndicatorWidthEqualTitle: Boolean = false

    /** underline  */
    private var mUnderlineColor: Int = 0
    private var mUnderlineHeight: Float = 0.toFloat()
    private var mUnderlineGravity: Int = 0

    /** divider  */
    private var mDividerColor: Int = 0
    private var mDividerWidth: Float = 0.toFloat()
    private var mDividerPadding: Float = 0.toFloat()
    private var mTextsize: Float = 0.toFloat()
    private var mTextSelectColor: Int = 0
    private var mTextUnselectColor: Int = 0
    private var mTextBold: Int = 0
    private var mTextAllCaps: Boolean = false

    private var mLastScrollX: Int = 0
    private var mHeight: Int = 0
    private var mSnapOnTabClick: Boolean = false

    private var margin: Float = 0.toFloat()

    //setter and getter
    var currentTab: Int
        get() = mCurrentTab
        set(currentTab) {
            this.mCurrentTab = currentTab
            mViewPager!!.setCurrentItem(currentTab)

        }

    var indicatorStyle: Int
        get() = mIndicatorStyle
        set(indicatorStyle) {
            this.mIndicatorStyle = indicatorStyle
            invalidate()
        }

    var tabPadding: Float
        get() = mTabPadding
        set(tabPadding) {
            this.mTabPadding = dp2px(tabPadding).toFloat()
            updateTabStyles()
        }

    var isTabSpaceEqual: Boolean
        get() = mTabSpaceEqual
        set(tabSpaceEqual) {
            this.mTabSpaceEqual = tabSpaceEqual
            updateTabStyles()
        }

    var tabWidth: Float
        get() = mTabWidth
        set(tabWidth) {
            this.mTabWidth = dp2px(tabWidth).toFloat()
            updateTabStyles()
        }

    var indicatorColor: Int
        get() = mIndicatorColor
        set(indicatorColor) {
            this.mIndicatorColor = indicatorColor
            invalidate()
        }

    var indicatorHeight: Float
        get() = mIndicatorHeight
        set(indicatorHeight) {
            this.mIndicatorHeight = dp2px(indicatorHeight).toFloat()
            invalidate()
        }

    var indicatorWidth: Float
        get() = mIndicatorWidth
        set(indicatorWidth) {
            this.mIndicatorWidth = dp2px(indicatorWidth).toFloat()
            invalidate()
        }

    var indicatorCornerRadius: Float
        get() = mIndicatorCornerRadius
        set(indicatorCornerRadius) {
            this.mIndicatorCornerRadius = dp2px(indicatorCornerRadius).toFloat()
            invalidate()
        }

    var underlineColor: Int
        get() = mUnderlineColor
        set(underlineColor) {
            this.mUnderlineColor = underlineColor
            invalidate()
        }

    var underlineHeight: Float
        get() = mUnderlineHeight
        set(underlineHeight) {
            this.mUnderlineHeight = dp2px(underlineHeight).toFloat()
            invalidate()
        }

    var dividerColor: Int
        get() = mDividerColor
        set(dividerColor) {
            this.mDividerColor = dividerColor
            invalidate()
        }

    var dividerWidth: Float
        get() = mDividerWidth
        set(dividerWidth) {
            this.mDividerWidth = dp2px(dividerWidth).toFloat()
            invalidate()
        }

    var dividerPadding: Float
        get() = mDividerPadding
        set(dividerPadding) {
            this.mDividerPadding = dp2px(dividerPadding).toFloat()
            invalidate()
        }

    var textsize: Float
        get() = mTextsize
        set(textsize) {
            this.mTextsize = sp2px(textsize).toFloat()
            updateTabStyles()
        }

    var textSelectColor: Int
        get() = mTextSelectColor
        set(textSelectColor) {
            this.mTextSelectColor = textSelectColor
            updateTabStyles()
        }

    var textUnselectColor: Int
        get() = mTextUnselectColor
        set(textUnselectColor) {
            this.mTextUnselectColor = textUnselectColor
            updateTabStyles()
        }

    var textBold: Int
        get() = mTextBold
        set(textBold) {
            this.mTextBold = textBold
            updateTabStyles()
        }

    var isTextAllCaps: Boolean
        get() = mTextAllCaps
        set(textAllCaps) {
            this.mTextAllCaps = textAllCaps
            updateTabStyles()
        }

    //setter and getter

    // show MsgTipView
    private val mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mInitSetMap = SparseArray<Boolean>()

    private var mListener: OnTabSelectListener? = null

    init {
        isFillViewport = true//设置滚动视图是否可以伸缩其内容以填充视口
        setWillNotDraw(false)//重写onDraw方法,需要调用这个方法来清除flag
        clipChildren = false
        clipToPadding = false
        mTabsContainer = LinearLayout(mContext)
        addView(mTabsContainer)

        obtainAttributes(mContext, attrs)

        //get layout_height
        val height = attrs!!.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_height")

        if (height == ViewGroup.LayoutParams.MATCH_PARENT.toString() + "") {
        } else if (height == ViewGroup.LayoutParams.WRAP_CONTENT.toString() + "") {
        } else {
            val systemAttrs = intArrayOf(android.R.attr.layout_height)
            val a = mContext.obtainStyledAttributes(attrs, systemAttrs)
            mHeight = a.getDimensionPixelSize(0, ViewGroup.LayoutParams.WRAP_CONTENT)
            a.recycle()
        }
    }

    private fun obtainAttributes(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.SlidingTabLayout)

        mIndicatorStyle = ta.getInt(R.styleable.SlidingTabLayout_tl_indicator_style, STYLE_NORMAL)
        mIndicatorColor = ta.getColor(
            R.styleable.SlidingTabLayout_tl_indicator_color,
            Color.parseColor(if (mIndicatorStyle == STYLE_BLOCK) "#4B6A87" else "#ffffff")
        )
        mIndicatorHeight = ta.getDimension(
            R.styleable.SlidingTabLayout_tl_indicator_height,
            dp2px((if (mIndicatorStyle == STYLE_TRIANGLE) 4 else if (mIndicatorStyle == STYLE_BLOCK) -1 else 2).toFloat()).toFloat()
        )
        mIndicatorWidth = ta.getDimension(
            R.styleable.SlidingTabLayout_tl_indicator_width,
            dp2px((if (mIndicatorStyle == STYLE_TRIANGLE) 10 else -1).toFloat()).toFloat()
        )
        mIndicatorCornerRadius = ta.getDimension(
            R.styleable.SlidingTabLayout_tl_indicator_corner_radius,
            dp2px((if (mIndicatorStyle == STYLE_BLOCK) -1 else 0).toFloat()).toFloat()
        )
        indicatorMarginLeft =
            ta.getDimension(R.styleable.SlidingTabLayout_tl_indicator_margin_left, dp2px(0f).toFloat())
        indicatorMarginTop = ta.getDimension(
            R.styleable.SlidingTabLayout_tl_indicator_margin_top,
            dp2px((if (mIndicatorStyle == STYLE_BLOCK) 7 else 0).toFloat()).toFloat()
        )
        indicatorMarginRight =
            ta.getDimension(R.styleable.SlidingTabLayout_tl_indicator_margin_right, dp2px(0f).toFloat())
        indicatorMarginBottom = ta.getDimension(
            R.styleable.SlidingTabLayout_tl_indicator_margin_bottom,
            dp2px((if (mIndicatorStyle == STYLE_BLOCK) 7 else 0).toFloat()).toFloat()
        )
        mIndicatorGravity = ta.getInt(R.styleable.SlidingTabLayout_tl_indicator_gravity, Gravity.BOTTOM)
        mIndicatorWidthEqualTitle = ta.getBoolean(R.styleable.SlidingTabLayout_tl_indicator_width_equal_title, false)

        mUnderlineColor = ta.getColor(R.styleable.SlidingTabLayout_tl_underline_color, Color.parseColor("#ffffff"))
        mUnderlineHeight = ta.getDimension(R.styleable.SlidingTabLayout_tl_underline_height, dp2px(0f).toFloat())
        mUnderlineGravity = ta.getInt(R.styleable.SlidingTabLayout_tl_underline_gravity, Gravity.BOTTOM)

        mDividerColor = ta.getColor(R.styleable.SlidingTabLayout_tl_divider_color, Color.parseColor("#ffffff"))
        mDividerWidth = ta.getDimension(R.styleable.SlidingTabLayout_tl_divider_width, dp2px(0f).toFloat())
        mDividerPadding = ta.getDimension(R.styleable.SlidingTabLayout_tl_divider_padding, dp2px(12f).toFloat())

        mTextsize = ta.getDimension(R.styleable.SlidingTabLayout_tl_textsize, sp2px(14f).toFloat())
        mTextSelectColor = ta.getColor(R.styleable.SlidingTabLayout_tl_textSelectColor, Color.parseColor("#ffffff"))
        mTextUnselectColor =
            ta.getColor(R.styleable.SlidingTabLayout_tl_textUnselectColor, Color.parseColor("#AAffffff"))
        mTextBold = ta.getInt(R.styleable.SlidingTabLayout_tl_textBold, TEXT_BOLD_NONE)
        mTextAllCaps = ta.getBoolean(R.styleable.SlidingTabLayout_tl_textAllCaps, false)

        mTabSpaceEqual = ta.getBoolean(R.styleable.SlidingTabLayout_tl_tab_space_equal, false)
        mTabWidth = ta.getDimension(R.styleable.SlidingTabLayout_tl_tab_width, dp2px(-1f).toFloat())
        mTabPadding = ta.getDimension(
            R.styleable.SlidingTabLayout_tl_tab_padding,
            (if (mTabSpaceEqual || mTabWidth > 0) dp2px(0f) else dp2px(20f)).toFloat()
        )

        ta.recycle()
    }

    /** 关联ViewPager  */
    fun setViewPager(vp: ViewPager?) {
        if (vp == null || vp!!.getAdapter() == null) {
            throw IllegalStateException("ViewPager or ViewPager adapter can not be NULL !")
        }

        this.mViewPager = vp

        this.mViewPager!!.removeOnPageChangeListener(this)
        this.mViewPager!!.addOnPageChangeListener(this)
        notifyDataSetChanged()
    }

    /** 关联ViewPager,用于不想在ViewPager适配器中设置titles数据的情况  */
    fun setViewPager(vp: ViewPager?, titles: Array<String>?) {
        if (vp == null || vp!!.getAdapter() == null) {
            throw IllegalStateException("ViewPager or ViewPager adapter can not be NULL !")
        }

        if (titles == null || titles.size == 0) {
            throw IllegalStateException("Titles can not be EMPTY !")
        }

        if (titles.size != vp!!.getAdapter()?.getCount()) {
            throw IllegalStateException("Titles length must be the same as the page count !")
        }

        this.mViewPager = vp
        mTitles = ArrayList()
        Collections.addAll(mTitles, *titles)

        this.mViewPager!!.removeOnPageChangeListener(this)
        this.mViewPager!!.addOnPageChangeListener(this)
        notifyDataSetChanged()
    }

    /** 关联ViewPager,用于连适配器都不想自己实例化的情况  */
    fun setViewPager(vp: ViewPager?, titles: Array<String>?, fa: FragmentActivity, fragments: ArrayList<Fragment>) {
        if (vp == null) {
            throw IllegalStateException("ViewPager can not be NULL !")
        }

        if (titles == null || titles.size == 0) {
            throw IllegalStateException("Titles can not be EMPTY !")
        }

        this.mViewPager = vp
        this.mViewPager!!.setAdapter(InnerPagerAdapter(fa.getSupportFragmentManager(), fragments, titles))

        this.mViewPager!!.removeOnPageChangeListener(this)
        this.mViewPager!!.addOnPageChangeListener(this)
        notifyDataSetChanged()
    }

    /** 更新数据  */
    fun notifyDataSetChanged() {
        mTabsContainer.removeAllViews()
        this.tabCount = if (mTitles == null) mViewPager!!.getAdapter()?.getCount() ?: 0 else mTitles!!.size
        var tabView: View
        for (i in 0 until tabCount) {
            tabView = View.inflate(mContext, R.layout.layout_tab, null)
            val pageTitle = if (mTitles == null) mViewPager!!.getAdapter()?.getPageTitle(i) else mTitles!![i]
            addTab(i, pageTitle?.toString(), tabView)
        }

        updateTabStyles()
    }

    fun addNewTab(title: String) {
        val tabView = View.inflate(mContext, R.layout.layout_tab, null)
        if (mTitles != null) {
            mTitles!!.add(title)
        }

        val pageTitle = if (mTitles == null) mViewPager!!.getAdapter()?.getPageTitle(tabCount) else mTitles!![tabCount]
        addTab(tabCount, pageTitle?.toString(), tabView)
        this.tabCount = if (mTitles == null) mViewPager?.getAdapter()?.getCount() ?: 0 else mTitles?.size ?: 0

        updateTabStyles()
    }

    /** 创建并添加tab  */
    private fun addTab(position: Int, title: String?, tabView: View) {
        val tv_tab_title = tabView.findViewById(R.id.tv_tab_title) as TextView
        if (tv_tab_title != null) {
            if (title != null) tv_tab_title.text = title
        }

        tabView.setOnClickListener { v ->
            val position = mTabsContainer.indexOfChild(v)
            if (position != -1) {
                if (mViewPager!!.getCurrentItem() !== position) {
                    if (mSnapOnTabClick) {
                        mViewPager!!.setCurrentItem(position, false)
                    } else {
                        mViewPager!!.setCurrentItem(position)
                    }

                    if (mListener != null) {
                        mListener!!.onTabSelect(position)
                    }
                } else {
                    if (mListener != null) {
                        mListener!!.onTabReselect(position)
                    }
                }
            }
        }

        /** 每一个Tab的布局参数  */
        var lp_tab = if (mTabSpaceEqual)
            LinearLayout.LayoutParams(0, FrameLayout.LayoutParams.MATCH_PARENT, 1.0f)
        else
            LinearLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT)
        if (mTabWidth > 0) {
            lp_tab = LinearLayout.LayoutParams(mTabWidth.toInt(), FrameLayout.LayoutParams.MATCH_PARENT)
        }

        mTabsContainer.addView(tabView, position, lp_tab)
    }

    private fun updateTabStyles() {
        for (i in 0 until tabCount) {
            val v = mTabsContainer.getChildAt(i)
            //            v.setPadding((int) mTabPadding, v.getPaddingTop(), (int) mTabPadding, v.getPaddingBottom());
            val tv_tab_title = v.findViewById(R.id.tv_tab_title) as TextView
            if (tv_tab_title != null) {
                tv_tab_title.setTextColor(if (i == mCurrentTab) mTextSelectColor else mTextUnselectColor)
                tv_tab_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextsize)
                tv_tab_title.setPadding(mTabPadding.toInt(), 0, mTabPadding.toInt(), 0)
                if (mTextAllCaps) {
                    tv_tab_title.text = tv_tab_title.text.toString().toUpperCase()
                }

                if (mTextBold == TEXT_BOLD_BOTH) {
                    tv_tab_title.paint.isFakeBoldText = true
                } else if (mTextBold == TEXT_BOLD_NONE) {
                    tv_tab_title.paint.isFakeBoldText = false
                }
            }
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        /**
         * position:当前View的位置
         * mCurrentPositionOffset:当前View的偏移量比例.[0,1)
         */
        this.mCurrentTab = position
        this.mCurrentPositionOffset = positionOffset
        scrollToCurrentTab()
        invalidate()
    }

    override fun onPageSelected(position: Int) {
        updateTabSelection(position)
    }

    override fun onPageScrollStateChanged(state: Int) {}

    /** HorizontalScrollView滚到当前tab,并且居中显示  */
    private fun scrollToCurrentTab() {
        if (tabCount <= 0) {
            return
        }

        val offset = (mCurrentPositionOffset * mTabsContainer.getChildAt(mCurrentTab).width).toInt()
        /**当前Tab的left+当前Tab的Width乘以positionOffset */
        var newScrollX = mTabsContainer.getChildAt(mCurrentTab).left + offset

        if (mCurrentTab > 0 || offset > 0) {
            /**HorizontalScrollView移动到当前tab,并居中 */
            newScrollX -= width / 2 - paddingLeft
            calcIndicatorRect()
            newScrollX += (mTabRect.right - mTabRect.left) / 2
        }

        if (newScrollX != mLastScrollX) {
            mLastScrollX = newScrollX
            /** scrollTo（int x,int y）:x,y代表的不是坐标点,而是偏移量
             * x:表示离起始位置的x水平方向的偏移量
             * y:表示离起始位置的y垂直方向的偏移量
             */
            scrollTo(newScrollX, 0)
        }
    }

    private fun updateTabSelection(position: Int) {
        for (i in 0 until tabCount) {
            val tabView = mTabsContainer.getChildAt(i)
            val isSelect = i == position
            val tab_title = tabView.findViewById(R.id.tv_tab_title) as TextView

            if (tab_title != null) {
                tab_title.setTextColor(if (isSelect) mTextSelectColor else mTextUnselectColor)
                if (mTextBold == TEXT_BOLD_WHEN_SELECT) {
                    tab_title.paint.isFakeBoldText = isSelect
                }
            }
        }
    }

    private fun calcIndicatorRect() {
        val currentTabView = mTabsContainer.getChildAt(this.mCurrentTab)
        var left = currentTabView.left.toFloat()
        var right = currentTabView.right.toFloat()

        //for mIndicatorWidthEqualTitle
        if (mIndicatorStyle == STYLE_NORMAL && mIndicatorWidthEqualTitle) {
            val tab_title = currentTabView.findViewById(R.id.tv_tab_title) as TextView
            mTextPaint.textSize = mTextsize
            val textWidth = mTextPaint.measureText(tab_title.text.toString())
            margin = (right - left - textWidth) / 2
        }

        if (this.mCurrentTab < tabCount - 1) {
            val nextTabView = mTabsContainer.getChildAt(this.mCurrentTab + 1)
            val nextTabLeft = nextTabView.left.toFloat()
            val nextTabRight = nextTabView.right.toFloat()

            left = left + mCurrentPositionOffset * (nextTabLeft - left)
            right = right + mCurrentPositionOffset * (nextTabRight - right)

            //for mIndicatorWidthEqualTitle
            if (mIndicatorStyle == STYLE_NORMAL && mIndicatorWidthEqualTitle) {
                val next_tab_title = nextTabView.findViewById(R.id.tv_tab_title) as TextView
                mTextPaint.textSize = mTextsize
                val nextTextWidth = mTextPaint.measureText(next_tab_title.text.toString())
                val nextMargin = (nextTabRight - nextTabLeft - nextTextWidth) / 2
                margin = margin + mCurrentPositionOffset * (nextMargin - margin)
            }
        }

        mIndicatorRect.left = left.toInt()
        mIndicatorRect.right = right.toInt()
        //for mIndicatorWidthEqualTitle
        if (mIndicatorStyle == STYLE_NORMAL && mIndicatorWidthEqualTitle) {
            mIndicatorRect.left = (left + margin - 1).toInt()
            mIndicatorRect.right = (right - margin - 1f).toInt()
        }

        mTabRect.left = left.toInt()
        mTabRect.right = right.toInt()

        if (mIndicatorWidth < 0) {   //indicatorWidth小于0时,原jpardogo's PagerSlidingTabStrip

        } else {//indicatorWidth大于0时,圆角矩形以及三角形
            var indicatorLeft = currentTabView.left + (currentTabView.width - mIndicatorWidth) / 2

            if (this.mCurrentTab < tabCount - 1) {
                val nextTab = mTabsContainer.getChildAt(this.mCurrentTab + 1)
                indicatorLeft = indicatorLeft + mCurrentPositionOffset * (currentTabView.width / 2 + nextTab.width / 2)
            }

            mIndicatorRect.left = indicatorLeft.toInt()
            mIndicatorRect.right = (mIndicatorRect.left + mIndicatorWidth).toInt()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (isInEditMode || tabCount <= 0) {
            return
        }

        val height = height
        val paddingLeft = paddingLeft
        // draw divider
        if (mDividerWidth > 0) {
            mDividerPaint.strokeWidth = mDividerWidth
            mDividerPaint.color = mDividerColor
            for (i in 0 until tabCount - 1) {
                val tab = mTabsContainer.getChildAt(i)
                canvas.drawLine(
                    (paddingLeft + tab.right).toFloat(),
                    mDividerPadding,
                    (paddingLeft + tab.right).toFloat(),
                    height - mDividerPadding,
                    mDividerPaint
                )
            }
        }

        // draw underline
        if (mUnderlineHeight > 0) {
            mRectPaint.color = mUnderlineColor
            if (mUnderlineGravity == Gravity.BOTTOM) {
                canvas.drawRect(
                    paddingLeft.toFloat(),
                    height - mUnderlineHeight,
                    (mTabsContainer.width + paddingLeft).toFloat(),
                    height.toFloat(),
                    mRectPaint
                )
            } else {
                canvas.drawRect(
                    paddingLeft.toFloat(),
                    0f,
                    (mTabsContainer.width + paddingLeft).toFloat(),
                    mUnderlineHeight,
                    mRectPaint
                )
            }
        }

        //draw indicator line

        calcIndicatorRect()
        if (mIndicatorStyle == STYLE_TRIANGLE) {
            if (mIndicatorHeight > 0) {
                mTrianglePaint.color = mIndicatorColor
                mTrianglePath.reset()
                mTrianglePath.moveTo((paddingLeft + mIndicatorRect.left).toFloat(), height.toFloat())
                mTrianglePath.lineTo(
                    (paddingLeft + mIndicatorRect.left / 2 + mIndicatorRect.right / 2).toFloat(),
                    height - mIndicatorHeight
                )
                mTrianglePath.lineTo((paddingLeft + mIndicatorRect.right).toFloat(), height.toFloat())
                mTrianglePath.close()
                canvas.drawPath(mTrianglePath, mTrianglePaint)
            }
        } else if (mIndicatorStyle == STYLE_BLOCK) {
            if (mIndicatorHeight < 0) {
                mIndicatorHeight = height.toFloat() - indicatorMarginTop - indicatorMarginBottom
            } else {

            }

            if (mIndicatorHeight > 0) {
                if (mIndicatorCornerRadius < 0 || mIndicatorCornerRadius > mIndicatorHeight / 2) {
                    mIndicatorCornerRadius = mIndicatorHeight / 2
                }

                mIndicatorDrawable.setColor(mIndicatorColor)
                mIndicatorDrawable.setBounds(
                    paddingLeft + indicatorMarginLeft.toInt() + mIndicatorRect.left,
                    indicatorMarginTop.toInt(), (paddingLeft + mIndicatorRect.right - indicatorMarginRight).toInt(),
                    (indicatorMarginTop + mIndicatorHeight).toInt()
                )
                mIndicatorDrawable.cornerRadius = mIndicatorCornerRadius
                mIndicatorDrawable.draw(canvas)
            }
        } else {
            /* mRectPaint.setColor(mIndicatorColor);
                calcIndicatorRect();
                canvas.drawRect(getPaddingLeft() + mIndicatorRect.left, getHeight() - mIndicatorHeight,
                        mIndicatorRect.right + getPaddingLeft(), getHeight(), mRectPaint);*/

            if (mIndicatorHeight > 0) {
                mIndicatorDrawable.setColor(mIndicatorColor)

                if (mIndicatorGravity == Gravity.BOTTOM) {
                    mIndicatorDrawable.setBounds(
                        paddingLeft + indicatorMarginLeft.toInt() + mIndicatorRect.left,
                        height - mIndicatorHeight.toInt() - indicatorMarginBottom.toInt(),
                        paddingLeft + mIndicatorRect.right - indicatorMarginRight.toInt(),
                        height - indicatorMarginBottom.toInt()
                    )
                } else {
                    mIndicatorDrawable.setBounds(
                        paddingLeft + indicatorMarginLeft.toInt() + mIndicatorRect.left,
                        indicatorMarginTop.toInt(),
                        paddingLeft + mIndicatorRect.right - indicatorMarginRight.toInt(),
                        mIndicatorHeight.toInt() + indicatorMarginTop.toInt()
                    )
                }
                mIndicatorDrawable.cornerRadius = mIndicatorCornerRadius
                mIndicatorDrawable.draw(canvas)
            }
        }
    }

    fun setCurrentTab(currentTab: Int, smoothScroll: Boolean) {
        this.mCurrentTab = currentTab
        mViewPager!!.setCurrentItem(currentTab, smoothScroll)
    }

    fun setIndicatorGravity(indicatorGravity: Int) {
        this.mIndicatorGravity = indicatorGravity
        invalidate()
    }

    fun setIndicatorMargin(
        indicatorMarginLeft: Float, indicatorMarginTop: Float,
        indicatorMarginRight: Float, indicatorMarginBottom: Float
    ) {
        this.indicatorMarginLeft = dp2px(indicatorMarginLeft).toFloat()
        this.indicatorMarginTop = dp2px(indicatorMarginTop).toFloat()
        this.indicatorMarginRight = dp2px(indicatorMarginRight).toFloat()
        this.indicatorMarginBottom = dp2px(indicatorMarginBottom).toFloat()
        invalidate()
    }

    fun setIndicatorWidthEqualTitle(indicatorWidthEqualTitle: Boolean) {
        this.mIndicatorWidthEqualTitle = indicatorWidthEqualTitle
        invalidate()
    }

    fun setUnderlineGravity(underlineGravity: Int) {
        this.mUnderlineGravity = underlineGravity
        invalidate()
    }

    fun setSnapOnTabClick(snapOnTabClick: Boolean) {
        mSnapOnTabClick = snapOnTabClick
    }

    fun getTitleView(tab: Int): TextView {
        val tabView = mTabsContainer.getChildAt(tab)
        return tabView.findViewById(R.id.tv_tab_title) as TextView
    }

    /**
     * 显示未读消息
     *
     * @param position 显示tab位置
     * @param num      num小于等于0显示红点,num大于0显示数字
     */
    fun showMsg(position: Int, num: Int) {
        var position = position
        if (position >= tabCount) {
            position = tabCount - 1
        }

        val tabView = mTabsContainer.getChildAt(position)
        val tipView = tabView.findViewById(R.id.rtv_msg_tip) as MsgView
        if (tipView != null) {
            UnreadMsgUtils.show(tipView, num)

            if (mInitSetMap.get(position) != null && mInitSetMap.get(position)) {
                return
            }

            setMsgMargin(position, 4f, 2f)
            mInitSetMap.put(position, true)
        }
    }

    /**
     * 显示未读红点
     *
     * @param position 显示tab位置
     */
    fun showDot(position: Int) {
        var position = position
        if (position >= tabCount) {
            position = tabCount - 1
        }
        showMsg(position, 0)
    }

    /** 隐藏未读消息  */
    fun hideMsg(position: Int) {
        var position = position
        if (position >= tabCount) {
            position = tabCount - 1
        }

        val tabView = mTabsContainer.getChildAt(position)
        val tipView = tabView.findViewById(R.id.rtv_msg_tip) as MsgView
        if (tipView != null) {
            tipView!!.setVisibility(View.GONE)
        }
    }

    /** 设置未读消息偏移,原点为文字的右上角.当控件高度固定,消息提示位置易控制,显示效果佳  */
    fun setMsgMargin(position: Int, leftPadding: Float, bottomPadding: Float) {
        var position = position
        if (position >= tabCount) {
            position = tabCount - 1
        }
        val tabView = mTabsContainer.getChildAt(position)
        val tipView = tabView.findViewById(R.id.rtv_msg_tip) as MsgView
        if (tipView != null) {
            val tv_tab_title = tabView.findViewById(R.id.tv_tab_title) as TextView
            mTextPaint.textSize = mTextsize
            val textWidth = mTextPaint.measureText(tv_tab_title.text.toString())
            val textHeight = mTextPaint.descent() - mTextPaint.ascent()
            val lp = tipView!!.getLayoutParams() as ViewGroup.MarginLayoutParams
            lp.leftMargin =
                if (mTabWidth >= 0) (mTabWidth / 2 + textWidth / 2 + dp2px(leftPadding).toFloat()).toInt() else (mTabPadding + textWidth + dp2px(
                    leftPadding
                ).toFloat()).toInt()
            lp.topMargin = if (mHeight > 0) (mHeight - textHeight).toInt() / 2 - dp2px(bottomPadding) else 0
            tipView!!.setLayoutParams(lp)
        }
    }

    /** 当前类只提供了少许设置未读消息属性的方法,可以通过该方法获取MsgView对象从而各种设置  */
    fun getMsgView(position: Int): MsgView {
        var position = position
        if (position >= tabCount) {
            position = tabCount - 1
        }
        val tabView = mTabsContainer.getChildAt(position)
        return tabView.findViewById(R.id.rtv_msg_tip) as MsgView
    }

    fun setOnTabSelectListener(listener: OnTabSelectListener) {
        this.mListener = listener
    }

    internal inner class InnerPagerAdapter(
        fm: FragmentManager,
        fragments: ArrayList<Fragment>,
        private val titles: Array<String>
    ) : FragmentPagerAdapter(fm) {

        private var fragments = ArrayList<Fragment>()

        override fun getCount(): Int {
            return fragments.size
        }

        init {
            this.fragments = fragments
        }

        override fun getPageTitle(position: Int): CharSequence {
            return titles[position]
        }

        override fun getItem(position: Int): Fragment {
            return fragments.get(position)
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            // 覆写destroyItem并且空实现,这样每个Fragment中的视图就不会被销毁
            // super.destroyItem(container, position, object);
        }

        override fun getItemPosition(`object`: Any): Int {
            return PagerAdapter.POSITION_NONE
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        val bundle = Bundle()
        bundle.putParcelable("instanceState", super.onSaveInstanceState())
        bundle.putInt("mCurrentTab", mCurrentTab)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var state = state
        if (state is Bundle) {
            val bundle = state as Bundle?
            mCurrentTab = bundle!!.getInt("mCurrentTab")
            state = bundle.getParcelable("instanceState")
            if (mCurrentTab != 0 && mTabsContainer.childCount > 0) {
                updateTabSelection(mCurrentTab)
                scrollToCurrentTab()
            }
        }
        super.onRestoreInstanceState(state)
    }

    protected fun dp2px(dp: Float): Int {
        val scale = mContext.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    protected fun sp2px(sp: Float): Int {
        val scale = this.mContext.resources.displayMetrics.scaledDensity
        return (sp * scale + 0.5f).toInt()
    }

    companion object {
        private val STYLE_NORMAL = 0
        private val STYLE_TRIANGLE = 1
        private val STYLE_BLOCK = 2

        /** title  */
        private val TEXT_BOLD_NONE = 0
        private val TEXT_BOLD_WHEN_SELECT = 1
        private val TEXT_BOLD_BOTH = 2
    }
}
