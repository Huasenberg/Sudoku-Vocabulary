package ca.cmpt276theta.sudokuvocabulary.view;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import ca.cmpt276theta.sudokuvocabulary.R;

public class GamePopupWindowView extends PopupWindow {
    private Window mWindow;
    private View mParentView;

    public GamePopupWindowView(View contentView, View parentView, int width, int height, Window window) {
        super(contentView, width, height);
        mParentView = parentView;
        mWindow = window;
        setFocusable(true);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        setActivityBackGroundAlpha(1);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        setAnimationStyle(R.style.pop_animation);
        setActivityBackGroundAlpha(0.3f);
        super.showAtLocation(parent, gravity, x, y);
    }

    private void setActivityBackGroundAlpha(final float num) {
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        lp.alpha = num;
        mWindow.setAttributes(lp);
    }
}
