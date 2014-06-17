package codepath.apps.gridimagesearch;

public enum ImageType {
	NONE, FACE, PHOTO, CLIPART, LINEART;
	
	public String toString() {
		return this.name().toLowerCase();
	}
}


