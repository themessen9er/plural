package com.bacon.mayo.ws;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Provider
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class GsonJsonProvider implements MessageBodyReader<Object>, MessageBodyWriter<Object> {

	private static final String ENCODING = "UTF-8";
	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	private static Gson gson;

	public static Gson getGson() {
		if (gson == null) {
			final GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(DATE_FORMAT);
			gson = gsonBuilder.create();
		}
		return gson;
	}

	@Override
	public boolean isReadable(Class<?> type, Type genericType,
			java.lang.annotation.Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@Override
	public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
		InputStreamReader streamReader = new InputStreamReader(entityStream, ENCODING);
		try {
			Type jsonType = type.equals(genericType) ? type : genericType;
			return getGson().fromJson(streamReader, jsonType);
		} finally {
			streamReader.close();
		}
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@Override
	public long getSize(Object object, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	@Override
	public void writeTo(Object object, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
		OutputStreamWriter writer = new OutputStreamWriter(entityStream, ENCODING);
		try {
			Type jsonType = type.equals(genericType) ? type : genericType;
			getGson().toJson(object, jsonType, writer);
		} finally {
			writer.close();
		}
	}
}
