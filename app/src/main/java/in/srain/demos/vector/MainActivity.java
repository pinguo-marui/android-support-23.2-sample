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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mStatusTextView;
    private ImageView mAnimDots1;
    private ImageView mAnimDots2;
    private int mDayNightMode = AppCompatDelegate.MODE_NIGHT_AUTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mStatusTextView = (TextView) findViewById(R.id.status_text_view);
        mAnimDots1 = (ImageView) findViewById(R.id.anim_dots_1);
        mAnimDots2 = (ImageView) findViewById(R.id.anim_dots_2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Drawable drawable1 = mAnimDots1.getDrawable();
            if (drawable1 instanceof Animatable) {
                ((Animatable) drawable1).start();
            }

            Drawable drawable2 = mAnimDots2.getDrawable();
            if (drawable2 instanceof Animatable) {
                ((Animatable) drawable2).start();
            }

            return true;
        }

        return super.onTouchEvent(event);
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
            }catch (ActivityNotFoundException e){
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
