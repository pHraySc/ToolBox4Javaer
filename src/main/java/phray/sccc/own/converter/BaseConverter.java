/**
 * pHray_sc
 * Copyright (c) 1970-2022 All Rights Reserved
 */
package phray.sccc.own.converter;

import lombok.Data;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * mapstruct转换器基类
 *
 * @author Phray
 * @Version BaseConverter.java, v 0.1 2022-05-24 7:27 Sccc Exp $
 */
public interface BaseConverter<SOURCE, TARGET> {

	/**
	 * source to target
	 *
	 * @param source source
	 * @return target
	 */
	TARGET source2Target(SOURCE source);

	/**
	 * sourceList to targetList
	 *
	 * @param source sourceList
	 * @return targetList
	 */
	List<TARGET> source2Target(List<SOURCE> source);

	/**
	 * target to source
	 *
	 * @param target target
	 * @return source
	 */
	SOURCE target2Source(TARGET target);

	/**
	 * targetList to sourceList
	 *
	 * @param target targetList
	 * @return sourceList
	 */
	List<SOURCE> target2Source(List<TARGET> target);


	@Data
	class SourceExample {
		private String name;
		private Integer code;
	}

	@Data
	class TargetExample {
		private String name;
		private Integer code;
	}

	@Mapper(componentModel = "spring")
	interface MapstructCaseMapper extends BaseConverter<SourceExample, TargetExample> {}
}
