package om.example.swapnil.sggsataglance;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by swapnil on 6/11/2016.
 */
public class FragmentPageAdapter extends FragmentPagerAdapter {

    public FragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {

    switch (position)
    {
        case 0:
            return new ProfileFragment();

        case 1:
            return new StaffFragment();
        case 2:
            return new InboxFragment();
        default:
            break;
    }

return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
