package org.dayatang.vaadin.client;

import org.dayatang.vaadin.NewComponent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;

@Connect(NewComponent.class)
public class NewComponentConnector extends AbstractComponentConnector {
    
    public NewComponentConnector() {
    }
    
    @Override
    protected Widget createWidget() {
        return GWT.create(Label.class);
    }
    
    @Override
    public Label getWidget() {
        return (Label) super.getWidget();
    }
}
