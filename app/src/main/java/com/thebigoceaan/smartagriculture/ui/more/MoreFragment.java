package com.thebigoceaan.smartagriculture.ui.more;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.thebigoceaan.smartagriculture.databinding.FragmentMoreBinding;
import com.thebigoceaan.smartagriculture.hyperlink.DailyVegMarketActivity;
import com.thebigoceaan.smartagriculture.hyperlink.WeatherInformationActivity;
import com.thebigoceaan.smartagriculture.models.Farmer;
import com.thebigoceaan.smartagriculture.services.news.NewsActivity;
import com.thebigoceaan.smartagriculture.services.products.ProductDashboard;
import com.thebigoceaan.smartagriculture.services.register.FarmerRegisterActivity;

import java.util.Objects;

public class MoreFragment extends Fragment {
    FirebaseDatabase database;
    FirebaseAuth auth;

    private FragmentMoreBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMoreBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //for display drawer layout
        ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        Objects.requireNonNull(mActionBar).setDisplayHomeAsUpEnabled(true);

        binding.krishiNewsImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                startActivity(intent);
            }
        });
        binding.kalimatiVegMarketImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DailyVegMarketActivity.class);
                startActivity(intent);
            }
        });
        binding.weatherInfoImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WeatherInformationActivity.class);
                startActivity(intent);
            }
        });
        binding.imgRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FarmerRegisterActivity.class);
                startActivity(intent);
            }
        });
        binding.productDashboardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProductDashboard.class);
                startActivity(intent);
            }
        });

        //to get farmer database
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        binding.productDashboardBtn.setVisibility(View.GONE);
        binding.registerAsFarmerDesc.setVisibility(View.GONE);
        binding.imgRegisterBtn.setVisibility(View.GONE);
        database.getReference("Farmer").child(auth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Farmer farmer = dataSnapshot.getValue(Farmer.class);
                if(farmer!=null){
                    binding.productDashboardBtn.setVisibility(View.VISIBLE);
                    binding.registerAsFarmerDesc.setVisibility(View.GONE);
                    binding.imgRegisterBtn.setVisibility(View.GONE);
                }
                else{
                    binding.productDashboardBtn.setVisibility(View.GONE);
                    binding.imgRegisterBtn.setVisibility(View.VISIBLE);
                    binding.registerAsFarmerDesc.setVisibility(View.VISIBLE);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}