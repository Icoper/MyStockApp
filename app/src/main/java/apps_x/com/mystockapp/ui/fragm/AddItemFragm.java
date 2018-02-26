package apps_x.com.mystockapp.ui.fragm;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import apps_x.com.mystockapp.R;

/**
 * Created by hp on 24.01.2018.
 */

public class AddItemFragm extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_item, container, false);
        return v;
    }
}
