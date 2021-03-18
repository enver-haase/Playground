package org.vaadin.enver.ui.utils.converters;

import com.vaadin.flow.templatemodel.ModelEncoder;
import org.vaadin.enver.ui.dataproviders.DataProviderUtil;
import org.vaadin.enver.ui.utils.FormattingUtils;

public class CurrencyFormatter implements ModelEncoder<Integer, String> {

	@Override
	public String encode(Integer modelValue) {
		return DataProviderUtil.convertIfNotNull(modelValue, FormattingUtils::formatAsCurrency);
	}

	@Override
	public Integer decode(String presentationValue) {
		throw new UnsupportedOperationException();
	}
}
