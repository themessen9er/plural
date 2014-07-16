package com.bacon.mayo.swagger;

import java.util.List;
import java.util.Map;

import com.wordnik.swagger.core.filter.SwaggerSpecFilter;
import com.wordnik.swagger.model.ApiDescription;
import com.wordnik.swagger.model.Operation;
import com.wordnik.swagger.model.Parameter;

public class BaconSwaggerFilter implements SwaggerSpecFilter {

	@Override
	public boolean isOperationAllowed(Operation op, ApiDescription desc,
			Map<String, List<String>> arg2, Map<String, String> arg3,
			Map<String, List<String>> arg4) {
		return true;
	}

	@Override
	public boolean isParamAllowed(Parameter param, Operation arg1,
			ApiDescription arg2, Map<String, List<String>> arg3,
			Map<String, String> arg4, Map<String, List<String>> arg5) {
		
		return !(param.paramAccess().isDefined() && param.paramAccess().get().equals("hidden"));
	}

}
