package example.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.style.base.BaseToolBarActivity;
import com.style.framework.R;
import com.style.framework.databinding.ActivitySoftMode2Binding;

public class SoftMode2Activity extends BaseToolBarActivity {

    ActivitySoftMode2Binding bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bd = DataBindingUtil.setContentView(this, R.layout.activity_soft_mode_2);
        super.setContentView(bd.getRoot());
        initData();
    }

    @Override
    public void initData() {
        setToolbarTitle("弹出软键盘滚动布局");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
