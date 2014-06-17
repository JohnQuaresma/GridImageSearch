package codepath.apps.gridimagesearch;

import java.io.Serializable;

public class SearchSettings implements Serializable {
	private static final long serialVersionUID = -8724126309208837319L;
	private String site;
	private ImageSize imageSize;
	private ImageColor imageColor;
	private ImageType imageType;
	
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public ImageSize getImageSize() {
		return imageSize;
	}
	public void setImageSize(ImageSize imageSize) {
		this.imageSize = imageSize;
	}
	public ImageColor getImageColor() {
		return imageColor;
	}
	public void setImageColor(ImageColor imageColor) {
		this.imageColor = imageColor;
	}
	public ImageType getImageType() {
		return imageType;
	}
	public void setImageType(ImageType imageType) {
		this.imageType = imageType;
	}
}
