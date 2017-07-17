package dsm.firealarm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by 10107박소현 on 2017-07-09.
 */

public class SecondFragment extends Fragment {

    public SecondFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_second, container, false);

        return layout;
    }
}