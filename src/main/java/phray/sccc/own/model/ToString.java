/**
 * pHray_sc
 * Copyright (c) 1970-2022 All Rights Reserved
 */
package phray.sccc.own.model;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * ToString基类
 *
 * @author Phray
 * @Version ToString.java, v 0.1 2022-05-29 21:21 Sccc Exp $
 */
@NoArgsConstructor
public class ToString implements Serializable {

	private static final long serialVersionUID = -8056087594478962317L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
