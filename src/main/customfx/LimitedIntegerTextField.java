package main.customfx;

import javafx.scene.control.TextField;

public class LimitedIntegerTextField extends TextField {

	public LimitedIntegerTextField(int characterLimit, String initialText) {
		super(initialText);
		textProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue.matches("\\d*")) setText(newValue.replaceAll("[^\\d]", ""));
			if(getText().length() > characterLimit) setText(getText().substring(0, characterLimit));
		});
	}

	public int getTextAsInteger() {
		return Integer.parseInt(getText().isEmpty() ? "0" : getText());
	}
}
