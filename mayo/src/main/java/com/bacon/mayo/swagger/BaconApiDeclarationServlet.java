package com.bacon.mayo.swagger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import scala.collection.Iterator;
import scala.collection.JavaConversions;

import com.google.common.base.Function;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.wordnik.swagger.config.ConfigFactory;
import com.wordnik.swagger.config.FilterFactory;
import com.wordnik.swagger.config.SwaggerConfig;
import com.wordnik.swagger.core.filter.SpecFilter;
import com.wordnik.swagger.core.util.JsonSerializer;
import com.wordnik.swagger.model.ApiListing;
import com.wordnik.swagger.model.ApiListingReference;
import com.wordnik.swagger.model.ResourceListing;
import com.wordnik.swagger.servlet.listing.ApiDeclarationServlet;
import com.wordnik.swagger.servlet.listing.ApiListingCache;

public class BaconApiDeclarationServlet extends ApiDeclarationServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void renderResourceListing(HttpServletRequest request,
			HttpServletResponse response) {
		
		String docRoot = "";
		scala.collection.immutable.Map$.MODULE$.empty();

		SpecFilter f = new SpecFilter();

		List<ApiListing> listings = null;
		if(ApiListingCache.listing(docRoot).isDefined()) {
			listings = Lists.newArrayList();
			Iterator<ApiListing> i = ApiListingCache.listing(docRoot).get().valuesIterator();
			while(i.hasNext()) {
				listings.add(
						f.filter(
							i.next(), 
							FilterFactory.filter(), 
							(scala.collection.immutable.Map<String, scala.collection.immutable.List<String>>) (Object) scala.collection.immutable.Map$.MODULE$.empty(),
							(scala.collection.immutable.Map<String, String>) (Object) scala.collection.immutable.Map$.MODULE$.empty(),
							(scala.collection.immutable.Map<String, scala.collection.immutable.List<String>>) (Object) scala.collection.immutable.Map$.MODULE$.empty()));
			}
		}

		List<ApiListingReference> references = null;
		if(listings == null) {
			references = Lists.newArrayList();
		
		} else {
			references = Lists.transform(listings, new Function<ApiListing, ApiListingReference>() {
				@Override
				public ApiListingReference apply(ApiListing input) {
					return new ApiListingReference(input.resourcePath(), input.description(), input.position());
				}
			});
			references = new ArrayList<>(references);
			Collections.sort(references, new Comparator<ApiListingReference>() {

				@Override
				public int compare(ApiListingReference o1,
						ApiListingReference o2) {
					return o1.position() - o2.position();
				}
			});
		}
		
		SwaggerConfig config = ConfigFactory.config();
		ResourceListing resourceListing = new ResourceListing(
				config.apiVersion(),
				config.swaggerVersion(),
				JavaConversions.asScalaBuffer(references).toList(),
				config.getAuthorizations(),
				config.info());
		try {
			response.getOutputStream().write(JsonSerializer.asJson(resourceListing).getBytes("utf-8"));
		} catch (IOException e) {
			Throwables.propagate(e);
		}
	}

}
