package codepath.apps.gridimagesearch;

public enum ImageColor {
	NONE, BLACK, BLUE, BROWN, GRAY, GREEN, ORANGE, PINK, PURPLE, RED, TEAL, WHITE, YELLOW;
	
	public String toString() {
		return this.name().toLowerCase();
	}
}