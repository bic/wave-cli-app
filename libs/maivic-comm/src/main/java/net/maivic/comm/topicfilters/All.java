package net.maivic.comm.topicfilters;

import net.maivic.comm.TopicFilter;
/**
 * Topic filter which always matches
 * @author paul
 *
 */
public class All implements TopicFilter {

	public boolean matches(String topic) {
		return true;
	}
	
}
