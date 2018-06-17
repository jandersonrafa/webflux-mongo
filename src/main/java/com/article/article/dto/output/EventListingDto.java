package com.article.article.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Janderson
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventListingDto {

	private String eventId;

	private String eventName;

}
