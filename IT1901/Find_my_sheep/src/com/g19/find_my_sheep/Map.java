package com.g19.find_my_sheep;

import java.lang.reflect.Field;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;
import com.g19.find_my_sheep.R;

public class Map extends Activity{
	static final LatLng TRONDHEIM = new LatLng(63.4297, 10.3933);
	private GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);

		map = ((RetainMapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		getOverflowMenu();

	}

	private void getOverflowMenu() {

		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			if(menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item){


		switch (item.getItemId()) {
		case R.id.action_logout:
			LogginStatus.setUserName(this, null);
			Intent intent = new Intent(Map.this, MainActivity.class);
			startActivity(intent);
			finish();

			break;
		case R.id.action_sat:
			map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			break;
		case R.id.action_map:
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			break;
		case R.id.action_ter:
			map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			break;

		case R.id.sheep_menu:
			Intent intent3 = new Intent(this, SheepMenu.class);
			intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent3);
			break;

		case R.id.user_menu:
			Intent intent2 = new Intent(this, UserMenu.class);
			intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent2);
			break;

		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

} 
