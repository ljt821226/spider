package com.jszx.spider.platform.expand.swagger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;

import com.jszx.spider.platform.annotation.EntityParam;
import com.jszx.spider.platform.module.entity.BaseEntity;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.jaxrs2.ResolvedParameter;
import io.swagger.v3.jaxrs2.ext.AbstractOpenAPIExtension;
import io.swagger.v3.jaxrs2.ext.OpenAPIExtension;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;

/**
 * EntityParam类型扩展类:扩展swagger的注解类型，使其支持EntityParam注解
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年4月8日 上午9:07:27
 * 
 */

public class EntityExtension extends AbstractOpenAPIExtension {

	@Override
	public ResolvedParameter extractParameters(final List<Annotation> annotations, final Type type, final Set<Type> typesToSkip, final Components components, final Consumes classConsumes, final Consumes methodConsumes, final boolean includeRequestBody, final Iterator<OpenAPIExtension> chain) {

		if (shouldIgnoreType(type, typesToSkip)) {
			return new ResolvedParameter();
		}
		List<Parameter> parameters = annotations.stream().filter(annotation -> annotation instanceof EntityParam).map(
					annotation ->
		{
						EntityParam param = (EntityParam) annotation;
						Parameter mp = new EntityParameter().name(param.value());
						mp.setExample(getJson(type));
						ResolvedSchema resolvedSchema = ModelConverters.getInstance().readAllAsResolvedSchema(type);
						if (resolvedSchema != null) {
							mp.setSchema(resolvedSchema.schema);
						}
						applyBeanValidatorAnnotations(mp, annotations);

						return mp;
					}).collect(Collectors.toList());

		// Only call down to the other items in the chain if no parameters were
		// produced
		if (parameters.isEmpty()) {
			return super.extractParameters(annotations, type, typesToSkip, components, classConsumes, methodConsumes,
						includeRequestBody, chain);
		}

		ResolvedParameter resolved = new ResolvedParameter();
		resolved.parameters = parameters;
		return resolved;
	}

	/**
	 * 
	 * 获取输入样例
	 * 
	 * @param typeName
	 * @return
	 * @author 2724216806@qq.com
	 * @date 2018年4月9日 下午2:59:46
	 */
	private String getJson(Type type) {
		try {
			String typeName = type.getTypeName();
			boolean isArray = false;
			if (typeName.indexOf("array type") > -1) {
				isArray = true;
			}
			int beginIndex = typeName.indexOf("class") + 6;
			int endIndex = typeName.length() - 1;
			if (isArray) {
				endIndex = typeName.length() - 2;
			}
			String className = typeName.substring(beginIndex, endIndex);
			Class<?> cls = Class.forName(className);
			BaseEntity be = (BaseEntity) cls.newInstance();
			String emp = be.toJson();
			if (isArray) {
				emp = "[" + emp + "]";
			}
			return emp;
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			return "{}";
		}
	}

	/**
	 * This is mostly a duplicate of
	 * {@link io.swagger.v3.core.jackson.ModelResolver#applyBeanValidatorAnnotations}.
	 *
	 * @param parameter
	 * @param annotations
	 */
	private void applyBeanValidatorAnnotations(final Parameter parameter, final List<Annotation> annotations) {
		Map<String, Annotation> annos = new HashMap<>();
		if (annotations != null) {
			annotations.forEach(annotation -> {
				annos.put(annotation.annotationType().getName(), annotation);
			});
		}

		if (annos.containsKey(NotNull.class.getName())) {
			parameter.setRequired(true);
		}

		Schema<?> schema = parameter.getSchema();
		if (annos.containsKey(Min.class.getName())) {
			Min min = (Min) annos.get(Min.class.getName());
			schema.setMinimum(BigDecimal.valueOf(min.value()));
		}
		if (annos.containsKey(Max.class.getName())) {
			Max max = (Max) annos.get(Max.class.getName());
			schema.setMaximum(BigDecimal.valueOf(max.value()));
		}
		if (annos.containsKey(Size.class.getName())) {
			Size size = (Size) annos.get(Size.class.getName());

			schema.setMinimum(BigDecimal.valueOf(size.min()));
			schema.setMaximum(BigDecimal.valueOf(size.max()));

			schema.setMinItems(size.min());
			schema.setMaxItems(size.max());
		}
		if (annos.containsKey(DecimalMin.class.getName())) {
			DecimalMin min = (DecimalMin) annos.get(DecimalMin.class.getName());
			if (min.inclusive()) {
				schema.setMinimum(BigDecimal.valueOf(new Double(min.value())));
			} else {
				schema.setExclusiveMinimum(!min.inclusive());
			}
		}
		if (annos.containsKey(DecimalMax.class.getName())) {
			DecimalMax max = (DecimalMax) annos.get(DecimalMax.class.getName());
			if (max.inclusive()) {
				schema.setMaximum(BigDecimal.valueOf(new Double(max.value())));
			} else {
				schema.setExclusiveMaximum(!max.inclusive());
			}
		}
		if (annos.containsKey(Pattern.class.getName())) {
			Pattern pattern = (Pattern) annos.get(Pattern.class.getName());
			schema.setPattern(pattern.regexp());
		}
	}

}
