/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.editors;

import java.text.NumberFormat;
import org.springframework.beans.propertyeditors.CustomNumberEditor;

/**
 *
 * @author dazz
 */
public class DecimalEditor extends CustomNumberEditor {

    private NumberFormat numberFormat;

    public DecimalEditor(Class<? extends Number> numberClass, boolean allowEmpty) throws IllegalArgumentException {
        super(numberClass, allowEmpty);
    }

    public DecimalEditor(Class<? extends Number> numberClass, NumberFormat numberFormat, boolean allowEmpty) throws IllegalArgumentException {
        super(numberClass, numberFormat, allowEmpty);
        this.numberFormat = numberFormat;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (numberFormat != null) {
            if (text.contains(".")) {
                text = text.replace(".", ",");
            }
        }
        super.setAsText(text);
    }
}
