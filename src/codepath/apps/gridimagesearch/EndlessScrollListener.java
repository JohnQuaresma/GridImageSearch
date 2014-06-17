package codepath.apps.gridimagesearch;

import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public abstract class EndlessScrollListener implements OnScrollListener {
	private int visibleThreshold = 4;
	private int currentPage = 0;
	private int previousTotalItemCount = 0;
	private boolean loading = true;
	private int startingPageIndex = 0;

	public EndlessScrollListener() {
	}

	public EndlessScrollListener(int visibleThreshold) {
		this.visibleThreshold = visibleThreshold;
	}

	public EndlessScrollListener(int visibleThreshold, int startPage) {
		this.visibleThreshold = visibleThreshold;
		this.startingPageIndex = startPage;
		this.currentPage = startPage;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		Log.d("DEBUG", String.format("Triggered onScroll. Page: %s. First visible: %s - Visible count: %s - Total count: %s", this.currentPage, firstVisibleItem, visibleItemCount, totalItemCount));
		
		if (totalItemCount < this.previousTotalItemCount) {
			Log.d("DEBUG", String.format("Reset paging triggered.  Previous count is %s - Total count is %s", this.previousTotalItemCount, totalItemCount));
			this.currentPage = this.startingPageIndex;
			this.previousTotalItemCount = 0;
			if (totalItemCount == 0) {
				this.loading = true; 
			} else {
				this.loading = false;
			}
		}

		if (this.loading && (totalItemCount > this.previousTotalItemCount)) {
			Log.d("DEBUG", String.format("Incrementing current page while loading. Page: %s.  Total: %s - Previous: %s", this.currentPage, totalItemCount, previousTotalItemCount));
			this.loading = false;
			previousTotalItemCount = totalItemCount;
			this.currentPage++;
		}
		
		if (!this.loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + this.visibleThreshold)) {
			Log.d("DEBUG", String.format("Calling to load more. Page: %s.  Total: %s - Visible: %s", this.currentPage, totalItemCount, visibleItemCount));
			onLoadMore(this.currentPage + 1, totalItemCount);
		    this.loading = true;
		}
	}

	public abstract void onLoadMore(int page, int totalItemsCount);

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// Noop
	}
}