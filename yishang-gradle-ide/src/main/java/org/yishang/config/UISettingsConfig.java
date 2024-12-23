//package org.yishang;
//
//
//import com.intellij.openapi.application.ApplicationManager;
//import com.intellij.openapi.options.Configurable;
//import com.intellij.openapi.options.ConfigurationException;
//import com.intellij.openapi.util.NlsContexts;
//import org.jetbrains.annotations.Nullable;
//
//import javax.swing.*;
//
//public class UISettingsConfig implements Configurable {
//	private MybatisGenerator form = new MybatisGenerator();
////	private JComponent mainPanel;
//	private UIGenerateState state = ApplicationManager.getApplication().getService(UIGenerateState.class);
////	private final Project project;
////	private UIDemoState state;
//
////	public UISettingsConfig(Project project) {
////		mainPanel = form.getMainPanel();
////		this.project = project;
////		state = project.getService(UIDemoState.class);
////		reset();
////	}
//
//	public UISettingsConfig() {
////		mainPanel = form.getMainPanel();
//		reset();
//		form.getButtonLogin().addActionListener(new MyListenerImpl2(form));
//		// TODO: place custom component creation code here
//
//	}
//
//	@Override
//	public @NlsContexts.ConfigurableName String getDisplayName() {
//		return "UISettingsConfig";
//	}
//
//	@Override
//	public @Nullable JComponent createComponent() {
//		return form.getMainPanel();
//	}
//
//	@Override
//	public boolean isModified() {
//		return true;
//	}
//
//	@Override
//	public void apply() throws ConfigurationException {
//		System.out.println("_1_"+form.getUsernameField().getText());
//		state.setUserName(form.getUsernameField().getText());
//	}
//
//	@Override
//	public void reset() {
//		System.out.println("_3_"+form.getUsernameField().getText());
//		System.out.println("_2_"+state.getUserName());
//		form.getUsernameField().setText(state.getUserName());
//	}
//
//}
