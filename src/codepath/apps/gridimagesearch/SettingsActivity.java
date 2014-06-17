package codepath.apps.gridimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class SettingsActivity extends Activity {

	private Spinner imageSize;
	private Spinner imageColor;
	private Spinner imageType;
	private EditText site;
	private SearchSettings searchSettings;
	
	private Spinner getSpinner(int spinnerId, int arrayResId) {
		Spinner spinner = (Spinner) findViewById(spinnerId);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		       arrayResId, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		return spinner;
	}
	
	private void setupViews() {
		site = (EditText) findViewById(R.id.etSite);
		imageSize = getSpinner(R.id.sImageSize, R.array.images_sizes_array);
		imageColor =  getSpinner(R.id.sImageColor, R.array.images_colors_array);
		imageType =  getSpinner(R.id.sImageType, R.array.images_type_array);
	}
	
	private void setupSpinnerListeners() {
		imageSize.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				searchSettings.setImageSize(ImageSize.values()[pos]);
			}
			 
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				searchSettings.setImageSize(null);
			}
		});
		imageColor.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				searchSettings.setImageColor(ImageColor.values()[pos]);
			}
			 
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				searchSettings.setImageColor(null);
			}
		});
		imageType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				searchSettings.setImageType(ImageType.values()[pos]);
			}
			 
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				searchSettings.setImageType(null);
			}
		});
	}
	
	private void setSpinnerValues() {
		ImageSize imageSizeVal = searchSettings.getImageSize();
		if (imageSizeVal != null) {
			imageSize.setSelection(imageSizeVal.ordinal());
		}
		ImageColor imageColorVal = searchSettings.getImageColor();
		if (imageColorVal != null) {
			imageColor.setSelection(imageColorVal.ordinal());
		}
		ImageType imageTypeVal = searchSettings.getImageType();
		if (imageTypeVal != null) {
			imageType.setSelection(imageTypeVal.ordinal());
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		searchSettings = (SearchSettings) getIntent().getSerializableExtra(SearchActivity.SETTINGS_PARAM);
		setupViews();
		String siteStr = searchSettings.getSite();
		if (siteStr != null && !"".equals(siteStr.trim())) {
			site.setText(siteStr);
		}
		setSpinnerValues();
		setupSpinnerListeners();
	}
	
	public void onSaveSettings(View view) {
		searchSettings.setSite(site.getText().toString());
		Intent i = new Intent();
		i.putExtra(SearchActivity.SETTINGS_PARAM, searchSettings);
		setResult(RESULT_OK, i);
		finish();
	}
	
}
