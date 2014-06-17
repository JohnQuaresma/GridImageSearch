package codepath.apps.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {

	public static final String SETTINGS_PARAM = "settings";
	private static final int SETTINGS_REQUEST = 1;
	private static final String BASE_SEARCH_URL = "https://ajax.googleapis.com/ajax/services/search/images";
	private EditText etQuery;
	private GridView gvResults;
	private Button btnSearch;
	private ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	private ImageResultArrayAdapter imageAdapter;
	private SearchSettings searchSettings;
	private Integer startIndex;
	
	private void setupViews() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
		btnSearch = (Button) findViewById(R.id.btnSearch);
	}
	
	private void addSettingsParams(StringBuilder urlBuffer) {
		String site = searchSettings.getSite();
		if (site != null && !"".equals(site.trim())) {
			urlBuffer.append("&as_sitesearch=" + site.trim());
		}
		ImageSize size = searchSettings.getImageSize();
		if (size != null && !ImageSize.NONE.equals(size)) {
			urlBuffer.append("&imgsz=" + size.toString());
		}
		ImageColor color = searchSettings.getImageColor();
		if (color != null && !ImageColor.NONE.equals(color)) {
			urlBuffer.append("&imgcolor=" + color.toString());
		}
		ImageType type = searchSettings.getImageType();
		if (type != null && !ImageType.NONE.equals(type)) {
			urlBuffer.append("&imgtype=" + type.toString());
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setupViews();
		searchSettings = new SearchSettings();
		startIndex = 0;
		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		gvResults.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position,
					long arg3) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result", imageResult);
				startActivity(i);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == SETTINGS_REQUEST) {
			searchSettings = (SearchSettings) data.getSerializableExtra(SETTINGS_PARAM);
		}
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_simple, menu);
        return true;
    }
	
	public void onSettings(MenuItem mi) {
		Intent i = new Intent(this, SettingsActivity.class);
		i.putExtra(SETTINGS_PARAM, searchSettings);
		startActivityForResult(i, SETTINGS_REQUEST);
	}
	
	public void onImageSearch(View view) {
		String query = etQuery.getText().toString();
		AsyncHttpClient client = new AsyncHttpClient();
		StringBuilder urlBuilder = new StringBuilder(BASE_SEARCH_URL);
		urlBuilder.append("?rsz=8");
		urlBuilder.append("&v=1.0");
		urlBuilder.append("&start=" + startIndex);
		urlBuilder.append("&q=" + Uri.encode(query));
		addSettingsParams(urlBuilder);
		client.get(urlBuilder.toString(), new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONObject response) {
					Log.d("DEBUG", response.toString());
					JSONArray imageJsonResults = null;
					try {
						imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
						imageResults.clear();
						imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
						Log.d("DEBUG", imageResults.toString());
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
		});
	}
}
