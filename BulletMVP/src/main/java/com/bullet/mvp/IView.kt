package  com.bullet.mvp;

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.*
import android.widget.*
import com.bullet.request.Container
import com.bullet.request.IRequest
import com.example.net.R
import java.util.*


interface IView : Container {
    var viewInit: ViewInit?
    var rootView: ViewGroup?
    var titleBar: RelativeLayout?
    var rightButtonGroup: LinearLayout?
    var defaultPadding: Int
    var contentLayoutParams: FrameLayout.LayoutParams?
    fun initFromAnnotation(viewInit: ViewInit?, rootView: ViewGroup?) { // //         rootView.addView(contentView, getRootViewLayoutParams());
        this.viewInit = viewInit
        this.rootView = rootView
        initTitleBar()
        if (viewInit?.layout != 0) {
            addView(viewInit?.layout!!)
        }

    }

    /**
     * 初始化titlebar
     */
    open fun initTitleBar() {
        contentLayoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        if (viewInit?.showTitleBar!!) {
            titleBar = LayoutInflater.from(getContext()).inflate(if (viewInit!!.titleLayoutId != 0) viewInit!!.titleLayoutId else R.layout.mvp_titlebar_layout, null) as RelativeLayout
            rootView?.addView(titleBar,FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,getContext().resources.getDimensionPixelSize(R.dimen.mvp_titleBar_height)))
            if (viewInit?.contentViewBlowTitleBar!!) {
                contentLayoutParams!!.topMargin =getContext().resources.getDimensionPixelSize(R.dimen.mvp_titleBar_height)
            }
            if (!TextUtils.isEmpty(viewInit!!.title)) {
                setTitle(viewInit!!.title)
            }
            if (viewInit!!.showBackButton) {
                addDefaultBackButton()
            }
        }

    }

    fun addDefaultBackButton() {
        val imageView = ImageView(getContext())
        imageView.setImageResource(R.mipmap.mvp_icon_back)
        imageView.setPadding(0, 0, 30, 0)
        addLeftButton(imageView)
        imageView.setOnClickListener { view: View? -> back(view) }
    }

    /**
     * 设置显示在titleBar上面的标题
     */
    fun setTitle(title: String?) {
        var textView = titleBar!!.findViewWithTag<TextView>("title")
        if (textView == null) {
            textView = TextView(getContext())
            val windowManager = getContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
            var textSize=16.0f
            if (windowManager != null) {
                val outMetrics = DisplayMetrics()
                windowManager.defaultDisplay.getMetrics(outMetrics)
                val density = outMetrics.density
                textSize=getContext().resources.getDimension(R.dimen.mvp_title_size)/density
            }
            textView.textSize =textSize
            textView.setTextColor(getContext().resources.getColor(R.color.mvp_title_text_color))
            setTitle(textView)
        }
        textView.setText(title)
    }

    /**
     *
     * 添加id=layoutId的布局文件到当前页面，覆盖在页面最顶层。默认的layoutParams会判断页面是否有titleBar，有过有则显示在titleBar
     * 底部，如果没有则全屏显示。
    */

    fun addView(layoutId: Int,layoutParams:FrameLayout.LayoutParams?=contentLayoutParams) {
        val view = LayoutInflater.from(getContext()).inflate(layoutId, null)
        addView(view,layoutParams)
    }

    /**
     * 添加view，覆盖在页面最顶层。默认的layoutParams会判断页面是否有titleBar，有过有则显示在titleBar
     * 底部，如果没有则全屏显示。
     */
    fun addView(view: View?,layoutParams:FrameLayout.LayoutParams?=contentLayoutParams) {
        rootView?.addView(view, layoutParams)
    }
    /**
     * 把view当做标题在titleBar上面
     */
    fun setTitle(view: View?) {
        val layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)
        titleBar!!.addView(view, layoutParams)
    }

    /**
     * 在titleBar右上角显示的view，可以添加多个。会横向排列
     */
    fun addRightButton(view: View?): View? {
        if (titleBar != null) {
            if (rightButtonGroup == null) {
                rightButtonGroup = LinearLayout(getContext())
                rightButtonGroup!!.gravity = Gravity.CENTER_VERTICAL or Gravity.RIGHT
                val layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
                layoutParams.addRule(RelativeLayout.CENTER_VERTICAL)
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                rightButtonGroup!!.layoutParams = layoutParams
                titleBar!!.addView(rightButtonGroup, layoutParams)
            }
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            params.rightMargin = getContext().resources.getDimensionPixelSize(R.dimen.mvp_default_padding)
            rightButtonGroup!!.addView(view, params)
            return view
        }
        return null
    }
    /**
     * 在titleBar右上角显示的文字，可以添加多个。会横向排列
     * text 显示的文字
     * onClickListener 文本点击的回调
     */
    fun addRightButton(text: String?, onClickListener: View.OnClickListener?): View? {
        val textView = TextView(getContext())
        textView.text = text
        textView.setTextColor(getContext().resources.getColor(R.color.mvp_title_bar_right_button_color))
        textView.textSize = getContext().resources.getDimension(R.dimen.mvp_title_bar_right_button_text_size)
        textView.setOnClickListener(onClickListener)
        return addRightButton(textView)
    }

    /**
     * 把view添加到titleBar左上角，替换默认的返回按钮
     */
    fun addLeftButton(view: View?) {
        val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.addRule(RelativeLayout.CENTER_VERTICAL)
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
        params.leftMargin = defaultPadding
        titleBar!!.addView(view, params)
    }
    /**
     * 显示加载框
     * text 加载文案
     */
    override fun showLoadingView(text: String?) {
        val loadingView = LayoutInflater.from(getContext()).inflate(R.layout.mvp_loading_progress, null)
        if (!text.isNullOrBlank()){
            val loadingTextView=loadingView.findViewById<TextView>(R.id.loading_text)
            loadingTextView.text=text
        }
        addView(loadingView)
        loadingView.tag = "loading"
    }
    /**
     * 关闭加载框
     */
    override fun closeLoadingView() {
        val loadingView = rootView?.findViewWithTag<View>("loading")
        if (loadingView != null) {
            rootView?.removeView(loadingView)
        }
    }


    override fun onBreachAgreement(code: Int, msg: String?, client: IRequest?) {
        val builder = AlertDialog.Builder(getContext())
        builder.setTitle("提示")
        builder.setMessage(msg)
        builder.setPositiveButton("确定") { dialog: DialogInterface, which: Int -> dialog.dismiss() }
        builder.create().show()
    }

    override fun onError(msg: String?, client: IRequest) {
        var errorView = rootView?.findViewWithTag<View>("error")
        if (errorView == null) {
            errorView = LayoutInflater.from(getContext()).inflate(R.layout.mvp_net_err, null)
            errorView.tag = "error"
            addView(errorView)
        }
        var tag = errorView!!.getTag(R.layout.mvp_net_err);
        var list: MutableList<IRequest>? = null
        if (tag != null) {
            var list = tag as MutableList<IRequest>
        } else {
            list = mutableListOf<IRequest>()
            errorView.setTag(R.layout.mvp_net_err, list)


        }


        list?.add(client)
//
        //
        errorView.findViewById<View>(R.id.mvp_err_image).setOnClickListener { v: View? -> retry() }
    }

    private fun retry() {
        val errorView = rootView?.findViewWithTag<View>("error")
        val list = errorView?.getTag(R.layout.mvp_net_err) as ArrayList<IRequest>
        if (list != null) {
            for (client in list) {
                client.sendRequest()
            }
        }
        list.clear()
        rootView?.removeView(errorView)
    }

    override fun onFinish() {
    }

    fun back(view: View?)

}