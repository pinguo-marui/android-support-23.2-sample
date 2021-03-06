package in.srain.demos.vector;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mStatusTextView;
    private AppCompatImageView mAnimDots1;
    private AppCompatImageView mAnimDots2;
    private int mDayNightMode = AppCompatDelegate.MODE_NIGHT_AUTO;


    private boolean expand = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mStatusTextView = (TextView) findViewById(R.id.status_text_view);
        mAnimDots1 = (AppCompatImageView) findViewById(R.id.anim_dots_1);
        mAnimDots2 = (AppCompatImageView) findViewById(R.id.anim_dots_2);

        mAnimDots1.setOnClickListener(this);
        mAnimDots2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == mAnimDots1) {
            if (expand) {
                mAnimDots1.setImageResource(R.drawable.animated_shirinkable_menu);
            } else {
                mAnimDots1.setImageResource(R.drawable.animated_expandable_menu_tog);
            }
            ((Animatable) mAnimDots1.getDrawable()).start();
            expand = !expand;
        } else if (v == mAnimDots2) {
            Drawable drawable2 = mAnimDots2.getDrawable();
            if (drawable2 instanceof Animatable) {
                ((Animatable) drawable2).start();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


        int uiMode = getResources().getConfiguration().uiMode;
        int dayNightUiMode = uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (dayNightUiMode == Configuration.UI_MODE_NIGHT_NO) {
            mDayNightMode = AppCompatDelegate.MODE_NIGHT_NO;
            mStatusTextView.setText(R.string.text_for_day_night_mode_night_no);
        } else if (dayNightUiMode == Configuration.UI_MODE_NIGHT_YES) {
            mDayNightMode = AppCompatDelegate.MODE_NIGHT_YES;
            mStatusTextView.setText(R.string.text_for_day_night_mode_night_yes);
        } else {
            mDayNightMode = AppCompatDelegate.MODE_NIGHT_AUTO;
            mStatusTextView.setText(R.string.text_for_day_night_mode_night_auto);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            try {
                String url = "https://github.com/liaohuqiu/android-support-23.2-sample";
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(myIntent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, getApplicationContext().getString(R.string.text_phone_has_no_browser), Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (id == R.id.action_day_night_yes) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            recreate();
            return true;
        } else if (id == R.id.action_day_night_no) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            recreate();
            return true;
        } else {
            if (id == R.id.action_bottom_sheet_dialog) {
                BottomSheetDialogView.show(this, mDayNightMode);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
