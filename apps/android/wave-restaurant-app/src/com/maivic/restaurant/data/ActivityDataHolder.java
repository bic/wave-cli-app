package com.maivic.restaurant.data;

public interface ActivityDataHolder {
	public OfferContent getOfferContent();
		
	public NotificationRegistry getMessageContent();
	
	public OfferOptionsContent getOfferOptionsContent() ;

	public LocalOfferOptionDistributor getCourseContent(int index) ;
	
	public LocalOfferOptionDistributor getOptionsConfigurationContent(int index);

	
}
