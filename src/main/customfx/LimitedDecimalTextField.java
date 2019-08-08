package main.customfx;

import javafx.scene.control.TextField;

public class LimitedDecimalTextField extends TextField {

	public LimitedDecimalTextField(int characterLimitInteger, int characterLimitDecimal, String initialText) {
		super(initialText);
		textProperty().addListener((observable, oldValue, newValue) -> {
			if(oldValue.contains(".") && newValue.split("\\.", -1).length - 1 >= 2) {
				setText(oldValue);
			}
			if(newValue.contains(".")) {
				final String[] split = newValue.split("\\.", 2);
				String newIntegerText = split[0];
				String newDecimalText = split[1];

				if(!newIntegerText.matches("\\d*")) newIntegerText = newIntegerText.replaceAll("[^\\d]", "");
				if(!newDecimalText.matches("\\d*")) newDecimalText = newDecimalText.replaceAll("[^\\d]", "");

				if(newIntegerText.length() > characterLimitInteger) newIntegerText = newIntegerText.substring(0, characterLimitInteger);
				if(newDecimalText.length() > characterLimitDecimal) newDecimalText = newDecimalText.substring(0, characterLimitDecimal);

				setText(newIntegerText + "." + newDecimalText);
			} else {
				if(!newValue.matches("\\d*")) setText(newValue.replaceAll("[^\\d]", ""));
				if(getText().length() > characterLimitInteger) setText(getText().substring(0, characterLimitInteger));
			}
		});
	}

	public double getTextAsDouble() {
		return Double.parseDouble(getText().isEmpty() ? "0" : getText());
	}
}
