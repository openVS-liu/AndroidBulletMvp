package  com.bullet.mvp;
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment(), IView {
    override var viewInit: ViewInit? = null
    override var rootView: ViewGroup? = null
    override var titleBar: RelativeLayout? = null
    override var rightButtonGroup: LinearLayout? = null
    override var defaultPadding: Int = 0
    override var contentLayoutParams: FrameLayout.LayoutParams? = null
    var mContext: Context? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = FrameLayout(context)
            viewInit = javaClass.getAnnotation(ViewInit::class.java)
            initFromAnnotation(viewInit, rootView)
        }
        return rootView
    }

    override fun getContext(): Context {
        return mContext!!
    }

    override fun back(view: View?) {
        activity?.finish()
    }

}