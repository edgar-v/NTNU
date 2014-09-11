package com.g19.find_my_sheep;

import android.os.Bundle;

import com.google.android.gms.maps.MapFragment;

public class RetainMapFragment extends MapFragment{
	
	@Override
	 public void onActivityCreated(Bundle savedInstanceState) {
	  super.onActivityCreated(savedInstanceState);
	  setRetainInstance(true);
	 }

}
