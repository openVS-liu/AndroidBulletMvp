package  com.bullet.mvp;

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            titleBar = LayoutInflater.from(getContext()).inflate(if (viewInit!!.titleLayouId != 0) viewInit!!.titleLayouId else R.layout.mvp_titlebar_layout, null) as RelativeLayout
            rootView?.addView(titleBar,FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,getContext().resources.getDimensionPixelSize(R.dimen.mvp_titlebar_height)))
            if (viewInit?.contentViewBlowTitleBar!!) {
                contentLayoutParams!!.topMargin =getContext().resources.getDimensionPixelSize(R.dimen.mvp_titlebar_height)
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


    fun setTitle(title: String?) {
        var textView = titleBar!!.findViewWithTag<TextView>("title")
        if (textView == null) {
            textView = TextView(getContext())
            textView.textSize = 18f
            textView.setTextColor(getContext().resources.getColor(R.color.mvp_title_text_color))
            setTitle(textView)
        }
        textView.setText(title)
    }

    fun addView(resId: Int) {
        val view = LayoutInflater.from(getContext()).inflate(resId, null)
        addView(view)
    }


    fun addView(view: View?) {
        rootView?.addView(view, contentLayoutParams)
    }

    fun setTitle(view: View?) {
        val layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)

        titleBar!!.addView(view, layoutParams)
    }

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

    fun addRightButton(text: String?, onClickListener: View.OnClickListener?): View? {
        val textView = TextView(getContext())
        textView.text = text
        textView.setTextColor(Color.WHITE)
        textView.textSize = 12f
        textView.setOnClickListener(onClickListener)
        return addRightButton(textView)
    }


    fun addLeftButton(view: View?) {
        val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.addRule(RelativeLayout.CENTER_VERTICAL)
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
        params.leftMargin = defaultPadding
        titleBar!!.addView(view, params)
    }

    override fun showLoadingView(text: String?) {
        val loadingView = LayoutInflater.from(getContext()).inflate(R.layout.mvp_loading_progress, null)
        addView(loadingView)
        loadingView.tag = "loading"
    }

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