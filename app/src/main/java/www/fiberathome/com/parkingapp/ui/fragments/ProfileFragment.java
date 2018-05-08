package www.fiberathome.com.parkingapp.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import www.fiberathome.com.parkingapp.R;
import www.fiberathome.com.parkingapp.model.User;
import www.fiberathome.com.parkingapp.utils.AppConfig;
import www.fiberathome.com.parkingapp.utils.SharedPreManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    private TextView fullnameTV;
    private TextView mobileNoTV;
    private TextView vehicleNoTV;
    private ImageView userProfilePic;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        // intialize
        fullnameTV = view.findViewById(R.id.user_fullName);
        mobileNoTV = view.findViewById(R.id.user_mobile_no);
        vehicleNoTV = view.findViewById(R.id.user_vehicle_no);
        userProfilePic = view.findViewById(R.id.profile_image);

        User user = SharedPreManager.getInstance(getContext()).getUser();
        fullnameTV.setText(user.getFullName());
        mobileNoTV.setText("+88" + user.getMobileNo());
        vehicleNoTV.setText(user.getVehicleNo());

        String url = AppConfig.IMAGES_URL + user.getProfilePic() + ".jpg";
        Log.e("URL",url);
        Glide.with(this).load(url).into(userProfilePic);


        return view;
    }

}
