package org.yishang.listener;

import git4idea.push.GitPushListener;
import git4idea.push.GitPushRepoResult;
import git4idea.repo.GitRepository;
import org.jetbrains.annotations.NotNull;
import org.yishang.StatisticsData;
import org.yishang.util.SingletonUtil;

public class GitListener implements GitPushListener {

	@Override
	public void onCompleted(@NotNull GitRepository gitRepository, @NotNull GitPushRepoResult gitPushRepoResult) {
		StatisticsData statisticsData = SingletonUtil.getInstance();
		statisticsData.getPushCount().incrementAndGet();
	}
}
