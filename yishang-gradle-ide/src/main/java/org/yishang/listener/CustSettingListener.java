//package org.yishang;
//
//import com.intellij.notification.Notification;
//import com.intellij.notification.NotificationAction;
//import com.intellij.notification.NotificationType;
//import com.intellij.notification.Notifications;
//import com.intellij.openapi.actionSystem.AnActionEvent;
//import com.intellij.openapi.application.ApplicationManager;
//import com.intellij.openapi.options.ShowSettingsUtil;
//import com.intellij.openapi.project.Project;
//import com.intellij.openapi.project.ProjectManagerListener;
//import org.jetbrains.annotations.NotNull;
//
//public class CustSettingListener  implements ProjectManagerListener {
//
//	private UIGenerateState state = ApplicationManager.getApplication().getService(UIGenerateState.class);
//
//	@Override
//	public void projectOpened(@NotNull Project project) {
////		ProjectManagerListener.super.projectOpened(project);
//		// 提示进行配置
//		if (state.getUserName() == null || "".equals(state.getUserName())) {
//			Notification notification = new Notification("Attach to Process action", "配置信息", "请配置username", NotificationType.WARNING);
//			// 在提示消息中，增加一个 Action，可以通过 Action 一步打开配置界面
//			notification.addAction(new CustomSettingAction());
//			// 发送通知
//			Notifications.Bus.notify(notification, project);
//		}
//
//	}
//
//	static class CustomSettingAction extends NotificationAction {
//
//		CustomSettingAction() {
//			super("打开配置界面");
//		}
//
//		@Override
//		public void actionPerformed(@NotNull AnActionEvent e, @NotNull Notification notification) {
//			// IntelliJ SDK 提供的一个工具类，可以通过配置项名字，直接显示对应的配置界面
//			ShowSettingsUtil.getInstance().showSettingsDialog(e.getProject(), "UISettingsConfig");
//			notification.expire();
//		}
//	}
//}
//
