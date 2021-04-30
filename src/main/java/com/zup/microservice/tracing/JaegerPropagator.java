package com.zup.microservice.tracing;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import brave.internal.propagation.StringPropagationAdapter;
import brave.propagation.*;
import brave.propagation.TraceContext.Extractor;
import brave.propagation.TraceContext.Injector;
import zipkin2.internal.HexCodec;

@Component
public class JaegerPropagator extends Propagation.Factory implements Propagation<String> {

	@Override
	public List<String> keys() {
		return Arrays.asList("uber-trace-id");
	}

	@Override
	public <R> Injector<R> injector(Setter<R, String> setter) {
		return (traceContext, request) -> {
			String ids = traceContext.traceIdString()+":"+traceContext.spanIdString()+":"+traceContext.traceIdString()+":1";
			setter.put(request, "uber-trace-id", ids);
		};
	}

	@Override
	public <R> Extractor<R> extractor(Getter<R, String> getter) {
		return request -> {
			String traceHeader = getter.get(request, "uber-trace-id");
			if (traceHeader == null || traceHeader.isEmpty() || traceHeader.isBlank())
				return TraceContextOrSamplingFlags.EMPTY;
			
			String[] idStrings = traceHeader.split(":");
			if (idStrings.length != 4)
				return TraceContextOrSamplingFlags.EMPTY;
			
			return TraceContextOrSamplingFlags.create(TraceContext.newBuilder()
					.traceId(HexCodec.lowerHexToUnsignedLong(idStrings[0]))
					.spanId(HexCodec.lowerHexToUnsignedLong(idStrings[1]))
					.build());
		};
	}

	@Override
	public <K> Propagation<K> create(@SuppressWarnings("deprecation") KeyFactory<K> keyFactory) {
		return StringPropagationAdapter.create(this, keyFactory);
	}

}
