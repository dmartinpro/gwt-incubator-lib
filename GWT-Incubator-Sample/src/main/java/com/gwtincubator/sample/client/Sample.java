/**
 * 
 */
package com.gwtincubator.sample.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtincubator.security.client.SecuredAsyncCallback;
import com.gwtincubator.security.client.SpringSecurityLoginWidget;
import com.gwtincubator.security.exception.ApplicationSecurityException;

/**
 * @author David MARTIN
 *
 */
public class Sample implements EntryPoint {

	private TestServiceAsync testSvc = GWT.create(TestService.class);

	public void onModuleLoad() {
		final TextBox textBox = new TextBox();
		textBox.setText("AbcDEFg");

		final Button bUser = new Button("Click me User", new ClickHandler() {
			public void onClick(ClickEvent event) {
				testSvc.testUser(textBox.getText(), new SecuredAsyncCallback<String>() {
					public void onSuccess(String modifiedString) {
						Window.alert("Great, success : " + modifiedString);
					}

		            public void onSecurityException(final ApplicationSecurityException exception) {
		                Window.alert("Security exception catched.\n" + exception.getMessage());
		            }

		            public void onOtherException(final Throwable exception) {
		                Window.alert("Unexpected exception while requesting the remote service.\n" + exception);
		            }
				});
			}
		});

		final Button bAdmin = new Button("Click me Admin", new ClickHandler() {
			public void onClick(ClickEvent event) {
				testSvc.testAdmin(textBox.getText(), new SecuredAsyncCallback<String>() {
					public void onSuccess(String modifiedString) {
						Window.alert("Great, success : " + modifiedString);
					}

		            public void onSecurityException(final ApplicationSecurityException exception) {
		                Window.alert("Security exception catched.\n" + exception.getMessage());
		            }

		            public void onOtherException(final Throwable exception) {
		                Window.alert("Unexpected exception while requesting the remote service.\n" + exception);
		            }
				});
			}
		});

		final Widget loginWidget = new SpringSecurityLoginWidget();

		RootPanel.get().add(textBox);
		RootPanel.get().add(bUser);
		RootPanel.get().add(bAdmin);
		RootPanel.get().add(loginWidget);
	}

}
