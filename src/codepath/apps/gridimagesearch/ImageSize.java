package codepath.apps.gridimagesearch;

public enum ImageSize {
	NONE, ICON, MEDIUM, XXLARGE, HUGE;
	
	public String toString() {
		return this.name().toLowerCase();
	}
}
