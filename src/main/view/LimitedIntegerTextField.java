package main.view;

import javafx.scene.control.TextField;

public class LimitedIntegerTextField extends TextField {

	public LimitedIntegerTextField(int characterLimit) {
		this(characterLimit, "");
	}

	public LimitedIntegerTextField(int characterLimit, String initialText) {
		super(initialText);
		textProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue.matches("\\d*")) setText(newValue.replaceAll("[^\\d]", ""));
			if(getText().length() > characterLimit) setText(getText().substring(0, characterLimit));
		});
	}
}
